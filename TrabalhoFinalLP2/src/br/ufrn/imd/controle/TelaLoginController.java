package br.ufrn.imd.controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import br.ufrn.imd.dao.UsuariosDAO;
import br.ufrn.imd.modelo.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Alert;

public class TelaLoginController implements Initializable{
	// aqui vou carregar
	// vou add o banco de dados nessas 3 telas (tela login, boas vindas e cadastro) 
	private static UsuariosDAO bdUsuarios;
	
	Usuarios admin;
	
	//Window window;
	
	private Alert alert;
	
	private Stage stage;
	@FXML
	private Label lbCadastro;
	@FXML
    private TextField tfUsuario;

    @FXML
    private TextField tfPassword;

    @FXML
    private Button btnEntrar;
    
  
    private File directory;
    private Path caminhoUsuarios;
    public void initialize(URL arg0, ResourceBundle arg1)
	{
    	// pego a instancia 
    	bdUsuarios = UsuariosDAO.getInstance();
    	
    	
    	// instancia o diretorio   	
    	directory = new File(System.getProperty("user.dir") + "\\data\\usuarios");
    	
    	
    	// caso ele nao exista, crie o diretorio
    	if(!directory.exists())
		{
			directory.mkdir();
		}
    	
    	// pegando o caminho até o arquivo txt
    	caminhoUsuarios = Path.of(System.getProperty("user.dir") +
    			"\\data\\usuarios\\usuarios.txt");
    	
    	try {
    		// caso nao exista o arquivo texto, crie
    		if(Files.notExists(caminhoUsuarios))
    			Files.createFile(caminhoUsuarios);		
    		
    		// id; usuario; senha; path; tipo;
    		
    		String teste = "1;usuario;senha;usuarios//dinizjunioo;vip";
    		
    		Files.writeString(caminhoUsuarios, teste + System.lineSeparator(), StandardOpenOption.APPEND);
    		
    		teste = "2;usuario2;senha2;usuarios//dinizjunioo1;comum";
    		
    		Files.writeString(caminhoUsuarios, teste + System.lineSeparator(), StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  	
    	// vamos ler o que tem lá ! (se é que tem alguma coisa...)
    	
    	// ...
   
    	// basicamente como estamos na tela de login só precisamos verificar 
    	// senha e usuario do arquivo txt 
    	// então se caso a nossa linha de arquivo txt for da seguinte forma:
    	// id;usuario;senha;path;tipo;
    	// vamos apenas pegar verificar se a partir da chave "usuario" se a "key"
    	// ou seja a senha, é igual. 
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(caminhoUsuarios.toString()))) {

			String line = br.readLine();
				
			while (line != null) {
				System.out.println("line -> " + line);
				
				String[] fields = line.split(";");				
				
				int id = Integer.parseInt(fields[0]);
				
				String name = fields[1];
				String senha = fields[2];
				
				Usuarios usuario = new Usuarios(id, name, senha);
				
				bdUsuarios.inserirUsuarios(usuario);
				
				line = br.readLine();
			}		
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	bdUsuarios.mostrarUsuarios();
    	//admin = UsuariosDAO.getAdmin(bdUsuarios.getLogin());
    	System.out.println("Tela de login");
	}
    
    @FXML
    public void testLogin(ActionEvent event) throws IOException
    {
    	//window = btnEntrar.getScene().getWindow();
    	System.out.println("Tela de login test");
    	if (tfUsuario.getText().equals("")) {
    		alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Cadastro de usuario - aviso");
    		alert.setContentText("O campo usuário está vazio!");
    		alert.setHeaderText("AVISO!");
    		alert.showAndWait();
    	}
    	else if (tfPassword.getText().equals("")) {
    		alert = new Alert(Alert.AlertType.WARNING);
    		alert.setTitle("Cadastro de usuario - aviso");
    		alert.setContentText("O campo senha está vazio!");
    		alert.setHeaderText("AVISO!");
    		alert.showAndWait();
    	}
    	else if(bdUsuarios.verifiqueExisteUsuario(tfUsuario.getText()))
    	{
    		if(bdUsuarios.verifiqueSenha(tfUsuario.getText(), tfPassword.getText()))
    		{
    			
    			stage = (Stage) btnEntrar.getScene().getWindow();
    	        stage.close();
    	        
    	        // carregar próxima tela
    	        
    			FXMLLoader loader = new FXMLLoader();
    	    	loader.setLocation(ViewPrincipalController.class.getResource("/br/ufrn/imd/visao/CDPlayer.fxml"));
    	    	AnchorPane page = (AnchorPane) loader.load();
    	    	
    	    	// Criando um novo Stage
    	    	Stage principalStage = new Stage();
    	    	principalStage.setTitle("MP3 Player");
    	    	principalStage.setResizable(false);
    	    	Scene scene = new Scene(page);
    	    	principalStage.setScene(scene);
    	    	
    	    	// Setando o Controle 
    	    	ViewPrincipalController controller = loader.getController();
    	    	
    	    	controller.setPrincipalStage(principalStage);
    	    	
    	    	principalStage.showAndWait();
    	    	
    		}
    	}else {
    		alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Cadastro de usuario - error");
    		alert.setContentText("Usuario e/ou senha errado(s)!");
    		alert.setHeaderText("ERROR!");
    		alert.showAndWait();
    	}
    	
    }
    
    @FXML
    public void showCadastroStage( ) throws IOException
    {
    	stage = (Stage) lbCadastro.getScene().getWindow();
        stage.close();
    	// carregar próxima tela
		
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaCadastroController.class.getResource("/br/ufrn/imd/visao/TelaCadastro.fxml"));
    	AnchorPane page = (AnchorPane) loader.load();
    	
    	// Criando um novo Stage
    	Stage cadStage = new Stage();
    	cadStage.setTitle("Tela de cadastro");
    	cadStage.setResizable(false);
    	Scene scene = new Scene(page);
    	cadStage.setScene(scene);
    	
    	// Setando o Controle 
    	TelaCadastroController controller = loader.getController();
    	
    	controller.setStage(cadStage);
    	
    	cadStage.showAndWait();
    }
    
    public void setUsuarioStage(Stage UsuarioStage) {
		this.stage = UsuarioStage;
	}
    
}
