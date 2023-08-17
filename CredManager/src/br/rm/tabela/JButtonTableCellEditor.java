/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author camaleaoti_2
 */
public abstract class JButtonTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JButton jButton;
    private Object value;

    public JButtonTableCellEditor(String texto) {
        this.jButton = new JButton(texto);
        this.init();
    }

    private void init()
    {
        this.jButton.addActionListener(new ActionListener()
                                        {
                                            public void actionPerformed(ActionEvent e)
                                            {
                                                JButtonTableCellEditor.this.buttonActionPerformed();
                                                JButtonTableCellEditor.this.fireEditingStopped();
                                            }
                                        });
    }

    public Object getCellEditorValue() {
        return this.value;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return this.jButton;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public abstract void buttonActionPerformed();
}
