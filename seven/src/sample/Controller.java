package sample;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleButton playPause;

    @FXML
    private ToggleButton StopPlay;

    @FXML
    private Label titleOfSong;

    @FXML
    private Slider Volue;

    @FXML
    private MenuItem openFile;

    @FXML
    private ImageView usImage;

    @FXML
    private Slider begMus;


    @FXML
    private MediaView medView;
    private Duration d;
    private Media media;
    private MediaPlayer mplayer;
    private boolean flag;
    @FXML
    void initialize() {
        medView = new MediaView();
        File file = new File("C:/Users/ptich/Desktop/ptest_music.mp3");
        URI uri = file.toURI();
        media = new Media(uri.toString());
        mplayer = new MediaPlayer(media);


        ToggleGroup togGr = new ToggleGroup();
        playPause.setToggleGroup(togGr);
        ImageView image = new ImageView(new Image("/sample/play.png"));
        playPause.graphicProperty().setValue(image);
        StopPlay.setToggleGroup(togGr);
        image = new ImageView(new Image("/sample/stop.png"));
        StopPlay.graphicProperty().setValue(image);
        flag = false;
        playPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (media != null){
                    if (!flag){
                        mplayer.play();
                        flag = true;
                    } else {
                        mplayer.pause();
                        flag = false;
                    }
                }
                if (playPause.isSelected()){
                    ImageView image = new ImageView(new Image("/sample/pause.png"));
                    playPause.graphicProperty().setValue(image);
                } else {
                    ImageView image = new ImageView(new Image("/sample/play.png"));
                    playPause.graphicProperty().setValue(image);
                }
            }
        });

        StopPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (media != null){
                    flag = false;
                    mplayer.stop();
                }
            }
        });

        ////////////////

        openFile.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Save Document");//Заголовок диалога
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(Main.prim);
                URI uri = file.toURI();
                if (file != null) {
                    media = new Media(uri.toString());
                    titleOfSong.setText("Да");
                    mplayer = new MediaPlayer(media);
                    medView.setMediaPlayer(mplayer);
                    mplayer.setVolume(0);
                }
            }
        });

        //////////////////////////

        Volue.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                mplayer.setVolume(Volue.getValue());
            }
        });

        mplayer.currentTimeProperty().addListener(ov -> {
            if (!begMus.isValueChanging()) {
                double total = mplayer.getTotalDuration().toMillis();
                double current = mplayer.getCurrentTime().toMillis();
                begMus.setMax(total);
                begMus.setValue(current);
                //lbTime.setTextFill(Color.RED);
                //lbTime.setText(getTimeString(current) + "/" + getTimeString(total));
            }
        });

        begMus.valueProperty().addListener(ov -> {
            if (begMus.isValueChanging()) {
                mplayer.seek(new Duration(begMus.getValue()));
            }
        });
    }
}

