package com.projeto.sistema1.repositorios;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.jpa.repository.Query;
import com.projeto.sistema1.modelos.Estagiario;
import com.projeto.sistema1.modelos.Funcionario;


public interface EstagiarioRepository extends JpaRepository<Estagiario, Long>{
	Iterable<Estagiario> findByFuncionario(Funcionario funcionario);
	Estagiario findByNuit(String nuit);
	
}
