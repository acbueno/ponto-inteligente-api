package br.com.abueno.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.abueno.api.entity.Empresa;
import br.com.abueno.api.repository.EmpresaRepository;
import br.com.abueno.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public Optional<Empresa> bucarPorCnpj(String cnpj) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		// TODO Auto-generated method stub
		return this.persistir(empresa);
	}

}
