package sample;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleButton butOne;

    @FXML
    private ToggleButton butTwo;

    @FXML
    private ToggleButton butThree;

    @FXML
    private Button butSave;

    @FXML
    private Canvas myCanvas;

    private double x;
    private double y;
    private boolean flag;

    @FXML
    void initialize() {
        ToggleGroup togGr = new ToggleGroup();
        butOne.setToggleGroup(togGr);
        butTwo.setToggleGroup(togGr);
        butThree.setToggleGroup(togGr);

        ImageView forOne = new ImageView(new Image("sample/Line.png"));
        butOne.graphicProperty().setValue(forOne);
        forOne = new ImageView(new Image("sample/krug.png"));
        butTwo.graphicProperty().setValue(forOne);
        forOne = new ImageView(new Image("sample/kvadrat.png"));
        butThree.graphicProperty().setValue(forOne);

        //////////////////////

        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        flag = false;

        myCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                gc.beginPath();
                if (butOne.isSelected() || butTwo.isSelected() || butThree.isSelected()){
                    x = event.getX();
                    y = event.getY();
                    flag = true;
                    Scene tmp = Main.prim.getScene();
                    if (butOne.isSelected()) {
                        tmp.setCursor(new ImageCursor(new Image("sample/Line.png"), 16,16));
                    } else if (butTwo.isSelected()) {
                        tmp.setCursor(new ImageCursor(new Image("sample/krug.png"), 16,16));
                    } else if (butThree.isSelected()){
                        tmp.setCursor(new ImageCursor(new Image("sample/kvadrat.png"), 16,16));
                    }
                }
            }
        });

        myCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (flag) {
                    if (butOne.isSelected()) {
                        gc.strokeLine(x - 20, y - 20, x + 20, y + 20);
                    } else if (butTwo.isSelected()) {
                        gc.fillOval(x-15, y-15, 30, 30);
                    } else if (butThree.isSelected()){
                        gc.fillRect(x-20, y-20, 40, 40);
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
