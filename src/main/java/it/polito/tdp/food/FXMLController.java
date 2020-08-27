package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCalorie;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Food> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	
    	Food f=this.boxFood.getValue();
    	if(f==null) {
    		txtResult.appendText("Crea il grafo o seleziona un cibo!");
    		return;
    	}
    	
    	List<Arco> a=this.model.getArchiConnessi(f);
    	for(int i=0; i<5 && i<a.size(); i++) {
    		
    		txtResult.appendText(a.get(i).toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
        String ps=this.txtPorzioni.getText();
    	
        Integer porzione;
    	
    	try {
    		porzione=Integer.parseInt(ps);
    	}catch(NumberFormatException e) {
    		
    		txtResult.setText("Devi inserire solo numeri");
    		return ;
    	}  
    	
    	this.model.creaGrafo(porzione);
    	boxFood.getItems().clear();
    	this.boxFood.getItems().addAll(model.getVertici());
    	
    	txtResult.appendText("Grafo Creato!\n");
    	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
    	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
