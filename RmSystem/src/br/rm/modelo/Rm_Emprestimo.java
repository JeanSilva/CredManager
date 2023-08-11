/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.modelo;

import br.rm.controle.StatusEmprestimo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Jayne
 */
@Entity
@NamedQueries({
    //POR CLIENTE
    @NamedQuery(name = "emprestimo.BuscarPorClienteStatus", query = "SELECT e FROM Rm_Emprestimo e WHERE e.cliente.id = :clienteId AND e.status =:status "),
    @NamedQuery(name = "emprestimo.BuscarPorClienteStatusData", query = "SELECT e FROM Rm_Emprestimo e WHERE e.cliente.id = :clienteId AND e.status =:status AND e.dataLacamento BETWEEN :dataInicial AND :dataFinal "),
    @NamedQuery(name = "emprestimo.BuscarPorTodosPorStatus", query = "SELECT e FROM Rm_Emprestimo e WHERE e.status =:status "),
    @NamedQuery(name = "emprestimo.BuscarPorColaborador", query = "SELECT e FROM Rm_Emprestimo e WHERE e.colaborador.id = :colaboradorId"),
    @NamedQuery(name = "emprestimo.BuscarPorColaboradorEPeriodo", query = "SELECT e FROM Rm_Emprestimo e WHERE e.colaborador.id = :colaboradorId AND e.dataLacamento BETWEEN :dataInicial AND :dataFinal"),
    @NamedQuery(name = "emprestimo.BuscarTodos", query = "SELECT e FROM Rm_Emprestimo e"),
    @NamedQuery(name = "emprestimo.BuscarTodosPorData", query = "SELECT e FROM Rm_Emprestimo e where e.dataLacamento BETWEEN :dataInicial AND :dataFinal"),
    @NamedQuery(name = "emprestimo.BuscarTodosPorDataStatus", query = "SELECT e FROM Rm_Emprestimo e where e.status =:status and e.dataLacamento BETWEEN :dataInicial AND :dataFinal"),
    
    @NamedQuery(name = "emprestimo.BuscarTodosPorCliente", query = "SELECT e FROM Rm_Emprestimo e WHERE e.cliente.id = :clienteId"),
   
    //@NamedQuery(name = "emprestimo.BuscarPorParcela", query = "SELECT e FROM Rm_Emprestimo e WHERE e.parcelas.id = :parcelaId"),
    
    @NamedQuery(name = "emprestimo.BuscarTodosPorClienteData", query = "SELECT e FROM Rm_Emprestimo e WHERE e.cliente.id = :clienteId AND e.dataLacamento BETWEEN :dataInicial AND :dataFinal"),})
public class Rm_Emprestimo implements Serializable {

    /**
     * @return the comissaoCobrador
     */
    public double getComissaoCobrador() {
        return comissaoCobrador;
    }

    /**
     * @param comissaoCobrador the comissaoCobrador to set
     */
    public void setComissaoCobrador(double comissaoCobrador) {
        this.comissaoCobrador = comissaoCobrador;
    }

    /**
     * @return the colaborador
     */
    public Rm_Colaborador getColaborador() {
        return colaborador;
    }

    /**
     * @param colaborador the colaborador to set
     */
    public void setColaborador(Rm_Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "Rm_Emprestimo", sequenceName = "seq_rm_Emprestimo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Rm_Emprestimo")

    @Id
    private Long id;

    @Column(nullable = true)
    private double valorEmprestimo;

    @Column(nullable = false)
    private double taxaJuros;

    @Column(nullable = false)
    private double qtdParcelas;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataLacamento;

    @Column(nullable = false)
    private double valorTotalPagar;

    @Column(nullable = false)
    private double lucro;

    @Column(nullable = false)
    private String tipoEmprestimo;

    @Column(nullable = false)
    private StatusEmprestimo status;
    ;

    @Column(nullable = false)
    private double taxaJurosMultaAtraso;

    @Column(nullable = false)
    private double comissaoCobrador;

    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Rm_Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "colaborador_id")
    private Rm_Colaborador colaborador;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL)
    private List<Rm_Parcela> parcelas;

    /**
     * @return the taxaJuros
     */
    public double getTaxaJuros() {
        return taxaJuros;
    }

    /**
     * @param taxaJuros the taxaJuros to set
     */
    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    /**
     * @return the id
     */
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
     * @return the cliente
     */
    public Rm_Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Rm_Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the parcela
     */
    public List<Rm_Parcela> getParcela() {
        return parcelas;
    }

    /**
     * @param parcela the parcela to set
     */
    public void setParcela(List<Rm_Parcela> parcela) {
        this.parcelas = parcela;
    }

    /**
     * @return the valorEmprestimo
     */
    public double getValorEmprestimo() {
        return valorEmprestimo;
    }

    /**
     * @param valorEmprestimo the valorEmprestimo to set
     */
    public void setValorEmprestimo(double valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }

    /**
     * @return the taxaJuros
     */
    public double getTaxaJurosMultaAtraso() {
        return taxaJurosMultaAtraso;
    }

    /**
     * @param taxaJurosMultaAtraso
     */
    public void setTaxaJurosMultaAtraso(double taxaJurosMultaAtraso) {
        this.taxaJurosMultaAtraso = taxaJurosMultaAtraso;
    }

    /**
     * @return the qtdParcelas
     */
    public double getQtdParcelas() {
        return qtdParcelas;
    }

    /**
     * @param qtdParcelas the qtdParcelas to set
     */
    public void setQtdParcelas(double qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    /**
     * @return the dataLacamento
     */
    public Date getDataLacamento() {
        return dataLacamento;
    }

    /**
     * @param dataLacamento the dataLacamento to set
     */
    public void setDataLacamento(Date dataLacamento) {
        this.dataLacamento = dataLacamento;
    }

    /**
     * @return the valorTotalPagar
     */
    public double getValorTotalPagar() {
        return valorTotalPagar;
    }

    /**
     * @param valorTotalPagar the valorTotalPagar to set
     */
    public void setValorTotalPagar(double valorTotalPagar) {
        this.valorTotalPagar = valorTotalPagar;
    }

    /**
     * @return the lucro
     */
    public double getLucro() {
        return lucro;
    }

    /**
     * @param lucro the lucro to set
     */
    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    /**
     * @return the tipoEmprestimo
     */
    public String getTipoEmprestimo() {
        return tipoEmprestimo;
    }

    /**
     * @param tipoEmprestimo the tipoEmprestimo to set
     */
    public void setTipoEmprestimo(String tipoEmprestimo) {
        this.tipoEmprestimo = tipoEmprestimo;
    }

    /**
     * @return the status
     */
    public StatusEmprestimo getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

}
