package br.com.uniriotec.sagui.repository;

import java.util.List;
import java.util.Optional;

import br.com.uniriotec.sagui.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "alunos", path = "alunos")
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
	boolean existsByMatricula(String matricula);
	/**
	 * Return a student by his code number
	 * @matricula - student code with 10 characters..
	 */
	Optional<Aluno> findByMatricula(@Param("matricula") String matricula);
	/**
	 * Counts the number of a determined (by code) student in the database
	 * @see used to validade the existence of a student, thus should always be 0 or 1
	 * @param matricula Student unique code
	 * @return Long number of occurrence of a Student in the database
	 */
	Long countByMatricula(@Param("matricula") String matricula);
	
	/**
	 * Finds the students by the year and period of entrance
	 * @param yearPeriod of entrance
	 * @return list of students accepted in the graduation by year and period
	 */
	List<Aluno> findByEntradaAnoPeriodo(@Param("anoPeriodo") String yearPeriod);
	
	/**
	 * Finds the students supervised by a given tutor per period  
	 * @param yearPeriod of entrance 
	 * @param tutorId Tutor of the {@value yearPeriod} 
	 * @return List of studres supervised by the {@value tutorId}
	 */
	//List<Aluno> findByEntradaAnoPeriodoAndUsuarioUsuarioId(String yearPeriod, Long tutorId);
	

	/**
	 * Finds the students supervised by a given tutor 
	 * @param yearPeriod of entrance 
	 * @param tutorId Tutor of the {@value yearPeriod} 
	 * @return List of studres supervised by the {@value tutorId}
	 */
	//List<Aluno> findByUsuarioUsuarioId(Long tutorId);
}
