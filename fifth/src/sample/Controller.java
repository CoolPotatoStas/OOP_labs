package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    void initialize() {
        Book one = new Book("Си", "Деннис Ритчи", "1972");
        Book two = new Book ("C++", "Бьерн Страуструп", "1983");
        Book three = new Book ("Python", "Гвидо ван Россум", "1991");
        Book four = new Book ("Java", "Джеймс Гослинг", "1995");
        Book five = new Book ("JavaScript", "Брендон Айк", "1995");
        Book six = new Book ("C#", "Андерс Хейлсберг", "2001");
        Book seven = new Book ("Scala", "Мартин Одерски", "2003");


    }
}
