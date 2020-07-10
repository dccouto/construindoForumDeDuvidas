package br.com.diego.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diego.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nomeCurso);

}
