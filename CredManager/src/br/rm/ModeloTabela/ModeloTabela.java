/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.ModeloTabela;

import br.rm.modelo.Rm_Parcela;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mera
 */
public class ModeloTabela extends AbstractTableModel {

    private List<Rm_Parcela> linhasTabela;  //as linhas que contem as informacoes da MEsa
    private String[] colunasTabela = new String[]{"Código", "Descrição", "Preço Compra", "Preço Venda", "Quantidade em Estoque", "Estoque Mimino", "Código de Barras"}; //Nome das colunas da tabela

    public ModeloTabela() { //Cria um ModeloTabela vazio, para ser utilizado 
        linhasTabela = new ArrayList<Rm_Parcela>();
    }

    public ModeloTabela(List<Rm_Parcela> listaProduto) { //Cria um 'ModeloTabela' com os dados da lista de Produtos
        linhasTabela = new ArrayList<Rm_Parcela>(listaProduto);
    }

    @Override
    public int getRowCount() { //retorna a quantidade de linhas
        return linhasTabela.size();
    }

    @Override
    public int getColumnCount() { //retorna a quantidade de colunas
        return colunasTabela.length;
    }

    @Override
    public String getColumnName(int columnIndex) { //retorna os dados do array que tem os nomes das colunas armazenados
        return colunasTabela[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rm_Parcela parcela = linhasTabela.get(rowIndex);
        /* Retorna o campo referente a coluna especificada.  
         Aqui é feito um switch para verificar qual é a coluna  
         e retornar o campo adequado. As colunas sãoas mesmas  
         que foram especificadas no array "colunas".  */

        switch (columnIndex) {
            case 0:
                return parcela.getId();
            case 1:
                return parcela.getValor();
            case 2:
                return parcela.getDataVencimento();
            case 3:
                return parcela.getDataPagamento();
            case 4:
                return parcela.getStatus();

            default:
                throw new IndexOutOfBoundsException("Index da coluna estourou !");
        }

    }

    public void setValueAt(Rm_Parcela dadoProduto, int rowIndex) { //modifica a linha especificada
        Rm_Parcela produto = linhasTabela.get(rowIndex);//pega o item da linha que vai ser modificado
        //pega os valores do getXXX e seta nos novos
        produto.setId(dadoProduto.getId());
        produto.setValor(dadoProduto.getValor());
     //   produto.setValorCompra(dadoProduto.getValorCompra());
 //       produto.setValorVenda(dadoProduto.getValorVenda());
 //       produto.setQuantidadeEstoque(dadoProduto.getQuantidadeEstoque());
 //       produto.setQuantidadeEstoqueMinimo(dadoProduto.getQuantidadeEstoqueMinimo());
  //      produto.setCodigoBarras(dadoProduto.getCodigoBarras());

        //faz o update dos nomes
        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        fireTableCellUpdated(rowIndex, 2);
        fireTableCellUpdated(rowIndex, 3);
        fireTableCellUpdated(rowIndex, 4);
        fireTableCellUpdated(rowIndex, 5);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { //diz que a celula nao eh editavel
        return false;
    }

    public Rm_Parcela getParcela(int indiceLinha) {
        return linhasTabela.get(indiceLinha);
    }

    public void addParcela(Rm_Parcela novaParcela) { //adiciona um registro a lista
        linhasTabela.add(novaParcela);

        int ultimoIndice = getRowCount() - 1;
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }

    public void excluiParcela(int indiceLinha) { //exclui a linha especificada
        try {
            linhasTabela.remove(indiceLinha);
            fireTableRowsDeleted(indiceLinha, indiceLinha);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void addListaDeProdutos(List<Rm_Parcela> listaParcela) { //adiciona uma lista Usuario no fim dos registros
        int ultimoTamanho = getRowCount(); //pega o ultimo tamnho da tabela
        linhasTabela.addAll(listaParcela); //adiciona os registros
        fireTableRowsInserted(ultimoTamanho, getRowCount() - 1);
    }

    public void limparLinhas() { //remove os registros
        linhasTabela.clear();
        fireTableDataChanged();
    }

    public boolean isModeloTabelaEmpity() { //verifica se a 'ModeloTabela' esta vazio
        return linhasTabela.isEmpty();
    }

}
