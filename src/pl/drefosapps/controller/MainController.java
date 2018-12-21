package pl.drefosapps.controller;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import pl.drefosapps.model.Interpreter;
import pl.drefosapps.model.Number;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TableView<Number> tableView;
    @FXML
    TextArea inputTextArea;
    @FXML
    TextArea outputTextArea;
    @FXML
    MenuItem clearInput;
    @FXML
    MenuItem clearOutput;

    ObservableMap<String, Number> variables;
    Interpreter interpreter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configMenu();
        configTableView();
        createInterpreter();
        configInputArea();
    }

    private void createInterpreter() {
        interpreter = new Interpreter(variables);
    }

    private void configMenu() {
        clearInput.setOnAction(x -> {
            inputTextArea.clear();
            inputTextArea.appendText("$");
        });
        clearOutput.setOnAction(x -> outputTextArea.clear());
    }

    private void configTableView() {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("label"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("value"));
        variables = FXCollections.observableHashMap();
        variables.addListener(new MapChangeListener<String, Number>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Number> change) {
                tableView.setItems(FXCollections.observableArrayList(variables.values()));
            }
        });
        StackPane placeHolder = new StackPane();
        placeHolder.setStyle("-fx-background-color:linear-gradient(from 50px 12px to 50px 37px , repeat, #686868 49% , #373737 12% )");
        tableView.setPlaceholder(placeHolder);
    }

    private void configInputArea() {
        inputTextArea.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String[] lines = inputTextArea.getText().split("\n");
                String lastLine = lines[lines.length - 1];
                outputTextArea.appendText("->" + interpreter.interpret(lastLine.replace("$", "")) + "\n");
                inputTextArea.appendText("$");
            }
        });
        inputTextArea.appendText("$");
    }

}
