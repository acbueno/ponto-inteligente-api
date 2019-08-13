package br.com.abueno.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.abueno.api.entity.Funcionario;
import br.com.abueno.api.repository.FuncionarioRepository;
import br.com.abueno.api.services.FuncionarioServices;

@Service
public class FuncionarioServicesImpl implements FuncionarioServices {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(FuncionarioServicesImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Funcionario persitir(Funcionario funcionario) {
		log.info("Persistindo funcionario: {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando pelo CPF: {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando por Email: {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando por Id: {}", id);
		return this.funcionarioRepository.findById(id);
	}
}
