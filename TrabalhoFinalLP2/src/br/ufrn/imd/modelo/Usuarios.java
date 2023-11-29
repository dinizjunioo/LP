package br.ufrn.imd.modelo;

import java.util.Date;

public class Usuarios {
	private int id;
	private String nomeUsuario;
	private String email;
	private String senha;
	private Date dataNascimento;
	private Tipo tipoUser;
	
	
	public Usuarios() {
		this.id = 0;
	}
	
	public Usuarios(int id, String nomeUsuario, String senha) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
	}
	
	public int getIdUsuario() {
		return id;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.id = idUsuario;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	public String getEmailUsuario() {
		return email;
	}

	public void setEmailUsuario(String email) {
		this.email = email;
	}

	public String getSenhaUsuario() {
		return senha;
	}

	public void setSenhaUsuario(String senha) {
		this.senha = senha;
	}

	public Tipo getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(Tipo tipoUser) {
		this.tipoUser = tipoUser;
	}
}
