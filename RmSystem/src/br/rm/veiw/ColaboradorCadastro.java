/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.controle.Repository;
import br.rm.controle.Validacao;
import br.rm.modelo.Rm_Colaborador;
import static br.rm.veiw.Inicio.jDesktopPane;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import br.rm.controle.JDecimalTextField;

/**
 *
 * @author Jayne
 */
public class ColaboradorCadastro extends javax.swing.JInternalFrame {

    private StringBuilder erros;
    private Rm_Colaborador colaborador;
    private String operacao;
    DefaultListModel modelo;
    List<Rm_Colaborador> colaboradorList;
    boolean validar;

    public ColaboradorCadastro() {
        initComponents();
        jAlerta.setVisible(false);
        this.operacao = "CRIAR";
        jListColaborador.setVisible(false);
        modelo = new DefaultListModel();
        jListColaborador.setModel(modelo);
        setFrameIcon(new ImageIcon("icons/colaborador.png"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDadosPessoais = new javax.swing.JPanel();
        jTNome = new br.rm.controle.JTextFieldHint(new JTextField(),  "Nome");
        ;
        jTCpf = new br.rm.controle.JTextFieldHint(new JTextField(), "CPF - 000.000.000-00");
        ;
        jTCelular = new br.rm.controle.JTextFieldHint(new JTextField(), "Celular");
        ;
        jTDataNascimento = new br.rm.controle.JTextFieldHint(new JTextField(),"Data Nasc. - DD/MM/AAAA");
        ;
        jTEmail = new br.rm.controle.JTextFieldHint(new JTextField(), "Email");
        ;
        jListColaborador = new javax.swing.JList<>();
        jPanelEndereco = new javax.swing.JPanel();
        jTRua = new br.rm.controle.JTextFieldHint(new JTextField(), "Rua");
        ;
        jTNumero = new br.rm.controle.JTextFieldHint(new JTextField(), "Número");
        ;
        jTCep = new br.rm.controle.JTextFieldHint(new JTextField(), "CEP");
        ;
        jTBairro = new br.rm.controle.JTextFieldHint(new JTextField(), "Bairro"

        );
        ;
        jBSalvar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTUsuario = new br.rm.controle.JTextFieldHint(new JTextField(), "usuario", "Nome de usuáio");
        ;
        jPSenha = new br.rm.controle.JPassWordFieldHint(new JPasswordField(), "lock", "Senha");
        ;
        jAlerta = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTSalario = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jTPorcentagemComissao = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Cadastrar e editar colaboradores");

        jPanelDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados pessoais", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jTNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNome.setForeground(new java.awt.Color(255, 255, 255));
        jTNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomeKeyPressed(evt);
            }
        });

        jTCpf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCpf.setForeground(new java.awt.Color(255, 255, 255));
        jTCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCpfFocusLost(evt);
            }
        });

        jTCelular.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCelular.setForeground(new java.awt.Color(255, 255, 255));

        jTDataNascimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTDataNascimento.setForeground(new java.awt.Color(255, 255, 255));

        jTEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTEmail.setForeground(new java.awt.Color(255, 255, 255));

        jListColaborador.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jListColaborador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListColaborador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListColaborador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListColaboradorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelDadosPessoaisLayout = new javax.swing.GroupLayout(jPanelDadosPessoais);
        jPanelDadosPessoais.setLayout(jPanelDadosPessoaisLayout);
        jPanelDadosPessoaisLayout.setHorizontalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jListColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTNome, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelDadosPessoaisLayout.setVerticalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jListColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jTRua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTRua.setForeground(new java.awt.Color(255, 255, 255));

        jTNumero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTNumero.setForeground(new java.awt.Color(255, 255, 255));

        jTCep.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCep.setForeground(new java.awt.Color(255, 255, 255));
        jTCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCepActionPerformed(evt);
            }
        });

        jTBairro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTBairro.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelEnderecoLayout = new javax.swing.GroupLayout(jPanelEndereco);
        jPanelEndereco.setLayout(jPanelEnderecoLayout);
        jPanelEnderecoLayout.setHorizontalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTCep, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTRua, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelEnderecoLayout.setVerticalGroup(
            jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEnderecoLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTRua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jBSalvar.setBackground(new java.awt.Color(51, 51, 51));
        jBSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBSalvar.setForeground(new java.awt.Color(255, 255, 255));
        jBSalvar.setText("Salvar");
        jBSalvar.setBorderPainted(false);
        jBSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSalvar.setFocusPainted(false);
        jBSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSalvarMouseExited(evt);
            }
        });
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados de login", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jTUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jTUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUsuarioFocusLost(evt);
            }
        });

        jAlerta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jAlerta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jPSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jAlerta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAlerta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Salárial", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jTSalario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTSalario.setForeground(new java.awt.Color(255, 255, 255));

        jTPorcentagemComissao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTPorcentagemComissao.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Salário");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("% Comissão");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTSalario, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                    .addComponent(jTPorcentagemComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTPorcentagemComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15)
                .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomeKeyPressed
        String tam = jTNome.getText();

        if (jTNome.getText().isEmpty()) {
            jListColaborador.setVisible(false);
        } else if (tam.length() > 5) {
            ListaPesquisa();
        }
    }//GEN-LAST:event_jTNomeKeyPressed

    private void jTCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCpfFocusLost
        
        if (!new Validacao().validarCPF(jTCpf.getText())) {
            JOptionPane.showMessageDialog(null, "CPF INVALIDO", "CPF INVALIDO", WIDTH);
            jTCpf.requestFocus();
        } else {

            colaborador = Repository.getInstance().buscarColaboradorPorCpf(jTCpf.getText().trim().replaceAll("[^0-9]", ""));
            if (colaborador != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "CPF JÁ CADASTRADO Deseja EDITAR as informaçoes?", "Confirmação", JOptionPane.YES_NO_OPTION);

                switch (resposta) {
                    // Ação a ser executada caso o usuário clique em "Sim"
                    case JOptionPane.YES_OPTION:
                        carregarFormulario(colaborador);
                        operacao = "EDITAR";
                        break;
                    // Ação a ser executada caso o usuário clique em "Não"
                    case JOptionPane.NO_OPTION:
                        jTCpf.requestFocus();

                    // Ação a ser executada caso o usuário feche a caixa de diálogo sem escolher uma opção
                    default:
                        jTCpf.requestFocus();
                }

            }
        }

    }//GEN-LAST:event_jTCpfFocusLost

    private void jListColaboradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListColaboradorMouseClicked
        inserirCliente();
        jListColaborador.setVisible(false);
    }//GEN-LAST:event_jListColaboradorMouseClicked

    private void jTCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCepActionPerformed

    private void jBSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvarMouseEntered
        jBSalvar.setBackground(new Color(153, 153, 0));
        jBSalvar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvarMouseEntered

    private void jBSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvarMouseExited
        jBSalvar.setBackground(new Color(51, 51, 51));
        jBSalvar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvarMouseExited

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed

        if (operacao.equals("CRIAR")) {
            criarColaborador();
        }
        if (operacao.equals("EDITAR")) {
            atualizarColaborador(colaborador);
        }

    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jTUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUsuarioFocusLost
        Rm_Colaborador user = Repository.getInstance().buscarColaboradorPorUsuario(jTUsuario.getText().trim());

        if (user != null) {
            jAlerta.setText("nome de usuário em uso");
            jAlerta.setForeground(Color.red);
            jTUsuario.requestFocus();
            jAlerta.setVisible(true);
        } else {
            jAlerta.setVisible(false);
        }
    }//GEN-LAST:event_jTUsuarioFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jAlerta;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jListColaborador;
    private javax.swing.JPasswordField jPSenha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelDadosPessoais;
    private javax.swing.JPanel jPanelEndereco;
    private javax.swing.JTextField jTBairro;
    private javax.swing.JTextField jTCelular;
    private javax.swing.JTextField jTCep;
    private javax.swing.JTextField jTCpf;
    private javax.swing.JTextField jTDataNascimento;
    private javax.swing.JTextField jTEmail;
    private javax.swing.JTextField jTNome;
    private javax.swing.JTextField jTNumero;
    private javax.swing.JTextField jTPorcentagemComissao;
    private javax.swing.JTextField jTRua;
    private javax.swing.JTextField jTSalario;
    private javax.swing.JTextField jTUsuario;
    // End of variables declaration//GEN-END:variables

    private void carregarFormulario(Rm_Colaborador colaborador) {
             
        jTNome.setText(colaborador.getNome());
        jTCpf.setText(new Validacao().formatarCpf(colaborador.getCpf()));
        jTEmail.setText(colaborador.getEmail());
        jTCelular.setText(colaborador.getTelefone());

        jTDataNascimento.setText(colaborador.getDataNascimento());

        //Endereco
        jTRua.setText(colaborador.getRua());
        jTBairro.setText(colaborador.getBairro());
        jTNumero.setText(colaborador.getNumero());
        jTCep.setText(colaborador.getCep());

        //dados login
        jTUsuario.setText(colaborador.getUsuario());
        jPSenha.setText(colaborador.getSenha());

        ((JDecimalTextField) jTSalario).setDoubleValue(colaborador.getSalario());
        ((JDecimalTextField) jTPorcentagemComissao).setDoubleValue(colaborador.getPorcentagemComissao());

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

    private void inserirCliente() {
        int linha = jListColaborador.getSelectedIndex();
        for (Rm_Colaborador c : colaboradorList) {
            if (c.getNome().equals(modelo.getElementAt(linha))) {
                colaborador = c;
                carregarFormulario(colaborador);
                operacao = "EDITAR";
            }
        }

    }

    private void atualizarColaborador(Rm_Colaborador colaborador) {

        //Dados pessoais
        colaborador.setNome(jTNome.getText());
        colaborador.setCpf(jTCpf.getText().replaceAll("[^0-9]", ""));
        colaborador.setTelefone(jTCelular.getText());
        colaborador.setDataNascimento(jTDataNascimento.getText());
        colaborador.setEmail(jTEmail.getText());
        //Endereco
        colaborador.setRua(jTRua.getText());
        colaborador.setBairro(jTBairro.getText());
        colaborador.setNumero(jTNumero.getText());
        colaborador.setCep(jTCep.getText());

        //dados de login
        colaborador.setUsuario(jTUsuario.getText());
        colaborador.setSenha(jPSenha.getText());

        //dados Salario
        colaborador.setSalario(((JDecimalTextField) jTSalario).getDoubleValue());
        colaborador.setPorcentagemComissao(((JDecimalTextField) jTPorcentagemComissao).getDoubleValue());

        Repository.getInstance().cadastrarColaborador(colaborador);
        JOptionPane.showMessageDialog(null, "DADOS ATUALIZADOS!", "Sucesso", WIDTH);
        operacao = "CRIAR";
        limpar();

    }

    private void criarColaborador() {

        if (!validaCampos()) {
            JOptionPane.showMessageDialog(null, erros, "Campo Obrigatórios", WIDTH);

        } else {
            salvarColaborador();

        }
    }

    private void limpar() {
        dispose();
        JInternalFrame frame = new ColaboradorCadastro();
        jDesktopPane.add(frame);
        frame.setVisible(true);
    }

    private boolean validaCampos() {
        erros = new StringBuilder();
        if (jTNome.getText().trim().isEmpty()) {
            erros.append("Campo NOME Obrigatório \n");
        }
        if (jTCpf.getText().trim().isEmpty()) {
            erros.append("Campo CPF é Obrigatório \n");
        }
        if (jTUsuario.getText().trim().isEmpty()) {
            erros.append("Campo USUARIO é Obrigatório \n");
        }
        if (jPSenha.getText().trim().isEmpty()) {
            erros.append("Campo SENHA é Obrigatório \n");
        }

        if (erros.length() <= 0) {
            return true;
        } else {

            return false;
        }
    }

    private void salvarColaborador() {
        Rm_Colaborador NovoColaborador = new Rm_Colaborador();

        //Dados pessoais
        NovoColaborador.setNome(jTNome.getText());
        NovoColaborador.setCpf(jTCpf.getText().replaceAll("[^0-9]", ""));
        NovoColaborador.setTelefone(jTCelular.getText());
        NovoColaborador.setDataNascimento(jTDataNascimento.getText());
        NovoColaborador.setEmail(jTEmail.getText());
        //Endereco
        NovoColaborador.setRua(jTRua.getText());
        NovoColaborador.setBairro(jTBairro.getText());
        NovoColaborador.setNumero(jTNumero.getText());
        NovoColaborador.setCep(jTCep.getText());

        //dados login
        NovoColaborador.setUsuario(jTUsuario.getText());
        NovoColaborador.setSenha(jPSenha.getText());

        //dados Salario
        NovoColaborador.setSalario(((JDecimalTextField) jTSalario).getDoubleValue());
        NovoColaborador.setPorcentagemComissao(((JDecimalTextField) jTPorcentagemComissao).getDoubleValue());

        Repository.getInstance().cadastrarColaborador(NovoColaborador);
        JOptionPane.showMessageDialog(null, "COLABORADOR CADASTRADO!", "Sucesso", WIDTH);
        limpar();

    }

 
}
