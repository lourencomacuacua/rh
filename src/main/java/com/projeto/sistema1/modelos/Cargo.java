package com.projeto.sistema1.modelos;



import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity //para referenciar que é uma entidade
public class Cargo implements Serializable{
	private static final long serialVersionUID=1L;
	@Id //referente a geração de id automático
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	private String nome;
	@ManyToOne
	private Departamento departamento;
	@OneToMany(mappedBy = "cargo",cascade=CascadeType.REMOVE)
	private List<Funcionario > funcionarios;
	@OneToMany(mappedBy = "cargo",cascade=CascadeType.REMOVE)
	private List<Vaga> vagas;//Um cargo para uma lista de vagas
	public List<Vaga> getVagas() {
		return vagas;
	}
	public void setVagas(List<Vaga> vagas) {
		this.vagas = vagas;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
 