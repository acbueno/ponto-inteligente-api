package br.com.abueno.api.services;

import java.util.Optional;

import br.com.abueno.api.entity.Funcionario;

public interface FuncionarioServices {
	
	
	Funcionario persitir(Funcionario funcionario);
	
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	Optional<Funcionario> buscarPorEmail(String email);
	
	Optional<Funcionario> buscarPorId(Long id);

}
