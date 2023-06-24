package br.com.uniriotec.sagui.repository;

import br.com.uniriotec.sagui.model.Svg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SvgRepository extends JpaRepository<Svg, Long>{
	public Optional<Svg> findByAlunoAlunoId(Long aluno);
}
