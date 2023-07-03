/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Component;
import java.awt.Font;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author camaleaoti_2
 */
public class CamaleaotiTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JTextField jTextField;

    public CamaleaotiTableCellEditor()
    {
        this.jTextField = new JTextField("");
        this.jTextField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    }

    public Object getCellEditorValue() {
        if (!this.jTextField.getText().equals(""))
            return this.jTextField.getText();
        else
            return null;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null)
        {
            this.jTextField.setText("");
            return this.jTextField;
        }

        this.jTextField.setText((String)value);
        return this.jTextField;
    }
}
