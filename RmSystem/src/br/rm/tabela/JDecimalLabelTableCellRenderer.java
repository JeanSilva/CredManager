/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.tabela;

import br.rm.controle.JDecimalLabel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author wagner.rodrigues
 */
@SuppressWarnings("serial")
public class JDecimalLabelTableCellRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel jLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JDecimalLabel jDecimalLabel = new JDecimalLabel(2);
        
        jDecimalLabel.setFont(jLabel.getFont());
        jDecimalLabel.setBackground(jLabel.getBackground());
        jDecimalLabel.setForeground(jLabel.getForeground());
 
        if (value != null) {
            jDecimalLabel.setDoubleValue((Double) value);
        }
        
        if (hasFocus)
        {
            jDecimalLabel.setBackground(new Color(255, 255, 219));
            jDecimalLabel.setBorder(new LineBorder(new Color(255, 204, 0), 2, true));
            jDecimalLabel.setForeground(Color.DARK_GRAY);
            jDecimalLabel.setOpaque(true);
        }
        else {
            jDecimalLabel.setBorder(null);
        }
        
        if (isSelected) {
            jDecimalLabel.setForeground(Color.DARK_GRAY);
        }
        
        return jDecimalLabel;
    }
}