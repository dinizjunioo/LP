package br.ufrn.imd.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import br.ufrn.imd.modelo.Usuarios;

public class UsuariosDAO extends Database{
	
	// vamos guardar nome e senha aqui
	// 
	private HashMap<String,String> usuarios;
	
	// garantindo que essa classe seja uma instancia unica
	private static UsuariosDAO bdusuarios;
	
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public static UsuariosDAO getInstance() {
		if (bdusuarios == null) {
			bdusuarios = new UsuariosDAO();
		}
		return bdusuarios;
	}
	
	public HashMap<String,String> getLogin()
	{
		return this.usuarios;
	}
	
	public UsuariosDAO(){
		System.out.println("criei usuarios");
		//usuarios  = new ArrayList<Usuarios>();
		usuarios  = new HashMap<String,String>();
	}
	
	
	
	
	public boolean verifiqueExisteUsuario (String c)
	{
		if(usuarios.containsKey(c))
			return true;
		else
			return false;
	}
	
	public boolean verifiqueSenha (String k, String v)
	{
		if(usuarios.get(k).equals(v))
			return true;
		else 
			return false;
	}
	
	public void inserirUsuarios(Usuarios c)  {
		usuarios.put(c.getNomeUsuario(), c.getSenhaUsuario());
		//usuarios.add(c);
		System.out.println("Usuario Inserido com sucesso!!");
	}
	
	public void mostrarUsuarios()
	{
		for (String key : usuarios.keySet())
		{
			System.out.println("Ch = " + key + ", valor = " +  usuarios.get(key));
		
		}
	}
	
	public Tipo typeUser()
	{
		return 
	}
	//public static Usuarios getAdmin(HashMap<String, String> login) {
	//	if (ADMIN_USUARIOS == null) {
	//		ADMIN_USUARIOS = new Usuarios();
	//		login.put("admin", "admin123");
	//	}
	//	return ADMIN_USUARIOS;
	//}
	
	/*
	public String listarClientes() {
		
		System.out.println("------------------------------------------------");
		String output = "";
		for (Usuarios c : usuarios) {
			//output += "C�digo: " + c.getIdCliente() + "\tNome: " + c.getNomeCliente() +
			//		"\nCPF...: " + c.getCpf() + "\tData Nascimento: " + formato.format(c.getDataNascimento()) +
			//		"\n";
			//output += "----------------------------------------------------------\n";
		}
		return output;
	}
	
	public int buscaCodigoCliente() {
		int tamanho = usuarios.size();
		return tamanho;
	}*/
}
