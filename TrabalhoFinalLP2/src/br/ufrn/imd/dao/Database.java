package br.ufrn.imd.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import br.ufrn.imd.modelo.Musicas;
import br.ufrn.imd.modelo.Playlists;
import br.ufrn.imd.modelo.Usuarios;

public class Database {
	private HashSet<Usuarios> usuarios;
	private ArrayList<Musicas> musicas;
	private ArrayList<Playlists> playlists;
	private static Database database;
	public Database()
	{
		usuarios = new HashSet<Usuarios>();
		musicas = new ArrayList<Musicas>();
		playlists = new ArrayList<Playlists>();
	}
	
	// Singleton
		public static Database getInstance() {
			if (database == null) {
				database = new Database();
			}
			return database;
		}
		
		public void inserirMusica(Musicas c)  {
			musicas.add(c);
			System.out.println("musica inserida com sucesso!!");
		}
		
		public void listarMusica()  {
			for(Musicas musica : musicas)
			{
				//System.out.println("-> " + musica.getPath());
			}
		}
		
		public void carregarMusicas()
		{
			Scanner sc = new Scanner(System.in);
			

			System.out.print("Enter file full path: ");
			String path = System.getProperty("user.dir") + "\\src\\resources\\musicas.txt";
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {

				String line = br.readLine();
				
				while (line != null) {
					
					String[] fields = line.split("|");
					
					String name = fields[0];
					
					Musicas musica = new Musicas();
					
					//musica.setNome(fields[0]);
					//musica.setPath(fields[1]);
					
					inserirMusica(musica);
					//int count = Integer.parseInt(fields[1]);
									
					//line = br.readLine();
				}
				
				//for (String key : votes.keySet()) {
				//	System.out.println(key + ": " + votes.get(key));
				//}
				
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}

			sc.close();
		}
		/*
		public void inserirFornecedor(Fornecedor f)  {
			fornecedores.add(f);
			System.out.println("Forncedor Inserido com sucesso!!");
		}
		
		public void inserirProduto(Produto p)  {
			produtos.add(p);
			System.out.println("Produto Inserido com sucesso!!");
		}
		*/
}
