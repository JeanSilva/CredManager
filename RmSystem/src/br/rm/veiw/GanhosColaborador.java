/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.controle.FormatacaoMoeda;
import br.rm.controle.Repository;
import br.rm.modelo.Rm_Colaborador;
import br.rm.modelo.Rm_Emprestimo;
import br.rm.modelo.Rm_Parcela;
import br.rm.tabela.JBeanTable;
import br.rm.tabela.JBeanTableColumn;
import br.rm.tabela.JDecimalTableCellEditor;
import br.rm.tabela.JDecimalTableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author DEV
 */
public class GanhosColaborador extends javax.swing.JInternalFrame {

    DefaultListModel modelo;
    List<Rm_Colaborador> colaboradorList;
    private Rm_Colaborador colaborador;
    private JBeanTable jBTParcelasRebidas;
    private JBeanTable jBTParcelasAtrasadas;
    private JBeanTable jBTParcelasEmAberto;

    private JBeanTable jBTEmprestimos;
    private List<Rm_Emprestimo> emprestimos;
    private List<Rm_Parcela> parcelasRebidas;
    private List<Rm_Parcela> parcelasAtrasadas;
    private List<Rm_Parcela> parcelasEmAberto;
    private List<Rm_Parcela> parcelasGeral;
    private FormatacaoMoeda format;

    /**
     * Creates new form GanhosColaborador
     */
    public GanhosColaborador() {
        initComponents();
        jListColaborador.setVisible(false);
        modelo = new DefaultListModel();
        jListColaborador.setModel(modelo);
        criarTabelaEmprestimos();
        criarTabelaParcelaRecebida();
        criarTabelaParcelaAtrasadas();
        criarTabelaParcelaEmAberto();
        //jTdataFinal.setDate(new Date());
        //jTdataInicial.setDate(new Date());
        //jTdataInicial.setFocusable(true);
        parcelasAtrasadas = new ArrayList<>();
        parcelasEmAberto = new ArrayList<>();
        parcelasRebidas = new ArrayList<>();
        format = new FormatacaoMoeda();
        setFrameIcon(new ImageIcon(getClass().getResource("/icons/coins.png"))); 
    }

    private void criarTabelaEmprestimos() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("cliente.nome", "CLIENTE", true, false, 200, null, null, null));
            columns.add(new JBeanTableColumn("valorEmprestimo", "VALOR", true, false, 120, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("status", "STATUS", true, false, 145, null, null, null));

            this.jBTEmprestimos = new JBeanTable(Rm_Emprestimo.class, columns);

            this.jBTEmprestimos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2)) {
                        try {

                            int indiceSelecionado = jBTEmprestimos.getSelectedRow();
                            // Produto produto = (Produto) jBTComada.obterBean(indiceSelecionado);
                            Rm_Emprestimo emprestimo = (Rm_Emprestimo) jBTEmprestimos.obterBean(indiceSelecionado);
                            List<Rm_Emprestimo> emp = new ArrayList<>();
                            emp.add(emprestimo);

                            buscarParcelas(emp);
                            organizarParcelas();
                            calculoIndividual();
                        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException ex) {
                            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

            });

            this.jScrollPaneEmprestimos.setViewportView(this.jBTEmprestimos);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaRecebida() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 49, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 95, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorRecebido", "VALOR RECEBIDO", true, false, 135, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasRebidas = new JBeanTable(Rm_Parcela.class, columns);

            this.jScrollPaneParcelasRecebidas.setViewportView(this.jBTParcelasRebidas);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaAtrasadas() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 40, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 110, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiario", "JUROS DIARIO", true, false, 138, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasAtrasadas = new JBeanTable(Rm_Parcela.class, columns);
            this.jScrollPaneParcelasAtrasadas.setViewportView(this.jBTParcelasAtrasadas);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaEmAberto() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 50, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 141, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 175, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasEmAberto = new JBeanTable(Rm_Parcela.class, columns);

            this.jScrollPaneParcelasAbertas.setViewportView(this.jBTParcelasEmAberto);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ListaPesquisa() {
        modelo = new DefaultListModel();
        colaboradorList = Repository.getInstance().buscarColaboradorNome(jTNome.getText());
        modelo.removeAllElements();
        if ((colaboradorList != null) && (!colaboradorList.isEmpty())) {
            for (Rm_Colaborador c : colaboradorList) {
                modelo.addElement(c.getNome());

            }

            jListColaborador.setModel(modelo);
            jListColaborador.setVisible(true);
        }

    }

    private void inserirColaborador() {
        int linha = jListColaborador.getSelectedIndex();
        for (Rm_Colaborador c : colaboradorList) {
            if (c.getNome().equals(modelo.getElementAt(linha))) {
                colaborador = c;
                buscarRelatorio();
                jLComissao.setText("% Comissão: "+format.formatarPercentual(colaborador.getPorcentagemComissao()));
                jLSalarioBase.setText("R$ - Salário Base: "+format.formatarMoeda(colaborador.getSalario()));
                jListColaborador.setVisible(false);
            }
        }
        jTNome.setText(colaborador.getNome());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jListColaborador = new javax.swing.JList<>();
        jTNome = new br.rm.controle.JTextFieldHint(new JTextField(),  "Nome Colaborador");
        ;
        jTdataInicial = new com.toedter.calendar.JDateChooser();
        jTdataFinal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLSalarioBase = new javax.swing.JLabel();
        jLTotalEmprestimo = new javax.swing.JLabel();
        jLTotalParcelaRecebida = new javax.swing.JLabel();
        jLTotalParcelaAtraso = new javax.swing.JLabel();
        jLComissao = new javax.swing.JLabel();
        jLTotalaReceber = new javax.swing.JLabel();
        jLTotalRecebida = new javax.swing.JLabel();
        jLTotalParcelaAberta = new javax.swing.JLabel();
        jLTotalAberto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTabbedPaneParcelas = new javax.swing.JTabbedPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelAbertas = new javax.swing.JPanel();
        jScrollPaneParcelasAbertas = new javax.swing.JScrollPane();
        jLQtdAberta = new javax.swing.JLabel();
        jLValorAberta = new javax.swing.JLabel();
        jLComissaoAberta = new javax.swing.JLabel();
        jPanelAtrasadas = new javax.swing.JPanel();
        jScrollPaneParcelasAtrasadas = new javax.swing.JScrollPane();
        jLQtdAtrasada = new javax.swing.JLabel();
        jLValorAtraso = new javax.swing.JLabel();
        jLComissaoAtrasada = new javax.swing.JLabel();
        jPanelRecebidas = new javax.swing.JPanel();
        jScrollPaneParcelasRecebidas = new javax.swing.JScrollPane();
        jLQtdParcelaRecebida = new javax.swing.JLabel();
        jLValorRecebido = new javax.swing.JLabel();
        jLComissaoRecebida = new javax.swing.JLabel();
        jTabbedPaneEmprestimo = new javax.swing.JTabbedPane();
        jScrollPaneEmprestimos = new javax.swing.JScrollPane();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setTitle("Ganhos Colaborador");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));

        jListColaborador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jListColaborador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListColaborador.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jListColaborador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListColaboradorMouseClicked(evt);
            }
        });

        jTNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNome.setForeground(new java.awt.Color(255, 255, 255));
        jTNome.setOpaque(true);
        jTNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomeKeyPressed(evt);
            }
        });

        jTdataInicial.setBackground(new java.awt.Color(255, 255, 255));
        jTdataInicial.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTdataInicial.setForeground(new java.awt.Color(255, 255, 255));
        jTdataInicial.setDateFormatString("dd/MM/yyyy");
        jTdataInicial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTdataFinal.setBackground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTdataFinal.setForeground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setToolTipText("Até");
        jTdataFinal.setDateFormatString("dd/MM/yyyy");
        jTdataFinal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Data inicio");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Data final");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLSalarioBase.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLSalarioBase.setText("R$ - Salário Base:");
        jLSalarioBase.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalEmprestimo.setText("R$ - Empréstimos: ");
        jLTotalEmprestimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalParcelaRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaRecebida.setText("R$ - Parcelas Recebidas:");
        jLTotalParcelaRecebida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalParcelaAtraso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaAtraso.setText("R$ - Parcelas atrasadas:");
        jLTotalParcelaAtraso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLComissao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissao.setText("% Comissão:");
        jLComissao.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalaReceber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalaReceber.setText("R$ - Saldo (SB + Comissão):");
        jLTotalaReceber.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalRecebida.setText("R$ - Comissão Recebida:");
        jLTotalRecebida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalParcelaAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaAberta.setText("R$ - Parcelas em Aberto:");
        jLTotalParcelaAberta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLTotalAberto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalAberto.setText("R$ - Comissão em aberto:");
        jLTotalAberto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Obs: Calculo do saldo a receber é:\nSaldo = Salario base+Comissão Recebida");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLSalarioBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLComissao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLTotalEmprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLTotalParcelaRecebida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLTotalParcelaAtraso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLTotalParcelaAberta, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLTotalRecebida, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTotalAberto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLTotalaReceber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLComissao)
                    .addComponent(jLTotalaReceber))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLSalarioBase)
                    .addComponent(jLTotalRecebida))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLTotalEmprestimo)
                    .addComponent(jLTotalAberto))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLTotalParcelaRecebida)
                        .addGap(0, 0, 0)
                        .addComponent(jLTotalParcelaAtraso)
                        .addGap(1, 1, 1)
                        .addComponent(jLTotalParcelaAberta))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );

        jTabbedPaneParcelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPaneParcelas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneParcelasStateChanged(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanelAbertas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPaneParcelasAbertas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLQtdAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdAberta.setText("Qtd Parcelas:");
        jLQtdAberta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLValorAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorAberta.setText("Valor aberto:");
        jLValorAberta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLComissaoAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoAberta.setText("Comissão:");
        jLComissaoAberta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelAbertasLayout = new javax.swing.GroupLayout(jPanelAbertas);
        jPanelAbertas.setLayout(jPanelAbertasLayout);
        jPanelAbertasLayout.setHorizontalGroup(
            jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAbertasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLComissaoAberta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLValorAberta, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(jLQtdAberta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelAbertasLayout.setVerticalGroup(
            jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAbertasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAbertasLayout.createSequentialGroup()
                        .addComponent(jLQtdAberta)
                        .addGap(0, 0, 0)
                        .addComponent(jLValorAberta)
                        .addGap(0, 0, 0)
                        .addComponent(jLComissaoAberta))
                    .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parcelas abertas", jPanelAbertas);

        jPanelAtrasadas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelAtrasadas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneParcelasAtrasadas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPaneParcelasAtrasadas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLQtdAtrasada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdAtrasada.setText("Qtd Parcelas:");
        jLQtdAtrasada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLValorAtraso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorAtraso.setText("Valor em atraso:");
        jLValorAtraso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLComissaoAtrasada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoAtrasada.setText("Comissão:");
        jLComissaoAtrasada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelAtrasadasLayout = new javax.swing.GroupLayout(jPanelAtrasadas);
        jPanelAtrasadas.setLayout(jPanelAtrasadasLayout);
        jPanelAtrasadasLayout.setHorizontalGroup(
            jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLQtdAtrasada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLValorAtraso, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                    .addComponent(jLComissaoAtrasada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAtrasadasLayout.setVerticalGroup(
            jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                        .addComponent(jLQtdAtrasada)
                        .addGap(0, 0, 0)
                        .addComponent(jLValorAtraso)
                        .addGap(0, 0, 0)
                        .addComponent(jLComissaoAtrasada))
                    .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("Parcelas atrasadas", jPanelAtrasadas);

        jPanelRecebidas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPaneParcelasRecebidas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPaneParcelasRecebidas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLQtdParcelaRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdParcelaRecebida.setText("Qtd Parcelas:");
        jLQtdParcelaRecebida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLValorRecebido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorRecebido.setText("Valor recebido:");
        jLValorRecebido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLComissaoRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoRecebida.setText("Comissão:");
        jLComissaoRecebida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelRecebidasLayout = new javax.swing.GroupLayout(jPanelRecebidas);
        jPanelRecebidas.setLayout(jPanelRecebidasLayout);
        jPanelRecebidasLayout.setHorizontalGroup(
            jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLComissaoRecebida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLQtdParcelaRecebida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLValorRecebido, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)))
        );
        jPanelRecebidasLayout.setVerticalGroup(
            jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                        .addComponent(jLQtdParcelaRecebida)
                        .addGap(0, 0, 0)
                        .addComponent(jLValorRecebido)
                        .addGap(0, 0, 0)
                        .addComponent(jLComissaoRecebida)
                        .addGap(0, 318, Short.MAX_VALUE))
                    .addComponent(jScrollPaneParcelasRecebidas))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parcelas recebidas", jPanelRecebidas);

        jTabbedPaneEmprestimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPaneEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneEmprestimos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPaneEmprestimos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPaneEmprestimo.addTab("Empréstimos Vendidos", jScrollPaneEmprestimos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jListColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(241, 241, 241))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPaneParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTabbedPaneEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(3, 3, 3)
                                .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(3, 3, 3)
                                .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jListColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTabbedPaneParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPaneEmprestimo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListColaboradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListColaboradorMouseClicked
        inserirColaborador();
        jListColaborador.setVisible(false);
    }//GEN-LAST:event_jListColaboradorMouseClicked

    private void jTNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomeKeyPressed
        String tam = jTNome.getText();

        if (jTNome.getText().isEmpty()) {
            jListColaborador.setVisible(false);
        } else if (tam.length() > 1) {
            ListaPesquisa();
        }
    }//GEN-LAST:event_jTNomeKeyPressed

    private void jTabbedPaneParcelasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneParcelasStateChanged
        // Obter o índice da aba selecionada
        int indiceAbaSelecionada = jTabbedPaneParcelas.getSelectedIndex();

        // Obter o título da aba selecionada
        String tituloAbaSelecionada = jTabbedPaneParcelas.getTitleAt(indiceAbaSelecionada);

        // Fazer algo com a aba selecionada
        System.out.println("Aba selecionada: " + tituloAbaSelecionada);
    }//GEN-LAST:event_jTabbedPaneParcelasStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLComissao;
    private javax.swing.JLabel jLComissaoAberta;
    private javax.swing.JLabel jLComissaoAtrasada;
    private javax.swing.JLabel jLComissaoRecebida;
    private javax.swing.JLabel jLQtdAberta;
    private javax.swing.JLabel jLQtdAtrasada;
    private javax.swing.JLabel jLQtdParcelaRecebida;
    private javax.swing.JLabel jLSalarioBase;
    private javax.swing.JLabel jLTotalAberto;
    private javax.swing.JLabel jLTotalEmprestimo;
    private javax.swing.JLabel jLTotalParcelaAberta;
    private javax.swing.JLabel jLTotalParcelaAtraso;
    private javax.swing.JLabel jLTotalParcelaRecebida;
    private javax.swing.JLabel jLTotalRecebida;
    private javax.swing.JLabel jLTotalaReceber;
    private javax.swing.JLabel jLValorAberta;
    private javax.swing.JLabel jLValorAtraso;
    private javax.swing.JLabel jLValorRecebido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jListColaborador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelAbertas;
    private javax.swing.JPanel jPanelAtrasadas;
    private javax.swing.JPanel jPanelRecebidas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneEmprestimos;
    private javax.swing.JScrollPane jScrollPaneParcelasAbertas;
    private javax.swing.JScrollPane jScrollPaneParcelasAtrasadas;
    private javax.swing.JScrollPane jScrollPaneParcelasRecebidas;
    private javax.swing.JTextField jTNome;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPaneEmprestimo;
    private javax.swing.JTabbedPane jTabbedPaneParcelas;
    private com.toedter.calendar.JDateChooser jTdataFinal;
    private com.toedter.calendar.JDateChooser jTdataInicial;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private void buscarRelatorio() {
        if ((jTdataInicial.getDate() != null) || (jTdataFinal.getDate() != null)) {
            buscarPorPeriodo(colaborador, jTdataInicial.getDate(), jTdataFinal.getDate());
        } else {
            buscarTodosPeriodo();
        }

    }

    private void buscarPorPeriodo(Rm_Colaborador colaborador, Date dateInicial, Date dateFinal) {
        try {
            emprestimos = Repository.getInstance().buscarEmprestimoPorDataEColaborador(colaborador, dateInicial, dateFinal);

            if (emprestimos != null) {

                jBTEmprestimos.removeAll();
                jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
                buscarParcelas(emprestimos);
                organizarParcelas();
                calcular();
            } else {
                JOptionPane.showMessageDialog(null, "NENHUMA VENDA REGISTRADA NESSE PERÍODO", "VENDA NÃO ENCONTRADA", WIDTH);

            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscarTodosPeriodo() {
        try {
            emprestimos = Repository.getInstance().buscarEmprestimoPorColaborador(colaborador);
            jBTEmprestimos.removeAll();

            if (emprestimos != null) {

                jBTEmprestimos.removeAll();
                jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
                buscarParcelas(emprestimos);
                organizarParcelas();
                calcular();
            } else {
                JOptionPane.showMessageDialog(null, "NENHUMA VENDA REGISTRADA NESSE PERÍODO", "VENDA NÃO ENCONTRADA", WIDTH);
                limpar();
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscarParcelas(List<Rm_Emprestimo> empre) {
        parcelasGeral = new ArrayList<>();
        List<Rm_Parcela> parcelas = new ArrayList<>();
        for (Rm_Emprestimo emprestimo : empre) {

            parcelas = Repository.getInstance().BuscarTodasPorEmprestimo(emprestimo);
            if (!parcelas.isEmpty()) {
                for (Rm_Parcela parcela : parcelas) {
                    parcelasGeral.add(parcela);
                }
            }

        }

    }

    private void organizarParcelas() {
        try {
            jBTParcelasAtrasadas.removeAll();
            jBTParcelasEmAberto.removeAll();
            jBTParcelasRebidas.removeAll();
            parcelasEmAberto.clear();
            parcelasAtrasadas.clear();
            parcelasRebidas.clear();

            for (Rm_Parcela p : parcelasGeral) {

                switch (p.getStatus()) {
                    case ABERTA:
                        parcelasEmAberto.add(p);
                        break;
                    case ATRASADA:
                        parcelasAtrasadas.add(p);
                        break;
                    case RECEBIDA:
                        parcelasRebidas.add(p);
                        break;
                    default:
                        break;
                }
            }

            jBTParcelasAtrasadas.setModeloDeDados(new ArrayList(parcelasAtrasadas));
            jBTParcelasEmAberto.setModeloDeDados(new ArrayList(parcelasEmAberto));
            jBTParcelasRebidas.setModeloDeDados(new ArrayList(parcelasRebidas));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void calcular() {
        calcularContratosVendidos();
        calcularTotalParcelas();
        calcularSaldosColaborador();
        calculoIndividual();
    }

    private void calcularContratosVendidos() {
        double totalVendido = 0d;

        for (Rm_Emprestimo emprestimo : emprestimos) {
            totalVendido += emprestimo.getValorEmprestimo();
        }
        jLTotalEmprestimo.setText("R$ - Empréstimos: "+format.formatarMoeda(totalVendido));
    }

    private void calcularTotalParcelas() {
        double totalRecebida = 0d;
        double totalAtrasado = 0d;
        double totalAberto = 0d;
        for (Rm_Parcela parcelaRecebida : parcelasRebidas) {
            totalRecebida += parcelaRecebida.getValorRecebido();
        }
        jLTotalParcelaRecebida.setText("R$ - Parcelas Recebidas: "+format.formatarMoeda(totalRecebida));

        for (Rm_Parcela parcelaAtraso : parcelasAtrasadas) {
            totalAtrasado += parcelaAtraso.getValor();
        }
        jLTotalParcelaAtraso.setText("R$ - Parcelas atrasadas: "+format.formatarMoeda(totalAtrasado));

        for (Rm_Parcela parcelaAberta : parcelasEmAberto) {
            totalAberto += parcelaAberta.getValor();
        }
        jLTotalParcelaAberta.setText("R$ - Parcelas em Aberto: "+format.formatarMoeda(totalAberto));

    }

    private void calcularSaldosColaborador() {
        double ganhoComissao = 0d;
        
        double parcialComissaoAtrasada = 0d;
        double parcialComissaoAberto = 0d;

        //CALCULAR comissao a receber das parcela ja pagas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasRebidas) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        ganhoComissao += p.getValorRecebido();
                    }

                }

            }
        }
        //calcula parcial a receber das parcelas em aberto e atrazadas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasAtrasadas) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        parcialComissaoAtrasada += p.getValor();
                    }

                }
                for (Rm_Parcela p : parcelasEmAberto) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        parcialComissaoAberto += p.getValor();
                    }

                }

            }
        }
        parcialComissaoAtrasada = parcialComissaoAtrasada * (colaborador.getPorcentagemComissao() / 100);
        parcialComissaoAberto = parcialComissaoAberto * (colaborador.getPorcentagemComissao() / 100);
        ganhoComissao = ganhoComissao * (colaborador.getPorcentagemComissao() / 100);

        jLTotalaReceber.setText("R$ - Saldo (SB + Comissão): "+format.formatarMoeda(ganhoComissao + colaborador.getSalario()));
        jLTotalAberto.setText("R$ - Comissão em aberto: "+format.formatarMoeda(parcialComissaoAberto + parcialComissaoAtrasada));
        jLTotalRecebida.setText("R$ - Comissão Recebida: "+format.formatarMoeda(ganhoComissao));
    }

    private void calculoIndividual() {
        double ganhoComissao = 0d;
        double parcialComissaoAtrasada = 0d;
        double parcialComissaoAberto = 0d;

        //CALCULAR comissao a receber das parcela ja pagas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasRebidas) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        ganhoComissao += p.getValorRecebido();
                    }

                }

            }
        }
        //calcula parcial a receber das parcelas em aberto e atrazadas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasAtrasadas) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        parcialComissaoAtrasada += p.getValor();
                    }

                }
                for (Rm_Parcela p : parcelasEmAberto) {
                    if (Objects.equals(emp.getId(), p.getEmprestimo().getId())) {
                        parcialComissaoAberto += p.getValor();
                    }

                }

            }
        }

        jLComissaoAtrasada.setText("Comissão: "+format.formatarMoeda(parcialComissaoAtrasada * (colaborador.getPorcentagemComissao() / 100)));
        jLComissaoAberta.setText("Comissão: "+format.formatarMoeda(parcialComissaoAberto * (colaborador.getPorcentagemComissao() / 100)));
        jLComissaoRecebida.setText("Comissão: "+format.formatarMoeda(ganhoComissao * (colaborador.getPorcentagemComissao() / 100)));

        jLValorRecebido.setText("Valor recebido: "+format.formatarMoeda(ganhoComissao));
        jLValorAtraso.setText("Valor em atraso: "+format.formatarMoeda(parcialComissaoAtrasada));
        jLValorAberta.setText("Valor aberto: "+format.formatarMoeda(parcialComissaoAberto));

        jLQtdParcelaRecebida.setText("Qtd Parcelas: "+String.valueOf(parcelasRebidas.size()));
        jLQtdAtrasada.setText("Qtd Parcelas: "+String.valueOf(parcelasAtrasadas.size()));
        jLQtdAberta.setText("Qtd Parcelas: "+String.valueOf(parcelasEmAberto.size()));

    }

    private void limpar() {
        try {

            jBTParcelasAtrasadas.removeAll();
            jBTParcelasEmAberto.removeAll();
            jBTParcelasRebidas.removeAll();
            parcelasEmAberto.clear();
            parcelasAtrasadas.clear();
            parcelasRebidas.clear();
            if (!emprestimos.isEmpty()) {
                emprestimos.clear();
            }
            jBTParcelasAtrasadas.setModeloDeDados(new ArrayList(parcelasAtrasadas));
            jBTParcelasEmAberto.setModeloDeDados(new ArrayList(parcelasEmAberto));
            jBTParcelasRebidas.setModeloDeDados(new ArrayList(parcelasRebidas));
            jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
