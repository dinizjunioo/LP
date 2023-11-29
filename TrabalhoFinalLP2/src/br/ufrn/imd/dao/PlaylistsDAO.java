package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.modelo.Musicas;
import br.ufrn.imd.modelo.Playlists;

public class PlaylistsDAO {
	
	private ArrayList<Playlists> playlists;
	
	private static PlaylistsDAO dbPlaylist;
	

	public PlaylistsDAO()
	{
		playlists  = new ArrayList<Playlists>();
	}
	
	public static PlaylistsDAO getInstance() {
		if (dbPlaylist == null) {
			dbPlaylist = new PlaylistsDAO();
		}
		return dbPlaylist;
	}
	
	
	public void addPlaylist(Playlists playlist)
	{
		this.playlists.add(playlist);
	}
	
	public void removePlaylist(Playlists playlist)
	{
		this.playlists.remove(playlist);
	}
	
	public ArrayList<Playlists> getPlaylist()
	{
		return this.playlists;
	}
	
	
}
