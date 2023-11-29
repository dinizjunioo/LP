package br.ufrn.imd.modelo;

import java.io.File;

public class Musicas {
	
	private String nome;
    private String banda;
    private int duracao; // em segundos
    private File arquivo;

    public Musicas() {
    	nome = banda = "";
    	duracao = 0;
    	arquivo = null;
    }
    // Construtor
    public Musicas(String nome, String banda, int duracao, File arquivo) {
        this.nome = nome;
        this.banda = banda;
        this.duracao = duracao;
        this.arquivo = arquivo;
    }

    // Getters
    
    public String getNome() {
        return this.nome;
    }

    public String getBanda() {
        return this.banda;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public File getArquivo() {
        return this.arquivo;
    }
	
	
}
