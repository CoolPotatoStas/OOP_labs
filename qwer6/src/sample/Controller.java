package sample;

//не меняет ячейки
//добавить +- отодвинуть камеру

//перспективе пришел пиздец

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem butOpen;

    @FXML
    private MenuItem butSave;

    @FXML
    private TableView<Coordinate> TabInfo;

    @FXML
    private TableColumn<Coordinate, String> idAtom;

    @FXML
    private TableColumn<Coordinate, Double> idX;

    @FXML
    private TableColumn<Coordinate, Double> idY;

    @FXML
    private TableColumn<Coordinate, Double> idZ;

    @FXML
    private TableColumn<Coordinate, ColorPicker> idColor;

    @FXML
    private AnchorPane PaneForModels;

    private PerspectiveCamera camera;

    @FXML
    private SubScene sub;

    public class Coordinate {
        public String atom;
        public Double x;
        public Double y;
        public Double z;
        public ColorPicker c;

        Coordinate(String a, double x0, double y0, double z0) {
            atom = a;
            x = x0;
            y = y0;
            z = z0;
        }

        public String getAtom() {
            return atom;
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }

        public Double getZ() {
            return z;
        }

        public ColorPicker getC() {
            return c;
        }

        public void setAtom(String atom) {
            this.atom = atom;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public void setY(Double y) {
            this.y = y;
        }

        public void setZ(Double z) {
            this.z = z;
        }

        public void setC(ColorPicker c) {
            this.c = c;
        }
    }

    public class AtomNColor{
        String nameOfAtom;
        Color col;
        Group grOfColPick;

        public AtomNColor(String n){
            nameOfAtom = n;
            col = null;
            grOfColPick = new Group();
        }

        public String getNameOfAtom() {
            return nameOfAtom;
        }

        public void setNameOfAtom(String nameOfAtom) {
            this.nameOfAtom = nameOfAtom;
        }

        public Color getCol() {
            return col;
        }

        public void setCol(Color col) {
            this.col = col;
        }
    }

    private ArrayList<Coordinate> allAtoms = new ArrayList<>();
    private ObservableList<Coordinate> usMol = FXCollections.observableArrayList();
    private ArrayList<AtomNColor> nameOfAtoms = new ArrayList<>();
    private ArrayList<Sphere> atoms = new ArrayList<>();;
    private Group all3DObject = new Group();

    private void searchColor() {
        double r;
        double g;
        double b;
        Random rand = new Random();

        for (AtomNColor a : nameOfAtoms) {
            r = rand.nextDouble();
            g = rand.nextDouble();
            b = rand.nextDouble();
            a.col = Color.color(r, g, b);
        }

        for (int i = 0; i < nameOfAtoms.size(); i++){
            rerun:
            for (int j = 0; j < nameOfAtoms.size(); j++){
                if (i != j && nameOfAtoms.get(i).col.equals(nameOfAtoms.get(j).col)){
                    r = rand.nextDouble();
                    g = rand.nextDouble();
                    b = rand.nextDouble();
                    nameOfAtoms.get(i).col = Color.color(r, g, b);
                    continue rerun;
                } else {
                    continue;
                }
            }
        }

    }

    private void readTXT(File file){
        try{
            FileReader fr = new FileReader(file);
            Scanner scan = new Scanner(fr);
            int countOfAtoms = scan.nextInt();
            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            while (scan.hasNextLine()){
                String infoAtom = scan.next();
                Matcher matcher = pattern.matcher(infoAtom);
                if (matcher.find()) {
                    String s = matcher.group();
                    int k = Integer.parseInt(s);
                    if (k > countOfAtoms) {
                        break;
                    }
                    for (int i = 0; i < k; i++) {
                        String one = scan.next();
                        if (i == 0){
                            nameOfAtoms.add(new AtomNColor(one));
                        }
                        double x = scan.nextDouble();
                        double y = scan.nextDouble();
                        double z = scan.nextDouble();
                        Coordinate tmp = new Coordinate(one, x, y, z);
                        allAtoms.add(tmp);
                    }
                }
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void paint3DAtom() {

        int radius = 30;
        for (Coordinate c : allAtoms) {
            Sphere sp = new Sphere(radius);

            sp.setTranslateX(c.x*100 + 100);
            sp.setTranslateY(c.y*100 + 100);
            sp.setTranslateZ(c.z*100 + 100);
            PhongMaterial material = new PhongMaterial();
            Color col = c.c.getValue();
            material.setSpecularColor(col);
            material.setDiffuseColor(col);
            sp.setMaterial(material);
            PaneForModels.getChildren().add(sp);
            all3DObject.getChildren().add(sp);
            atoms.add(sp);
        }

       /* for (Sphere sp1: atoms){
            for (Sphere sp2: atoms){ //- sp1.getTranslateZ()* sp2.getTranslateZ()
                double len = Math.sqrt((sp2.getTranslateX()*sp2.getTranslateX() - sp1.getTranslateX()* sp2.getTranslateX())
                        + (sp2.getTranslateY()*sp2.getTranslateY() - sp1.getTranslateY()* sp2.getTranslateY())
                +(sp2.getTranslateZ()*sp2.getTranslateZ() ))
                        - sp1.getRadius() - sp2.getRadius();
                if (!sp1.equals(sp2) && len > 5 && len <= sp1.getRadius()){
                    //Line l = new Line(sp1.getTranslateX(), sp1.getTranslateY(), sp2.getTranslateX(), sp2.getTranslateY());
                    //l.setFill(Color.BLACK);
                    Cylinder l = new Cylinder(5, len, 5);
                    l.setMaterial(sp1.getMaterial());
                    l.setTranslateX(sp1.getTranslateX()-(x/2));
                    l.setTranslateY(sphere.getTranslateY()-(y/2));
                    cyl.getTransforms().add(new Rotate(90+Math.toDegrees(Math.atan2(y, x)), 0, 0, 0, Rotate.Z_AXIS));
                    PaneForModels.getChildren().add(l);
                    all3DObject.getChildren().add(l);
                    lines.add(l);
                }
            }
        }*/
    }

    private void createGroup(){
        for (AtomNColor i: nameOfAtoms){
            for (Coordinate c: allAtoms){
                if (c.c.getValue().equals(i.col)){
                    i.grOfColPick.getChildren().add(c.c);
                }
            }
        }
        for (Coordinate tmp: allAtoms){
            tmp.c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String name = tmp.atom;
                    Color color = tmp.c.getValue();
                    for (int i = 0; i < allAtoms.size();i++ ){
                        if (allAtoms.get(i).atom.equals(name)) {
                            allAtoms.get(i).c.setValue(color);
                            PhongMaterial material = new PhongMaterial();
                            material.setSpecularColor(color);
                            material.setDiffuseColor(color);
                            atoms.get(i).setMaterial(material);
                        }
                        PaneForModels.getChildren().add(atoms.get(i));
                        all3DObject.getChildren().add(atoms.get(i));
                    }
                }
            });
        }

    }

    @FXML
    void initialize() {

        PaneForModels.getChildren().clear();
        PaneForModels.getChildren().add(0, sub);
        sub.setRoot(all3DObject);
        camera = new PerspectiveCamera();
        sub.setCamera(camera);

        idAtom.setCellValueFactory(new PropertyValueFactory<Coordinate, String>("atom"));
        idX.setCellValueFactory(new PropertyValueFactory<Coordinate, Double>("x"));
        idY.setCellValueFactory(new PropertyValueFactory<Coordinate, Double>("y"));
        idZ.setCellValueFactory(new PropertyValueFactory<Coordinate, Double>("z"));
        idColor.setCellValueFactory(new PropertyValueFactory<Coordinate, ColorPicker>("c"));

        butOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                allAtoms.clear();
                usMol.clear();
                nameOfAtoms.clear();
                atoms.clear();
                all3DObject.getChildren().clear();
                TabInfo.getItems().clear();

                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(Main.prim);
                if (file != null){
                    readTXT(file);
                    for (Coordinate i: allAtoms){
                        usMol.add(i);
                    }
                    searchColor();
                    for (AtomNColor j: nameOfAtoms){
                        for (Coordinate i: allAtoms){
                            if (i.atom.equals(j.nameOfAtom)){
                                i.c = new ColorPicker(j.col);
                            }
                        }
                    }

                    TabInfo.setEditable(true);
                    idAtom.setEditable(false);
                    idX.setEditable(true);
                    idY.setEditable(true);
                    idZ.setEditable(true);
                    idColor.setEditable(true);
                    TabInfo.setItems(usMol);
                    paint3DAtom();
                    createGroup();
                }
            }
        });

        butSave.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Document");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(Main.prim);
                if (file != null) {
                    try {
                        WritableImage writableImage = new WritableImage(452, 491);
                        PaneForModels.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        System.out.println("Ошибка");
                    }
                }
            }
        });

        ///////////////////////////////////////

        Main.prim.addEventHandler(KeyEvent.KEY_PRESSED, event->{
            switch (event.getCode()){
                case W:
                    camera.translateYProperty().set(camera.getTranslateY() + 20);
                    break;
                case S:
                    camera.translateYProperty().set(camera.getTranslateY()-20);
                    break;
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() + 20);
                    break;
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() - 20);
                    break;
                case Q:
                    camera.translateZProperty().set(camera.getTranslateZ() + 20);
                    break;
                case E:
                    camera.translateZProperty().set(camera.getTranslateZ() - 20);
                    break;
                case G:
                    //camera.setRotate(camera.getRotate() + 2);
                    camera.setFieldOfView(camera.getFieldOfView() + 2);
                    break;
                case L:
                    //camera.setRotate(camera.getRotate() - 2);
                    camera.setFieldOfView(camera.getFieldOfView() - 2);
                    break;
            }
        });

        ///////////////////////

       //idX.setCellFactory(TextFieldTableCell.forTableColumn());
        idX.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Coordinate, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Coordinate, Double> t) {
                        ((Coordinate) t.getTableView().getItems().get(t.getTablePosition().getRow())).setX(t.getNewValue());

                        TablePosition firstCell = TabInfo.getSelectionModel().getSelectedCells().get(0);
                        int i = firstCell.getRow();
                        double tmp = allAtoms.get(i).getX();
                        allAtoms.get(i).setX(t.getNewValue());
                        Sphere sp = null;
                        for(Sphere tm: atoms){
                            if ((tm.getTranslateX()-100)/100 == tmp){
                                sp = tm;
                                break;
                            }
                        }

                        sp.setTranslateX(t.getNewValue());
                    }
                }
        );

        idY.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Coordinate, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Coordinate, Double> t) {
                        ((Coordinate) t.getTableView().getItems().get(t.getTablePosition().getRow())).setY(t.getNewValue());

                        TablePosition firstCell = TabInfo.getSelectionModel().getSelectedCells().get(0);
                        int i = firstCell.getRow();
                        double tmp = allAtoms.get(i).getY();
                        allAtoms.get(i).setY(t.getNewValue());
                        Sphere sp = null;
                        for(Sphere tm: atoms){
                            if ((tm.getTranslateY()-100)/100 == tmp){
                                sp = tm;
                                break;
                            }
                        }

                        sp.setTranslateY(t.getNewValue());
                    }
                }
        );

        idZ.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Coordinate, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Coordinate, Double> t) {
                        ((Coordinate) t.getTableView().getItems().get(t.getTablePosition().getRow())).setZ(t.getNewValue());

                        TablePosition firstCell = TabInfo.getSelectionModel().getSelectedCells().get(0);
                        int i = firstCell.getRow();
                        double tmp = allAtoms.get(i).getZ();
                        allAtoms.get(i).setZ(t.getNewValue());
                        Sphere sp = null;
                        for(Sphere tm: atoms){
                            if ((tm.getTranslateZ()-100)/100 == tmp){
                                sp = tm;
                                break;
                            }
                        }

                        sp.setTranslateZ(t.getNewValue());
                    }
                }
        );
    }
}