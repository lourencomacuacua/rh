package com.projeto.sistema1.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity //para referenciar que é uma entidade
public class Departamento implements Serializable{
	private static final long serialVersionUID=1L;
	@Id //referente a geração de id automático
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	private String nome;	
	@OneToMany(mappedBy = "departamento",cascade=CascadeType.REMOVE)
	private List<Cargo> cargos ;
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
	public List<Cargo> getCargos() {
		return cargos;
	}
	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
