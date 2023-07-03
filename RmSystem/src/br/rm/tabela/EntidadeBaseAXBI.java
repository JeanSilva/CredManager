/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.rm.tabela;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Cassio Leodegario
 */
@MappedSuperclass
public abstract class EntidadeBaseAXBI{
    
    protected Boolean ativo = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_insercao")
    private Date dataInsercao;

    @Column(name = "usuario_insercao")
    private Long usuarioInsercao;

    @Column(name = "autorizador_insercao")
    private Long autorizadorInsercao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "usuario_alteracao")
    private Long usuarioAlteracao;

    @Column(name = "autorizador_alteracao")
    private Long autorizadorAlteracao;
    
    public Boolean isAtivo() {
        return ativo;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public Long getUsuarioInsercao() {
        return usuarioInsercao;
    }

    public void setUsuarioInsercao(Long usuarioInsercao) {
        this.usuarioInsercao = usuarioInsercao;
    }

    public Long getAutorizadorInsercao() {
        return autorizadorInsercao;
    }

    public void setAutorizadorInsercao(Long autorizadorInsercao) {
        this.autorizadorInsercao = autorizadorInsercao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(Long usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Long getAutorizadorAlteracao() {
        return autorizadorAlteracao;
    }

    public void setAutorizadorAlteracao(Long autorizadorAlteracao) {
        this.autorizadorAlteracao = autorizadorAlteracao;
    }

    
}
