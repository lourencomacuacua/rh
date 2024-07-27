package com.projeto.sistema1.repositorios;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.jpa.repository.Query;
import com.projeto.sistema1.modelos.Candidato;
import com.projeto.sistema1.modelos.Vaga;


public interface CandidatoRepository extends JpaRepository<Candidato, Long>{
	Iterable<Candidato> findByVaga(Vaga vaga);
	Candidato findByNuit(String nuit);
	
}
