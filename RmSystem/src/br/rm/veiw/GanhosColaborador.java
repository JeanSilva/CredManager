/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.controle.FormatacaoMoeda;
import br.rm.controle.JDecimalLabel;
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
    }

    private void criarTabelaEmprestimos() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("cliente.nome", "CLIENTE", true, false, 135, null, null, null));
            columns.add(new JBeanTableColumn("valorEmprestimo", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTEmprestimos = new JBeanTable(Rm_Emprestimo.class, columns);

            this.jBTEmprestimos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2)) {
                        try {

                            int indiceSelecionado = jBTEmprestimos.getSelectedRow();
                            // Produto produto = (Produto) jBTComada.obterBean(indiceSelecionado);
                            Rm_Emprestimo emprestimo = (Rm_Emprestimo) jBTEmprestimos.obterBean(indiceSelecionado);

                            // buscarParcela(emprestimo);
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
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 60, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 80, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorRecebido", "R$ PARCELA RECEBIDA", true, true, 190, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiarioRecebido", "R$ JUROS RECEBIDO", true, true, 190, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasRebidas = new JBeanTable(Rm_Parcela.class, columns);

            this.jScrollPaneParcelasRecebidas.setViewportView(this.jBTParcelasRebidas);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaAtrasadas() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 60, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 80, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiario", "R$ JUROS DIARIO", true, false, 140, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasAtrasadas = new JBeanTable(Rm_Parcela.class, columns);
            this.jScrollPaneParcelasAtrasadas.setViewportView(this.jBTParcelasAtrasadas);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaEmAberto() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 60, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 80, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

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
        jScrollPaneParcelasRecebidas = new javax.swing.JScrollPane();
        jLabel4 = new javax.swing.JLabel();
        jScrollPaneParcelasAtrasadas = new javax.swing.JScrollPane();
        jLabel5 = new javax.swing.JLabel();
        jScrollPaneParcelasAbertas = new javax.swing.JScrollPane();
        jLabel6 = new javax.swing.JLabel();
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
        jLTotalParcelaAReceber = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLTotalaReceber = new javax.swing.JLabel();
        jLTotalAberto = new javax.swing.JLabel();

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
        jLabel3.setText("Contratos Vendidos");

        jScrollPaneParcelasRecebidas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Parcelas Recebidas");

        jScrollPaneParcelasAtrasadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Parcelas Atrasadas");

        jScrollPaneParcelasAbertas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Parcelas Abertas");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("SALÁRIO BASE:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("TOTAL DE EMPRESTIMOS: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL DE PARCELAS RECEBIDAS:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("TOTAL DE PARCELAS A RECEBER:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("COMISSÃO:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("TOTAL A RECEBER:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("TOTAL A RECEBER EM ABERTO:");

        jLComissao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLComissao.setText("valor");

        jLSalarioBase.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLSalarioBase.setText("valor");

        jLTotalEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalEmprestimo.setText("valor");

        jLTotalParcelaRecebida.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaRecebida.setText("valor");

        jLTotalParcelaAReceber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalParcelaAReceber.setText("valor");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Cálculo de recebimentos = SB + Comissão.");

        jLTotalaReceber.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalaReceber.setText("Valor");

        jLTotalAberto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLTotalAberto.setText("valor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLTotalParcelaAReceber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLTotalParcelaRecebida, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                                .addComponent(jLSalarioBase, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLTotalaReceber, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTotalAberto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLSalarioBase))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLTotalEmprestimo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLTotalaReceber))
                                .addGap(15, 15, 15)
                                .addComponent(jLTotalAberto)))))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLTotalParcelaRecebida))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLTotalParcelaAReceber))
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(162, 162, 162)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPaneEmprestimos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jListColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(115, 115, 115)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneEmprestimos, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
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
        } else if (tam.length() > 3) {
            ListaPesquisa();
        }
    }//GEN-LAST:event_jTNomeKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLComissao;
    private javax.swing.JLabel jLSalarioBase;
    private javax.swing.JLabel jLTotalAberto;
    private javax.swing.JLabel jLTotalEmprestimo;
    private javax.swing.JLabel jLTotalParcelaAReceber;
    private javax.swing.JLabel jLTotalParcelaRecebida;
    private javax.swing.JLabel jLTotalaReceber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListColaborador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPaneEmprestimos;
    private javax.swing.JScrollPane jScrollPaneParcelasAbertas;
    private javax.swing.JScrollPane jScrollPaneParcelasAtrasadas;
    private javax.swing.JScrollPane jScrollPaneParcelasRecebidas;
    private javax.swing.JTextField jTNome;
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
                buscarParcelas();
                organizarParcelas();
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
                buscarParcelas();
                organizarParcelas();
                calcular();
            } else {
                JOptionPane.showMessageDialog(null, "NENHUMA VENDA REGISTRADA NESSE PERÍODO", "VENDA NÃO ENCONTRADA", WIDTH);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscarParcelas() {
        parcelasGeral = new ArrayList<>();
        List<Rm_Parcela> parcelas = new ArrayList<>();
        for (Rm_Emprestimo emprestimo : emprestimos) {

            parcelas = Repository.getInstance().BuscarTodasPorEmprestimo(emprestimo);
            if (!parcelas.isEmpty()) {
                for (Rm_Parcela parcela : parcelas) {
                    parcelasGeral.add(parcela);
                }
            }
            //parcelas.removeAll()
        }

    }

    private void organizarParcelas() {
        try {

            for (Rm_Parcela p : parcelasGeral) {

                if (p.getStatus().equals("ABERTA")) {
                    parcelasEmAberto.add(p);
                } else if (p.getStatus().equals("ATRASADA")) {
                    parcelasAtrasadas.add(p);
                } else if (p.getStatus().equals("RECEBIDA")) {

                    parcelasRebidas.add(p);
                }
            }
            jBTParcelasAtrasadas.removeAll();
            jBTParcelasEmAberto.removeAll();
            jBTParcelasRebidas.removeAll();

            jBTParcelasAtrasadas.setModeloDeDados(new ArrayList(parcelasAtrasadas));
            jBTParcelasEmAberto.setModeloDeDados(new ArrayList(parcelasEmAberto));
            jBTParcelasRebidas.setModeloDeDados(new ArrayList(parcelasRebidas));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(GanhosColaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void calcular() {
    }
}
