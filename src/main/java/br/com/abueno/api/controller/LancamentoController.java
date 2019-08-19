package br.com.abueno.api.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.abueno.api.dto.LancamentoDto;
import br.com.abueno.api.entity.Funcionario;
import br.com.abueno.api.entity.Lancamento;
import br.com.abueno.api.enums.TipoEnum;
import br.com.abueno.api.response.Response;
import br.com.abueno.api.services.FuncionarioServices;
import br.com.abueno.api.services.LancamentoService;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FuncionarioServices funcionarioServices;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public LancamentoController() {

	}

	@GetMapping(value = "/funcionario/{funcioarioId}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listaFuncionarioPorId(
			@PathVariable("funcioarioId") Long funcionarioId, @RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {

		log.info("Buscando lacamento por ID do Funcionario: [}, pagina: {}", funcionarioId, pag);
		Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
		Page<LancamentoDto> lancametosDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

		response.setData(lancametosDto);
		return ResponseEntity.ok(response);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> listaPorId(@PathVariable("id") Long id) {
		log.info("Buscando lacamento por ID: {}", id);
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Lancamento não encontrado para o ID", id);
			response.getErrors().add("Lacamento não encontrado para o ID " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterLancamentoDto(lancamento.get()));
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
			BindingResult result) throws NoSuchAlgorithmException, ParseException {

		log.info("Adicionar lancamento: {}", lancamentoDto.toString());
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		Lancamento lancamento = this.converterDtoparaLancamento(lancamentoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando lancament: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.converterLancamentoDto(lancamento));

		return ResponseEntity.ok(response);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
		log.info("Atualizando lancamento: {}", lancamentoDto.toString());
		Response<LancamentoDto> response = new Response<LancamentoDto>();
		validarFuncionario(lancamentoDto, result);
		lancamentoDto.setId(Optional.of(id));
		Lancamento lancamento = this.converterDtoparaLancamento(lancamentoDto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.converterLancamentoDto(lancamento));

		return ResponseEntity.ok(response);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		log.info("Removendo Lancamento {}" , id);
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);
		
		if(!lancamento.isPresent()) {
			log.info("Erro ao remover devido ao Lancamento ID {} ser invalido", id);
			response.getErrors().add("Erro ao remover lancamento. Registro não encontrado para o id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		this.lancamentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	private Lancamento converterDtoparaLancamento(@Valid LancamentoDto lancamentoDto, BindingResult result)
			throws ParseException {
		Lancamento lancamento = new Lancamento();

		if (lancamentoDto.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
			if (lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lancamento não encontrado"));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
		}

		lancamento.setDescricao(lancamentoDto.getDescricao());
		lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
		lancamento.setData(this.dateFormat.parse(lancamentoDto.getData()));

		
	
		if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo invalido"));
		}
		return lancamento;
	}

	private void validarFuncionario(@Valid LancamentoDto lancamentoDto, BindingResult result) {
		if (lancamentoDto.getFuncionarioId() == null) {
			result.addError(new ObjectError("funcionario", "Funcionario nao informado"));
			return;
		}

		log.info("Validando funcionario id {}", lancamentoDto.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioServices.buscarPorId(lancamentoDto.getFuncionarioId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario nao encontrado. ID inexistente."));
		}

	}

	private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
		LancamentoDto lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(Optional.of(lancamento.getId()));
		lancamentoDto.setData(this.dateFormat.format(lancamento.getData()));
		lancamentoDto.setTipo(lancamento.getTipo().toString());
		lancamentoDto.setDescricao(lancamento.getDescricao());
		lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
		lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());

		return lancamentoDto;
	}

}
