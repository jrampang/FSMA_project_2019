package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Enchere;

public class MarcheController {
	@FXML
    private TableView<Enchere> tableView;
	
	@FXML
	private TableColumn<Enchere, String> EnchereCol;
	
	@FXML
	private TableColumn<Enchere, String> PrixCol;
	
	private static ObservableList<Enchere> list = FXCollections.observableArrayList();

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
	}
}
