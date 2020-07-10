package br.com.diego.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.diego.forum.controller.dto.TopicoDto;
import br.com.diego.forum.model.Topico;
import br.com.diego.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {

	@NotNull
	private Long id;
	
	@NotEmpty
	@NotNull
	@Length(min = 5)
	private String titulo;
	
	@NotEmpty
	@NotNull
	@Length(min = 10)
	private String mensagem;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	

	public Topico atualiza(TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getOne(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topicoRepository.save(topico);	
	}

	
	
}
