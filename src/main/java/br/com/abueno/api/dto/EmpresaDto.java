package br.com.abueno.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class EmpresaDto {

	private Long id;
	private String razaoSocial;
	private String cnpj;

	public EmpresaDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@NotEmpty(message = "Razao social não deve ser vazia")
	@Length(min = 5, max = 200, message = "Razao social deve estar entre 5 até 200 caracteres")
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	@NotEmpty(message = "CNPJ não deve ser vazio")
	@CNPJ
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
