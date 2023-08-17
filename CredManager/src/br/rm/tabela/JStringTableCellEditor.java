/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import br.rm.controle.JIntegerTextField;
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
public class JStringTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JIntegerTextField integerText;

    public JStringTableCellEditor()
    {
        this.integerText = new JIntegerTextField(JIntegerTextField.PRECISAO_INTEGER);
        this.integerText.setHorizontalAlignment(JIntegerTextField.CENTER);
        this.integerText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        this.integerText.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                JStringTableCellEditor.this.fireEditingStopped();
            }

        });
    } 

    public Object getCellEditorValue()
    {
        return this.integerText.getText();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.integerText.setText("");
        this.integerText.setFocusable(true);

        if (value == null)
            return this.integerText;

        if (value instanceof String)
            integerText.setIntValue(Integer.parseInt((String) value));
        

        this.integerText.setSelectionStart(0);
        this.integerText.setSelectionEnd(this.integerText.getText().length());

        this.integerText.requestFocus();
             
        return this.integerText;
    }
}