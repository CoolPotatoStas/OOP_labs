import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.Random;


public class Case2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Random rand = new Random();
        int choice = rand.nextInt(1000);

        Rectangle r = new Rectangle();
        r.setX(100);
        r.setY(100);
        r.setWidth(100);
        r.setHeight(100);
        r.setStroke(Color.BLACK);
        r.setStyle("-fx-background-color: transparent;");
        r.setFill(Color.WHITE);

        Ellipse c = new Ellipse();
        Rectangle p = new Rectangle(50, 50);
        if (choice%2 == 0){
            c.setFill(Color.AQUAMARINE);
            c.setRadiusX(40.0);
            c.setRadiusY(40);
            c.setCenterX(150);
            c.setCenterY(150);
        } else {
            p.setFill(Color.AQUAMARINE);
            p.setLayoutX(125);
            p.setLayoutY(125);
        }

        AnchorPane root = new AnchorPane();
        if (choice%2 == 0){
            root.getChildren().addAll(r,c);
        } else {
            root.getChildren().addAll(r,p);
        }
        primaryStage.setTitle("Case2");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (choice%2 == 0){
                    if (keyEvent.getCode() == KeyCode.ADD && (r.getHeight() + 5 < 300)) {
                        r.setHeight(r.getHeight()+10);
                        r.setLayoutY(r.getLayoutY() - 5);
                        c.setRadiusY(c.getRadiusY() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.SUBTRACT && (r.getHeight() - 5 > (2*c.getRadiusY()+5))) {
                        r.setHeight(r.getHeight()-10);
                        r.setLayoutY(r.getLayoutY() + 5);
                        c.setRadiusY(c.getRadiusY() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT && (r.getWidth() + 5 < 300)) {
                        r.setWidth(r.getWidth()+10);
                        r.setLayoutX(r.getLayoutX() - 5);
                        c.setRadiusX(c.getRadiusX() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT && (r.getWidth() - 5 > (2*c.getRadiusX()+5))) {
                        r.setWidth(r.getWidth()-10);
                        r.setLayoutX(r.getLayoutX() + 5);
                        c.setRadiusX(c.getRadiusX() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.D) {
                        c.setLayoutX(c.getLayoutX()+5);
                        r.setLayoutX(r.getLayoutX() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.A) {
                        c.setLayoutX(c.getLayoutX()-5);
                        r.setLayoutX(r.getLayoutX() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.W) {
                        c.setLayoutY(c.getLayoutY()-5);
                        r.setLayoutY(r.getLayoutY() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.S) {
                        c.setLayoutY(c.getLayoutY()+5);
                        r.setLayoutY(r.getLayoutY() + 5);
                    }
                } else {
                    if (keyEvent.getCode() == KeyCode.ADD && (r.getHeight() + 5 < 300)) {
                        r.setHeight(r.getHeight()+10);
                        r.setLayoutY(r.getLayoutY() - 5);
                        p.setHeight(p.getHeight()+10);
                        p.setLayoutY(p.getLayoutY() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.SUBTRACT && (r.getHeight() - 5 > (p.getHeight()+5))) {
                        r.setHeight(r.getHeight()-10);
                        r.setLayoutY(r.getLayoutY() + 5);
                        p.setHeight(p.getHeight()-10);
                        p.setLayoutY(p.getLayoutY() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT && (r.getWidth() + 5 < 300)) {
                        r.setWidth(r.getWidth()+10);
                        r.setLayoutX(r.getLayoutX() - 5);
                        p.setWidth(p.getWidth()+10);
                        p.setLayoutX(p.getLayoutX() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT && (r.getWidth() - 5 > (p.getHeight()+5))) {
                        r.setWidth(r.getWidth()-10);
                        r.setLayoutX(r.getLayoutX() + 5);
                        p.setWidth(p.getWidth()-10);
                        p.setLayoutX(p.getLayoutX() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.D) {
                        p.setLayoutX(p.getLayoutX()+5);
                        r.setLayoutX(r.getLayoutX() + 5);
                    }
                    if (keyEvent.getCode() == KeyCode.A) {
                        p.setLayoutX(p.getLayoutX()-5);
                        r.setLayoutX(r.getLayoutX() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.W) {
                        p.setLayoutY(p.getLayoutY()-5);
                        r.setLayoutY(r.getLayoutY() - 5);
                    }
                    if (keyEvent.getCode() == KeyCode.S) {
                        p.setLayoutY(p.getLayoutY()+5);
                        r.setLayoutY(r.getLayoutY() + 5);
                    }
                }
            }
        });
    }
}
