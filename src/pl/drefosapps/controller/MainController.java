package pl.drefosapps.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TableView tableView;
    @FXML
    TextArea inputTextArea;
    @FXML
    TextArea outputTextArea;
    @FXML
    MenuItem clearInput;
    @FXML
    MenuItem clearOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configMenu();
    }

    private void configMenu() {
        clearInput.setOnAction(x -> inputTextArea.clear());
        clearOutput.setOnAction(x -> outputTextArea.clear());
    }
}
