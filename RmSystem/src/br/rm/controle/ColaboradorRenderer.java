/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.controle;

import br.rm.modelo.Rm_Colaborador;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author DEV
 */
public class ColaboradorRenderer extends JLabel implements ListCellRenderer<Rm_Colaborador> {
    public ColaboradorRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Rm_Colaborador> list, Rm_Colaborador value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText(value.getNome());
        } else {
            setText("");
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
