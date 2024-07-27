package com.projeto.sistema1.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.projeto.sistema1.modelos.Vaga;
import org.springframework.data.jpa.repository.Query;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
	Vaga findByid(long id);

}
