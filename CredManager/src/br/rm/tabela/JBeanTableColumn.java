/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.tabela;

import br.rm.tabela.EntidadeBaseAXBI;
import java.util.Comparator;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author camaleaoti_2
 */
public class JBeanTableColumn {

    private String acesso;
    private String titulo;
    private Boolean visivel;
    private Boolean editavel;
    private Class cellEditorClass;
    private Class cellRenderer;
    private Class headerRenderer;
    private Comparator comparator;
    private Integer largura;
    private String toolTipText;
    private TableCellEditor cellEditorInstance;
    private TableCellRenderer cellRendererInstance;
    private EntidadeBaseAXBI entidade;

    public JBeanTableColumn() {
        this.titulo = "";
        this.visivel = true;
        this.editavel = false;
        this.cellEditorClass = CamaleaotiTableCellEditor.class;
        this.cellRenderer = CamaleaotiBodyTableCellRenderer.class;
        this.headerRenderer = CamaleaotiHeaderTableCellRenderer.class;
    }

    public JBeanTableColumn(String acesso, String titulo, Boolean visivel, Boolean editavel,
            Class cellEditor, Class cellRenderer, Class headerRenderer, EntidadeBaseAXBI entidade) {
        this.acesso = acesso;
        this.titulo = titulo;
        this.visivel = visivel;
        this.editavel = editavel;
        this.entidade = entidade;

        if (cellEditor != null) {
            this.cellEditorClass = cellEditor;
        } else {
            this.cellEditorClass = CamaleaotiTableCellEditor.class;
        }

        if (cellRenderer != null) {
            this.cellRenderer = cellRenderer;
        } else {
            this.cellRenderer = CamaleaotiBodyTableCellRenderer.class;
        }

        if (headerRenderer != null) {
            this.headerRenderer = headerRenderer;
        } else {
            this.headerRenderer = CamaleaotiHeaderTableCellRenderer.class;
        }
    }
    public JBeanTableColumn(String acesso, String titulo, Boolean visivel, Boolean editavel,
            Class cellEditor, Class cellRenderer, Class headerRenderer) {
        this.acesso = acesso;
        this.titulo = titulo;
        this.visivel = visivel;
        this.editavel = editavel;        

        if (cellEditor != null) {
            this.cellEditorClass = cellEditor;
        } else {
            this.cellEditorClass = CamaleaotiTableCellEditor.class;
        }

        if (cellRenderer != null) {
            this.cellRenderer = cellRenderer;
        } else {
            this.cellRenderer = CamaleaotiBodyTableCellRenderer.class;
        }

        if (headerRenderer != null) {
            this.headerRenderer = headerRenderer;
        } else {
            this.headerRenderer = CamaleaotiHeaderTableCellRenderer.class;
        }
    }

     public JBeanTableColumn(String acesso, String titulo, boolean visivel, boolean editavel, int largura,
            Class cellEditor, Class cellRenderer, Class headerRenderer) {
        this(acesso, titulo, visivel, editavel, cellEditor, cellRenderer, headerRenderer);
        this.largura = largura;
    }

    public JBeanTableColumn(String acesso, String titulo, boolean visivel, boolean editavel, int largura,
            Class cellEditor, Class cellRenderer, Class headerRenderer, String toolTipText) {
        this(acesso, titulo, visivel, editavel, largura, cellEditor, cellRenderer, headerRenderer);
        this.toolTipText = toolTipText;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Class getCellRenderer() {
        return cellRenderer;
    }

    public void setCellRenderer(Class cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public Class getCellEditorClass() {
        return cellEditorClass;
    }

    public void setCellEditorClass(Class cellEditorClass) {
        this.cellEditorClass = cellEditorClass;
    }

    public TableCellEditor getCellEditorInstance() {
        return cellEditorInstance;
    }

    public JBeanTableColumn setCellEditorInstance(TableCellEditor cellEditorInstance) {
        this.cellEditorInstance = cellEditorInstance;

        return this;
    }

    public TableCellRenderer getCellRendererInstance() {
        return cellRendererInstance;
    }

    public JBeanTableColumn setCellRendererInstance(TableCellRenderer cellRendererInstance) {
        this.cellRendererInstance = cellRendererInstance;
        return this;
    }

    public JBeanTableColumn setComparatorInstance(Comparator comparator) {
        this.comparator = comparator;
        return this;
    }

    public Boolean isEditavel() {
        return editavel;
    }

    public void setEditavel(Boolean editavel) {
        this.editavel = editavel;
    }

    public Integer getLargura() {
        return largura;
    }

    public void setLargura(Integer largura) {
        this.largura = largura;
    }

    public Class getHeaderRenderer() {
        return headerRenderer;
    }

    public void setHeaderRenderer(Class headerRenderer) {
        this.headerRenderer = headerRenderer;
    }

    public Boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(Boolean visivel) {
        this.visivel = visivel;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public EntidadeBaseAXBI getEntidade() {
        return entidade;
    }

    public void setEntidade(EntidadeBaseAXBI entidade) {
        this.entidade = entidade;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JBeanTableColumn other = (JBeanTableColumn) obj;
        if ((this.acesso == null) ? (other.acesso != null) : !this.acesso.equals(other.acesso)) {
            return false;
        }
        return true;
    }
}
