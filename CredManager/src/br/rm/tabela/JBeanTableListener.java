/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.util.EventListener;

/**
 *
 * @author camaleaoti_2
 */
public interface JBeanTableListener extends EventListener
{
    public void beanAdicionado(JBeanTableEvent e);
    public void beanAtualizado(JBeanTableEvent e);
    public void beanEmRemocao(JBeanTableEvent e);
    public void beanRemovido(JBeanTableEvent e);
}
