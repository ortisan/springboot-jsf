package br.com.ortiz.repository;

import br.com.ortiz.domain.CotacaoDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotacaoDiariaRepository extends JpaRepository<CotacaoDiaria, Long> {
}
