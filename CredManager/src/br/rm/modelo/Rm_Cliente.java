/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.rm.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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

/**
 *
 * @author Jayne
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "cliente.buscarCpf", query = "SELECT c FROM Rm_Cliente c WHERE c.cpf = :cpf"),
    @NamedQuery(name = "cliente.buscarNome", query = "SELECT c FROM Rm_Cliente c where LOWER(c.nome) like LOWER(:nome) ORDER BY c.nome"),})
public class Rm_Cliente implements Serializable {


    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "Rm_Cliente", sequenceName = "seq_rm_cliente", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Rm_Cliente")

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = true)
    private String rg;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = true)
    private String cep;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = true)
    private String telefoneRecado;

    @Column(nullable = true)
    private String dataNascimento;

    @Column(nullable = true)
    private String nomeComercio;

    @Column()
    private String email;

    @Column(nullable = true)
    private String cnpj;

    @Column(nullable = true)
    private String ruaComercio;

    @Column(nullable = true)
    private String numeroComercio;

    @Column(nullable = true)
    private String bairroComerico;

    @ManyToOne()
    @JoinColumn(name = "colaborador_id")
    private Rm_Colaborador colaborador;

    @OneToMany(mappedBy = "cliente")
    private List<Rm_Emprestimo> emprestimos;

    /**
     * @return the rua
     */
    
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRua() {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua) {
        this.rua = rua;
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
     * @return the nomeComercio
     */
    public String getNomeComercio() {
        return nomeComercio;
    }

    /**
     * @param nomeComercio the nomeComercio to set
     */
    public void setNomeComercio(String nomeComercio) {
        this.nomeComercio = nomeComercio;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the ruaComercio
     */
    public String getRuaComercio() {
        return ruaComercio;
    }

    /**
     * @param ruaComercio the ruaComercio to set
     */
    public void setRuaComercio(String ruaComercio) {
        this.ruaComercio = ruaComercio;
    }

    /**
     * @return the numeroComercio
     */
    public String getNumeroComercio() {
        return numeroComercio;
    }

    /**
     * @param numeroComercio the numeroComercio to set
     */
    public void setNumeroComercio(String numeroComercio) {
        this.numeroComercio = numeroComercio;
    }

    /**
     * @return the bairroComerico
     */
    public String getBairroComerico() {
        return bairroComerico;
    }

    /**
     * @param bairroComerico the bairroComerico to set
     */
    public void setBairroComerico(String bairroComerico) {
        this.bairroComerico = bairroComerico;
    }

    /**
     * @return the emprestimo
     */
    public List<Rm_Emprestimo> getEmprestimo() {
        return emprestimos;
    }

    /**
     * @param emprestimo the emprestimo to set
     */
    public void setEmprestimo(List<Rm_Emprestimo> emprestimo) {
        this.emprestimos = emprestimo;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the rg
     */
    public String getRg() {
        return rg;
    }

    /**
     * @param rg the rg to set
     */
    public void setRg(String rg) {
        this.rg = rg;
    }

    /**
     * @return the rua
     */
    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the telefoneRecado
     */
    public String getTelefoneRecado() {
        return telefoneRecado;
    }

    /**
     * @param telefoneRecado the telefoneRecado to set
     */
    public void setTelefoneRecado(String telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
