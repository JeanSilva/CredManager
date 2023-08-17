/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.rm.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author DEV
 */
@Entity
public class Rm_PermissaoUsuario implements Serializable{

    /**
     * @return the excluirEmprestimo
     */
    public boolean isExcluirEmprestimo() {
        return excluirEmprestimo;
    }

    /**
     * @param excluirEmprestimo the excluirEmprestimo to set
     */
    public void setExcluirEmprestimo(boolean excluirEmprestimo) {
        this.excluirEmprestimo = excluirEmprestimo;
    }

   
    @SequenceGenerator(name = "Rm_PermissaoUsuario", sequenceName = "seq_rm_PermissaoUsuario", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Rm_PermissaoUsuario")

    @Id
    private Long id;
    
    
    @Column(nullable = false)
    private boolean cadastro;
    
    @Column(nullable = false)
    private boolean relatorio;
    
    @Column(nullable = false)
    private boolean ganhos;
    
    @Column(nullable = false)
    private boolean novoEmprestimo;
    
    @Column(nullable = false)
    private boolean recalculoEmprestimo;
    
    @Column(nullable = false)
    private boolean baixarEmprestimo;
    
    @Column(nullable = false)
    private boolean configuracoes;
    
    @Column(nullable = false)
    private boolean excluirEmprestimo;
    
    
    
    @OneToOne()
    @JoinColumn(name = "colaborador_id")
    private Rm_Colaborador colaborador;

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
     * @return the cadastro
     */
    public boolean isCadastro() {
        return cadastro;
    }

    /**
     * @param cadastro the cadastro to set
     */
    public void setCadastro(boolean cadastro) {
        this.cadastro = cadastro;
    }

    /**
     * @return the relatorio
     */
    public boolean isRelatorio() {
        return relatorio;
    }

    /**
     * @param relatorio the relatorio to set
     */
    public void setRelatorio(boolean relatorio) {
        this.relatorio = relatorio;
    }

    /**
     * @return the ganhos
     */
    public boolean isGanhos() {
        return ganhos;
    }

    /**
     * @param ganhos the ganhos to set
     */
    public void setGanhos(boolean ganhos) {
        this.ganhos = ganhos;
    }

    /**
     * @return the novoEmprestimo
     */
    public boolean isNovoEmprestimo() {
        return novoEmprestimo;
    }

    /**
     * @param novoEmprestimo the novoEmprestimo to set
     */
    public void setNovoEmprestimo(boolean novoEmprestimo) {
        this.novoEmprestimo = novoEmprestimo;
    }

    /**
     * @return the recalculoEmprestimo
     */
    public boolean isRecalculoEmprestimo() {
        return recalculoEmprestimo;
    }

    /**
     * @param recalculoEmprestimo the recalculoEmprestimo to set
     */
    public void setRecalculoEmprestimo(boolean recalculoEmprestimo) {
        this.recalculoEmprestimo = recalculoEmprestimo;
    }

    /**
     * @return the baixarEmprestimo
     */
    public boolean isBaixarEmprestimo() {
        return baixarEmprestimo;
    }

    /**
     * @param baixarEmprestimo the baixarEmprestimo to set
     */
    public void setBaixarEmprestimo(boolean baixarEmprestimo) {
        this.baixarEmprestimo = baixarEmprestimo;
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
     * @return the configuracoes
     */
    public boolean isConfiguracoes() {
        return configuracoes;
    }

    /**
     * @param configuracoes the configuracoes to set
     */
    public void setConfiguracoes(boolean configuracoes) {
        this.configuracoes = configuracoes;
    }
    
}
