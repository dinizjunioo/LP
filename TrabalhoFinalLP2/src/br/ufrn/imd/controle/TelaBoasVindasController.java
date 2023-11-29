package br.ufrn.imd.controle;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class TelaBoasVindasController implements Initializable{
	
	public Main main;
	
	private Stage stage;
	@FXML
	private Button goToPageCadastroBtn;
	@FXML
	private Button goToPageLoginBtn;
	
	private File directoryData;
	
	public TelaBoasVindasController()
	{
		//System.out.println("Tela de boas vindas!");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		// instancia o diretorio
 
		directoryData = new File(System.getProperty("user.dir") + "\\data");
    	
    	
    	// caso ele nao exista, crie o diretorio
    	if(!directoryData.exists())
		{
    		directoryData.mkdir();
		}
    	
		goToPageCadastroBtn.setDisable(true);
		
		System.out.println("Tela de boas vindas!!!");
		
	}
	
	@FXML
	public void goToPageLogin( ActionEvent event ) throws IOException
	{
		stage = (Stage) goToPageLoginBtn.getScene().getWindow();
        stage.close();
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(TelaLoginController.class.getResource("/br/ufrn/imd/visao/TelaLogin.fxml"));
    	AnchorPane page = (AnchorPane) loader.load();
    	
    	// Criando um novo Stage
    	Stage loginStage = new Stage();
    	loginStage.setTitle("Tela de login");
    	loginStage.setResizable(false);
    	Scene scene = new Scene(page);
    	loginStage.setScene(scene);
    	
    	// Setando o Controle 
    	TelaLoginController controller = loader.getController();
    	
    	controller.setUsuarioStage(loginStage);
    	
    	loginStage.showAndWait();
	}
	
	@FXML
	public void goToPageCadastro( ActionEvent event ) throws IOException
	{
		stage = (Stage) goToPageLoginBtn.getScene().getWindow();
        stage.close();
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
	
	public void setStage(Main main) {
		this.main = main;
	}
	
}