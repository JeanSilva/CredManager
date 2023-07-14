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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
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
        jListColaborador.setVisible(true);
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
            columns.add(new JBeanTableColumn("valorRecebido", "VALOR RECEBIDO", true, true, 143, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            
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
                jLComissao.setText(format.formatarPercentual(colaborador.getPorcentagemComissao()));
                jLSalarioBase.setText(format.formatarMoeda(colaborador.getSalario()));
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
        jScrollPaneEmprestimos = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLComissao = new javax.swing.JLabel();
        jLSalarioBase = new javax.swing.JLabel();
        jLTotalEmprestimo = new javax.swing.JLabel();
        jLTotalParcelaRecebida = new javax.swing.JLabel();
        jLTotalParcelaAtraso = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLTotalaReceber = new javax.swing.JLabel();
        jLTotalAberto = new javax.swing.JLabel();
        jLTotalParcelaAberta = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLTotalRecebida = new javax.swing.JLabel();
        jTabbedPaneParcelas = new javax.swing.JTabbedPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelRecebidas = new javax.swing.JPanel();
        jScrollPaneParcelasRecebidas = new javax.swing.JScrollPane();
        jLabel17 = new javax.swing.JLabel();
        jLQtdParcelaRecebida = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLValorRecebido = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLComissaoRecebida = new javax.swing.JLabel();
        jPanelAbertas = new javax.swing.JPanel();
        jScrollPaneParcelasAbertas = new javax.swing.JScrollPane();
        jLabel23 = new javax.swing.JLabel();
        jLQtdAberta = new javax.swing.JLabel();
        jLValorAberta = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLComissaoAberta = new javax.swing.JLabel();
        jPanelAtrasadas = new javax.swing.JPanel();
        jScrollPaneParcelasAtrasadas = new javax.swing.JScrollPane();
        jLabel20 = new javax.swing.JLabel();
        jLQtdAtrasada = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLValorAtraso = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLComissaoAtrasada = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Ganhos Colaborador");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 500));

        jListColaborador.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jTdataInicial.setForeground(new java.awt.Color(255, 255, 255));
        jTdataInicial.setDateFormatString("dd/MM/yyyy");
        jTdataInicial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jTdataFinal.setBackground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setForeground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setToolTipText("Até");
        jTdataFinal.setDateFormatString("dd/MM/yyyy");
        jTdataFinal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Data inicio");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Data final");

        jScrollPaneEmprestimos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Empréstimos Vendidos");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("SALÁRIO BASE:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("TOTAL DE EMPRESTIMOS: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL DE PARCELAS RECEBIDAS:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("TOTAL DE PARCELAS ATRASADAS:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("COMISSÃO:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("TOTAL A RECEBER:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("TOTAL COMISSÃO RECEBIDA:");

        jLComissao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissao.setText("valor");

        jLSalarioBase.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLSalarioBase.setText("valor");

        jLTotalEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalEmprestimo.setText("valor");

        jLTotalParcelaRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaRecebida.setText("valor");

        jLTotalParcelaAtraso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaAtraso.setText("valor");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Cálculo de recebimentos = SB + Comissão.");

        jLTotalaReceber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalaReceber.setText("Valor");

        jLTotalAberto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalAberto.setText("valor");

        jLTotalParcelaAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaAberta.setText("valor");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("TOTAL DE PARCELAS Á VENCER:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("TOTAL COMISSÃO EM ABERTO:");

        jLTotalRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalRecebida.setText("valor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLTotalEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLSalarioBase, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLTotalParcelaAberta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLTotalParcelaAtraso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLTotalParcelaRecebida, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(150, 150, 150)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(80, 80, 80))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTotalaReceber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTotalRecebida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTotalAberto, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLComissao)))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLSalarioBase)
                    .addComponent(jLabel12)
                    .addComponent(jLTotalaReceber))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLTotalEmprestimo)
                    .addComponent(jLabel13)
                    .addComponent(jLTotalRecebida))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLTotalParcelaRecebida)
                    .addComponent(jLabel16)
                    .addComponent(jLTotalAberto))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLTotalParcelaAtraso))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLTotalParcelaAberta))
                .addGap(26, 26, 26))
        );

        jTabbedPaneParcelas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPaneParcelas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneParcelasStateChanged(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanelRecebidas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPaneParcelasRecebidas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPaneParcelasRecebidas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Qtd Parcelas:");

        jLQtdParcelaRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdParcelaRecebida.setText("valor");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Valor recebido:");

        jLValorRecebido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorRecebido.setText("Valor");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Comissão:");

        jLComissaoRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoRecebida.setText("Valor");

        javax.swing.GroupLayout jPanelRecebidasLayout = new javax.swing.GroupLayout(jPanelRecebidas);
        jPanelRecebidas.setLayout(jPanelRecebidasLayout);
        jPanelRecebidasLayout.setHorizontalGroup(
            jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLComissaoRecebida, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLQtdParcelaRecebida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRecebidasLayout.setVerticalGroup(
            jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRecebidasLayout.createSequentialGroup()
                        .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLQtdParcelaRecebida))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLValorRecebido))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelRecebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLComissaoRecebida))
                        .addGap(0, 193, Short.MAX_VALUE))
                    .addComponent(jScrollPaneParcelasRecebidas))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parcelas recebidas", jPanelRecebidas);

        jPanelAbertas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPaneParcelasAbertas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Qtd Parcelas:");

        jLQtdAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdAberta.setText("Valor");

        jLValorAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorAberta.setText("Valor");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Valor aberto:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Comissão:");

        jLComissaoAberta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoAberta.setText("Valor");

        javax.swing.GroupLayout jPanelAbertasLayout = new javax.swing.GroupLayout(jPanelAbertas);
        jPanelAbertas.setLayout(jPanelAbertasLayout);
        jPanelAbertasLayout.setHorizontalGroup(
            jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAbertasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelAbertasLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLComissaoAberta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelAbertasLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLValorAberta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAbertasLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLQtdAberta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanelAbertasLayout.setVerticalGroup(
            jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAbertasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAbertasLayout.createSequentialGroup()
                        .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jLQtdAberta))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLValorAberta))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelAbertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLComissaoAberta))
                        .addGap(0, 0, 0))
                    .addComponent(jScrollPaneParcelasAbertas))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parcelas abertas", jPanelAbertas);

        jPanelAtrasadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelAtrasadas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneParcelasAtrasadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPaneParcelasAtrasadas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Qtd Parcelas:");

        jLQtdAtrasada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLQtdAtrasada.setText("Valor");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Valor em atraso:");

        jLValorAtraso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLValorAtraso.setText("Valor");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Comissão:");

        jLComissaoAtrasada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissaoAtrasada.setText("Valor");

        javax.swing.GroupLayout jPanelAtrasadasLayout = new javax.swing.GroupLayout(jPanelAtrasadas);
        jPanelAtrasadas.setLayout(jPanelAtrasadasLayout);
        jPanelAtrasadasLayout.setHorizontalGroup(
            jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLQtdAtrasada, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLValorAtraso, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLComissaoAtrasada, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanelAtrasadasLayout.setVerticalGroup(
            jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAtrasadasLayout.createSequentialGroup()
                        .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLQtdAtrasada))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLValorAtraso))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelAtrasadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLComissaoAtrasada))
                        .addGap(0, 0, 0))
                    .addComponent(jScrollPaneParcelasAtrasadas))
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("Parcelas atrasadas", jPanelAtrasadas);

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
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPaneParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneEmprestimos, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jListColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPaneEmprestimos, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListColaborador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelAbertas;
    private javax.swing.JPanel jPanelAtrasadas;
    private javax.swing.JPanel jPanelRecebidas;
    private javax.swing.JScrollPane jScrollPaneEmprestimos;
    private javax.swing.JScrollPane jScrollPaneParcelasAbertas;
    private javax.swing.JScrollPane jScrollPaneParcelasAtrasadas;
    private javax.swing.JScrollPane jScrollPaneParcelasRecebidas;
    private javax.swing.JTextField jTNome;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPaneParcelas;
    private com.toedter.calendar.JDateChooser jTdataFinal;
    private com.toedter.calendar.JDateChooser jTdataInicial;
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
            emprestimos = Repository.getInstance().BuscarEmprestimoPorDataEColaborador(colaborador, dateInicial, dateFinal);

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
            emprestimos = Repository.getInstance().BuscarEmprestimoPorColaborador(colaborador);
            jBTEmprestimos.removeAll();
            jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
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
                    case "ABERTA":
                        parcelasEmAberto.add(p);
                        break;
                    case "ATRASADA":
                        parcelasAtrasadas.add(p);
                        break;
                    case "RECEBIDA":
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
        jLTotalEmprestimo.setText(format.formatarMoeda(totalVendido));
    }

    private void calcularTotalParcelas() {
        double totalRecebida = 0d;
        double totalAtrasado = 0d;
        double totalAberto = 0d;
        for (Rm_Parcela parcelaRecebida : parcelasRebidas) {
            totalRecebida += parcelaRecebida.getValorRecebido();
        }
        jLTotalParcelaRecebida.setText(format.formatarMoeda(totalRecebida));

        for (Rm_Parcela parcelaAtraso : parcelasAtrasadas) {
            totalAtrasado += parcelaAtraso.getValor();
        }
        jLTotalParcelaAtraso.setText(format.formatarMoeda(totalAtrasado));

        for (Rm_Parcela parcelaAberta : parcelasEmAberto) {
            totalAberto += parcelaAberta.getValor();
        }
        jLTotalParcelaAberta.setText(format.formatarMoeda(totalAberto));

    }

    private void calcularSaldosColaborador() {
        double ganhoComissao = 0d;
        double totalReceber = 0d;
        double parcialComissaoAtrasada = 0d;
        double parcialComissaoAberto = 0d;

        //CALCULAR comissao a receber das parcela ja pagas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasRebidas) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        ganhoComissao += p.getValorRecebido();
                    }

                }

            }
        }
        //calcula parcial a receber das parcelas em aberto e atrazadas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasAtrasadas) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        parcialComissaoAtrasada += p.getValor();
                    }

                }
                for (Rm_Parcela p : parcelasEmAberto) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        parcialComissaoAberto += p.getValor();
                    }

                }

            }
        }
        parcialComissaoAtrasada = parcialComissaoAtrasada * (colaborador.getPorcentagemComissao() / 100);
        parcialComissaoAberto = parcialComissaoAberto * (colaborador.getPorcentagemComissao() / 100);
        ganhoComissao = ganhoComissao * (colaborador.getPorcentagemComissao() / 100);

        jLTotalaReceber.setText(format.formatarMoeda(ganhoComissao + colaborador.getSalario()));
        jLTotalAberto.setText(format.formatarMoeda(parcialComissaoAberto + parcialComissaoAtrasada));
        jLTotalRecebida.setText(format.formatarMoeda(ganhoComissao));
    }

    private void calculoIndividual() {
        double ganhoComissao = 0d;
        double totalRecebido = 0d;
        double totalAberto = 0d;
        double totalAtrasado = 0d;
        double parcialComissaoAtrasada = 0d;
        double parcialComissaoAberto = 0d;

        //CALCULAR comissao a receber das parcela ja pagas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasRebidas) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        ganhoComissao += p.getValorRecebido();
                    }

                }

            }
        }
        //calcula parcial a receber das parcelas em aberto e atrazadas
        for (Rm_Emprestimo emp : emprestimos) {
            if (colaborador.getPorcentagemComissao() > 0) {
                for (Rm_Parcela p : parcelasAtrasadas) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        parcialComissaoAtrasada += p.getValor();
                    }

                }
                for (Rm_Parcela p : parcelasEmAberto) {
                    if (emp.getId() == p.getEmprestimo().getId()) {
                        parcialComissaoAberto += p.getValor();
                    }

                }

            }
        }

        jLComissaoAtrasada.setText(format.formatarMoeda(parcialComissaoAtrasada * (colaborador.getPorcentagemComissao() / 100)));
        jLComissaoAberta.setText(format.formatarMoeda(parcialComissaoAberto * (colaborador.getPorcentagemComissao() / 100)));
        jLComissaoRecebida.setText(format.formatarMoeda(ganhoComissao * (colaborador.getPorcentagemComissao() / 100)));

        jLValorRecebido.setText(format.formatarMoeda(ganhoComissao));
        jLValorAtraso.setText(format.formatarMoeda(parcialComissaoAtrasada));
        jLValorAberta.setText(format.formatarMoeda(parcialComissaoAberto));

        jLQtdParcelaRecebida.setText(String.valueOf(parcelasRebidas.size()));
        jLQtdAtrasada.setText(String.valueOf(parcelasAtrasadas.size()));
        jLQtdAberta.setText(String.valueOf(parcelasEmAberto.size()));

    }

    private void limpar() {
        try {

            jBTParcelasAtrasadas.removeAll();
            jBTParcelasEmAberto.removeAll();
            jBTParcelasRebidas.removeAll();
            parcelasEmAberto.clear();
            parcelasAtrasadas.clear();
            parcelasRebidas.clear();
            emprestimos.clear();

            jBTParcelasAtrasadas.setModeloDeDados(new ArrayList(parcelasAtrasadas));
            jBTParcelasEmAberto.setModeloDeDados(new ArrayList(parcelasEmAberto));
            jBTParcelasRebidas.setModeloDeDados(new ArrayList(parcelasRebidas));
            jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
