/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Jayne
 */
@Entity
  @NamedQueries({ 
    @NamedQuery(name = "parcela.BuscarPorEmprestimo", query = "SELECT p FROM Rm_Parcela p WHERE p.emprestimo.id = :emprestimoId and p.status <> 'RECEBIDA' ORDER BY p.numeroParcela "),
          @NamedQuery(name = "parcela.BuscarAtrazadas", query = "SELECT p FROM Rm_Parcela p WHERE p.dataVencimento < CURRENT_DATE and p.status <> 'RECEBIDA' ORDER BY p.numeroParcela")
})
public class Rm_Parcela implements Serializable {

    /**
     * @return the valorJurosDiarioRecebido
     */
    public double getValorJurosDiarioRecebido() {
        return valorJurosDiarioRecebido;
    }

    /**
     * @param valorJurosDiarioRecebido the valorJurosDiarioRecebido to set
     */
    public void setValorJurosDiarioRecebido(double valorJurosDiarioRecebido) {
        this.valorJurosDiarioRecebido = valorJurosDiarioRecebido;
    }

    /**
     * @return the valorRecebido
     */
    public double getValorRecebido() {
        return valorRecebido;
    }

    /**
     * @param valorRecebido the valorRecebido to set
     */
    public void setValorRecebido(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    /**
     * @return the taxaJurosDiaria
     */
    public double getTaxaJurosDiaria() {
        return taxaJurosDiaria;
    }

    /**
     * @param taxaJurosDiaria the taxaJurosDiaria to set
     */
    public void setTaxaJurosDiaria(double taxaJurosDiaria) {
        this.taxaJurosDiaria = taxaJurosDiaria;
    }

    /**
     * @return the dataFormatada
     */
    public String getDataFormatada() {
          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = format.format(getDataVencimento());
         
         return dataFormatada;
    }

    /**
     * @param dataFormatada the dataFormatada to set
     */
    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "Rm_Parcela", sequenceName = "seq_rm_Parcela", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Rm_Parcela")

    @Id
    private Long id;
    
    @Transient
    private String dataFormatada;

    @Column(nullable = false)
    private double valor;
    
    
    @Column(nullable = false)
    private double valorRecebido;
    
    @Column(nullable = false)
    private double valorJurosDiarioRecebido;

    @Column(nullable = false)
    private double valorJurosDiario;
    
     @Column(nullable = false)
    private double taxaJurosDiaria;

    @Column(nullable = false)
    private int numeroParcela;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataVencimento;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPagamento;

    @ManyToOne
    @JoinColumn(name = "emprestimo_id")
    private Rm_Emprestimo emprestimo;

    /**
     * @return the serialVersionUID
     */
    /**
     * @return the id
     */
    /**
     * @return the numeroParcela
     */
    public int getNumeroParcela() {
        return numeroParcela;
    }

    /**
     * @param numeroParcela the numeroParcela to set
     */
    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the valorJurosDiario
     */
    public double getValorJurosDiario() {
        return valorJurosDiario;
    }

    /**
     * @param ValorJurosDiario the valorJurosDiario to set
     */
    public void setValorJurosDiario(double ValorJurosDiario) {
        this.valorJurosDiario = ValorJurosDiario;
    }

   

    /**
     * @return the dataVencimento
     */
    public Date getDataVencimento() {
        return dataVencimento;
    }

    /**
     * @param dataVencimento the dataVencimento to set
     */
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the dataPagamento
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    /**
     * @param dataPagamento the dataPagamento to set
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    /**
     * @return the emprestimo
     */
    public Rm_Emprestimo getEmprestimo() {
        return emprestimo;
    }

    /**
     * @param emprestimo the emprestimo to set
     */
    public void setEmprestimo(Rm_Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

}