package br.com.abueno.api.services;

import java.util.Optional;

import br.com.abueno.api.entity.Empresa;

public interface EmpresaService {
	
	Optional<Empresa> bucarPorCnpj(String cnpj);
	
	Empresa persistir(Empresa empresa);

}
