package br.com.abueno.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.abueno.api.enums.TipoEnum;

@Entity
@Table(name = "lancamento") 
public class Lancamento implements Serializable {

	private static final long serialVersionUID = -5558336472182290075L;

	private Long id;
	private Date data;
	private String descricao;
	private String localizacao;
	private Date dataCricacao;
	private Date dataAutalizacao;
	private TipoEnum tipo;
	private Funcionario funcionario;

	public Lancamento() {

	}
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "descricao", nullable = true)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "localizcao", nullable = true)
	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCricacao() {
		return dataCricacao;
	}

	public void setDataCricacao(Date dataCricacao) {
		this.dataCricacao = dataCricacao;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAutalizacao() {
		return dataAutalizacao;
	}

	public void setDataAutalizacao(Date dataAutalizacao) {
		this.dataAutalizacao = dataAutalizacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	public TipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnum tipo) {
		this.tipo = tipo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataCricacao = atual;
		dataAutalizacao = atual;
	}
	
	@PreUpdate
	public void preUpdate() {
		dataAutalizacao = new Date();
	}



	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", data=" + data + ", descricao=" + descricao + ", localizacao=" + localizacao
				+ ", dataCricacao=" + dataCricacao + ", dataAutalizacao=" + dataAutalizacao + ", tipo=" + tipo
				+ ", funcionario=" + funcionario + "]";
	} 

}
