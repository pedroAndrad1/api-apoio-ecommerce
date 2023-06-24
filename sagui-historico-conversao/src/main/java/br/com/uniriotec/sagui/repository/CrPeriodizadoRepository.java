package br.com.unirio.sagui.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.unirio.sagui.model.Aluno;
import br.com.unirio.sagui.model.CrPeriodizado;
@Repository
public interface CrPeriodizadoRepository extends JpaRepository<CrPeriodizado, Long>{
	List<CrPeriodizado> findByAlunoMatricula(String Matricula);
	/**
	 * Counts CrPeriodizados. 
	 * @see used to validade the existence of a determined Grade on CrPeriodizado table, thus should always be 0 or 1
	 * @param codigo Discipline unique code
	 * @return Long number of occurrence of grades in the database
	 */
	@Query("SELECT COUNT(c) FROM CrPeriodizado c WHERE c.aluno.alunoId = :alunoId AND c.periodo = :periodo")
	Long countByAlunoIdAndPeriodo(@Param("alunoId") Long aluno_AlunoId, @Param("periodo") String periodo);
	
	CrPeriodizado findByPeriodoAndAluno(String Periodo, Aluno AlunoId);
}
 