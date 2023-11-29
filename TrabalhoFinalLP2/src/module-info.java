module TrabalhoFinalLP2 {
	
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.graphics;
	requires javafx.base;
	requires jaudiotagger;

	
	
	exports br.ufrn.imd.controle;
	
	opens application to javafx.graphics, javafx.fxml;
	opens br.ufrn.imd.controle to javafx.fxml;
 
} 
