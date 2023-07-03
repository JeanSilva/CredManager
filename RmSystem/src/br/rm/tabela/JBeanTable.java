/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.tabela;


import java.awt.Dimension;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 *
 * @author camaleaoti_2
 */
public class JBeanTable extends JTable {

    protected Class classeDoBean;
    protected ArrayList beanArrayList;
    protected ArrayList<HashMap> tmpArrayList;
    private ArrayList<JBeanTableColumn> jBeanTableColumns;
    private ArrayList<JBeanTableListener> jBeanTableListeners;
    private Boolean ordena;
    private static boolean TABLE_CHANGED_BLOQUEADO = false;

    @SuppressWarnings("unchecked")
    public JBeanTable(Class classeDoBean, ArrayList<JBeanTableColumn> jBeanTableColumns, Boolean usaSelecao) throws InstantiationException, IllegalAccessException {
      this.initComponent();
      
      Dimension dim = new Dimension(10,1);
      setIntercellSpacing(new Dimension(dim));
      
      this.jBeanTableListeners = new ArrayList();
      this.classeDoBean = classeDoBean;
      this.beanArrayList = new ArrayList();
      this.tmpArrayList = new ArrayList();
      this.ordena = true;
      this.jBeanTableColumns = jBeanTableColumns;
      if(usaSelecao){
        this.inserirColunaDeSelecao();
      }
      setAutoResizeMode(JBeanTable.AUTO_RESIZE_OFF);
      configurarColunasDaTabela();
      configurarLinhasDaTabela();
    }
    
    @SuppressWarnings("unchecked")
    public JBeanTable(Class classeDoBean, ArrayList<JBeanTableColumn> jBeanTableColumns) throws InstantiationException, IllegalAccessException {
        this.initComponent();
        
        Dimension dim = new Dimension(10,1);
        setIntercellSpacing(new Dimension(dim));
        
        this.jBeanTableListeners = new ArrayList();
        this.classeDoBean = classeDoBean;
        this.beanArrayList = new ArrayList();
        this.tmpArrayList = new ArrayList();
        this.ordena = true;
        this.jBeanTableColumns = jBeanTableColumns;
        this.inserirColunaDeSelecao();
        this.setAutoResizeMode(JBeanTable.AUTO_RESIZE_OFF);
        this.configurarColunasDaTabela();
        this.configurarLinhasDaTabela();
    }

    private void initComponent() {
        this.setCellSelectionEnabled(true);
        this.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                JBeanTable.this.jBeanTableChanged(e);
            }
        });

        this.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JBeanTable.this.ordena) {
                    int i = JBeanTable.this.getTableHeader().columnAtPoint(e.getPoint());
                    String chave = (String) JBeanTable.this.getColumnModel().getColumn(i).getIdentifier();
                    JBeanTable.this.ordenar(chave);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int coordX = e.getX();
                int indiceDaColuna = JBeanTable.this.getColumnModel().getColumnIndexAtX(coordX);

                if (indiceDaColuna > -1) {
                    if(JBeanTable.this.obterColuna(JBeanTable.this.getColumnModel()
                            .getColumn(indiceDaColuna)
                            .getIdentifier().toString())
                            .getToolTipText()!=null){
                        String toolTipText = JBeanTable.this.obterColuna(JBeanTable.this.getColumnModel()
                            .getColumn(indiceDaColuna)
                            .getIdentifier().toString())
                            .getToolTipText();

                    JBeanTable.this.getTableHeader().setToolTipText(toolTipText);
                    }
                    
                }

                super.mouseMoved(e);
            }
        });

        this.getTableHeader().setComponentPopupMenu(this.obterHeaderPopupMenu());
    }

    private void jBeanTableChanged(TableModelEvent e) {
        if (!JBeanTable.TABLE_CHANGED_BLOQUEADO) {
            try {
                JBeanTable.TABLE_CHANGED_BLOQUEADO = true;
                if (this.beanArrayList != null) {
                    if ((this.beanArrayList.size() > 0) && (e.getFirstRow() > -1)) {
                        Object beanAnterior = this.beanArrayList.get(e.getFirstRow());

                        if (this.ehColunaForaDoModelo(e.getColumn())) {
                            this.sincronizarPropriedade(e.getFirstRow(), e.getColumn());
                        } else {
                            this.sincronizarAtributo(e.getFirstRow(), e.getColumn(), this.beanArrayList.get(e.getFirstRow()));
                        }

                        this.fireBeanAtualizado(beanArrayList.get(e.getFirstRow()), beanAnterior, e.getFirstRow(), e.getColumn());
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
            }
        }
    }

    private JPopupMenu obterHeaderPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        JMenu jMenuSelecao = new JMenu("Seleção");
        JMenuItem jMenuItemMarcarTodos = new JMenuItem("Marcar Todos");
        jMenuItemMarcarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JBeanTable.TABLE_CHANGED_BLOQUEADO = true;

                    JBeanTable.this.selecionar(true);
                } catch (Exception ex) {
                    Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
                }
            }
        });
        jMenuSelecao.add(jMenuItemMarcarTodos);

        JMenuItem jMenuItemDesmarcarTodos = new JMenuItem("Desmarcar Todos");
        jMenuItemDesmarcarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JBeanTable.TABLE_CHANGED_BLOQUEADO = true;

                    JBeanTable.this.selecionar(false);
                } catch (Exception ex) {
                    Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
                }
            }
        });
        jMenuSelecao.add(jMenuItemDesmarcarTodos);

        jPopupMenu.add(jMenuSelecao);

        return jPopupMenu;
    }

    private boolean ehColunaForaDoModelo(int coluna) {
        return (this.jBeanTableColumns.get(coluna).getAcesso().startsWith("."));
    }

    private void inserirColunaDeSelecao() {
        this.jBeanTableColumns.add(0, new JBeanTableColumn(".selecao", "Sel.", true, true, 30, JCheckBoxTableCellEditor.class, JCheckBoxTableCellRenderer.class, null)
                .setComparatorInstance(this.obterComparadorDeSelecao()));
    }
    
    private void configurarColunasDaTabela() throws InstantiationException, IllegalAccessException {
        adicionarColunasNoModel();
        adicionarColunasNaView();
    }

    protected void adicionarColunasNoModel() {
        if (this.jBeanTableColumns.size() > 0) {
            for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
                JBeanTableColumn jBeanTableColumn = this.jBeanTableColumns.get(i);
                //Configuração do modelo de dados da coluna na tabela.
                if(jBeanTableColumn.isVisivel())
                    ((DefaultTableModel) this.getModel()).addColumn(jBeanTableColumn.getTitulo());
            }
        }
    }

    protected void adicionarColunasNaView() throws InstantiationException, IllegalAccessException {
        if (this.jBeanTableColumns.size() > 0) {
            for (int i = 0; i < this.getModel().getColumnCount(); i++) {
                JBeanTableColumn jBeanTableColumn = this.jBeanTableColumns.get(i);
                 
                 
                if(jBeanTableColumn.isVisivel()){
                    int indiceDaColuna = this.obterIndiceDaColuna(jBeanTableColumn.getAcesso());

                    TableColumn tableColumn = this.getColumnModel().getColumn(indiceDaColuna);
                    tableColumn.setIdentifier(jBeanTableColumn.getAcesso());
                    //Visibilidade da Coluna
                    this.setVisibilidadeDaColuna(tableColumn, jBeanTableColumn.isVisivel());
                    //Configuração do Rótulo da Coluna
                    tableColumn.setHeaderRenderer((TableCellRenderer) jBeanTableColumn.getHeaderRenderer().newInstance());
                    //Configuração do Renderizador do corpo da Coluna
                    if (jBeanTableColumn.getCellRendererInstance() == null) {
                        tableColumn.setCellRenderer((TableCellRenderer) jBeanTableColumn.getCellRenderer().newInstance());
                    } else {
                        tableColumn.setCellRenderer(jBeanTableColumn.getCellRendererInstance());
                    }
                    //Configuração do Editor da Coluna
                    if (jBeanTableColumn.getCellEditorInstance() == null) {
                        tableColumn.setCellEditor((TableCellEditor) jBeanTableColumn.getCellEditorClass().newInstance());
                    } else {
                        tableColumn.setCellEditor(jBeanTableColumn.getCellEditorInstance());
                    }
                    //Configuração da largura da coluna
                    if (jBeanTableColumn.getLargura() != null) {
                        tableColumn.setPreferredWidth(jBeanTableColumn.getLargura());
                    }
                }
            }
        }
    }

    private void configurarLinhasDaTabela() {
        this.setRowHeight(18);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return this.jBeanTableColumns.get(column).isEditavel();
    }

    private void setVisibilidadeDaColuna(TableColumn tableColumn, boolean visivel) {
        if (!visivel) {
            tableColumn.setMaxWidth(0);
            tableColumn.setMinWidth(0);
            tableColumn.setPreferredWidth(0);
        }
    }

    public void adicionarColuna(JBeanTableColumn jBeanTableColumn) throws InstantiationException, IllegalAccessException {
        this.jBeanTableColumns.add(jBeanTableColumn);

        ((DefaultTableModel) this.getModel()).addColumn(jBeanTableColumn.getTitulo());

        this.adicionarColunasNaView();
    }

    public void removerColuna(String chave) throws InstantiationException, IllegalAccessException {
        this.getColumnModel().removeColumn(this.getColumnModel().getColumn(this.obterIndiceDaColuna(chave)));
        this.repaint();
        this.validate();
        Vector<Vector> dataModel = ((DefaultTableModel) this.getModel()).getDataVector();
        for (int i = 0; i < dataModel.size(); i++) {
            Vector vector = dataModel.get(i);
            HashMap linhaForaDoModelo = this.tmpArrayList.get(i);
            vector.remove(this.obterIndiceDaColuna(chave));
            linhaForaDoModelo.remove(chave);
        }

        this.jBeanTableColumns.remove(this.obterIndiceDaColuna(chave));
    }

    public void addJBeanTableListener(JBeanTableListener jBeanTableListener) {
        this.jBeanTableListeners.add(jBeanTableListener);
    }

    public void fireBeanAdicionado(Object bean) {
        if (this.jBeanTableListeners.size() > 0) {
            JBeanTableEvent jBeanTableEvent = new JBeanTableEvent(this, bean, this.getRowCount() - 1, -1, "");

            for (Iterator<JBeanTableListener> it = this.jBeanTableListeners.iterator(); it.hasNext();) {
                JBeanTableListener jBeanTableListener = it.next();
                jBeanTableListener.beanAdicionado(jBeanTableEvent);
            }
        }
    }

    public void fireBeanAtualizado(Object bean, Object beanAnterior, int linha, int coluna) {
        if (this.jBeanTableListeners.size() > 0) {
            String acesso = (coluna > -1 ? this.jBeanTableColumns.get(coluna).getAcesso() : "");
            JBeanTableEvent jBeanTableEvent = new JBeanTableEvent(this, bean, linha, coluna, acesso);

            for (Iterator<JBeanTableListener> it = this.jBeanTableListeners.iterator(); it.hasNext();) {
                JBeanTableListener jBeanTableListener = it.next();
                jBeanTableListener.beanAtualizado(jBeanTableEvent);
            }
        }
    }

    public void fireBeanEmRemocao(Object bean, int linha, int coluna) {
        if (this.jBeanTableListeners.size() > 0) {
            String acesso = (coluna > -1 ? this.jBeanTableColumns.get(coluna).getAcesso() : "");
            JBeanTableEvent jBeanTableEvent = new JBeanTableEvent(this, bean, linha, coluna, acesso);

            for (Iterator<JBeanTableListener> it = this.jBeanTableListeners.iterator(); it.hasNext();) {
                JBeanTableListener jBeanTableListener = it.next();
                jBeanTableListener.beanEmRemocao(jBeanTableEvent);
            }
        }
    }

    public void fireBeanRemovido(Object bean, int linha, int coluna) {
        if (this.jBeanTableListeners.size() > 0) {
            String acesso = (coluna > -1 ? this.jBeanTableColumns.get(coluna).getAcesso() : "");
            JBeanTableEvent jBeanTableEvent = new JBeanTableEvent(this, bean, linha, coluna, acesso);

            for (Iterator<JBeanTableListener> it = this.jBeanTableListeners.iterator(); it.hasNext();) {
                JBeanTableListener jBeanTableListener = it.next();
                jBeanTableListener.beanRemovido(jBeanTableEvent);
            }
        }
    }

    public ArrayList getModeloDeDados() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (this.getModel().getRowCount() > 0) {
            ArrayList v = new ArrayList();

            for (int i = 0; i < this.getModel().getRowCount(); i++) {
                v.add(this.obterBean(i));
            }

            return v;
        } else {
            return new ArrayList();
        }
    }

    public void setModeloDeDados(ArrayList v) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (v != null) {
            //ArrayList arrayTmp = this.obterDadosForaDoModelo();
            this.atribuirBeans(v);

            //this.atribuirDadosForaDoModelo(arrayTmp);
        } else {
            this.beanArrayList = new ArrayList();
            this.tmpArrayList = new ArrayList();
            ((DefaultTableModel) this.getModel()).setRowCount(0);
        }
    }

    public Object obterBean(int linha) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        this.sincronizarModel(linha, this.beanArrayList.get(linha));

        return this.beanArrayList.get(linha);
    }

    public Object obterValorForaDoModelo(String chave, int linha) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        this.sincronizarPropriedade(linha, this.obterIndiceDaColuna(chave));

        return this.tmpArrayList.get(linha).get(chave);
    }

    public int obterLinha(Object bean) {
        return this.beanArrayList.indexOf(bean);
    }

    public final int obterIndiceDaColuna(String chave) {
        for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
            if (this.jBeanTableColumns.get(i).getAcesso().equals(chave)) {
                return i;
            }
        }

        return -1;
    }

    private JBeanTableColumn obterColuna(String chave) {
        for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
            if (this.jBeanTableColumns.get(i).getAcesso().equals(chave)) {
                return this.jBeanTableColumns.get(i);
            }
        }

        return null;
    }

    public Object obterBeanNaoSincronizado(int linha) {
        return this.beanArrayList.get(linha);
    }

    public void atualizarBean(Object bean, int linha) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        Object beanAnterior = this.beanArrayList.get(linha);

        this.beanArrayList.set(linha, bean);

        this.sincronizarView(linha, this.beanArrayList.get(linha));

        this.fireBeanAtualizado(this.beanArrayList.get(linha), beanAnterior, linha, -1);
    }

    public void atualizarCelulaForaDoModelo(String chave, Integer linha, Object valor) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if(this.tmpArrayList.size()>0){
            if(this.jBeanTableColumns.get(this.obterIndiceDaColuna(chave)).isVisivel()){
                this.tmpArrayList.get(linha).put(chave, valor);
                this.sincronizarCelulaForaDoModelo(linha, this.obterIndiceDaColuna(chave), valor);
            }
        }
    }

    public void removerBean(int linha) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        JBeanTable.TABLE_CHANGED_BLOQUEADO = true;
        ((DefaultTableModel) this.getModel()).removeRow(linha);

        Object beanRemovido = this.beanArrayList.remove(linha);
        Object obj = this.tmpArrayList.remove(linha);

        if (beanRemovido != null) {
            this.fireBeanRemovido(beanRemovido, linha, 0);
        }

        JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
    }

    public void removerBean(Object bean) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        JBeanTable.TABLE_CHANGED_BLOQUEADO = true;
        ((DefaultTableModel) this.getModel()).removeRow(this.beanArrayList.indexOf(bean));

        int linhaASerRemovida = this.beanArrayList.indexOf(bean);

        if (this.beanArrayList.remove(bean)) {
            this.fireBeanRemovido(bean, linhaASerRemovida, 0);
        }

        JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
    }

    public void removerBeansSelecionados() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        for (int i = 0; i < beanArrayList.size();) {
            if (this.tmpArrayList.get(i).get(".selecao") != null) {
                if ((Boolean) this.tmpArrayList.get(i).get(".selecao")) {
                    this.removerBean(i);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }
    }

    public ArrayList retirarBeansSelecionados() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        ArrayList list = new ArrayList();

        for (int i = 0; i < beanArrayList.size();) {
            if (this.tmpArrayList.get(i).get(".selecao") != null) {
                if ((Boolean) this.tmpArrayList.get(i).get(".selecao")) {
                    list.add(this.beanArrayList.get(i));
                    this.removerBean(i);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }

        return list;
    }

    public void selecionar(boolean opcao) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        for (int i = 0; i < beanArrayList.size(); i++) {
            this.atualizarCelulaForaDoModelo(".selecao", i, opcao);
        }
    }

    public ArrayList obterBeansSelecionados() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        ArrayList list = new ArrayList();

        for (int i = 0; i < beanArrayList.size(); i++) {
            if (this.tmpArrayList.get(i).get(".selecao") != null) {
                if ((Boolean) this.tmpArrayList.get(i).get(".selecao")) {
                    list.add(this.beanArrayList.get(i));
                }
            }
        }

        return list;
    }

    public boolean contemBean(Object bean) {
        return this.beanArrayList.contains(bean);
    }

    // Sincroniza os dados no sentido bean --> table.
    private void sincronizarView(int linha, Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        JBeanTable.TABLE_CHANGED_BLOQUEADO = true;

        for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
            if(this.jBeanTableColumns.get(i).isVisivel()){
                this.sincronizarCelula(linha, i, obj);
            }
        }

        JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
    }

    private void sincronizarCelula(int linha, int coluna, Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (this.jBeanTableColumns.get(coluna).getAcesso() != null) {
            if (!this.jBeanTableColumns.get(coluna).getAcesso().equals("") && this.jBeanTableColumns.get(coluna).isVisivel()) {
                Object value;

                if (!this.jBeanTableColumns.get(coluna).getAcesso().startsWith(".")) {
                    value = CamaleaotiBeanTools.obterValorDoAtributo(obj, this.jBeanTableColumns.get(coluna).getAcesso());
                    this.getModel().setValueAt(value, linha, coluna);
                }
            }
        }
    }

    // Sincroniza os dados no sentido bean --> table para um atributo específico.
    private void sincronizarCelulaForaDoModelo(int linha, int coluna, Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (this.jBeanTableColumns.get(coluna).getAcesso() != null) {
            if (!this.jBeanTableColumns.get(coluna).getAcesso().equals("")) {
                if (this.jBeanTableColumns.get(coluna).getAcesso().startsWith(".")) {
                    this.getModel().setValueAt(obj, linha, coluna);
                }
            }
        }
    }

    // Sincroniza os dados no sentido table --> bean.
    private void sincronizarModel(int linha, Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
            this.sincronizarAtributo(linha, i, obj);
        }
    }

    // Sincroniza os dados no sentido table --> bean para um atributo específico.
    private void sincronizarAtributo(int linha, int coluna, Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        if (!this.jBeanTableColumns.get(coluna).getAcesso().equals("") && this.jBeanTableColumns.get(coluna).isVisivel()) {
            if (!this.jBeanTableColumns.get(coluna).getAcesso().startsWith(".")) {
                CamaleaotiBeanTools.setValorNoAtributo(obj, this.jBeanTableColumns.get(coluna).getAcesso(), this.getModel().getValueAt(linha, coluna));
            }
        }
    }

    private void sincronizarPropriedade(int linha, int coluna) {
        if (this.jBeanTableColumns.get(coluna).getAcesso().startsWith(".")) {
            this.tmpArrayList.get(linha).put(this.jBeanTableColumns.get(coluna).getAcesso(), this.getModel().getValueAt(linha, coluna));
        }
    }

    private HashMap<String, Object> construirLinhaForaDoModelo() {
        HashMap<String, Object> linha = new HashMap();
        for (int i = 0; i < this.jBeanTableColumns.size(); i++) {
            JBeanTableColumn jBeanTableColumn = (JBeanTableColumn) this.jBeanTableColumns.get(i);
            if (jBeanTableColumn.getAcesso().startsWith(".")) {
                linha.put(jBeanTableColumn.getAcesso(), null);
            }
        }

        return linha;
    }

    public void adicionarBean(Object bean) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        DefaultTableModel tableModel = (DefaultTableModel) this.getModel();

        JBeanTable.TABLE_CHANGED_BLOQUEADO = true;
        tableModel.addRow(new ArrayList(this.jBeanTableColumns.size()).toArray());
        JBeanTable.TABLE_CHANGED_BLOQUEADO = false;
        this.beanArrayList.add(bean);
        this.tmpArrayList.add(this.construirLinhaForaDoModelo());

        this.sincronizarView(tableModel.getRowCount() - 1, this.beanArrayList.get(tableModel.getRowCount() - 1));

        this.fireBeanAdicionado(bean);
    }

    public boolean estahVazia() {
        return this.beanArrayList.isEmpty();
    }

    private void atribuirBeans(ArrayList beans) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        this.beanArrayList = new ArrayList();
        ((DefaultTableModel) this.getModel()).setRowCount(0);

        for (int i = 0; i < beans.size(); i++) {
            this.adicionarBean(beans.get(i));
        }
    }

    public ArrayList<JBeanTableColumn> getJBeanTableColumns() {
        return jBeanTableColumns;
    }

    public void ativarOrdenacao(Boolean ativar) {
        this.ordena = ativar;
    }

    private void ordenar(String chave) {
        try {
            ArrayList<Object[]> array = new ArrayList();

            for (int i = 0; i < this.beanArrayList.size(); i++) {
                Object object = this.beanArrayList.get(i);
                HashMap hashMap = (HashMap) this.tmpArrayList.get(i).clone();

                array.add(new Object[]{object, hashMap});
            }

            JBeanTableColumn jBeanTableColumn = this.obterColuna(chave);

            if (jBeanTableColumn != null) {
                if (jBeanTableColumn.getComparator() != null) {
                    Collections.sort(array, jBeanTableColumn.getComparator());
                } else {
                    Collections.sort(array, new JBeanTableComparator(chave));
                }
            } else {
                Collections.sort(array, new JBeanTableComparator(chave));
            }

            ArrayList beanArrayListReordenado = new ArrayList();
            ArrayList itensForaDoModeloReordenados = new ArrayList();

            for (Iterator<Object[]> it = array.iterator(); it.hasNext();) {
                Object[] itemReordenado = it.next();
                beanArrayListReordenado.add(itemReordenado[0]);
                itensForaDoModeloReordenados.add(itemReordenado[1]);
            }

            this.setModeloDeDados(beanArrayListReordenado);
            this.atualizarDadosForaDoModelo(itensForaDoModeloReordenados);
        } catch (Exception ex) {
            Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizarDadosForaDoModelo(ArrayList dadosForaDoModelo) {
        try {
            if (dadosForaDoModelo != null) {
                for (int i = 0; i < dadosForaDoModelo.size(); i++) {
                    HashMap hashMap = (HashMap) dadosForaDoModelo.get(i);
                    ArrayList<Map.Entry> entradas = new ArrayList(hashMap.entrySet());
                    for (Iterator<Map.Entry> it = entradas.iterator(); it.hasNext();) {
                        Map.Entry entry = it.next();
                        this.atualizarCelulaForaDoModelo((String) entry.getKey(), i, entry.getValue());
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList obterDadosForaDoModelo() {
        return this.tmpArrayList;
    }

    private Comparator obterComparadorDeSelecao() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Object[] tObject1 = (Object[]) o1;
                Object[] tObject2 = (Object[]) o2;

                Object v1 = ((HashMap) tObject1[1]).get(".selecao");
                Object v2 = ((HashMap) tObject2[1]).get(".selecao");

                if ((v1 == null) && (v2 == null)) {
                    return 0;
                }

                if ((v1 != null) && (v2 == null)) {
                    return -1;
                } else if ((v1 == null) && (v2 != null)) {
                    return 1;
                } else {
                    if (((Boolean) v1).equals(Boolean.TRUE) && ((Boolean) v2).equals(Boolean.FALSE)) {
                        return -1;
                    }
                    if (((Boolean) v1).equals(Boolean.FALSE) && ((Boolean) v2).equals(Boolean.TRUE)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        };
    }

    private static class JBeanTableComparator implements Comparator {

        private String chave;

        public JBeanTableComparator(String chave) {
            this.chave = chave;
        }

        @Override
        public int compare(Object o1, Object o2) {
            try {
                Object[] tObject1 = (Object[]) o1;
                Object[] tObject2 = (Object[]) o2;

                Object value1;
                Object value2;

                if (this.chave.startsWith(".")) {
                    value1 = ((HashMap) tObject1[1]).get(this.chave);
                    value2 = ((HashMap) tObject2[1]).get(this.chave);
                } else {
                    value1 = CamaleaotiBeanTools.obterValorDoAtributo(tObject1[0], this.chave);
                    value2 = CamaleaotiBeanTools.obterValorDoAtributo(tObject2[0], this.chave);
                }

                if ((value1 != null) && (value2 != null)) {
                    if (value1 instanceof Comparable) {
                        return ((Comparable) value1).compareTo(value2);
                    } else {
                        return value1.toString().compareTo(value2.toString());
                    }
                } else {
                    int v1 = (value1 == null ? 1 : 0);
                    int v2 = (value2 == null ? 1 : 0);

                    return v2 - v1;
                }
            } catch (Exception ex) {
                Logger.getLogger(JBeanTable.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
    }
}