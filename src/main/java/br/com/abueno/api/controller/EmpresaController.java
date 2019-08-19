package br.com.abueno.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abueno.api.dto.EmpresaDto;
import br.com.abueno.api.entity.Empresa;
import br.com.abueno.api.response.Response;
import br.com.abueno.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	EmpresaService empresaService;

	public EmpresaController() {

	}

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buarPorCnpj(@PathVariable("cnpj") String cnpj) {

		log.info("Buscando empresas por CNPJ", cnpj);
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.bucarPorCnpj(cnpj);

		if (!empresa.isPresent()) {
			log.info("Empresa não encontrado por CNPJ: {}", cnpj);
			response.getErrors().add("Empresa não encontrado por CNPJ" + cnpj);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.conveterEmpresaDto(empresa.get()));

		return ResponseEntity.ok(response);

	}

	private EmpresaDto conveterEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());

		return empresaDto;
	}

}
