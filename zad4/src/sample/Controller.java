package sample;

//import java.net.URL;
//import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    //@FXML
   // private ResourceBundle resources;

    //@FXML
    //private URL location;

    @FXML
    private TextArea Output;

    @FXML
    private ChoiceBox<String> function;

    @FXML
    private TextField Shir;

    @FXML
    private TextField Min;
    private double minimum;

    @FXML
    private TextField Max;
    private double maximum;

    @FXML
    private RadioButton butShow;

    @FXML
    private RadioButton butClear;

    @FXML
    private LineChart<Number, Number> lineFun;

    @FXML
    private NumberAxis NumX;

    //@FXML
    //private NumberAxis NumY;

    private boolean[] infoFun = new boolean[4];
    private int ind = -1;
    ArrayList<XYChart.Series> series = new ArrayList();
    ArrayList<ObservableList<XYChart.Data> > datas = new ArrayList(); //
    @FXML
    void initialize() {
        lineFun.setCreateSymbols(false);
        ObservableList<String> langs1 = FXCollections.observableArrayList();
        langs1.add("<...>");
        langs1.add("y = cos(x)");
        langs1.add("y = sin(x)");
        langs1.add("y = tag(x)");
        langs1.add("y = sin(x*x)");

        for(int i = 0; i < 4;i++) {
            infoFun[i] = false;
            ObservableList<XYChart.Data> tmp = FXCollections.observableArrayList();
            datas.add(tmp);
            XYChart.Series tmp2 = new XYChart.Series();
            series.add(tmp2);
            if (i == 0) {
                for (double j = -5; j <= 5; j = j + 0.05) {
                    datas.get(i).add(new XYChart.Data(j, Math.cos(j)));
                }
            } else if (i == 1) {
                for (double j = -5; j <= 5; j = j + 0.05) {
                    datas.get(i).add(new XYChart.Data(j, Math.sin(j)));
                }
            } else if (i == 2) {
                for (double j = -1; j <= 1; j = j + 0.05) {
                    datas.get(i).add(new XYChart.Data(j, Math.tan(j)));
                }
            } else {
                for (double j = -5; j <= 5; j = j + 0.05) {
                    datas.get(i).add(new XYChart.Data(j, Math.sin(j*j)));
                }
            }
            series.get(i).setData(datas.get(i));
        }

        function.getItems().addAll(langs1);
        function.setValue("<...>");
        function.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = function.getValue();
                if (text.equals("y = cos(x)")){
                    ind = 0;
                } else if (text.equals("y = sin(x)")){
                    ind = 1;
                } else if (text.equals("y = tag(x)")){
                    ind = 2;
                } else if (text.equals("y = sin(x*x)")){
                    ind = 3;
                } else {
                    ind = -1;
                }
            }
        });

        /////////////////////////////////

        ToggleGroup togGr = new ToggleGroup();
        butShow.setToggleGroup(togGr);
        butShow.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if (ind != -1){
                    if (!infoFun[ind]){
                        infoFun[ind] = true;
                        lineFun.getData().add(series.get(ind));
                    }
                }
            }
        });

        butClear.setToggleGroup(togGr);
        butClear.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if (ind != -1){
                    if (infoFun[ind]){
                        infoFun[ind] = false;
                        lineFun.getData().remove(series.get(ind));
                    }
                }
            }
        });

        /////////////////////////////
//изменять толищну через css
        /*Shir.setOnAction(new EventHandler<ActionEvent>(){ //нет редактора для изменения толщины линий
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Shir.getText().isEmpty()){
                    String text = Shir.getText();
                    double count = Double.parseDouble(text);
                    if (count >= 10 && count <= 100){

                    }
                }
            }
        });*/

        //////////////////////////////////////

        minimum = NumX.getLowerBound();
        maximum = NumX.getUpperBound();

        Min.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Min.getText().isEmpty()){
                    String text = Min.getText();
                    double count = Double.parseDouble(text);
                    if (count >= -50 && count <= 50 && count < (maximum+1)){
                        NumX.setAutoRanging(false);
                        NumX.setLowerBound(count);
                        minimum = count;
                    }
                }
            }
        });

        Max.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!Max.getText().isEmpty()){
                    String text = Max.getText();
                    double count = Double.parseDouble(text);
                    if (count >= -50 && count <= 50 && count > (minimum+1)){
                        NumX.setAutoRanging(false);
                        NumX.setUpperBound(count);
                        maximum = count;
                    }
                }
            }
        });
    }
}
