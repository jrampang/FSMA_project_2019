package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Enchere;

public class PreneurChoixController {
	@FXML
    private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	@FXML
	private Button lancerEnchere;
	
	@FXML
	private ChoiceBox<String> mode;
	
	private static ObservableList<Enchere> list = FXCollections.observableArrayList();
	
	private String auto = "Mode Automatique";
	
	private String manuel = "Mode manuel";
	
	private ObservableList<String> modes = FXCollections.observableArrayList(auto, manuel);
	
	
	public ObservableList<Enchere> getList() {
		return list;
	}

	public static void setList(ObservableList<Enchere> inputList) {
		list = inputList;
	}
	
	public static void addEnchere(Enchere e) {
		list.add(e);
	}
	
	@FXML
	public void initialize() {
		tableView.setItems(list);
		EnchereCol.setCellValueFactory(cellData -> cellData.getValue().objetProperty());
		PrixCol.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		mode.setItems(modes);
		mode.setValue(auto);
		lancerEnchere.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent event) {
		        //fermer cette fenetre et ouvrir la fenetre Preneur avec le bon mode
		    	System.out.println("j'ai choisi");
		    }
		});
	}
}
