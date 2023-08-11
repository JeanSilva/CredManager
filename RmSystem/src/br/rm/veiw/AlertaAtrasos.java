/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.rm.veiw;

import br.rm.modelo.Rm_Cliente;
import java.util.List;

/**
 *
 * @author DEV
 */
public class AlertaAtrasos extends javax.swing.JInternalFrame {

     private List<Rm_Cliente> clientes;
    private StringBuilder clienteAtrasado;
    
    public AlertaAtrasos(List<Rm_Cliente> clientes) {
        initComponents();
        this.clientes = clientes;
        inserirClienteAtraso();
       
       
    
    }

    public AlertaAtrasos() {
       
       
    
    }

    private void inserirClienteAtraso() {
        clienteAtrasado = new StringBuilder();
        for (Rm_Cliente cliente : clientes) {
            //clienteAtrasado.append("Tempo de atraso: ").append(cliente.getNome()).append("\n");
            clienteAtrasado.append("Nome: ").append(cliente.getNome()).append("\n");
            clienteAtrasado.append("Telefone: ").append(cliente.getTelefone()).append("\n");
            clienteAtrasado.append("Rua: ").append(cliente.getRua()).append("  Num: ").append(cliente.getNumero()).append("\n");
            clienteAtrasado.append("Bairro: ").append(cliente.getBairro()).append("\n");
            clienteAtrasado.append("Cobrador Responsavel: ").append(cliente.getColaborador().getNome()).append("\n");;
            clienteAtrasado.append("____________________________________________________________\n");
            cliente.getColaborador().getNome();
        }
        jTAClientesAtraso.setText(clienteAtrasado.toString());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAClientesAtraso = new javax.swing.JTextArea();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setClosable(true);
        setTitle("Notificações");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Lista de Clientes em Atrasos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTAClientesAtraso.setEditable(false);
        jTAClientesAtraso.setColumns(20);
        jTAClientesAtraso.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTAClientesAtraso.setRows(5);
        jTAClientesAtraso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(jTAClientesAtraso);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTAClientesAtraso;
    // End of variables declaration//GEN-END:variables
}
