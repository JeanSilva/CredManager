/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.controle.Repository;
import br.rm.modelo.Rm_Cliente;
import br.rm.modelo.Rm_Emprestimo;
import br.rm.modelo.Rm_Parcela;
import br.rm.tabela.JBeanTable;
import br.rm.tabela.JBeanTableAdapter;
import br.rm.tabela.JBeanTableColumn;
import br.rm.tabela.JBeanTableEvent;
import br.rm.tabela.JDecimalTableCellEditor;
import br.rm.tabela.JDecimalTableCellRenderer;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jayne
 */
public class BaixarParcelaEmprestimo extends javax.swing.JInternalFrame {

    DefaultListModel modelo;
    List<Rm_Cliente> clienteList;
    private Rm_Cliente cliente;
    private JBeanTable jBTParcelas;
    private JBeanTable jBTEmprestimos;
    private List<Rm_Emprestimo> emprestimos;
    private List<Rm_Parcela> parcelas;
    private Boolean alterandoTabelaDeProdutos = false;

    /**
     * Creates new form BaixarParcelaEmprestimo
     */
    public BaixarParcelaEmprestimo() {
        initComponents();
        jListCliente.setVisible(false);
        modelo = new DefaultListModel();
        jListCliente.setModel(modelo);
        criarTabelaParcelas();
        criarTabelaEmprestimos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTCliente = new br.rm.controle.JTextFieldHint(new JTextField(), "Cliente");
        ;
        jListCliente = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPaneEmprestimo = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jSPParcelas = new javax.swing.JScrollPane();
        jBSalvar = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel6 = new javax.swing.JPanel();
        jTCliente1 = new br.rm.controle.JTextFieldHint(new JTextField(), "Cliente");
        ;
        jListCliente1 = new javax.swing.JList<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPaneEmprestimo1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jSPParcelas1 = new javax.swing.JScrollPane();
        jBSalvar1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Receber parcelas");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClienteKeyPressed(evt);
            }
        });

        jListCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jListCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListClienteMouseClicked(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empréstimos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPaneEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPaneEmprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parcelas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSPParcelas)
                .addGap(5, 5, 5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jSPParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jBSalvar.setBackground(new java.awt.Color(51, 51, 51));
        jBSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBSalvar.setForeground(new java.awt.Color(255, 255, 255));
        jBSalvar.setText("Baixar parcelas selecinadas");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jListCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 632, Short.MAX_VALUE)
                        .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jListCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jInternalFrame1.setClosable(true);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTCliente1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTCliente1.setForeground(new java.awt.Color(255, 255, 255));
        jTCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliente1KeyPressed(evt);
            }
        });

        jListCliente1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jListCliente1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListCliente1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListCliente1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCliente1MouseClicked(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empréstimos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPaneEmprestimo1)
                .addGap(10, 10, 10))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPaneEmprestimo1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parcelas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSPParcelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSPParcelas1)
                .addGap(10, 10, 10))
        );

        jBSalvar1.setBackground(new java.awt.Color(51, 51, 51));
        jBSalvar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBSalvar1.setForeground(new java.awt.Color(255, 255, 255));
        jBSalvar1.setText("Baixar Parcelas");
        jBSalvar1.setBorderPainted(false);
        jBSalvar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSalvar1.setFocusPainted(false);
        jBSalvar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSalvar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSalvar1MouseExited(evt);
            }
        });
        jBSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBSalvar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jListCliente1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jListCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addComponent(jBSalvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 589, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 590, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 291, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 291, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListClienteMouseClicked
        inserirCliente();
        buscarEmprestimo();
       jListCliente.setVisible(false);
       jTCliente.setText("");
    }//GEN-LAST:event_jListClienteMouseClicked

    private void jTClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClienteKeyPressed

    
        String tam = jTCliente.getText();

        if (jTCliente.getText().isEmpty()) {
            jListCliente.setVisible(false);
        } else if (tam.length() > 5) {
            ListaPesquisa();
        }
    }//GEN-LAST:event_jTClienteKeyPressed

    private void jBSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvarMouseEntered
        jBSalvar.setBackground(new Color(153, 153, 0));
        jBSalvar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvarMouseEntered

    private void jBSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvarMouseExited
        jBSalvar.setBackground(new Color(51, 51, 51));
        jBSalvar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jBSalvarMouseExited

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        try {
            baixarParcela();
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jTCliente1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliente1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCliente1KeyPressed

    private void jListCliente1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCliente1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jListCliente1MouseClicked

    private void jBSalvar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvar1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBSalvar1MouseEntered

    private void jBSalvar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalvar1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jBSalvar1MouseExited

    private void jBSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBSalvar1ActionPerformed
    private void ListaPesquisa() {
        modelo = new DefaultListModel();
        clienteList = Repository.getInstance().buscarClienteNome(jTCliente.getText());
        modelo.removeAllElements();
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

            }
        }

    }

    private void criarTabelaEmprestimos() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("valorEmprestimo", "VALOR", true, false, 80, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("qtdParcelas", "PARCELAS", true, false, 80, null, null, null));
            columns.add(new JBeanTableColumn("cliente.nome", "CLIENTE", true, false, 120, null, null, null));
           
            this.jBTEmprestimos = new JBeanTable(Rm_Emprestimo.class, columns);

            this.jBTEmprestimos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if ((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2)) {
                        try {

                            int indiceSelecionado = jBTEmprestimos.getSelectedRow();
                            // Produto produto = (Produto) jBTComada.obterBean(indiceSelecionado);
                            Rm_Emprestimo emprestimo = (Rm_Emprestimo) jBTEmprestimos.obterBean(indiceSelecionado);

                            buscarParcela(emprestimo);

                        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException ex) {
                            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });

            this.jScrollPaneEmprestimo.setViewportView(this.jBTEmprestimos);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jBSalvar1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JList<String> jListCliente;
    private javax.swing.JList<String> jListCliente1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jSPParcelas;
    private javax.swing.JScrollPane jSPParcelas1;
    private javax.swing.JScrollPane jScrollPaneEmprestimo;
    private javax.swing.JScrollPane jScrollPaneEmprestimo1;
    private javax.swing.JTextField jTCliente;
    private javax.swing.JTextField jTCliente1;
    // End of variables declaration//GEN-END:variables

    private void criarTabelaParcelas() {
        try {

            ArrayList<JBeanTableColumn> columns = new ArrayList<>();
            columns.add(new JBeanTableColumn("numeroParcela", "Num.", true, false, 40, null, null, null));
            columns.add(new JBeanTableColumn("status", "STATUS", true, false, 100, null, null, null));
            columns.add(new JBeanTableColumn("dataFormatada", "VENCIMENTO", true, false, 100, null, null, null));
            columns.add(new JBeanTableColumn("taxaJurosDiaria", "R$ TAXA DIÁRIA", true, false, 120, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiario", "R$ JUROS DIARIO", true, false, 120, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valor", "VALOR", true, false, 100, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorRecebido", "R$ PARCELA RECEBIDA", true, true, 170, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));
            columns.add(new JBeanTableColumn("valorJurosDiarioRecebido", "R$ JUROS RECEBIDO", true, true, 150, JDecimalTableCellEditor.class, JDecimalTableCellRenderer.class, null));

            this.jBTParcelas = new JBeanTable(Rm_Parcela.class, columns);

            this.jBTParcelas.addJBeanTableListener(new JBeanTableAdapter() {
                @Override
                public void beanAtualizado(JBeanTableEvent e) {
                    try {
                        Rm_Parcela parcela = (Rm_Parcela) e.getBean();
                        if (!alterandoTabelaDeProdutos) {
                            if (parcela.getValor() < ((Rm_Parcela) e.getBean()).getValorRecebido()) {

                                alterandoTabelaDeProdutos = true;
                                jBTParcelas.atualizarBean(parcela, e.getLinha());
                            }
                        }
                        alterandoTabelaDeProdutos = false;

                    } catch (Exception ex) {
                        Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.jSPParcelas.setViewportView(this.jBTParcelas);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(NovoEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscarEmprestimo() {
        try {
            emprestimos = Repository.getInstance().buscarEmprestimoPorCliente(cliente);
           if (emprestimos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "CLIENTE NÃO TEM EMPRÉSTIMO ABERTO", "Sucesso", WIDTH);

            }else{
            
            jBTEmprestimos.removeAll();

            jBTEmprestimos.setModeloDeDados(new ArrayList(emprestimos));
           }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarParcela(Rm_Emprestimo emprestimo) {
        try {
            parcelas = Repository.getInstance().buscarParcelasEmprestimo(emprestimo);
            jBTParcelas.removeAll();

            jBTParcelas.setModeloDeDados(new ArrayList(parcelas));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException ex) {
            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void baixarParcela() throws NoSuchFieldException {
        try {
            ArrayList<Rm_Parcela> parcelasRecebidas = (ArrayList<Rm_Parcela>) jBTParcelas.obterBeansSelecionados();;

            if (parcelasRecebidas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "SELECIONE UMA PARCELA PARA RECEBER", "Sucesso", WIDTH);

            } else {
                for (Rm_Parcela parcela : parcelasRecebidas) {
                    parcela.setStatus("RECEBIDA");
                    parcela.setDataPagamento(new Date());

                }
                Repository.getInstance().atualizarParcela(parcelasRecebidas);

                //remover parcelas recebidas 
                jBTParcelas.removerBeansSelecionados();

                parcelas = jBTParcelas.getModeloDeDados();

                for (Rm_Parcela parcelaRecebida : parcelasRecebidas) {
                    double valorRecebido = parcelaRecebida.getValorRecebido();
                    double valorParcela = parcelaRecebida.getValor();

                    if (valorRecebido < valorParcela) {
                        Date dataVencimento = parcelaRecebida.getDataVencimento();
                        Rm_Parcela proximaParcela = null;
                        double diferenca = valorParcela - valorRecebido;

                        for (Rm_Parcela parcela : parcelas) {
                            if (parcela.getDataVencimento().after(dataVencimento) && parcela.getStatus().equals("ABERTA")) {
                                proximaParcela = parcela;
                                break;
                            }
                        }

                        if (proximaParcela != null) {
                            double valorProximaParcela = proximaParcela.getValor();
                            proximaParcela.setValor(valorProximaParcela + diferenca);
                        }
                    } else if (valorRecebido > valorParcela) {
                        Date dataVencimento = parcelaRecebida.getDataVencimento();
                        Rm_Parcela proximaParcela = null;
                        double diferenca = valorRecebido - valorParcela;

                        for (Rm_Parcela parcela : parcelas) {
                            if (parcela.getDataVencimento().after(dataVencimento) && parcela.getStatus().equals("ABERTA")) {
                                proximaParcela = parcela;
                                break;
                            }
                        }

                        if (proximaParcela != null) {
                            double valorProximaParcela = proximaParcela.getValor();
                            proximaParcela.setValor(Math.max(0, valorProximaParcela - diferenca));
                        }
                    }
                }
                Repository.getInstance().atualizarParcela(parcelas);
                jBTParcelas.setModeloDeDados(new ArrayList(parcelas));
                JOptionPane.showMessageDialog(null, "PARCELAS RECEBIDAS COM SUCESSO", "Sucesso", WIDTH);

            }

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaixarParcelaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}