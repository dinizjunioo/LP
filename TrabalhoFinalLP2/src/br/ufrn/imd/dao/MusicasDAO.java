package br.ufrn.imd.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import br.ufrn.imd.modelo.Musicas;

public class MusicasDAO {
	
	private ArrayList<Musicas> musicas;
	
	private static MusicasDAO dbMusica;
	
	public MusicasDAO()
	{
		musicas  = new ArrayList<Musicas>();
	}
	
	public static MusicasDAO getInstance() {
		if (dbMusica == null) {
			dbMusica = new MusicasDAO();
		}
		return dbMusica;
	}
	
	public  void adicionarMusica(Musicas musica) {
	        musicas.add(musica);
	}

	public  ArrayList<Musicas> GetSongs() {
	        return musicas;
	}
	
	public boolean IsHaveAnySound()
	{
		return (musicas != null && musicas.size() > 0);
	}
	//adicionando uma musica
	//public void  addSong(File file)
	//{
	//	this.files.add(file);
	//}
	
	//public ArrayList<File> GetSongs()
	//{
	//	return this.files;
	//}
	
	//public boolean IsHaveAnySound()
	//{
	//	return (files != null && files.size() > 0);
	//}
	
}
