package sample;
//добавить тип штриха и все пучком
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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
    private AnchorPane canAncPane;

    @FXML
    private Canvas myCanvas;
    boolean flag = false;
    double x, y;
    GraphicsContext gc;
    boolean k = false;

    Color zal;
    Color cont;
    double tolsh = 1;

    @FXML
    void initialize() {
        gc = myCanvas.getGraphicsContext2D();
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
                    if (count > 0 && count <= 10){
                        tolsh = count;
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
        cont = Color.BLACK;
        Contur.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = Contur.getValue();
                if (text.equals("Черный")){ //setStroleWidht + изменение цвета через Chooser + фокус
                    //gc.setStroke(Color.BLACK);
                    cont = Color.BLACK;
                } else if (text.equals("Красный")){
                    //gc.setStroke(Color.RED);
                    cont = Color.RED;
                } else if (text.equals("Синий")){
                    //gc.setStroke(Color.BLUE);
                    cont = Color.BLUE;
                } else if (text.equals("Желтый")){
                    //gc.setStroke(Color.YELLOW);
                    cont = Color.YELLOW;
                }
            }
        });

        zal = Color.BLACK;
        Zalivka.getItems().addAll(langs1);
        Zalivka.setValue("Черный");
        Zalivka.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = Zalivka.getValue();
                if (text.equals("Черный")){
                    //gc.setFill(Color.BLACK);
                    zal = Color.BLACK;
                } else if (text.equals("Красный")){
                    //gc.setFill(Color.RED);
                    zal = Color.RED;
                } else if (text.equals("Синий")){
                    //gc.setFill(Color.BLUE);
                    zal = Color.BLUE;
                } else if (text.equals("Желтый")){
                    //gc.setFill(Color.YELLOW);
                    zal = Color.YELLOW;
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
                    //gc.setLineDashes(0);
                } else if (text.equals("Штрих")){
                    //gc.setLineDashes(5, 5);
                } else if (text.equals("Точка")){
                    //gc.setLineDashes(1, 3);
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
                        k = true;
                        //Rectangle r = new Rectangle();
                        //r.setX(x - 25);
                        //r.setY(y - 25);
                        //r.setHeight(50);
                        //r.setWidth(50);
                        //r.setStroke(Color.BLACK);
                        //r.setStyle("-fx-background-color: transparent;");
                        //r.setFill(Color.WHITE);
                        //canAncPane.getChildren().add(r);
                        Line l = new Line(x - 20, y - 20, x + 20, y + 20);
                        l.setStroke(cont);
                        l.setStrokeWidth(tolsh);
                        canAncPane.getChildren().add(l);

                        Main.sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent keyEvent) {
                                if (k){
                                    if (keyEvent.getCode() == KeyCode.ADD) {//&& (r.getHeight() + 5 < 300)
                                        //r.setHeight(r.getHeight() + 10);
                                        //r.setLayoutY(r.getLayoutY() - 5);
                                        //r.setWidth(r.getWidth() + 10);
                                        //r.setLayoutX(r.getLayoutX() - 5);
                                        l.setStartX(l.getStartX() - 5);
                                        l.setStartY(l.getStartY() - 5);
                                        l.setEndX(l.getEndX() + 5);
                                        l.setEndY(l.getEndY() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.SUBTRACT) {//&& (r.getHeight() - 5 > (2*c.getRadius()+5)
                                        //r.setHeight(r.getHeight() - 10);
                                        //r.setLayoutY(r.getLayoutY() + 5);
                                        //r.setWidth(r.getWidth() - 10);
                                        //r.setLayoutX(r.getLayoutX() + 5);
                                        l.setStartX(l.getStartX() + 5);
                                        l.setStartY(l.getStartY() + 5);
                                        l.setEndX(l.getEndX() - 5);
                                        l.setEndY(l.getEndY() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.D) {
                                        l.setLayoutX(l.getLayoutX() + 5);
                                        //r.setLayoutX(r.getLayoutX() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.A) {
                                        l.setLayoutX(l.getLayoutX() - 5);
                                        //r.setLayoutX(r.getLayoutX() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.W) {
                                        l.setLayoutY(l.getLayoutY() - 5);
                                        //r.setLayoutY(r.getLayoutY() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.S) {
                                        l.setLayoutY(l.getLayoutY() + 5);
                                        //r.setLayoutY(r.getLayoutY() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.G) {
                                        //r.setFill(Color.WHITE);
                                        //canAncPane.getChildren().remove(r);
                                        k = false;
                                    }
                                }
                            }
                        });
                        if (!k){
                            Main.sc.setOnKeyPressed(null);
                        }
                    } else if (PalBut2.isSelected()) {
                        k = true;

                        Circle l = new Circle();
                        l.setCenterX(x);
                        l.setCenterY(y);
                        l.setRadius(10);
                        l.setStroke(cont);
                        l.setFill(zal);
                        l.setStrokeWidth(tolsh);
                        canAncPane.getChildren().add(l);

                        Main.sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent keyEvent) {
                                if (k){
                                    if (keyEvent.getCode() == KeyCode.ADD) {//&& (r.getHeight() + 5 < 300)
                                        l.setRadius(l.getRadius() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.SUBTRACT) {//&& (r.getHeight() - 5 > (2*c.getRadius()+5)

                                        l.setRadius(l.getRadius() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.D) {
                                        l.setLayoutX(l.getLayoutX() + 5);

                                    }
                                    if (keyEvent.getCode() == KeyCode.A) {
                                        l.setLayoutX(l.getLayoutX() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.W) {
                                        l.setLayoutY(l.getLayoutY() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.S) {
                                        l.setLayoutY(l.getLayoutY() + 5);

                                    }
                                    if (keyEvent.getCode() == KeyCode.G) {

                                        k = false;
                                    }
                                }
                            }
                        });
                        if (!k){
                            Main.sc.setOnKeyPressed(null);
                        }
                    } else if (PalBut3.isSelected()) {
                        k = true;

                        Ellipse l = new Ellipse();
                        l.setCenterX(x);
                        l.setCenterY(y);
                        l.setRadiusX(20);
                        l.setRadiusY(10);
                        l.setStroke(cont);
                        l.setFill(zal);
                        l.setStrokeWidth(tolsh);
                        canAncPane.getChildren().add(l);

                        Main.sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent keyEvent) {
                                if (k){
                                    if (keyEvent.getCode() == KeyCode.ADD) {//&& (r.getHeight() + 5 < 300)

                                        l.setRadiusX(l.getRadiusX() + 5);
                                        l.setRadiusY(l.getRadiusY() + 2.5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.SUBTRACT) {//&& (r.getHeight() - 5 > (2*c.getRadius()+5)

                                        l.setRadiusX(l.getRadiusX() - 5);
                                        l.setRadiusY(l.getRadiusY() - 2.5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.D) {
                                        l.setLayoutX(l.getLayoutX() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.A) {
                                        l.setLayoutX(l.getLayoutX() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.W) {
                                        l.setLayoutY(l.getLayoutY() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.S) {
                                        l.setLayoutY(l.getLayoutY() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.G) {
                                        k = false;
                                    }
                                }
                            }
                        });
                        if (!k){
                            Main.sc.setOnKeyPressed(null);
                        }
                    } else if (PalBut4.isSelected()){
                        k = true;
                        Rectangle l = new Rectangle();
                        l.setHeight(25);
                        l.setWidth(25);
                        l.setX(x - 11.625);
                        l.setY(y - 11.625);
                        l.setStroke(cont);
                        l.setFill(zal);
                        l.setStrokeWidth(tolsh);
                        canAncPane.getChildren().add(l);

                        Main.sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent keyEvent) {
                                if (k){
                                    if (keyEvent.getCode() == KeyCode.ADD) {
                                        l.setHeight(l.getHeight() + 10);
                                        l.setLayoutY(l.getLayoutY() - 5);
                                        l.setWidth(l.getWidth() + 10);
                                        l.setLayoutX(l.getLayoutX() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.SUBTRACT) {
                                        l.setHeight(l.getHeight() - 10);
                                        l.setLayoutY(l.getLayoutY() + 5);
                                        l.setWidth(l.getWidth() - 10);
                                        l.setLayoutX(l.getLayoutX() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.D) {
                                        l.setLayoutX(l.getLayoutX() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.A) {
                                        l.setLayoutX(l.getLayoutX() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.W) {
                                        l.setLayoutY(l.getLayoutY() - 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.S) {
                                        l.setLayoutY(l.getLayoutY() + 5);
                                    }
                                    if (keyEvent.getCode() == KeyCode.G) {
                                        k = false;
                                    }
                                }
                            }
                        });
                        if (!k){
                            Main.sc.setOnKeyPressed(null);
                        }
                    }
                    gc.stroke();
                    gc.closePath();
                    flag = false;
                }
            }
        });

//////////////////////
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
                        WritableImage writableImage = new WritableImage((int)myCanvas.getWidth(), (int)myCanvas.getHeight());
                        canAncPane.snapshot(null, writableImage);
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