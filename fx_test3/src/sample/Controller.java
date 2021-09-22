package sample;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Shirina;

    @FXML
    private TextField Visota;

    @FXML
    private final ObservableList<String> langs1 = FXCollections.observableArrayList("Черный", "Красный", "Синий", "Желтый");
    public ChoiceBox<String> Contur;

    @FXML
    public ChoiceBox<String> Zalivka;

    @FXML
    private TextField Tolshina;

    @FXML
    private final ObservableList<String> langs2 = FXCollections.observableArrayList("Обычный", "Штрих", "Точка");
    public ChoiceBox<String> TypeOfContur;

    @FXML
    private ToggleButton PalBut1;

    @FXML
    private ToggleButton PalBut2;

    @FXML
    private ToggleButton PalBut3;

    @FXML
    private ToggleButton PalBut4;

    @FXML
    private Rectangle Fon;

    @FXML
    private MenuItem butSave;

    @FXML
    private Canvas myCanvas;
    boolean flag = false;
    double x, y;

    @FXML
    void initialize() {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        Shirina.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Shirina.getText().isEmpty()){
                    String text = Shirina.getText();
                    int count = Integer.parseInt(text);
                    if (count > 10 && count < 371){
                      myCanvas.setWidth(count);
                      Fon.setWidth(count);
                    }
                } else {
                    myCanvas.setWidth(370);
                    Fon.setWidth(370);
                }
            }
        });

        Visota.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Visota.getText().isEmpty()){
                    String text = Visota.getText();
                    int count = Integer.parseInt(text);
                    if (count > 10 && count < 371){
                        myCanvas.setHeight(count);
                        Fon.setHeight(count);
                    }
                }else {
                    myCanvas.setHeight(370);
                    Fon.setHeight(370);
                }
            }
        });

        Tolshina.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Tolshina.getText().isEmpty()){
                    String text = Tolshina.getText();
                    double count = Double.parseDouble(text);
                    if (count > 0 && count < 50){
                        gc.setLineWidth(count);
                    }
                }
            }
        });
///////////////
        ImageView forOne = new ImageView(new Image("sample/Line.png"));
        PalBut1.graphicProperty().setValue(forOne);
        forOne = new ImageView(new Image("sample/krug.png"));
        PalBut2.graphicProperty().setValue(forOne);
        forOne = new ImageView(new Image("sample/oval.png"));
        PalBut3.graphicProperty().setValue(forOne);
        forOne = new ImageView(new Image("sample/kvadrat.png"));
        PalBut4.graphicProperty().setValue(forOne);

        ToggleGroup grTogBut = new ToggleGroup();
        PalBut1.setToggleGroup(grTogBut);
        PalBut2.setToggleGroup(grTogBut);
        PalBut3.setToggleGroup(grTogBut);
        PalBut4.setToggleGroup(grTogBut);

//////////////
        Contur.getItems().addAll(langs1);
        Contur.setValue("Черный");
        Contur.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = Contur.getValue();
                if (text.equals("Черный")){
                    gc.setStroke(Color.BLACK);
                } else if (text.equals("Красный")){
                    gc.setStroke(Color.RED);
                } else if (text.equals("Синий")){
                    gc.setStroke(Color.BLUE);
                } else if (text.equals("Желтый")){
                    gc.setStroke(Color.YELLOW);
                }
            }
        });

        Zalivka.getItems().addAll(langs1);
        Zalivka.setValue("Черный");
        Zalivka.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = Zalivka.getValue();
                if (text.equals("Черный")){
                    gc.setFill(Color.BLACK);
                } else if (text.equals("Красный")){
                    gc.setFill(Color.RED);
                } else if (text.equals("Синий")){
                    gc.setFill(Color.BLUE);
                } else if (text.equals("Желтый")){
                    gc.setFill(Color.YELLOW);
                }
            }
        });

        TypeOfContur.getItems().addAll(langs2);
        TypeOfContur.setValue("Обычный");
        TypeOfContur.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = TypeOfContur.getValue();
                if (text.equals("Обычный")){
                    gc.setLineDashes(0);
                } else if (text.equals("Штрих")){
                    gc.setLineDashes(5, 5);
                } else if (text.equals("Точка")){
                    gc.setLineDashes(1, 3);
                }
            }
        });
//////////////


        myCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                gc.beginPath();
                if (PalBut1.isSelected() || PalBut2.isSelected() || PalBut3.isSelected() || PalBut4.isSelected()){
                    x = event.getX();
                    y = event.getY();
                    flag = true;
                }
            }
        });

        myCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (flag) {
                    if (PalBut1.isSelected()) {
                        gc.strokeLine(x - 20, y - 20, x + 20, y + 20);
                    } else if (PalBut2.isSelected()) {
                        //gc.fillOval(x, y, 40, 40);
                        Circle c = new Circle(20);
                        c.setLayoutX(x);
                        c.setLayoutY(y);
                    } else if (PalBut3.isSelected()) {
                        gc.fillOval(x, y, 60, 30);
                    } else if (PalBut4.isSelected()){
                        gc.fillRect(x, y, 40, 40);
                    }
                    gc.stroke();
                    gc.closePath();
                    flag = false;
                }
            }
        });

//////////////////////
        gc.setFill(Color.BLACK);
        butSave.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Save Document");//Заголовок диалога
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(Main.prim);
                if (file != null) {
                    try {
                        WritableImage writableImage = new WritableImage(360, 360);
                        myCanvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        System.out.println("Ошибка");
                    }
                }
            }
        });
    }
}
