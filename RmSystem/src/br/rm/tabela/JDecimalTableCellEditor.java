/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import br.rm.controle.JDecimalTextField;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author camaleaoti_2
 */
public class JDecimalTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JDecimalTextField decimalText;

    public JDecimalTableCellEditor()
    {
        this.decimalText = new JDecimalTextField(2, JDecimalTextField.PRECISAO_DOUBLE);
        this.decimalText.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        this.decimalText.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                JDecimalTableCellEditor.this.fireEditingStopped();
            }

        });
    } 

    public Object getCellEditorValue()
    {
        return this.decimalText.getDoubleValue();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.decimalText.setText("");
        this.decimalText.setFocusable(true);

        if (value == null)
            return this.decimalText;

        if (value instanceof Double) {
            decimalText.setDoubleValue((Double) value);
        } else if(value instanceof Float){
            decimalText.setFloatValue((Float) value);
        }

        this.decimalText.setSelectionStart(0);
        this.decimalText.setSelectionEnd(this.decimalText.getText().length());

        this.decimalText.requestFocus();
             
        return this.decimalText;
    }
}