/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author camaleaoti_2
 */
public class JCheckBoxTableCellRenderer implements TableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setHorizontalAlignment(JCheckBox.CENTER);

        if (isSelected)
            jCheckBox.setBorder(new LineBorder(new Color(255, 204, 0), 2, true));

        if (hasFocus)
            jCheckBox.setBackground(new Color(255, 255, 219));
        else
            jCheckBox.setBorder(null);

        if (value != null)
            jCheckBox.setSelected((Boolean)value);

        return jCheckBox;
    }
}