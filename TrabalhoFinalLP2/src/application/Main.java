package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import br.ufrn.imd.controle.ViewPrincipalController;
import br.ufrn.imd.controle.TelaBoasVindasController;
import br.ufrn.imd.controle.TelaLoginController;

public class Main extends Application {
	
	public Stage stg1;
	public AnchorPane principal;
	
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			
			stg1 = primaryStage;
			stg1.setTitle("MP3 Player");
			
			// Carrega o layout Principal
			FXMLLoader loader = new FXMLLoader();
			
			//loader.setLocation(Main.class.getResource("/br/ufrn/imd/visao/TelaBoasVindas.fxml"));
			loader.setLocation(Main.class.getResource("/br/ufrn/imd/visao/CDPlayer.fxml"));
			principal = (AnchorPane) loader.load();
			

			
			Scene scene = new Scene(principal);
			stg1.setScene(scene);
			stg1.show();
			
			// Passando o controle
			
			//TelaBoasVindasController tpController = loader.getController();
			//tpController.setStage(this);
			
			ViewPrincipalController tpController = loader.getController();
			tpController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
