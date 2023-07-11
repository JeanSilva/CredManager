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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "colaborador.buscarUsuario", query = "SELECT c FROM Rm_Colaborador c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "colaborador.buscarCpf", query = "SELECT c FROM Rm_Colaborador c WHERE c.cpf = :cpf"),
    @NamedQuery(name = "colaborador.buscarNome", query = "SELECT c FROM Rm_Colaborador c where LOWER(c.nome) like LOWER(:nome) ORDER BY c.nome"),})
public class Rm_Colaborador implements Serializable {

    /**
     * @return the emprestimos
     */
    public List<Rm_Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    /**
     * @param emprestimos the emprestimos to set
     */
    public void setEmprestimos(List<Rm_Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

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

    /**
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the comissao
     */
    public double getComissao() {
        return comissao;
    }

    /**
     * @param comissao the comissao to set
     */
    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    /**
     * @return the porcentagemComissao
     */
    public double getPorcentagemComissao() {
        return porcentagemComissao;
    }

    /**
     * @param porcentagemComissao the porcentagemComissao to set
     */
    public void setPorcentagemComissao(double porcentagemComissao) {
        this.porcentagemComissao = porcentagemComissao;
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

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "Rm_Colaborador", sequenceName = "seq_rm_Colaborador", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Rm_Colaborador")

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column()
    private String rua;

    @Column()
    private String numero;

    @Column()
    private String bairro;

    @Column()
    private String cep;

    @Column()
    private String telefone;
    
    @Column()
    private String email;

    @Column()
    private String dataNascimento;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String senha;
    
    @Column()
    private double salario;
    
    @Column()
    private double comissao;
    
    @Column()
    private double porcentagemComissao;
    

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rm_Cliente> clientes = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rm_Emprestimo> emprestimos = new ArrayList<>();

    /**
     * @return the cliente
     */
    public List<Rm_Cliente> getCliente() {
        return clientes;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(List<Rm_Cliente> cliente) {
        this.clientes = cliente;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
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
     * @return the nome
     */
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

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the rua
     */
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

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
