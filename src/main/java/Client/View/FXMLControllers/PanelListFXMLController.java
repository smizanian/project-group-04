package Client.View.FXMLControllers;

import Client.Database.GameDatabase;
import Server.Model.City;
import Server.Model.GameModel;
import Server.Model.Unit;
import Client.View.GraphicalBases;
import Client.View.Transitions.CityPanelChoosingTransition;
import Client.View.Transitions.UnitPanelChoosingTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanelListFXMLController {

    @FXML
    private AnchorPane mainAnchorPane;

    private ChoiceBox<String> cities;
    private TextArea cityInformation;

    private ChoiceBox<String> units;
    private TextArea unitInformation;

    private Button cityPanel;

    private UnitPanelChoosingTransition unitPanelChoosingTransition;
    private CityPanelChoosingTransition cityPanelChoosingTransition;

    @FXML
    public void initialize() throws IOException {
        setBackground();
        setBackButton();



        setNoUnitText();
        setUnitInformation();
        setUnitsList();

        setNoCitiesText();
        setCityInformation();
        setCitiesList();
        setCityPanelButton();

        GameModel.isGame = true;
    }

    private void setUnitsList() throws IOException {
        cities = new ChoiceBox<>();

        unitPanelChoosingTransition = new UnitPanelChoosingTransition(this);
        unitPanelChoosingTransition.play();

        units = new ChoiceBox<>();
        ArrayList<String> soldierNames = new ArrayList<>();
        for (Unit soldier : GameDatabase.getCivilizationByTurn(GameDatabase.turn).getCombatUnits()) {
            soldierNames.add(soldier.getUnitType() + " in X: " + Integer.toString(soldier.getX()) + " and Y: " + Integer.toString(soldier.getY()));
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(soldierNames);
        units.setItems(usersInput);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.turn).getCombatUnits().size()  == 0) {
            units.setVisible(false);
        }

        units.setLayoutX(800);
        units.setLayoutY(100);
        mainAnchorPane.getChildren().add(units);
    }

    private void setUnitInformation() {
        unitInformation = new TextArea();
        unitInformation.setPrefWidth(130);
        unitInformation.setPrefWidth(130);
        unitInformation.setLayoutX(1020);
        unitInformation.setLayoutY(100);
        unitInformation.setEditable(false);
        mainAnchorPane.getChildren().add(unitInformation);
    }

    private void setNoUnitText() throws IOException {
        Text text = new Text("No Units yet");
        text.setStyle("-fx-fill: white; -fx-font-size: 70");
        text.setX(770);
        text.setY(300);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.turn).getCombatUnits().size() != 0) {
            text.setVisible(false);
        }
        mainAnchorPane.getChildren().add(text);
    }

    private void setCityInformation() {
        cityInformation = new TextArea();
        cityInformation.setPrefWidth(300);
        cityInformation.setPrefWidth(300);
        cityInformation.setLayoutX(220);
        cityInformation.setLayoutY(100);
        cityInformation.setEditable(false);
        mainAnchorPane.getChildren().add(cityInformation);
    }

    private void setCityPanelButton() {
        cityPanel = new Button("Go to City Panel");
        cityPanel.setPrefWidth(300);
        cityPanel.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        cityPanel.setLayoutX(220);
        cityPanel.setLayoutY(400);
        cityPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicalBases.enterGame("CityPanel");
            }
        });
        mainAnchorPane.getChildren().add(cityPanel);
    }

    private void setNoCitiesText() throws IOException {
        Text text = new Text("No Cities yet");
        text.setStyle("-fx-fill: white; -fx-font-size: 70");
        text.setX(70);
        text.setY(300);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() != 0) {
            text.setVisible(false);
        }
        mainAnchorPane.getChildren().add(text);
    }

    private void setCitiesList() throws IOException {
        cities = new ChoiceBox<>();

        cityPanelChoosingTransition = new CityPanelChoosingTransition(this);
        cityPanelChoosingTransition.play();

        cities = new ChoiceBox<>();
        ArrayList<String> cityNames = new ArrayList<>();
        for (City city : GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities()) {
            cityNames.add(city.getName());
        }
        ObservableList<String> usersInput = FXCollections.observableArrayList(cityNames);
        cities.setItems(usersInput);
        if(GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).getCities().size() == 0) {
            cities.setVisible(false);
        }

        cities.setLayoutX(100);
        cities.setLayoutY(100);
        mainAnchorPane.getChildren().add(cities);
    }

    private void setBackButton() {
        Button button = new Button("BACK");
        button.setPrefWidth(200);
        button.setLayoutX(540);
        button.setLayoutY(650);
        button.setStyle("-fx-background-color: #222c41;-fx-border-color: #555564; -fx-text-fill: white;-fx-border-width: 3;");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    restartTransitions();
                    GraphicalBases.enterGame("Game");
                }
        });
        mainAnchorPane.getChildren().add(button);
    }

    private void restartTransitions() {
        if(cityPanelChoosingTransition != null) {
            cityPanelChoosingTransition.pause();
        }
        if(unitPanelChoosingTransition != null) {
            unitPanelChoosingTransition.pause();
        }
    }

    private void setBackground() {
        Rectangle black = new Rectangle();
        black.setX(0);
        black.setY(0);;
        black.setWidth(1280);
        black.setHeight(720);
        black.setFill(new ImagePattern(GraphicalBases.BLACK));
        mainAnchorPane.getChildren().add(black);

        Rectangle sasani = new Rectangle();
        sasani.setX(310);
        sasani.setY(100);
        sasani.setWidth(1280 - 2*sasani.getX());
        sasani.setHeight(500);
        sasani.setFill(new ImagePattern(GraphicalBases.SASANI));
        mainAnchorPane.getChildren().add(sasani);
    }

    public void handleCityChoiceBox() throws IOException {
        if(cities.getValue() != null) {
            cityInformation.setText(GameDatabase.getCityByName(cities.getValue()).toString());
            cityPanel.setVisible(true);
            cityInformation.setVisible(true);
            selectCity(cities.getValue());
        } else {
            cityPanel.setVisible(false);
            cityInformation.setVisible(false);
        }
    }

    private void selectCity(String value) throws IOException {
        GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedCity(GameDatabase.getCityByName(value));
    }

    public void handleUnitChoiceBox() throws IOException {

        if(units.getValue() != null) {
            unitInformation.setText(findUnit().toString());
            unitInformation.setVisible(true);
            selectUnit(units.getValue());
        } else {
            unitInformation.setVisible(false);
        }
    }

    private Unit findUnit() throws IOException {
        Pattern pattern = Pattern.compile("(?<name>\\S+) in X: (?<x>\\d+) and Y: (?<y>\\d+)");
        Matcher matcher = pattern.matcher(units.getValue());
        if(!matcher.matches()) {
            return null;
        }
//        System.out.println(units.getValue());
//        System.out.println(matcher.matches());
//        System.out.println(matcher.group("x"));
//        System.out.println(matcher.group("y"));
        return GameDatabase.getTileByXAndY(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))).getCombatUnit();
    }

    private void selectUnit(String value) throws IOException {

        GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(findUnit());
    }
}
