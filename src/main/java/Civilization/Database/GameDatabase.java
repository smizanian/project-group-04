package Civilization.Database;

import Civilization.Controller.CopyOfGameDatabase;
import Civilization.Controller.SavingGame;
import Civilization.Controller.GameMenuController;
import Civilization.Model.*;
import Civilization.View.FXMLControllers.GameFXMLController;
import Civilization.View.Transitions.TransitionDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameDatabase {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();

    public static int length = 50;
    public static int width = 10;

    public static int turn = 0;
    public static int year = 0;
    public static boolean cheated = false;
    public static Civilization cheatedCivilization = null;

    public static void setStaticFields(ArrayList<Civilization> civilizations,ArrayList<Tile> tiles,int tool,int arz,int nobat
            ,int sal, boolean taghalobKarde,Civilization civilizationtaghalob){
        players = civilizations;
        map = tiles;
        length = tool;
        width = arz;
        year = sal;
        turn = nobat;
        cheated = taghalobKarde;
        cheatedCivilization = civilizationtaghalob;
    }

//    public static class SavingData {
//        private int length;
//        private int width;
//        private int turn;
//        private int year;
//        private ArrayList<String> mapData;
//        private ArrayList<String> civilizationData;
//
//        public SavingData(int length, int width, int turn, int year, ArrayList<Civilization> players, ArrayList<Tile> map) {
//            this.length = length;
//            this.width = width;
//            this.turn = turn;
//            this.year = year;
//            mapData = new ArrayList<>();
//            civilizationData = new ArrayList<>();
//            setMapData(players, map);
//            setCivilizationData(players);
//        }
//
//        public void retrieveGameFromSavingData() {
//            Matcher mapMatcher;
//            for (int i = 0; i < mapData.size(); i++) {
//                String tile = mapData.get(i);
//                mapMatcher = Pattern.compile("type \u003d (?<type>.+)\nX \u003d (?<x>\\d+) Y \u003d (?<y>\\d+)").matcher(tile);
//                map = new ArrayList<>();
//                if (mapMatcher.matches()) {
//
//                    Tile newTile = new Tile("fogOfWar", mapMatcher.group("type")
//                            , Integer.parseInt(mapMatcher.group("x"))
//                            , Integer.parseInt(mapMatcher.group("y")));
//                    map.add(newTile);
//                } else {
//                    mapMatcher = Pattern.compile("type \u003d (?<type>.+)\nX \u003d (?<x>\\d+) Y \u003d (?<y>\\d+) (?<civilName>\\S+)").matcher(tile);
//                    if (mapMatcher.matches()){
//                        String civilName = mapMatcher.group("civilName");
//                        Civilization civilization = GameDatabase.getCivilizationByNickname(civilName);
//                        if (civilization == null){
//                            civilization = new Civilization(UserDatabase.getUserByNickname(civilName).getUsername(),civilName);
//                            players.add(civilization);
//                        }
//                        Tile newTile = new Tile("fogOfWar", mapMatcher.group("type")
//                                , Integer.parseInt(mapMatcher.group("x"))
//                                , Integer.parseInt(mapMatcher.group("y")));
//                        map.add(newTile);
//                        civilization.addTile(newTile);
//                    }
//                    else {
//                        mapMatcher = Pattern.compile("type \u003d (?<type>.+)\nX \u003d (?<x>\\d+) Y \u003d (?<y>\\d+) (?<civilName>\\S+) (?<cityName>\\S+)").matcher(tile);
//                        if (mapMatcher.matches()){
//                            String civilName = mapMatcher.group("civilName");
//                            Civilization civilization = GameDatabase.getCivilizationByNickname(civilName);
//                            City city = GameDatabase.getCityByName(mapMatcher.group("cityName"));
//                            if (civilization == null){
//                                civilization = new Civilization(UserDatabase.getUserByNickname(civilName).getUsername(),civilName);
//                                players.add(civilization);
//                            }
//                            if (city == null){
//                                city = new City(mapMatcher.group("cityName"),0,0,);
//                            }
//                            Tile newTile = new Tile("fogOfWar", mapMatcher.group("type")
//                                    , Integer.parseInt(mapMatcher.group("x"))
//                                    , Integer.parseInt(mapMatcher.group("y")));
//                            map.add(newTile);
//                            civilization.addTile(newTile);
//                        }
//                        else {
//                            System.out.println("dude the fuck?!");
//                        }
//                    }
//                }
//            }
//        }
//
//        public void writeMapOnTheFile() throws FileNotFoundException {
//
//            try {
//                Object obj = new JSONParser().parse(new FileReader("c:\\file.json"));
//
//                JSONObject jsonObject = (JSONObject) obj;
//
//                turn = (int) jsonObject.get("turn");
//                width = (int) jsonObject.get("width");
//                length = (int) jsonObject.get("length");
//                year = (int) jsonObject.get("year");
//                mapData = new ArrayList<>();
//                JSONArray jsonArray = (JSONArray) jsonObject.get("mapData");
//                if (jsonArray != null) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        mapData.add(jsonArray.getString(i));
//                    }
//                }
//                civilizationData = new ArrayList<>();
//                jsonArray = (JSONArray) jsonObject.get("civilizationData");
//                if (jsonArray != null) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        civilizationData.add(jsonArray.getString(i));
//                    }
//                }
//
//
//            } catch (Exception ex) {
//                System.out.println("kose nanat!");
//            }
//            // generate players
//            // generate tiles
//            // assign tiles --> players
//            //
//        }
//
//
//        private void setCivilizationData(ArrayList<Civilization> players) {
//            for (Civilization civilization : players) {
//                civilizationData.add(civilization.toString());
//            }
//        }
//
//        private void setMapData(ArrayList<Civilization> players, ArrayList<Tile> map) {
//            for (Tile tile : map) {
//                String tileData = tile.toString();
//                if (GameDatabase.getCivilizationByTile(tile) != null) {
//                    tileData += " " + Objects.requireNonNull(GameDatabase.getCivilizationByTile(tile)).getNickname();
//                }
//                if (GameDatabase.getCityByXAndY(tile.getX(), tile.getY()) != null) {
//                    tileData += " " + Objects.requireNonNull(GameDatabase.getCityByXAndY(tile.getX(), tile.getY())).getName() +
//                            " " + Objects.requireNonNull(GameDatabase.getCityByXAndY(tile.getX(), tile.getY())).getCivilizationName();
//                }
//                mapData.add(tileData);
//            }
//
//        }
//
//        public int getLength() {
//            return length;
//        }
//
//        public int getWidth() {
//            return width;
//        }
//
//        public int getTurn() {
//            return turn;
//        }
//
//    }
    public static Civilization getCivilizationByUnit(Unit unit) {
        GameMenuController gameMenuController = new GameMenuController(new GameModel());
        for (Civilization player : players) {
            if(gameMenuController.isUnitForThisCivilization(getCivilizationIndex(player.getNickname()), unit)) {
                return player;
            }
        }
        return null;
    }

    public static void generateRuin() {
        Random random = new Random();
        for (Tile tile : GameDatabase.map) {
            if(getCivilizationByTile(tile) == null) {
                int ruin = random.nextInt(500);
                if(ruin == 12) {
                    tile.setRuin(new Ruin());
                }
            }
        }
    }


    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.turn = 0;
        GameDatabase.year = 0;
        GameDatabase.cheated = false;
        GameDatabase.cheatedCivilization = null;
        GameDatabase.players = players;
        TransitionDatabase.restart();
        for (Civilization civilization : players) {
            civilization.setHappiness(GlobalVariables.firstHappiness * GameDatabase.players.size());
            if (civilization.getNickname().equals(User.loggedInUser.getNickname())) {
                civilization.getMessages().add("It's your game, Good luck ;)");
            } else {
                civilization.getMessages().add("You have an invitation from " + User.loggedInUser.getNickname());
            }

        }
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByUsername(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }

    public static Civilization getCivilizationByNickname(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (GameDatabase.players.get(i).getNickname().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }


    public static City getCityByName(String cityName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getCities().size(); j++) {
                if (GameDatabase.players.get(i).getCities().get(j).getName().equals(cityName)) {
                    return GameDatabase.players.get(i).getCities().get(j);
                }
            }
        }
        return null;
    }

    public static City getCityByXAndY(int x, int y) {
        for (Civilization player : GameDatabase.players) {
            for (City city : player.getCities()) {
                if (city.getX() == x
                        && city.getY() == y) {
                    return city;
                }
            }
        }
        return null;
    }

    public static ArrayList<Tile> getMap() {
        return GameDatabase.map;
    }


    public static Tile getTileByXAndY(int x, int y) {

        for (Tile tile : map) {
            if (tile.getX() == x && tile.getY() == y) return tile;
        }
        return null;

    }

    public static boolean isTileForACity(Tile tile) {
        for (Civilization civilization : GameDatabase.players) {
            for (City city : civilization.getCities()) {
                if (city.isTileForThisCity(tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Civilization getCivilizationByTile(Tile tile) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getTiles().size(); j++) {
                if (GameDatabase.players.get(i).getTiles().get(j).equals(tile)) {
                    return GameDatabase.players.get(i);
                }
            }
        }
        return null;
    }

    public static int getCivilizationIndex(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (GameDatabase.players.get(i).getNickname().equals(civilizationName)) {
                return i;
            }
        }
        return -1;
    }


    public static void readMapFromFile() {


    }

    public static void generateMap(int numberOfPlayers) {
        Worker.setHashMap();
        Random random = new Random();
        int[] possibilities = {10, 10, 10, 10, 10, 10, 10, 10};
        int sumOfPossibilities = 0;
        int possibilityOfEdgeBeingRiver = 40;// from 1000
        //initialize and set hashmap
        HashMap<Integer, String> baseTerrains = new HashMap<>();
        baseTerrains.put(0, "Desert");
        baseTerrains.put(1, "Meadow");
        baseTerrains.put(2, "Hill");
        baseTerrains.put(3, "Mountain");
        baseTerrains.put(4, "Ocean");
        baseTerrains.put(5, "Plain");
        baseTerrains.put(6, "Snow");
        baseTerrains.put(7, "Tundra");
        for (int i = 0; i < possibilities.length; i++) {
            sumOfPossibilities += possibilities[i];
        }
        //random initialize tiles of map
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                int randomGenerate = random.nextInt(sumOfPossibilities);
                int counter = 0;
                int flag = -1;
                for (int k = 0; k < possibilities.length; k++) {
                    counter += possibilities[k];
                    if (randomGenerate < counter) {
                        flag = k;
                        break;
                    }
                }
                Tile tile = new Tile("fogOfWar", baseTerrains.get(flag), i, j);
                map.add(tile);
            }
        }
        //random initialize river
        int[] deltaX0 = {-1, 0, 1, 1, 1, 0};
        int[] deltaY0 = {0, 1, 1, 0, -1, -1};
        int[] deltaX1 = {-1, -1, 0, 1, 0, -1};
        int[] deltaY1 = {0, 1, 1, 0, -1, -1};
        int[] numbOfEdge = {3, 4, 5, 0, 1, 2};
        for (int i = 0; i < map.size(); i++) {
            Tile tile = map.get(i);
            for (int j = 0; j < 6; j++) {
                int xOfTile = tile.getX() + deltaX1[j];
                int yOfTile = tile.getY() + deltaY1[j];
                if (tile.getY() % 2 == 0) {
                    xOfTile = tile.getX() + deltaX0[j];
                    yOfTile = tile.getY() + deltaY0[j];
                }
                if ((xOfTile >= length || xOfTile < 0
                        || yOfTile >= width || yOfTile < 0)
                        || (tile.getBaseTerrainType().equals("Ocean")
                        && getTileByXAndY(xOfTile, yOfTile).getBaseTerrainType().equals("Ocean"))) {
                    continue;
                }
                int randomGenerate = random.nextInt(1000);
                if (randomGenerate < possibilityOfEdgeBeingRiver) {
                    Tile tile1 = getTileByXAndY(xOfTile, yOfTile);
                    tile.setRiverByEdgeIndex(j);
                    tile1.setRiverByEdgeIndex(numbOfEdge[j]);
                }
            }
        }
        //random initialize terrainFeature
        for (int i = 0; i < map.size(); i++) {
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<TerrainFeatures> terrainFeatures = baseTerrain.getPossibleFeatures();
            for (int j = 0; j < terrainFeatures.size(); j++) {
                int randomGenerate = random.nextInt(terrainFeatures.size() * 2);
                if (randomGenerate < 3) {//the possibility can be changed
                    String type = terrainFeatures.get(j).getType();
                    TerrainFeatures terrainFeatures1 = randomInitializeFeature(type);
                    map.get(i).getBaseTerrain().setFeature(terrainFeatures1);
                    break;
                }
            }
        }
        //random initialize resources
        for (int i = 0; i < map.size(); i++) {
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<Resources> resources = baseTerrain.getPossibleResources();
            for (int j = 0; j < resources.size(); j++) {
                int randomGenerate = random.nextInt(resources.size() * 2);
                if (randomGenerate < 3) {//the possibility can be changed
                    String name = resources.get(j).getName();
                    Resources resource = new Resources(name);
                    map.get(i).getBaseTerrain().addResource(resource);
                    break;
                }
            }
        }
        //random set beginning tiles for each player
        int counter = 0;
        while (counter < players.size()) {
            int xRandomGenerate = random.nextInt(length);
            int yRandomGenerate = random.nextInt(length);
            int direction = random.nextInt(6);
            int x1 = xRandomGenerate + deltaX0[direction];
            int y1 = yRandomGenerate + deltaY0[direction];
            if (yRandomGenerate % 2 == 1) {
                x1 = xRandomGenerate + deltaX1[direction];
                y1 = yRandomGenerate + deltaY1[direction];
            }
            if (getTileByXAndY(xRandomGenerate, yRandomGenerate) == null
                    || getTileByXAndY(x1, y1) == null
                    || getTileByXAndY(xRandomGenerate, yRandomGenerate).getBaseTerrainType().equals("Ocean")
                    || getTileByXAndY(xRandomGenerate, yRandomGenerate).getBaseTerrainType().equals("Mountain")
                    || getTileByXAndY(x1, y1).getBaseTerrainType().equals("Ocean")
                    || getTileByXAndY(x1, y1).getBaseTerrainType().equals("Mountain")) {

                continue;
            }
            System.out.println(x1 + " " + y1);
            boolean isOccupied = false;
            for (int i = 0; i < counter; i++) {
                if (players.get(i).isTileInCivilization(xRandomGenerate, yRandomGenerate)
                        || players.get(i).isTileInCivilization(x1, y1)) {
                    isOccupied = true;
                    break;
                }
            }

            if (!isOccupied) {
                players.get(counter).addTile(getTileByXAndY(xRandomGenerate, yRandomGenerate));
                players.get(counter).addTile(getTileByXAndY(x1, y1));
                //set non-attacking units in the beginning
                Worker worker = new Worker(xRandomGenerate, yRandomGenerate, counter);
                Settler settler = new Settler(x1, y1, counter);
                getTileByXAndY(xRandomGenerate, yRandomGenerate).addWorker(worker);
                getTileByXAndY(x1, y1).addSettler(settler);
                worker.setTileOfUnit(getTileByXAndY(xRandomGenerate, yRandomGenerate));
                settler.setTileOfUnit(getTileByXAndY(x1, y1));
                counter++;
            }
        }
    }

    public static TerrainFeatures randomInitializeFeature(String type) {
        Random random = new Random();
        TerrainFeatures terrainFeature = new TerrainFeatures(type);
        ArrayList<Resources> resources = terrainFeature.getPossibleResources();
        for (int j = 0; j < resources.size(); j++) {
            int randomGenerate = random.nextInt(resources.size() * 2);
            if (randomGenerate < 3) {//the possibility can be changed
                String name = resources.get(j).getName();
                Resources resource = new Resources(name);
                terrainFeature.addResource(resource);
            }
        }
        return terrainFeature;
    }

    public static Civilization getCivilizationForCity(String cityName) {
        for (Civilization player : GameDatabase.players) {
            for (City city : player.getCities()) {
                if (city.getName().equals(cityName)) {
                    return player;
                }
            }
        }
        return null;
    }

    public static void nextTurn() {
        setTurn(calculateNextTurn());
        for (Civilization player : GameDatabase.players) {
            player.nextTurn();
        }
    }

    private static int calculateNextTurn() {
        if (turn != GameDatabase.players.size() - 1) {
            return turn + 1;
        }
        year++;
        return 0;
    }

    public static int getTurn() {
        return turn;
    }

    public static Civilization getCivilizationByTurn(int turn) {
        return GameDatabase.players.get(turn);
    }

    public static Tile findTileBySettler(Settler settler) {
        for (Tile tile : map) {
            ArrayList<Unit> soldiers = tile.getUnits();
            for (Unit soldier : soldiers) {
                if (soldier.getUnitType().equals("Settler")
                        && settler == soldier) {
                    return tile;
                }
            }
        }
        return null;
    }

    public static Tile findTileByWorker(Worker worker) {
        for (Tile tile : map) {
            ArrayList<Unit> soldiers = tile.getUnits();
            for (Unit soldier : soldiers) {
                if (soldier.getUnitType().equals("worker")
                        && worker == soldier) {
                    return tile;
                }
            }
        }
        return null;
    }

    public static Tile findTileByCitizen(Citizen citizen) {
        for (Tile tile : map) {
            ArrayList<Citizen> citizens = tile.getCitizens();
            if (citizens.contains(citizen)) return tile;
        }
        return null;
    }

    public static ArrayList<Civilization> getPlayers() {
        return GameDatabase.players;
    }

    public static boolean isTileInCivilization(Tile tile, Civilization civilization) {
        if (civilization.isTileInCivilization(tile.getX(), tile.getY())) {
            return true;
        }
        return false;
    }

    public static void setTurn(int newTurn) {
        turn = newTurn;
        GameFXMLController.turn = turn;
    }

    public static User getUserForCivilization(String civilizationName) {
        String username = Objects.requireNonNull(getCivilizationByNickname(civilizationName)).getUsername();
        return UserDatabase.getUserByUsername(username);
    }

//    public static void saveGame() throws IOException {
//        SavingData savingData = new SavingData(length, width, turn, year, players, map);
//
//        // saving information;
//        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
//        Path userPath = Paths.get("savedMap.json");
//        Writer writer = Files.newBufferedWriter(userPath);
//        gsonBuilder.toJson(savingData, writer);
//        writer.close();
//    }

    public static Civilization checkIfWin() {
        if (GameDatabase.year >= 2050) {
            return GameDatabase.getCivilizationByTurn(GameDatabase.getTurn());
        }
        if (GameDatabase.cheated && GameDatabase.cheatedCivilization != null) {
            return cheatedCivilization;
        }
        if (players.size() == 1) {
            return players.get(0);
        } else {
            for (Civilization civilization : players) {
                if (civilization.getTechnologies().size() == GlobalVariables.TECHNOLOGIES.length) {
                    return civilization;
                }
            }
        }
        return null;
    }



    public static void saveGame() {
        CopyOfGameDatabase copy = new CopyOfGameDatabase(players,map,length,width,turn,year,cheated,cheatedCivilization);
        SavingGame.saveGame(copy);
    }
}
