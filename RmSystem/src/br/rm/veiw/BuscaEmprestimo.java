/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.controle.FormatacaoMoeda;
import br.rm.controle.JDecimalTextField;
import br.rm.controle.ParcelasUtil;
import br.rm.controle.Repository;
import br.rm.controle.StatusEmprestimo;
import br.rm.controle.StatusParcela;
import static br.rm.controle.StatusParcela.ABERTA;
import static br.rm.controle.StatusParcela.ATRASADA;
import static br.rm.controle.StatusParcela.RECEBIDA;
import br.rm.controle.Validacao;
import br.rm.modelo.Rm_Cliente;
import br.rm.modelo.Rm_Colaborador;
import br.rm.modelo.Rm_Emprestimo;
import br.rm.modelo.Rm_Parcela;
import br.rm.tabela.JBeanTable;
import br.rm.tabela.JBeanTableColumn;
import br.rm.tabela.JDecimalTableCellEditor;
import br.rm.tabela.JDecimalTableCellRenderer;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class BuscaEmprestimo extends javax.swing.JInternalFrame {

    private Rm_Cliente cliente;
    DefaultListModel modelo;
    List<Rm_Cliente> clienteList;
    DefaultListModel modeloCobrador;
    private StringBuilder erros;
    private JBeanTable jBTParcelasRebidas;
    private JBeanTable jBTParcelasAtrasadas;
    private JBeanTable jBTParcelasEmAberto;
    private JBeanTable jBTParcelasRecalculo;
    private JBeanTable jBTParcelasNovasRecalculo;
    private Rm_Colaborador colaborador;
    List<Rm_Colaborador> colaboradorList;
    private JBeanTable jBTEmprestimos;
    private List<Rm_Emprestimo> emprestimos;
    private List<Rm_Parcela> parcelasRebidas = new ArrayList<>();
    private List<Rm_Parcela> parcelasAtrasadas = new ArrayList<>();
    private List<Rm_Parcela> parcelasEmAberto = new ArrayList<>();
    private List<Rm_Parcela> parcelasGeral = new ArrayList<>();
    private List<Rm_Parcela> parcelas = new ArrayList<>();
    private List<Rm_Parcela> parcelasNovasRecalculo = new ArrayList<>();
    private List<Rm_Parcela> parcelasRecalculo = new ArrayList<>();
    List<Rm_Parcela> parcelasRecebidasRecalculo = new ArrayList<>();
    private FormatacaoMoeda format;
    private Rm_Emprestimo emprestimo;
    double valorPago = 0d;
    double valorAPagar = 0;

    public BuscaEmprestimo() {
        initComponents();
        emprestimo = new Rm_Emprestimo();
        colaborador = new Rm_Colaborador();
        jListCliente.setVisible(true);
        jListCobrador.setVisible(true);
        modelo = new DefaultListModel();
        modeloCobrador = new DefaultListModel();
        jListCliente.setModel(modelo);
        jListCobrador.setModel(modeloCobrador);
        jCheckBoxTodos.setSelected(true);
        format = new FormatacaoMoeda();
        criarTabelaEmprestimos();
        criarTabelaParcelaAtrasadas();
        criarTabelaParcelaEmAberto();
        criarTabelaParcelaRecebida();
        criarTabelaParcelaPagaRecalculo();
        criarTabelaNovasParcelas();
        jTabbedPrincipal.setEnabledAt(1, false);
           setFrameIcon(new ImageIcon(getClass().getResource("/icons/search-alt.png")));

    }

    private void criarTabelaEmprestimos() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("cliente.nome", "CLIENTE", true, false, 173, null, null, null));
            columns.add(new JBeanTableColumn("valorEmprestimo", "VALOR", true, false, 100, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("status", "STATUS", true, false, 100, null, null, null));

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
                            carregarInfoCliente(emprestimo.getCliente());
                            carregarInfoeEmprestimo(emprestimo);
                            organizarParcelas();

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
            columns.add(new JBeanTableColumn("valorRecebido", "VALOR RECEBIDO", true, false, 143, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("dataRecebimentoFormatada", "PAGAMENTO EM", true, false, 143, null, null, null));
            columns.add(new JBeanTableColumn("valorJurosDiario", "JUROS DIARIO", true, false, 138, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiarioRecebido", "JUROS DIARIO RECEBIDO", true, false, 178, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPrincipal = new javax.swing.JTabbedPane();
        jScrollPaneEdicao = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jTdataInicial = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTdataFinal = new com.toedter.calendar.JDateChooser();
        jCheckBoxAberto = new javax.swing.JCheckBox();
        jCheckBoxFinalizado = new javax.swing.JCheckBox();
        jCheckBoxTodos = new javax.swing.JCheckBox();
        jBuscar = new javax.swing.JButton();
        jTNome = new br.rm.controle.JTextFieldHint(new JTextField(), "Nome do cliente");
        ;
        jListCliente = new javax.swing.JList<>();
        jTabbedPaneInformacoes = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTNomeCliente = new br.rm.controle.JTextFieldHint(new JTextField(), "Nome cliente");
        ;
        jTCpf = new br.rm.controle.JTextFieldHint(new JTextField(), "CPF - 000.000.000-00");
        ;
        jTelefone = new br.rm.controle.JTextFieldHint(new JTextField(), "Telefone")
        ;
        ;
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTEndereco = new br.rm.controle.JTextFieldHint(new JTextField(), "Endereço")
        ;
        ;
        jTNumero = new br.rm.controle.JTextFieldHint(new JTextField(), "Numero");
        ;
        jTBairro = new br.rm.controle.JTextFieldHint(new JTextField(), "Bairro");
        ;
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTRecado = new br.rm.controle.JTextFieldHint(new JTextField(), "Telefone Recado");
        ;
        jLabel11 = new javax.swing.JLabel();
        jTCep = new br.rm.controle.JTextFieldHint(new JTextField(), "Cep");
        ;
        jLabel12 = new javax.swing.JLabel();
        jTEmail = new br.rm.controle.JTextFieldHint(new JTextField(), "Email");
        ;
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTNomeComercio = new br.rm.controle.JTextFieldHint(new JTextField(), "Nome do Comércio");
        ;
        jTCnpj = new br.rm.controle.JTextFieldHint(new JTextField(), "CNPJ - 00.000.000/0001-00");
        ;
        jTRuaComercio = new br.rm.controle.JTextFieldHint(new JTextField(), "Rua");
        ;
        jTBairroComercio = new br.rm.controle.JTextFieldHint(new JTextField(), "Bairro"

        );
        ;
        jTNumeroComercio = new br.rm.controle.JTextFieldHint(new JTextField(), "Número");
        ;
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTValorEmprestar = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel20 = new javax.swing.JLabel();
        jTjuros = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel21 = new javax.swing.JLabel();
        jTPagamentoTotal = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel22 = new javax.swing.JLabel();
        jTLucro = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel23 = new javax.swing.JLabel();
        jTQtdParcela = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel24 = new javax.swing.JLabel();
        jTValorParcela = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel25 = new javax.swing.JLabel();
        jTdataLacamento = new com.toedter.calendar.JDateChooser();
        jTNomeCobrador = new br.rm.controle.JTextFieldHint(new JTextField(), "Nome do cobrador");
        ;
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTvalorJurosDiario = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel28 = new javax.swing.JLabel();
        jLTipo = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLStatus = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPaneParcelasRecebidas = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPaneParcelasAbertas = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPaneParcelasAtrasadas = new javax.swing.JScrollPane();
        jTabbedPaneEmprestimo = new javax.swing.JTabbedPane();
        jScrollPaneEmprestimos = new javax.swing.JScrollPane();
        jBExcluir = new javax.swing.JButton();
        jBEditar = new javax.swing.JButton();
        jScrollPaneRecalculo = new javax.swing.JScrollPane();
        jPanelRecalcularEmprestimo = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPaneParcelasRecalculo = new javax.swing.JScrollPane();
        jLabel36 = new javax.swing.JLabel();
        jLValorJaPago = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLTotalEmprestimo = new javax.swing.JLabel();
        jLValorAtraso = new javax.swing.JLabel();
        jBFinalizar = new javax.swing.JButton();
        jPanelNovoEmprestimo = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTValorEmprestarNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel3 = new javax.swing.JLabel();
        jTPagamentoTotalNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel4 = new javax.swing.JLabel();
        jTLucroNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel30 = new javax.swing.JLabel();
        jTQtdParcelaNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel31 = new javax.swing.JLabel();
        jTdataLacamentoNovo = new com.toedter.calendar.JDateChooser();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTjurosNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel34 = new javax.swing.JLabel();
        jTCobrador = new br.rm.controle.JTextFieldHint(new JTextField(), "Cobrador");
        ;
        jListCobrador = new javax.swing.JList<>();
        jCheckDiario = new javax.swing.JCheckBox();
        jCheckSemanal = new javax.swing.JCheckBox();
        jCheckQuinzenal = new javax.swing.JCheckBox();
        jCheckMensal = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        jTvalorJurosDiarioNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jTValorParcelaNovo = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jBGerarParcela = new javax.swing.JButton();
        tipoSegundaSexta = new javax.swing.JCheckBox();
        tipoSegundaSabado = new javax.swing.JCheckBox();
        tipoSegundaSegunda = new javax.swing.JCheckBox();
        jPanelNovasParcelas = new javax.swing.JPanel();
        jScrollPaneNovasParcelas = new javax.swing.JScrollPane();
        jBSalva = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setTitle("Buscar empréstimo");

        jTabbedPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneEdicao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTdataInicial.setBackground(new java.awt.Color(255, 255, 255));
        jTdataInicial.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTdataInicial.setForeground(new java.awt.Color(255, 255, 255));
        jTdataInicial.setDateFormatString("dd/MM/yyyy");
        jTdataInicial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Data inicio");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Data final");

        jTdataFinal.setBackground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTdataFinal.setForeground(new java.awt.Color(255, 255, 255));
        jTdataFinal.setToolTipText("Até");
        jTdataFinal.setDateFormatString("dd/MM/yyyy");
        jTdataFinal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        buttonGroup1.add(jCheckBoxAberto);
        jCheckBoxAberto.setText("ABERTO");

        buttonGroup1.add(jCheckBoxFinalizado);
        jCheckBoxFinalizado.setText("FINALIZADO");

        buttonGroup1.add(jCheckBoxTodos);
        jCheckBoxTodos.setText("TODOS");

        jBuscar.setBackground(new java.awt.Color(51, 51, 51));
        jBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jBuscar.setText("Listar");
        jBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBuscar.setFocusPainted(false);
        jBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBuscarMouseExited(evt);
            }
        });
        jBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscarActionPerformed(evt);
            }
        });

        jTNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNome.setForeground(new java.awt.Color(255, 255, 255));
        jTNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTNomeActionPerformed(evt);
            }
        });
        jTNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomeKeyPressed(evt);
            }
        });

        jListCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jListCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jListCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListClienteMouseClicked(evt);
            }
        });

        jTabbedPaneInformacoes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPaneInformacoes.setToolTipText("");
        jTabbedPaneInformacoes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTNomeCliente.setEditable(false);
        jTNomeCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNomeCliente.setForeground(new java.awt.Color(255, 255, 255));

        jTCpf.setEditable(false);
        jTCpf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCpf.setForeground(new java.awt.Color(255, 255, 255));
        jTCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCpfActionPerformed(evt);
            }
        });

        jTelefone.setEditable(false);
        jTelefone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTelefone.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nome");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Telefone");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Telefone recado");

        jTEndereco.setEditable(false);
        jTEndereco.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTEndereco.setForeground(new java.awt.Color(255, 255, 255));

        jTNumero.setEditable(false);
        jTNumero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNumero.setForeground(new java.awt.Color(255, 255, 255));

        jTBairro.setEditable(false);
        jTBairro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTBairro.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Endereço");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Nº");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Bairro");

        jTRecado.setEditable(false);
        jTRecado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTRecado.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Cep");

        jTCep.setEditable(false);
        jTCep.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCep.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("CPF");

        jTEmail.setEditable(false);
        jTEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTEmail.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Email");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jTRecado))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTCep, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11))
                    .addComponent(jLabel12))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTCep, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTRecado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6))
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jTabbedPaneInformacoes.addTab("Info Cliente", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTNomeComercio.setEditable(false);
        jTNomeComercio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNomeComercio.setForeground(new java.awt.Color(255, 255, 255));

        jTCnpj.setEditable(false);
        jTCnpj.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCnpj.setForeground(new java.awt.Color(255, 255, 255));
        jTCnpj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCnpjFocusLost(evt);
            }
        });

        jTRuaComercio.setEditable(false);
        jTRuaComercio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTRuaComercio.setForeground(new java.awt.Color(255, 255, 255));
        jTRuaComercio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTRuaComercioActionPerformed(evt);
            }
        });

        jTBairroComercio.setEditable(false);
        jTBairroComercio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTBairroComercio.setForeground(new java.awt.Color(255, 255, 255));

        jTNumeroComercio.setEditable(false);
        jTNumeroComercio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNumeroComercio.setForeground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Nome ");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("CNPJ");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Bairro");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Rua");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Número");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jTNumeroComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTNomeComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTBairroComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTCnpj)
                                .addComponent(jTRuaComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(549, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTNomeComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTRuaComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBairroComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel18)
                .addGap(0, 0, 0)
                .addComponent(jTNumeroComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jTabbedPaneInformacoes.addTab("Info Comércio", jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("R$ Valor ");

        jTValorEmprestar.setEditable(false);
        jTValorEmprestar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTValorEmprestar.setForeground(new java.awt.Color(255, 255, 255));
        jTValorEmprestar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTValorEmprestarFocusLost(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("% Juros");

        jTjuros.setEditable(false);
        jTjuros.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTjuros.setForeground(new java.awt.Color(255, 255, 255));
        jTjuros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTjurosFocusLost(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("R$ Valor total");

        jTPagamentoTotal.setEditable(false);
        jTPagamentoTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTPagamentoTotal.setForeground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("R$ Lucro ");

        jTLucro.setEditable(false);
        jTLucro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTLucro.setForeground(new java.awt.Color(255, 255, 255));
        jTLucro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTLucroActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Qtd. parcelas");

        jTQtdParcela.setEditable(false);
        jTQtdParcela.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTQtdParcela.setForeground(new java.awt.Color(255, 255, 255));
        jTQtdParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTQtdParcelaFocusLost(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("R$ Valor Parc.");

        jTValorParcela.setEditable(false);
        jTValorParcela.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTValorParcela.setForeground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Data lançamento");

        jTdataLacamento.setBackground(new java.awt.Color(255, 255, 255));
        jTdataLacamento.setForeground(new java.awt.Color(255, 255, 255));
        jTdataLacamento.setDateFormatString("dd/MM/yyyy");
        jTdataLacamento.setEnabled(false);
        jTdataLacamento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTdataLacamento.setOpaque(false);

        jTNomeCobrador.setEditable(false);
        jTNomeCobrador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNomeCobrador.setForeground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Cobrador");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("R$ Juros díario  ");

        jTvalorJurosDiario.setEditable(false);
        jTvalorJurosDiario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTvalorJurosDiario.setForeground(new java.awt.Color(255, 255, 255));
        jTvalorJurosDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTvalorJurosDiarioActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Tipo:");

        jLTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLTipo.setText("Tipo");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Status:");

        jLStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLStatus.setText("Status");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTQtdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                        .addComponent(jTValorEmprestar)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTValorParcela, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(jTjuros))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTPagamentoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel27)
                                    .addComponent(jTvalorJurosDiario)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTNomeCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTdataLacamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(24, 24, 24)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTLucro)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(2, 2, 2)
                        .addComponent(jTLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel25)
                        .addGap(0, 0, 0)
                        .addComponent(jTdataLacamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(jLTipo))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(jLStatus))
                            .addComponent(jTNomeCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(2, 2, 2)
                                .addComponent(jTValorEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTjuros, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel23))
                                        .addGap(25, 25, 25))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTQtdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTPagamentoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)
                                .addComponent(jLabel27)
                                .addGap(3, 3, 3)
                                .addComponent(jTvalorJurosDiario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, 0))
        );

        jTabbedPaneInformacoes.addTab("Info Emprestimo", jPanel4);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneParcelasRecebidas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasRecebidas, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parcelas pagas", jPanel5);

        jScrollPaneParcelasAbertas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasAbertas, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parcelas à vencer", jPanel6);

        jScrollPaneParcelasAtrasadas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneParcelasAtrasadas, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parcelas em atraso", jPanel7);

        jTabbedPaneEmprestimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPaneEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jScrollPaneEmprestimos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPaneEmprestimos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPaneEmprestimo.addTab("Empréstimos", jScrollPaneEmprestimos);

        jBExcluir.setBackground(new java.awt.Color(51, 51, 51));
        jBExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBExcluir.setForeground(new java.awt.Color(255, 255, 255));
        jBExcluir.setText("Excluir");
        jBExcluir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBExcluir.setFocusPainted(false);
        jBExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBExcluirMouseExited(evt);
            }
        });
        jBExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirActionPerformed(evt);
            }
        });

        jBEditar.setBackground(new java.awt.Color(51, 51, 51));
        jBEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBEditar.setForeground(new java.awt.Color(255, 255, 255));
        jBEditar.setText("Editar");
        jBEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBEditar.setFocusPainted(false);
        jBEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEditarMouseExited(evt);
            }
        });
        jBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBoxAberto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jCheckBoxFinalizado)
                                .addGap(20, 20, 20)
                                .addComponent(jCheckBoxTodos))
                            .addComponent(jTNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jListCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jTabbedPaneInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177)
                        .addComponent(jBEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jTabbedPaneEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPaneInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, 0)
                                .addComponent(jTdataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, 0)
                                .addComponent(jTdataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxAberto)
                                    .addComponent(jCheckBoxFinalizado)
                                    .addComponent(jCheckBoxTodos))
                                .addGap(10, 10, 10)
                                .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jListCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addComponent(jBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPaneEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );

        jTabbedPaneInformacoes.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleName("Parcelas Recebidas");

        jScrollPaneEdicao.setViewportView(jPanel2);

        jTabbedPrincipal.addTab("Buscar Empréstimos", jScrollPaneEdicao);

        jScrollPaneRecalculo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPaneParcelasRecalculo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Parcelas pagas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setText("Total pago:");

        jLValorJaPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Total a pagar:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setText("Valor em atraso:");

        jLTotalEmprestimo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLValorAtraso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jBFinalizar.setBackground(new java.awt.Color(51, 51, 51));
        jBFinalizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        jBFinalizar.setText("Finalizar ");
        jBFinalizar.setToolTipText("Finaliza e baixa emprestimo com o valor recebido parcial");
        jBFinalizar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBFinalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBFinalizar.setFocusPainted(false);
        jBFinalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFinalizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFinalizarMouseExited(evt);
            }
        });
        jBFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneParcelasRecalculo)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLValorJaPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(3, 3, 3)
                                .addComponent(jLTotalEmprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLValorAtraso, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPaneParcelasRecalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLTotalEmprestimo))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLValorJaPago))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(jLValorAtraso)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelNovoEmprestimo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Dados do novo calculo", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jTValorEmprestarNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTValorEmprestarNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTValorEmprestarNovo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTValorEmprestarNovoFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Data lançamento");

        jTPagamentoTotalNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTPagamentoTotalNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTPagamentoTotalNovo.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("R$ Valor total");

        jTLucroNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTLucroNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTLucroNovo.setEnabled(false);
        jTLucroNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTLucroNovoActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("R$ Lucro ");

        jTQtdParcelaNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTQtdParcelaNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTQtdParcelaNovo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTQtdParcelaNovoFocusLost(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Qtd. parcelas");

        jTdataLacamentoNovo.setBackground(new java.awt.Color(255, 255, 255));
        jTdataLacamentoNovo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTdataLacamentoNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTdataLacamentoNovo.setDateFormatString("dd/MM/yyyy");
        jTdataLacamentoNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTdataLacamentoNovo.setOpaque(false);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("R$ Valor ");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("% Juros");

        jTjurosNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTjurosNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTjurosNovo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTjurosNovoFocusLost(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("R$ Valor Parc.");

        jTCobrador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCobrador.setForeground(new java.awt.Color(255, 255, 255));
        jTCobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCobradorKeyPressed(evt);
            }
        });

        jListCobrador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jListCobrador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jListCobrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCobradorMouseClicked(evt);
            }
        });

        buttonGroup1.add(jCheckDiario);
        jCheckDiario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckDiario.setText("Diário");
        jCheckDiario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckDiarioMouseClicked(evt);
            }
        });
        jCheckDiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckDiarioActionPerformed(evt);
            }
        });

        buttonGroup1.add(jCheckSemanal);
        jCheckSemanal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckSemanal.setText("Semanal");

        buttonGroup1.add(jCheckQuinzenal);
        jCheckQuinzenal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckQuinzenal.setText("Quinzenal");
        jCheckQuinzenal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckQuinzenalActionPerformed(evt);
            }
        });

        buttonGroup1.add(jCheckMensal);
        jCheckMensal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckMensal.setText("Mensal");
        jCheckMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckMensalActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("R$ Juros díario  ");

        jTvalorJurosDiarioNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTvalorJurosDiarioNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTvalorJurosDiarioNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTvalorJurosDiarioNovoActionPerformed(evt);
            }
        });

        jTValorParcelaNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTValorParcelaNovo.setForeground(new java.awt.Color(255, 255, 255));
        jTValorParcelaNovo.setEnabled(false);

        jBGerarParcela.setBackground(new java.awt.Color(51, 51, 51));
        jBGerarParcela.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBGerarParcela.setForeground(new java.awt.Color(255, 255, 255));
        jBGerarParcela.setText("Gerar novas parcelas");
        jBGerarParcela.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBGerarParcela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGerarParcela.setFocusPainted(false);
        jBGerarParcela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGerarParcelaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGerarParcelaMouseExited(evt);
            }
        });
        jBGerarParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGerarParcelaActionPerformed(evt);
            }
        });

        buttonGroup2.add(tipoSegundaSexta);
        tipoSegundaSexta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tipoSegundaSexta.setText("Segunda á sexta");

        buttonGroup2.add(tipoSegundaSabado);
        tipoSegundaSabado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tipoSegundaSabado.setText("Segunda á sábado");

        buttonGroup2.add(tipoSegundaSegunda);
        tipoSegundaSegunda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tipoSegundaSegunda.setText("Segunda á segunda");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jListCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(tipoSegundaSexta)
                                .addGap(18, 18, 18)
                                .addComponent(tipoSegundaSabado)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jBGerarParcela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTvalorJurosDiarioNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35)
                                    .addComponent(tipoSegundaSegunda)
                                    .addComponent(jCheckMensal))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jCheckDiario)
                                .addGap(30, 30, 30)
                                .addComponent(jCheckSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jCheckQuinzenal)
                                .addGap(15, 15, 15))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTValorEmprestarNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTjurosNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTPagamentoTotalNovo)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTLucroNovo)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTQtdParcelaNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTValorParcelaNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTdataLacamentoNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(2, 2, 2)
                        .addComponent(jTLucroNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(2, 2, 2)
                        .addComponent(jTValorEmprestarNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTPagamentoTotalNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jTjurosNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel33)
                            .addGap(27, 27, 27))))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(jLabel31)
                            .addComponent(jLabel3))
                        .addGap(2, 2, 2)
                        .addComponent(jTdataLacamentoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTQtdParcelaNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTValorParcelaNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckDiario)
                    .addComponent(jCheckSemanal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckMensal)
                    .addComponent(jCheckQuinzenal))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoSegundaSabado)
                    .addComponent(tipoSegundaSexta)
                    .addComponent(tipoSegundaSegunda))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(3, 3, 3)
                        .addComponent(jTvalorJurosDiarioNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBGerarParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jTCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jListCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelNovoEmprestimoLayout = new javax.swing.GroupLayout(jPanelNovoEmprestimo);
        jPanelNovoEmprestimo.setLayout(jPanelNovoEmprestimoLayout);
        jPanelNovoEmprestimoLayout.setHorizontalGroup(
            jPanelNovoEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNovoEmprestimoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanelNovoEmprestimoLayout.setVerticalGroup(
            jPanelNovoEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNovoEmprestimoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanelNovasParcelas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPaneNovasParcelas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Novas parcelas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanelNovasParcelasLayout = new javax.swing.GroupLayout(jPanelNovasParcelas);
        jPanelNovasParcelas.setLayout(jPanelNovasParcelasLayout);
        jPanelNovasParcelasLayout.setHorizontalGroup(
            jPanelNovasParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNovasParcelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneNovasParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelNovasParcelasLayout.setVerticalGroup(
            jPanelNovasParcelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNovasParcelasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneNovasParcelas)
                .addContainerGap())
        );

        jBSalva.setBackground(new java.awt.Color(51, 51, 51));
        jBSalva.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBSalva.setForeground(new java.awt.Color(255, 255, 255));
        jBSalva.setText("Salvar");
        jBSalva.setToolTipText("cria novo emprestimo ");
        jBSalva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSalva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSalva.setFocusPainted(false);
        jBSalva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSalvaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSalvaMouseExited(evt);
            }
        });
        jBSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRecalcularEmprestimoLayout = new javax.swing.GroupLayout(jPanelRecalcularEmprestimo);
        jPanelRecalcularEmprestimo.setLayout(jPanelRecalcularEmprestimoLayout);
        jPanelRecalcularEmprestimoLayout.setHorizontalGroup(
            jPanelRecalcularEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecalcularEmprestimoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelRecalcularEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelRecalcularEmprestimoLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanelNovoEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanelNovasParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelRecalcularEmprestimoLayout.setVerticalGroup(
            jPanelRecalcularEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRecalcularEmprestimoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanelRecalcularEmprestimoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelNovoEmprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelNovasParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPaneRecalculo.setViewportView(jPanelRecalcularEmprestimo);

        jTabbedPrincipal.addTab("Editar e Recálcular Empréstimos", jScrollPaneRecalculo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBuscarMouseEntered
        jBuscar.setBackground(new Color(153, 153, 0));
        jBuscar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBuscarMouseEntered

    private void jBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBuscarMouseExited
        jBuscar.setBackground(new Color(51, 51, 51));
        jBuscar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBuscarMouseExited

    private void jBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscarActionPerformed
        buscarEmprestimo();
    }//GEN-LAST:event_jBuscarActionPerformed

    private void jTNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomeKeyPressed
        String tam = jTNome.getText();
        if (jTNome.getText().isEmpty()) {
            jListCliente.setVisible(false);
        } else if (tam.length() > 2) {
            ListaPesquisaCliente();
        }
    }//GEN-LAST:event_jTNomeKeyPressed

    private void jListClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListClienteMouseClicked
        inserirCliente();

        jListCliente.setVisible(false);
    }//GEN-LAST:event_jListClienteMouseClicked

    private void jTNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTNomeActionPerformed

    private void jTCnpjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCnpjFocusLost


    }//GEN-LAST:event_jTCnpjFocusLost

    private void jTRuaComercioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTRuaComercioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTRuaComercioActionPerformed

    private void jTValorEmprestarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValorEmprestarFocusLost

    }//GEN-LAST:event_jTValorEmprestarFocusLost

    private void jTjurosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTjurosFocusLost


    }//GEN-LAST:event_jTjurosFocusLost

    private void jTLucroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTLucroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTLucroActionPerformed

    private void jTQtdParcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTQtdParcelaFocusLost


    }//GEN-LAST:event_jTQtdParcelaFocusLost

    private void jTvalorJurosDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTvalorJurosDiarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTvalorJurosDiarioActionPerformed

    private void jBExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExcluirMouseEntered
        jBExcluir.setBackground(new Color(153, 153, 0));
        jBExcluir.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBExcluirMouseEntered

    private void jBExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExcluirMouseExited
        jBExcluir.setBackground(new Color(51, 51, 51));
        jBExcluir.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBExcluirMouseExited

    private void jBExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirActionPerformed
        excluirEmprestimo();
    }//GEN-LAST:event_jBExcluirActionPerformed

    private void jBEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEditarMouseEntered
        jBEditar.setBackground(new Color(153, 153, 0));
        jBEditar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBEditarMouseEntered

    private void jBEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEditarMouseExited
        jBEditar.setBackground(new Color(51, 51, 51));
        jBEditar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBEditarMouseExited

    private void jBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarActionPerformed
        verificarPossivelRecalculo();

    }//GEN-LAST:event_jBEditarActionPerformed

    private void jTValorEmprestarNovoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValorEmprestarNovoFocusLost
//        calcularTotal();
    }//GEN-LAST:event_jTValorEmprestarNovoFocusLost

    private void jTLucroNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTLucroNovoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTLucroNovoActionPerformed

    private void jTQtdParcelaNovoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTQtdParcelaNovoFocusLost
        Double totalPagar = ((JDecimalTextField) jTPagamentoTotalNovo).getDoubleValue();
        Double qtdParcelas = ((JDecimalTextField) jTQtdParcelaNovo).getDoubleValue();
        ((JDecimalTextField) jTValorParcelaNovo).setDoubleValue(totalPagar / qtdParcelas);

    }//GEN-LAST:event_jTQtdParcelaNovoFocusLost

    private void jTjurosNovoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTjurosNovoFocusLost
        calcularTotal();

    }//GEN-LAST:event_jTjurosNovoFocusLost

    private void jTCobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCobradorKeyPressed
        String tam = jTCobrador.getText();

        if (jTCobrador.getText().isEmpty()) {
            jListCobrador.setVisible(false);
        } else if (tam.length() > 2) {
            ListaPesquisa();
        }
    }//GEN-LAST:event_jTCobradorKeyPressed

    private void jListCobradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCobradorMouseClicked
        inserirCobrador();
        jListCobrador.setVisible(false);
    }//GEN-LAST:event_jListCobradorMouseClicked

    private void jCheckDiarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckDiarioMouseClicked

    }//GEN-LAST:event_jCheckDiarioMouseClicked

    private void jCheckDiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckDiarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckDiarioActionPerformed

    private void jCheckQuinzenalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckQuinzenalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckQuinzenalActionPerformed

    private void jCheckMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckMensalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckMensalActionPerformed

    private void jTvalorJurosDiarioNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTvalorJurosDiarioNovoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTvalorJurosDiarioNovoActionPerformed

    private void jBGerarParcelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGerarParcelaMouseEntered
        jBGerarParcela.setBackground(new Color(153, 153, 0));
        jBGerarParcela.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBGerarParcelaMouseEntered

    private void jBGerarParcelaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGerarParcelaMouseExited
        jBGerarParcela.setBackground(new Color(51, 51, 51));
        jBGerarParcela.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBGerarParcelaMouseExited

    private void jBGerarParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGerarParcelaActionPerformed
        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, erros, "Campo Obrigatórios", WIDTH);

        } else {
            addParcela();
        }
    }//GEN-LAST:event_jBGerarParcelaActionPerformed

    private void jBFinalizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFinalizarMouseEntered
        jBFinalizar.setBackground(new Color(153, 153, 0));
        jBFinalizar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBFinalizarMouseEntered

    private void jBFinalizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFinalizarMouseExited
        jBFinalizar.setBackground(new Color(51, 51, 51));
        jBFinalizar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBFinalizarMouseExited

    private void jBFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinalizarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, mensagem(), "Confirmação", JOptionPane.YES_NO_OPTION);

        switch (resposta) {
            // Ação a ser executada caso o usuário clique em "Sim"
            case JOptionPane.YES_OPTION:

                calcularEFinalizarEmprestimo();
                jPanelNovoEmprestimo.setVisible(true);
                jPanelNovasParcelas.setVisible(true);
                jBSalva.setVisible(true);
                break;
            // Ação a ser executada caso o usuário clique em "Não"
            case JOptionPane.NO_OPTION:

                return;

            // Ação a ser executada caso o usuário feche a caixa de diálogo sem escolher uma opção
            default:
                return;
        }
    }//GEN-LAST:event_jBFinalizarActionPerformed

    private void jTCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCpfActionPerformed

    private void jBSalvaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvaMouseEntered
        jBSalva.setBackground(new Color(153, 153, 0));
        jBSalva.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvaMouseEntered

    private void jBSalvaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvaMouseExited
        jBSalva.setBackground(new Color(51, 51, 51));
        jBSalva.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvaMouseExited

    private void jBSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvaActionPerformed

        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, erros, "Campo Obrigatórios", WIDTH);

        } else if (parcelasNovasRecalculo == null) {
            JOptionPane.showMessageDialog(null, "Gere pelo menos uma parcela", "Campo Obrigatorio", WIDTH);

        } else {

            criarEmprestimo();
        }

    }//GEN-LAST:event_jBSalvaActionPerformed
    private StringBuilder mensagem() {
        StringBuilder produtosEmFalta = new StringBuilder();

        produtosEmFalta.append("Ao prosseguir com essa ação \n");
        produtosEmFalta.append("Sera finalizado o empréstimo atual no valor de: ").append(jLTotalEmprestimo.getText()).append("\n");
        produtosEmFalta.append("sera quitado com o valor de: ").append(jLValorJaPago.getText()).append("\n");
        produtosEmFalta.append("referente ao valor já pago pelo cliente \n");
        produtosEmFalta.append("valor de: ").append(jLValorAtraso.getText()).append(" Ficará em aberto \n");
        produtosEmFalta.append("e será necessário informalo para recálculo \n ");
        produtosEmFalta.append("\n DESEJA REALMENTE PROSSEGUIR COM A AÇÃO ?");
        return produtosEmFalta;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jBEditar;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBFinalizar;
    private javax.swing.JButton jBGerarParcela;
    private javax.swing.JButton jBSalva;
    private javax.swing.JButton jBuscar;
    private javax.swing.JCheckBox jCheckBoxAberto;
    private javax.swing.JCheckBox jCheckBoxFinalizado;
    private javax.swing.JCheckBox jCheckBoxTodos;
    private javax.swing.JCheckBox jCheckDiario;
    private javax.swing.JCheckBox jCheckMensal;
    private javax.swing.JCheckBox jCheckQuinzenal;
    private javax.swing.JCheckBox jCheckSemanal;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLTipo;
    private javax.swing.JLabel jLTotalEmprestimo;
    private javax.swing.JLabel jLValorAtraso;
    private javax.swing.JLabel jLValorJaPago;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListCliente;
    private javax.swing.JList<String> jListCobrador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelNovasParcelas;
    private javax.swing.JPanel jPanelNovoEmprestimo;
    private javax.swing.JPanel jPanelRecalcularEmprestimo;
    private javax.swing.JScrollPane jScrollPaneEdicao;
    private javax.swing.JScrollPane jScrollPaneEmprestimos;
    private javax.swing.JScrollPane jScrollPaneNovasParcelas;
    private javax.swing.JScrollPane jScrollPaneParcelasAbertas;
    private javax.swing.JScrollPane jScrollPaneParcelasAtrasadas;
    private javax.swing.JScrollPane jScrollPaneParcelasRecalculo;
    private javax.swing.JScrollPane jScrollPaneParcelasRecebidas;
    private javax.swing.JScrollPane jScrollPaneRecalculo;
    private javax.swing.JTextField jTBairro;
    private javax.swing.JTextField jTBairroComercio;
    private javax.swing.JTextField jTCep;
    private javax.swing.JTextField jTCnpj;
    private javax.swing.JTextField jTCobrador;
    private javax.swing.JTextField jTCpf;
    private javax.swing.JTextField jTEmail;
    private javax.swing.JTextField jTEndereco;
    private javax.swing.JTextField jTLucro;
    private javax.swing.JTextField jTLucroNovo;
    private javax.swing.JTextField jTNome;
    private javax.swing.JTextField jTNomeCliente;
    private javax.swing.JTextField jTNomeCobrador;
    private javax.swing.JTextField jTNomeComercio;
    private javax.swing.JTextField jTNumero;
    private javax.swing.JTextField jTNumeroComercio;
    private javax.swing.JTextField jTPagamentoTotal;
    private javax.swing.JTextField jTPagamentoTotalNovo;
    private javax.swing.JTextField jTQtdParcela;
    private javax.swing.JTextField jTQtdParcelaNovo;
    private javax.swing.JTextField jTRecado;
    private javax.swing.JTextField jTRuaComercio;
    private javax.swing.JTextField jTValorEmprestar;
    private javax.swing.JTextField jTValorEmprestarNovo;
    private javax.swing.JTextField jTValorParcela;
    private javax.swing.JTextField jTValorParcelaNovo;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPaneEmprestimo;
    private javax.swing.JTabbedPane jTabbedPaneInformacoes;
    private javax.swing.JTabbedPane jTabbedPrincipal;
    private com.toedter.calendar.JDateChooser jTdataFinal;
    private com.toedter.calendar.JDateChooser jTdataInicial;
    private com.toedter.calendar.JDateChooser jTdataLacamento;
    private com.toedter.calendar.JDateChooser jTdataLacamentoNovo;
    private javax.swing.JTextField jTelefone;
    private javax.swing.JTextField jTjuros;
    private javax.swing.JTextField jTjurosNovo;
    private javax.swing.JTextField jTvalorJurosDiario;
    private javax.swing.JTextField jTvalorJurosDiarioNovo;
    private javax.swing.JCheckBox tipoSegundaSabado;
    private javax.swing.JCheckBox tipoSegundaSegunda;
    private javax.swing.JCheckBox tipoSegundaSexta;
    // End of variables declaration//GEN-END:variables
 private void ListaPesquisa() {
        modelo = new DefaultListModel();
        colaboradorList = Repository.getInstance().buscarColaboradorNome(jTCobrador.getText());
        modelo.removeAllElements();
        if ((colaboradorList != null) && (!colaboradorList.isEmpty())) {
            for (Rm_Colaborador c : colaboradorList) {
                modeloCobrador.addElement(c.getNome());

            }

            jListCobrador.setModel(modeloCobrador);
            jListCobrador.setVisible(true);
        }
    }

    private void ListaPesquisaCliente() {
        clienteList = Repository.getInstance().buscarClienteNome(jTNome.getText());
        if ((clienteList != null) && (!clienteList.isEmpty())) {
            for (Rm_Cliente c : clienteList) {
                modelo.addElement(c.getNome());

            }
            jListCliente.setModel(modelo);
            jListCliente.setVisible(true);
        }

    }

    private void inserirCliente() {
        int linha = jListCliente.getSelectedIndex();
        for (Rm_Cliente c : clienteList) {
            if (c.getNome().equals(modelo.getElementAt(linha))) {
                cliente = c;
                jTNome.setText(cliente.getNome());
                carregarInfoCliente(cliente);
            }
        }

    }

    private void carregarInfoCliente(Rm_Cliente cliente) {
        jTNomeCliente.setText(cliente.getNome());
        jTCpf.setText(new Validacao().formatarCpf(cliente.getCpf()));
        jTelefone.setText(cliente.getTelefone());
        jTRecado.setText(cliente.getTelefoneRecado());
        jTEmail.setText(cliente.getEmail());

        //Endereco
        jTEndereco.setText(cliente.getRua());
        jTBairro.setText(cliente.getBairro());
        jTNumero.setText(cliente.getNumero());
        jTCep.setText(cliente.getCep());

        //dados comercio
        jTNomeComercio.setText(cliente.getNomeComercio());
        jTCnpj.setText(cliente.getCnpj());
        jTRuaComercio.setText(cliente.getRuaComercio());
        jTNumeroComercio.setText(cliente.getNumeroComercio());
    }

    private void carregarInfoeEmprestimo(Rm_Emprestimo emprestimo) {
        jTNomeCobrador.setText(emprestimo.getColaborador().getNome());
        jLTipo.setText(emprestimo.getTipoEmprestimo());
        jLStatus.setText(emprestimo.getStatus().toString());
        ((JDecimalTextField) jTValorEmprestar).setDoubleValue(emprestimo.getValorEmprestimo());
        ((JDecimalTextField) jTjuros).setDoubleValue(emprestimo.getTaxaJuros());
        ((JDecimalTextField) jTPagamentoTotal).setDoubleValue(emprestimo.getValorTotalPagar());
        ((JDecimalTextField) jTLucro).setDoubleValue(emprestimo.getLucro());
        ((JDecimalTextField) jTQtdParcela).setDoubleValue(emprestimo.getQtdParcelas());
        ((JDecimalTextField) jTValorParcela).setDoubleValue(emprestimo.getValorTotalPagar() / emprestimo.getQtdParcelas());
        ((JDecimalTextField) jTvalorJurosDiario).setDoubleValue(emprestimo.getTaxaJurosMultaAtraso());
        jTdataLacamento.setDate(emprestimo.getDataLacamento());

    }

    private void buscarEmprestimo() {
        //busca todos por status
        if ((cliente == null) && (jTdataInicial.getDate() == null) && (jTdataFinal.getDate() == null)) {
            if (jCheckBoxAberto.isSelected()) {
                emprestimos = Repository.getInstance().buscarTodosEmprestimoPorStatus(StatusEmprestimo.ABERTO);
            } else if (jCheckBoxFinalizado.isSelected()) {
                emprestimos = Repository.getInstance().buscarTodosEmprestimoPorStatus(StatusEmprestimo.FINALIZADO);

            } else {
                emprestimos = Repository.getInstance().buscarTodosEmprestimo();
            }
            //busca todos por cliente e status
        } else if ((cliente != null) && (jTdataInicial.getDate() == null) && (jTdataFinal.getDate() == null)) {
            if (jCheckBoxAberto.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorClienteStatus(cliente, StatusEmprestimo.ABERTO);
            } else if (jCheckBoxFinalizado.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorClienteStatus(cliente, StatusEmprestimo.FINALIZADO);

            } else {
                emprestimos = Repository.getInstance().buscarTodosEmprestimoPorCliente(cliente);
            }
            //busca todos por cliente e data
        } else if ((cliente != null) && (jTdataInicial.getDate() != null) && (jTdataFinal.getDate() != null)) {
            if (jCheckBoxAberto.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorClienteStatusData(cliente, StatusEmprestimo.ABERTO, jTdataInicial.getDate(), jTdataFinal.getDate());
            } else if (jCheckBoxFinalizado.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorClienteStatusData(cliente, StatusEmprestimo.FINALIZADO, jTdataInicial.getDate(), jTdataFinal.getDate());

            } else {
                emprestimos = Repository.getInstance().buscarTodosEmprestimoPorClienteData(cliente, jTdataInicial.getDate(), jTdataFinal.getDate());
            }
            //busca por data
        } else if ((cliente == null) && (jTdataInicial.getDate() != null) && (jTdataFinal.getDate() != null)) {
            if (jCheckBoxAberto.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorStatusData(StatusEmprestimo.ABERTO, jTdataInicial.getDate(), jTdataFinal.getDate());
            } else if (jCheckBoxFinalizado.isSelected()) {
                emprestimos = Repository.getInstance().buscarEmprestimoPorStatusData(StatusEmprestimo.FINALIZADO, jTdataInicial.getDate(), jTdataFinal.getDate());

            } else {
                emprestimos = Repository.getInstance().buscarTodosEmprestimoPorData(jTdataInicial.getDate(), jTdataFinal.getDate());
            }

        } else {
            JOptionPane.showMessageDialog(null, "PARAMETROS DE PESQUISA INVÁLIDOS", "ATEÇÃO", WIDTH);
        }

        if (emprestimos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "NENHUM EMPRÉSTIMO ENCONTRADO!", "ATEÇÃO", WIDTH);
        } else {
            carregarTabelaEmprestimo();
        }
    }

    private void carregarTabelaEmprestimo() {
        try {
            jBTEmprestimos.removeAll();

            jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BuscaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscarParcelas(List<Rm_Emprestimo> empre) {
        parcelasGeral = new ArrayList<>();

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

    private void excluirEmprestimo() {
        try {

            int indiceSelecionado = jBTEmprestimos.getSelectedRow();
            if (indiceSelecionado < 0) {
                JOptionPane.showMessageDialog(null, "SELECIONE UM EMPRÉTIMO PARA EXCLUIR", "ATEÇÃO", WIDTH);
            } else {
                emprestimo = (Rm_Emprestimo) jBTEmprestimos.obterBean(indiceSelecionado);
                parcelas = new ArrayList<>();
                parcelas = Repository.getInstance().BuscarTodasPorEmprestimo(emprestimo);

                for (Rm_Parcela parcela : parcelas) {
                    if (parcela.getStatus().equals(StatusParcela.RECEBIDA)) {
                        JOptionPane.showMessageDialog(null, "ESSE EMPRESTIMO CONTEM PACELAS JA RECEBIDAS NÃO É POSSIVEL EXCLUIR!", "ATEÇÃO", WIDTH);
                        return;
                    }

                }
                int resposta = JOptionPane.showConfirmDialog(null, "TEM CERTEZA QUE DESEJA EXCLUIR ESSE EMPRÉSTIMO?", "Confirmação", JOptionPane.YES_NO_OPTION);

                switch (resposta) {
                    // Ação a ser executada caso o usuário clique em "Sim"
                    case JOptionPane.YES_OPTION:
                        jBTEmprestimos.removerBean(emprestimo);

                        emprestimos = jBTEmprestimos.getModeloDeDados();

                        Repository.getInstance().excluirEmprestimo(emprestimo);

                        Repository.getInstance().excluirParcela(parcelas);
                        JOptionPane.showMessageDialog(null, "EMPRÉSTIMO EXCLUIDO COM SUCESSO", "SUCESSO", WIDTH);
                        jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
                        break;
                    // Ação a ser executada caso o usuário clique em "Não"
                    case JOptionPane.NO_OPTION:
                        return;

                    // Ação a ser executada caso o usuário feche a caixa de diálogo sem escolher uma opção
                    default:
                        return;
                }

            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BuscaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void verificarPossivelRecalculo() {

        try {

            int indiceSelecionado = jBTEmprestimos.getSelectedRow();

            if (indiceSelecionado < 0) {
                JOptionPane.showMessageDialog(null, "SELECIONE UM EMPRÉTIMO PARA RECÁLCULAR", "ATEÇÃO", WIDTH);
            } else {
                emprestimo = (Rm_Emprestimo) jBTEmprestimos.obterBean(indiceSelecionado);
                if (emprestimo.getStatus().equals(StatusEmprestimo.FINALIZADO)) {
                    JOptionPane.showMessageDialog(null, "NÃO É POSSIVEL RECALCULAR EMPRÉSTIMOS JÁ FINALIZADO", "ATEÇÃO", WIDTH);
                    return;
                }

                parcelasRecalculo = new ArrayList<>();
                parcelasRecalculo = Repository.getInstance().BuscarTodasPorEmprestimo(emprestimo);
                if (verificaParcelaPaga(parcelasRecalculo)) {
                    enviarPraTelaDeRecalculo(emprestimo);
                    jTabbedPrincipal.setEnabledAt(1, true);
                    jPanelNovoEmprestimo.setVisible(false);
                    jPanelNovasParcelas.setVisible(false);
                    jBSalva.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "EMPRÉSTIMO SEM NEHUMA PARCELA PAGA NÃO É POSSIVEL RECÁLCULAR", "ATEÇÃO", WIDTH);
                }

            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BuscaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean verificaParcelaPaga(List<Rm_Parcela> listaParcela) {
        boolean nenhumaParcelaPaga = true;

        for (Rm_Parcela parcela : parcelasRecalculo) {
            if (parcela.getStatus().equals(StatusParcela.RECEBIDA)) {

                return true;
            } else {
                nenhumaParcelaPaga = false;
            }

        }
        return nenhumaParcelaPaga;
    }

    private void enviarPraTelaDeRecalculo(Rm_Emprestimo emprestimo) {
        cliente = emprestimo.getCliente();
        jTabbedPrincipal.setSelectedIndex(1);

        jLTotalEmprestimo.setText(format.formatarMoeda(emprestimo.getValorTotalPagar()));

        for (Rm_Parcela p : parcelasRecalculo) {
            if (p.getStatus().equals(StatusParcela.RECEBIDA)) {
                parcelasRecebidasRecalculo.add(p);
                valorPago += p.getValorRecebido();
            } else {
                valorAPagar += p.getValor();

            }
        }
        jLValorJaPago.setText(format.formatarMoeda(valorPago));
        jLValorAtraso.setText(format.formatarMoeda(valorAPagar));
        carregarRecalculo(parcelasRecebidasRecalculo);
    }

    private void carregarRecalculo(List<Rm_Parcela> parcelasRecebidasRecalculo) {

        try {

            jBTParcelasRecalculo.removeAll();

            jBTParcelasRecalculo.getModeloDeDados();
            jBTParcelasRecalculo.setModeloDeDados(new ArrayList(parcelasRecebidasRecalculo));

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BuscaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaParcelaPagaRecalculo() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 49, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 95, null, null, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorRecebido", "VALOR RECEBIDO", true, false, 143, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            //columns.add(new JBeanTableColumn("dataRecebimentoFormatada", "PAGAMENTO EM", true, false, 143, null, null, null));
            columns.add(new JBeanTableColumn("valorJurosDiario", "JUROS DIARIO", true, false, 138, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiarioRecebido", "JUROS DIARIO RECEBIDO", true, false, 178, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasRecalculo = new JBeanTable(Rm_Parcela.class, columns);

            this.jScrollPaneParcelasRecalculo.setViewportView(this.jBTParcelasRecalculo);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criarTabelaNovasParcelas() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Numero", true, false, 55, null, null, null));
            columns.add(new JBeanTableColumn("status", "Status", true, false, 80, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "Vencimento", true, false, 90, null, null, null));
            columns.add(new JBeanTableColumn("valor", "Valor", true, false, 85, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelasNovasRecalculo = new JBeanTable(Rm_Parcela.class, columns);
            this.jScrollPaneNovasParcelas.setViewportView(this.jBTParcelasNovasRecalculo);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void calcularEFinalizarEmprestimo() {
        double novoValorEmprestimo = 0d;
        novoValorEmprestimo = calcularValorEmprestado(valorPago, emprestimo.getTaxaJuros());
        atualizarEmprestimo(novoValorEmprestimo);
    }

    private double calcularValorEmprestado(double valorPagoTotal, double jurosIncluidos) {
        // Convertendo a porcentagem dos juros para decimal
        double jurosDecimal = jurosIncluidos / 100.0;

        // Cálculo do valor emprestado antes dos juros
        double valorEmprestado = valorPagoTotal / (1 + jurosDecimal);

        return valorEmprestado;
    }

    private void atualizarEmprestimo(double novoValorEmprestimo) {
        double valorTotalPagar = novoValorEmprestimo + (novoValorEmprestimo * emprestimo.getTaxaJuros()) / 100;
        emprestimo.setValorEmprestimo(novoValorEmprestimo);
        emprestimo.setValorTotalPagar(valorTotalPagar);
        emprestimo.setLucro(valorTotalPagar - novoValorEmprestimo);
        emprestimo.setStatus(StatusEmprestimo.FINALIZADO);
        emprestimo.setQtdParcelas(parcelasRecebidasRecalculo.size());
        emprestimo.setParcela(parcelasRecebidasRecalculo);
        parcelasRecalculo.removeAll(parcelasRecebidasRecalculo);

        Repository.getInstance().excluirParcela(parcelasRecalculo);
        Repository.getInstance().atualizarEmprestimo(emprestimo);
        JOptionPane.showMessageDialog(null, "EMPRÉSTIMO FINALIZADO COM SUCESSO", "SUCESSO", WIDTH);

        carregarformularioRecalculo();
    }

    private void carregarformularioRecalculo() {
        colaborador = emprestimo.getColaborador();
        jTCobrador.setText(emprestimo.getColaborador().getNome());
        ((JDecimalTextField) jTValorEmprestarNovo).setDoubleValue(valorAPagar);
        jTdataLacamentoNovo.setDate(new Date());

    }

    private void calcularTotal() {
        //total a pagar
        Double total = ((JDecimalTextField) jTjurosNovo).getDoubleValue();
        Double valorEmprestado = ((JDecimalTextField) jTValorEmprestarNovo).getDoubleValue();
        ((JDecimalTextField) jTPagamentoTotalNovo).setDoubleValue(valorEmprestado + (valorEmprestado * total) / 100);

        //lucro
        ((JDecimalTextField) jTLucroNovo).setDoubleValue((valorEmprestado * total) / 100);
    }

    private void inserirCobrador() {

        int linha = jListCobrador.getSelectedIndex();
        for (Rm_Colaborador c : colaboradorList) {
            if (c.getNome().equals(modeloCobrador.getElementAt(linha))) {
                colaborador = c;
                jTCobrador.setText(colaborador.getNome());
            }
        }

    }

    private boolean validaCampos() {
        erros = new StringBuilder();

        if (jTValorEmprestarNovo.getText().trim().isEmpty()) {
            erros.append("Campo VALOR DO EMPRÉTIMO é Obrigatório \n");
        }
        if (jTjurosNovo.getText().trim().isEmpty()) {
            erros.append("Campo JÚROS é Obrigatório \n");
        }
        if (jTdataLacamentoNovo.getDate() == null) {
            erros.append("Campo Data Lançamento é Obrigatório \n");
        }
        if (colaborador == null) {
            erros.append("Selecione um Cobrador \n");
        }

        if (erros.length() <= 0) {
            return true;
        } else {

            return false;
        }
    }

    private void addParcela() {
        try {
            int intervaloDia = getVencimentoSelecionado();
            ParcelasUtil parcela = new ParcelasUtil();
            double qtdParcela = ((JDecimalTextField) jTQtdParcelaNovo).getDoubleValue();

            List<Date> datas = parcela.calcularParcelas(jTdataLacamentoNovo.getDate(), intervaloDia, (int) qtdParcela, getTipoVencimento());
            criarParcela(datas);
            jBTParcelasNovasRecalculo.removeAll();

            jBTParcelasNovasRecalculo.setModeloDeDados(new ArrayList(parcelasNovasRecalculo));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getTipoEmprestimo() {
        if (jCheckDiario.isSelected()) {
            return "DIARIO";
        }
        if (jCheckQuinzenal.isSelected()) {
            return "QUINZENAL";
        }
        if (jCheckSemanal.isSelected()) {
            return "SEMANAL";

        }
        if (jCheckMensal.isSelected()) {
            return "MENSAL";
        }
        return " ";
    }

    private int getVencimentoSelecionado() {
        if (jCheckDiario.isSelected()) {
            return 1;
        }
        if (jCheckQuinzenal.isSelected()) {
            return 15;
        }
        if (jCheckSemanal.isSelected()) {
            return 7;

        }
        if (jCheckMensal.isSelected()) {
            return 30;
        }
        return 0;
    }

    private String getTipoVencimento() {
        if (tipoSegundaSexta.isSelected()) {
            return "segunda/sexta";
        }
        if (tipoSegundaSabado.isSelected()) {
            return "segunda/sabado";
        }
        if (tipoSegundaSegunda.isSelected()) {
            return "segunda/segunda";

        }

        return " ";
    }

    public List<Rm_Parcela> criarParcela(List<Date> listaVencimentos) {

        parcelasNovasRecalculo = new ArrayList<>();
        for (int i = 0; i < listaVencimentos.size(); i++) {
            Rm_Parcela parcela = new Rm_Parcela();
            parcela.setDataVencimento(listaVencimentos.get(i));
            parcela.setNumeroParcela(i + 1);
            parcela.setValor(((JDecimalTextField) jTValorParcelaNovo).getDoubleValue());
            parcela.setDataPagamento(null);
            parcela.setStatus(StatusParcela.ABERTA);
            parcela.setTaxaJurosDiaria(((JDecimalTextField) jTvalorJurosDiarioNovo).getDoubleValue());
            parcela.setValorJurosDiarioRecebido(0d);
            parcela.setValorRecebido(0d);
            parcela.setValorJurosDiario(0d);
            parcelasNovasRecalculo.add(parcela);
        }

        return parcelasNovasRecalculo;
    }

    private void criarEmprestimo() {
        Rm_Emprestimo novoEmprestimo = new Rm_Emprestimo();

        cliente.setColaborador(emprestimo.getColaborador());
        novoEmprestimo.setCliente(cliente);
        novoEmprestimo.setColaborador(colaborador);
        novoEmprestimo.setDataLacamento(jTdataLacamentoNovo.getDate());
        novoEmprestimo.setValorEmprestimo(((JDecimalTextField) jTValorEmprestarNovo).getDoubleValue());
        novoEmprestimo.setQtdParcelas(((JDecimalTextField) jTQtdParcelaNovo).getDoubleValue());
        novoEmprestimo.setTaxaJuros(((JDecimalTextField) jTjurosNovo).getDoubleValue());
        novoEmprestimo.setValorTotalPagar(((JDecimalTextField) jTPagamentoTotalNovo).getDoubleValue());
        novoEmprestimo.setLucro(((JDecimalTextField) jTLucroNovo).getDoubleValue());
        novoEmprestimo.setStatus(StatusEmprestimo.ABERTO);
        novoEmprestimo.setTipoEmprestimo(getTipoEmprestimo());
        novoEmprestimo.setTaxaJurosMultaAtraso(((JDecimalTextField) jTvalorJurosDiarioNovo).getDoubleValue());

        for (int i = 0; i < parcelasNovasRecalculo.size(); i++) {
            parcelasNovasRecalculo.get(i).setEmprestimo(novoEmprestimo);
        }
        novoEmprestimo.setParcela(parcelasNovasRecalculo);
        //Salva no banco  
        Repository.getInstance().cadastrarEmprestimo(novoEmprestimo);
        JOptionPane.showMessageDialog(null, "EMPRÉSTIMO CRIADO COM SUCESSO", "Sucesso", WIDTH);
        calcularMultaDiaria();

        jTabbedPrincipal.setSelectedIndex(0);
        jTabbedPrincipal.setEnabledAt(1, false);
    }

    public void calcularMultaDiaria() {

        LocalDate dataAtual = LocalDate.now();
        List<Rm_Parcela> novaParcelas = new ArrayList<>();
        for (Rm_Parcela parcela : parcelasNovasRecalculo) {
            Date dataVencimento = parcela.getDataVencimento();
            LocalDate localDateVencimento = dataVencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (localDateVencimento.isBefore(dataAtual)) {
                int diasAtraso = dataAtual.getDayOfMonth() - localDateVencimento.getDayOfMonth();

                parcela.setValorJurosDiario(diasAtraso * parcela.getTaxaJurosDiaria());
                parcela.setStatus(StatusParcela.ATRASADA);
                novaParcelas.add(parcela);
            }
        }
        if (!novaParcelas.isEmpty()) {
            Repository.getInstance().atualizarParcela(novaParcelas);
        }

    }

    private void limpar() {
        jBTParcelasNovasRecalculo.removeAll();

        jTCobrador.setText("");
        ((JDecimalTextField) jTjurosNovo).setDoubleValue(0.0);
        ((JDecimalTextField) jTValorEmprestarNovo).setDoubleValue(0.0);
        ((JDecimalTextField) jTPagamentoTotalNovo).setDoubleValue(0.0);
        ((JDecimalTextField) jTQtdParcelaNovo).setDoubleValue(0.0);
        ((JDecimalTextField) jTValorParcelaNovo).setDoubleValue(0.0);
        ((JDecimalTextField) jTvalorJurosDiarioNovo).setDoubleValue(0.0);
        criarTabelaNovasParcelas();

    }

}
