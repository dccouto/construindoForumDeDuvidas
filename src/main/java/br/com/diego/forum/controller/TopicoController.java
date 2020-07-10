package br.com.diego.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.diego.forum.controller.dto.TopicoDto;
import br.com.diego.forum.controller.form.TopicoForm;
import br.com.diego.forum.model.Topico;
import br.com.diego.forum.repository.CursoRepository;
import br.com.diego.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/api/")
public class TopicoController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	
	@GetMapping("topicos")
	public List<TopicoDto> listaTopicos() {
		
		return TopicoDto.converter(topicoRepository.findAll());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("topico/nome-curso/{nome}")
	public List<TopicoDto> listaTopicosPorNome(@PathVariable(value="nome") String nome) {

		return TopicoDto.converter(topicoRepository.findByCurso_Nome(nome));
	}
	
	
	@PostMapping("topico")
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		
		Topico topico = form.convertParaTopico(cursoRepository);
		Topico topicoSalvo = topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/api/topico/{id}").buildAndExpand(topicoSalvo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topicoSalvo));
	}
}
