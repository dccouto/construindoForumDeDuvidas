package br.com.diego.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.diego.forum.model.StatusTopico;
import br.com.diego.forum.model.Topico;

public class DetalhesTopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	private List<RespostaDto> resposta;
	private StatusTopico status;
	
	
	public DetalhesTopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.resposta = new ArrayList<>();
		this.resposta.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
	}


	public Long getId() {
		return id;
	}


	public String getTitulo() {
		return titulo;
	}


	public String getMensagem() {
		return mensagem;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public String getNomeAutor() {
		return nomeAutor;
	}


	public List<RespostaDto> getResposta() {
		return resposta;
	}


	public StatusTopico getStatus() {
		return status;
	}
	
	
	

}
