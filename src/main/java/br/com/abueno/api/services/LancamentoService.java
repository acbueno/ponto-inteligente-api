package br.com.abueno.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.abueno.api.entity.Funcionario;
import br.com.abueno.api.entity.Lancamento;

public interface LancamentoService {
	
	
	Page<Lancamento> buscarPorFuncionarioId(Long funcionario, PageRequest pageRequest);
	
	Optional<Lancamento> buscarPorId(Long id);
	
	Lancamento persistir(Lancamento lancamento);
	
	void remover(long id);
	

}
