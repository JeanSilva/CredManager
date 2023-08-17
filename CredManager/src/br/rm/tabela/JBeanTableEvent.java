/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;

import java.util.EventObject;

/**
 *
 * @author camaleaoti_2
 */
public class JBeanTableEvent extends EventObject
{
    private Object bean;
    private Object beanAnterior;
    private Integer linha;
    private Integer coluna;
    private String chaveDaColuna;

    public JBeanTableEvent(Object source, Object bean, Integer linha, Integer coluna, String chaveDaColuna)
    {
        super(source);
        this.bean = bean;
        this.linha = linha;
        this.coluna = coluna;
        this.chaveDaColuna = chaveDaColuna;
    }

    public JBeanTableEvent(Object source, Object bean, Object beanAnterior, Integer linha, Integer coluna)
    {
        super(source);
        this.bean = bean;
        this.beanAnterior = beanAnterior;
        this.linha = linha;
        this.coluna = coluna;
    }

    public Object getBean() {
        return bean;
    }

    public Object getBeanAnterior() {
        return beanAnterior;
    }

    public Integer getLinha() {
        return linha;
    }

    public Integer getColuna() {
        return coluna;
    }

    public String getChaveDaColuna() {
        return chaveDaColuna;
    }
}
