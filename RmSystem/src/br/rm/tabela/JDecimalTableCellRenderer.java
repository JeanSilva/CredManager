/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.tabela;

import br.rm.controle.JDecimalTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Rogerio
 */
public class JDecimalTableCellRenderer implements TableCellRenderer {

    /**
     *
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        JDecimalTextField decimalText;
        decimalText = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        decimalText.setFont(new Font("Lucida Grande", Font.PLAIN, 14));

        if (hasFocus) {
            decimalText.setBackground(new Color(255, 255, 219));
        } else {
            decimalText.setBorder(null);
        }

        if (isSelected) {
            decimalText.setBorder(new LineBorder(new Color(255, 204, 0), 2, true));
        }

        if (value == null) {
            return decimalText;
        }
        if (value instanceof Double) {
            decimalText.setDoubleValue((Double) value);
        } else if(value instanceof Float){
            decimalText.setFloatValue((Float) value);
        }

        return decimalText;
    }
}
