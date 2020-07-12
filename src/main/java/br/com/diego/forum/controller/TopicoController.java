package br.com.diego.forum.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.diego.forum.controller.dto.DetalhesTopicoDto;
import br.com.diego.forum.controller.dto.TopicoDto;
import br.com.diego.forum.controller.form.AtualizacaoTopicoForm;
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
	public Page<TopicoDto> listaTopicos(@PageableDefault(page= 0 , size=10) Pageable paginacao) {//Pageable recebe automaticamento os parâmetros 
		//da url, é necessário colocar os nome em inglês. Ex: page, size, order.
		//(verificar o Main, pois é necessário colocar uma anotation a mais para funcionar)
		
		
		return TopicoDto.converterListaTopico(topicoRepository.findAll(paginacao));
	}
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("topico/nome-curso/{nome}/{pagina}/{quantidade}")//Exemplo de paginação como o Pageable do spring
	public ResponseEntity<Page<TopicoDto>> listaTopicosPorNome(@PathVariable(value="nome") String nome, @PathVariable int pagina, @PathVariable int quantidade) {

		Pageable paginacao = PageRequest.of(pagina, quantidade);

		Page<TopicoDto> listaTopico = TopicoDto.converterListaTopico(topicoRepository.findByCurso_Nome(nome, paginacao));
		if(listaTopico.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(listaTopico);
	}
	
	
	
	@GetMapping("topico/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalharTopico(@PathVariable(name="id") long id) {
		
		if(!topicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(new DetalhesTopicoDto(topicoRepository.getOne(id)));
	}

	
	
	@PostMapping("topico")
	@Transactional //Nesse caso não seria necessário, mas pode ser que em algum banco de dados solicite, a doc diz que todos os post, put e delete precisa
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		
		Topico topico = form.convertParaTopico(cursoRepository);
		Topico topicoSalvo = topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/api/topico/{id}").buildAndExpand(topicoSalvo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topicoSalvo));
	}
	
	@PutMapping("topico")
	@Transactional //Nesse caso não seria necessário, mas pode ser que em algum banco de dados solicite, a doc diz que todos os post, put e delete precisa
	public TopicoDto atualizar(@Valid @RequestBody AtualizacaoTopicoForm form) {
		if(!topicoRepository.existsById(form.getId())) {
			return null;
		}
		Topico topico = form.atualiza(topicoRepository);
		
		return TopicoDto.converterTopico(topico);
	}
	
	@DeleteMapping("topico/{id}")
	@Transactional //Nesse caso não seria necessário, mas pode ser que em algum banco de dados solicite, a doc diz que todos os post, put e delete precisa
	public ResponseEntity<?> remover(@PathVariable(name="id") long id) {
		
		if(!topicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();

	}
	
}










