package br.com.abueno.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 6348228584061892730L;
	
	private long id;
	private String razaoSocial;
	private String cnpj;
	private Date dataCriaçao;
	private Date dataAtulizao;
	private List<Funcionarios> funcionarios;
	
	
	
	
	public Empresa() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "razao_social", nullable = false)
	public String getRazaoSocial() {
		return razaoSocial;
	}


	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriaçao() {
		return dataCriaçao;
	}


	public void setDataCriaçao(Date dataCriaçao) {
		this.dataCriaçao = dataCriaçao;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtulizao() {
		return dataAtulizao;
	}


	public void setDataAtulizao(Date dataAtulizao) {
		this.dataAtulizao = dataAtulizao;
	}

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Funcionarios> getFuncionarios() {
		return funcionarios;
	}


	public void setFuncionarios(List<Funcionarios> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PrePersist
	public void prePersist() {
		dataAtulizao = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		final Date atual = new Date();
		dataCriaçao = atual;
		dataAtulizao = atual;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", dataCriaçao=" + dataCriaçao
				+ ", dataAtulizao=" + dataAtulizao + ", funcionarios=" + funcionarios + "]";
	}
	
	
	
	
	
	

}
