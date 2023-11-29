package br.ufrn.imd.controle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaCadastroController implements Initializable{
	
	private Stage stage;
	
	@FXML
	private Label lbLogin;
	
	
	public TelaCadastroController()
	{
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		System.out.println("Na tela de cadastro");
		
	}
	
	
	@FXML
	public void testCadastro ( ) throws IOException
	{
		
	}
	
	@FXML
	public void ShowLogin ( ) throws IOException
	{
		// carregar próxima tela
		System.out.println("voltando para login...");
		stage = (Stage) lbLogin.getScene().getWindow();
		stage.close();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TelaLoginController.class.getResource("/br/ufrn/imd/visao/TelaLogin.fxml"));
		AnchorPane page = (AnchorPane) loader.load();
		    	
		// Criando um novo Stage
		Stage loginStage = new Stage();
		loginStage.setTitle("Tela de Login");
		loginStage.setResizable(false);
		Scene scene = new Scene(page);
		loginStage.setScene(scene);
		    	
		// Setando o Controle 
		TelaLoginController controller = loader.getController();
		    	
		controller.setUsuarioStage(loginStage);
		    	
		loginStage.showAndWait();
	}
	
	public void setStage (Stage stage)
	{
		this.stage = stage;
	}
}
