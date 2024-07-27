package com.projeto.sistema1.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Estagiario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)//Para essa atributo ser 'unico na basse de dados, ou seja n~ao podem ter dois ou mais estagiarios com mesmo nuit
	private String nuit;
	private String nomeEstagiario;
	private String emal;
	@ManyToOne
	private Funcionario funcionario;//Muitos estagiarios para uma funcionario
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNuit() {
		return nuit;
	}
	public void setNuit(String nuit) {
		this.nuit = nuit;
	}
	public String getNomeEstagiario() {
		return nomeEstagiario;
	}
	public void setNomeEstagiario(String nomeEstagiario) {
		this.nomeEstagiario = nomeEstagiario;
	}
	public String getEmal() {
		return emal;
	}
	public void setEmal(String emal) {
		this.emal = emal;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	
}
