package br.com.unirio.sagui.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unirio.sagui.model.Svg;

public interface SvgRepository extends JpaRepository<Svg, Long>{
	public Optional<Svg> findByAlunoAlunoId(Long aluno_alunoId);
}
