/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author camaleaoti_2
 */
public class CamaleaotiHeaderTableCellRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel jLabel = new JLabel("");
        jLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        jLabel.setHorizontalAlignment(jLabel.CENTER);
        jLabel.setBorder(new LineBorder(new Color(190,190,190)));
        
        if (value != null)
        {
            jLabel.setText(value.toString());
        }

        return jLabel;
    }
}