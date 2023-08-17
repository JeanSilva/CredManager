/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author camaleaoti_2
 */
public class JCheckBoxTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
    private JCheckBox jCheckBox;

    public JCheckBoxTableCellEditor()
    {
        this.jCheckBox = new JCheckBox();
        this.jCheckBox.setHorizontalAlignment(JCheckBox.CENTER);
        this.jCheckBox.addActionListener(new ActionListener() {

                                                public void actionPerformed(ActionEvent e) {
                                                    JCheckBoxTableCellEditor.this.fireEditingStopped();
                                                }
                                            });
    }

    public Object getCellEditorValue() {

        return (Boolean)this.jCheckBox.isSelected();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (isSelected)
            this.jCheckBox.setBorder(new LineBorder(new Color(255, 204, 0), 2, true));

        if (value != null)
            this.jCheckBox.setSelected((Boolean)value);
        else
            this.jCheckBox.setSelected(false);

        return this.jCheckBox;
    }
}
