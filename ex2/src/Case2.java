import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;


public class Case2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Rectangle r = new Rectangle();
        r.setX(100);
        r.setY(100);
        r.setWidth(100);
        r.setHeight(100);
        r.setStroke(Color.BLACK);
//рнадомная фигура
        //изменение размеров
        r.setStyle("-fx-background-color: transparent;");
        r.setFill(Color.WHITE);

        Circle c = new Circle();
        c.setFill(Color.AQUAMARINE);
        c.setRadius(40.0);
        c.setCenterX(150);
        c.setCenterY(150);

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(r,c);
        primaryStage.setTitle("Case2");
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ADD && (r.getHeight() + 5 < 300)) {
                    r.setHeight(r.getHeight()+10);
                    r.setLayoutY(r.getLayoutY() - 5);
                }
                if (keyEvent.getCode() == KeyCode.SUBTRACT && (r.getHeight() - 5 > (2*c.getRadius()+5))) {
                     r.setHeight(r.getHeight()-10);
                     r.setLayoutY(r.getLayoutY() + 5);
                }
                if (keyEvent.getCode() == KeyCode.RIGHT && (r.getWidth() + 5 < 300)) {
                    r.setWidth(r.getWidth()+10);
                    r.setLayoutX(r.getLayoutX() - 5);
                }
                if (keyEvent.getCode() == KeyCode.LEFT && (r.getWidth() - 5 > (2*c.getRadius()+5))) {
                    r.setWidth(r.getWidth()-10);
                    r.setLayoutX(r.getLayoutX() + 5);
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
            }
        });
    }
}
