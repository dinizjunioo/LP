package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Date;

public class Playlists {
	
	private String nome;
	private String descricao;
	private String criador;
	private Date dataCriacao;
	private ArrayList<Musicas> playlist;
	//private String genero;
	
	public Playlists()
	{
		nome = descricao = criador = "";
		dataCriacao = null;
		playlist = new ArrayList<Musicas>();
	}
	
	public Playlists(String nome, String descricao, String criador, Date dataCriacao)
	{
		this.nome = nome;
		this.descricao = descricao;
		this.criador = criador;
		this.dataCriacao = dataCriacao;
		playlist = new ArrayList<Musicas>();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	
	public String getCriador() {
		return criador;
	}

	

	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	

	public ArrayList<Musicas> getPlaylist() {
		return playlist;
	}

	public void setPlaylist(ArrayList<Musicas> playlist) {
		this.playlist = playlist;
	}
	
	
	// passivel de deletar
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	public void setCriador(String criador) {
		this.criador = criador;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
