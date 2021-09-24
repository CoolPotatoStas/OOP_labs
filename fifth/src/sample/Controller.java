package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> myTab;

    @FXML
    private TableColumn<Book, String> Lang;

    @FXML
    private TableColumn<Book, String> Author;

    @FXML
    private TableColumn<Book, String> Year;

    @FXML
    private Button butDel;

    @FXML
    private Button butAdd;

    @FXML
    private TextField newLang;

    @FXML
    private TextField newAut;

    @FXML
    private TextField newYear;

    ObservableList<Book> Books = FXCollections.observableArrayList(
            new Book("Си", "Деннис Ритчи", "1972"),
            new Book ("C++", "Бьерн Страуструп", "1983"),
            new Book ("Python", "Гвидо ван Россум", "1991"),
            new Book ("Java", "Джеймс Гослинг", "1995"),
            new Book ("JavaScript", "Брендон Айк", "1995"),
            new Book ("C#", "Андерс Хейлсберг", "2001"),
            new Book ("Scala", "Мартин Одерски", "2003")
    );

    @FXML
    void initialize() {
        Lang.setCellValueFactory(new PropertyValueFactory<Book, String>("language"));
        Author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        Year.setCellValueFactory(new PropertyValueFactory<Book, String>("year"));
        myTab.setEditable(true);
        Lang.setEditable(true);
        Author.setEditable(true);
        Year.setEditable(true);
        myTab.setItems(Books);

        Lang.setCellFactory(TextFieldTableCell.forTableColumn());
        Lang.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLanguage(t.getNewValue());
                    }
                }
        );

        Author.setCellFactory(TextFieldTableCell.forTableColumn());
        Author.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAuthor(t.getNewValue());
                    }
                }
        );
        Year.setCellFactory(TextFieldTableCell.forTableColumn());
        Year.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
                    public void handle(TableColumn.CellEditEvent<Book, String> t) {
                        ((Book) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setYear(t.getNewValue());
                    }
            }
        );

        ////////////////////////////

        newLang.setEditable(true);
        newAut.setEditable(true);
        newYear.setEditable(true);

        butAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (newLang.getText() != null && newAut.getText() != null && newYear.getText() != null){
                    Books.add(new Book(newLang.getText(),newAut.getText(),newYear.getText()));
                    newLang.setText(null);
                    newAut.setText(null);
                    newYear.setText(null);
                }
            }
        });


        butDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { //удаление по выбору строки
                if (newLang.getText() != null && newAut.getText() != null && newYear.getText() != null){
                    Book x = new Book(newLang.getText(),newAut.getText(),newYear.getText());
                    for (Book i: Books){
                        if (i.equals(x)){
                            Books.remove(i);
                            break;
                        }
                    }
                    newLang.setText(null);
                    newAut.setText(null);
                    newYear.setText(null);
                }
            }
        });
    }
}
