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
public class Funcionario implements Serializable{
	private static final long serialVersionUID=1L;
	@Id //referente a geração de id automático
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	private String nome;
	private String telefone;
	private String endereco;
	private String email;
	private String senha;
	@ManyToOne
	private Cargo cargo;
	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.REMOVE)//Um funcin'ario para uma lista de estagiários
	private List<Estagiario> estagiarios;
	
	public List<Estagiario> getEstagiarios() {
		return estagiarios;
	}
	public void setEstagiarios(List<Estagiario> estagiarios) {
		this.estagiarios = estagiarios;
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
