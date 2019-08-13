package br.com.abueno.api.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.abueno.api.entity.Lancamento;

@NamedQueries({
		@NamedQuery(name = "LancamentoRepository.findByFuncioanarioId", query = "SELECT lanc FROM Lancamento lanc "
				+ " WHERE lanc.funcionario.id = :funcionarioId") })
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId );
	
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId , Pageable pageable);

	
}
