package br.com.abueno.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.abueno.api.dto.FuncionarioDto;
import br.com.abueno.api.entity.Funcionario;
import br.com.abueno.api.response.Response;
import br.com.abueno.api.services.FuncionarioServices;
import br.com.abueno.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioServices funcionarioServices;

	public FuncionarioController() {

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando funcionario: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();

		Optional<Funcionario> funcionario = this.funcionarioServices.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcioario", "Funcioario não encontrado."));
		}

		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando ");
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.funcionarioServices.persitir(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));

		return ResponseEntity.ok(response);

	}

	private void atualizarDadosFuncionario(Funcionario funcionario, @Valid FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException {

		funcionario.setNome(funcionarioDto.getNome());

		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			this.funcionarioServices.buscarPorEmail(funcionarioDto.getEmail())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existe")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}

		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));

		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

		if (funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtils.gerarBcrypt(funcionarioDto.getSenha().get()));
		} 

	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());

		funcionario.getQtdHorasAlmocoOpt().ifPresent(
				qtHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtHorasAlmoco))));
		funcionario.getQtdHorasTrabalhadasDiaOpt().ifPresent(qtdHorasTrabalhoDia -> funcionarioDto
				.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));

		funcionario.getValorHorasOpt()
				.ifPresent(valorHoras -> funcionarioDto.setValorHora(Optional.of(valorHoras.toString())));
		return funcionarioDto;
	}

}
