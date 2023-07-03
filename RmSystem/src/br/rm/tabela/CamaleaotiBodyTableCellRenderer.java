/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author camaleaoti_2
 */
public class CamaleaotiBodyTableCellRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        /*JTextField jTextField = new JTextField("");
        jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        if (hasFocus)
            jTextField.setBackground(new Color(255, 255, 219));
        else
            jTextField.setBorder(null);

        if (isSelected)
            jTextField.setBorder(new LineBorder(new Color(255, 204, 0), 2, true));

        if (value != null)
        {
            jTextField.setText(value.toString());
        }

        return jTextField;*/
        
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
       
        //label.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        return label;
    }
}