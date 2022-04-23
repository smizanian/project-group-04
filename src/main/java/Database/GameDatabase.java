package Database;

import Model.City;
import Model.Civilization;
import Model.Tile;
import Model.Resources;
import Model.BaseTerrain;
import Model.TerrainFeatures;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameDatabase {

    public static ArrayList<Civilization> players = new ArrayList<Civilization>();
    public static ArrayList<Tile> map = new ArrayList<Tile>();
    private static final int length = 50;
    private static final int width = 50;

    public static void setPlayers(ArrayList<Civilization> players) {
        GameDatabase.players = players;
    }

    /**
     * @param civilizationName
     * @return selected civilization
     */
    public static Civilization getCivilizationByName(String civilizationName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            if (GameDatabase.players.get(i).getUsername().equals(civilizationName)) {
                return GameDatabase.players.get(i);
            }
        }
        return null;
    }


    public static City getCityByName(String cityName) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getCities().size(); j++) {
                if(GameDatabase.players.get(i).getCities().get(j).getName().equals(cityName)) {
                    return GameDatabase.players.get(i).getCities().get(j);
                }
            }
        }
        return null;
    }


    public static Tile getBlockByXandY(int x, int y) {

        for (Tile tile : map) {
            if (tile.getX() == x && tile.getY() == y) return tile;
        }
        return null;

    }


    public static Civilization getCivilizationByTile(Tile tile) {
        for (int i = 0; i < GameDatabase.players.size(); i++) {
            for (int j = 0; j < GameDatabase.players.get(i).getTiles().size(); j++) {
                if(GameDatabase.players.get(i).getTiles().get(j).equals(tile)) {
                    return GameDatabase.players.get(i);
                }
            }
        }
        return null;
    }


    public static void readMapFromFile() {


    }

    public static void writeMapOnTheFile() {

    }

    public static void generateMap(int numberOfPlayers) {
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
        //initialize players
        String[] usernames = {"A","B","C","D","E","F","H"};
        String[] nicknames = {"A","B","C","D","E","F","H"};
        for (int i=0;i<numberOfPlayers;i++){
            Civilization civilization = new Civilization(usernames[i],nicknames[i]);
            players.add(civilization);
        }
        //
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
                Tile tile = new Tile(baseTerrains.get(flag), i, j);
                map.add(tile);
            }
        }
        //random initialize river
        int[] deltaX = {-1, -1, 0, 1, 0, -1};
        int[] deltaY = {0, 1, 1, 0, -1, -1};
        int[] numbOfEdge = {3, 4, 5, 0, 1, 2};
        for (int i = 0; i < map.size(); i++) {
            Tile tile = map.get(i);
            for (int j = 0; j < 6; j++) {
                int xOfTile = tile.getX() + deltaX[j];
                int yOfTile = tile.getY() + deltaY[j];
                if ((xOfTile > length || xOfTile < 0
                        || yOfTile > width || yOfTile < 0)
                        || (tile.getType().equals("Ocean")
                        && getBlockByXandY(xOfTile, yOfTile).getType().equals("Ocean"))) {
                    continue;
                }
                int randomGenerate = random.nextInt(1000);
                if (randomGenerate < possibilityOfEdgeBeingRiver) {
                    Tile tile1 = getBlockByXandY(xOfTile, yOfTile);
                    tile.setRiverByEdgeIndex(j);
                    tile1.setRiverByEdgeIndex(numbOfEdge[j]);
                }
            }
        }
        //random initialize terrainFeatures
        for (int i=0;i<map.size();i++){
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<TerrainFeatures> terrainFeatures = baseTerrain.getPossibleFeatures();
            for (int j=0;j<terrainFeatures.size();j++){
                int randomGenerate = random.nextInt(terrainFeatures.size()*2);
                if (randomGenerate < 3){//TODO change the possibility
                    String type = terrainFeatures.get(j).getType();
                    TerrainFeatures terrainFeatures1 = randomInitializeFeature(type);
                    map.get(i).getBaseTerrain().addFeature(terrainFeatures1);
                }
            }
        }
        //random initialize resources
        for (int i=0;i<map.size();i++){
            BaseTerrain baseTerrain = map.get(i).getBaseTerrain();
            ArrayList<Resources> resources = baseTerrain.getPossibleResources();
            for (int j=0;j<resources.size();j++){
                int randomGenerate = random.nextInt(resources.size()*2);
                if (randomGenerate < 3){//TODO change the possibility
                    String name = resources.get(j).getName();
                    Resources resource = new Resources(name);
                    map.get(i).getBaseTerrain().addResource(resource);
                }
            }
        }
    }

    public static TerrainFeatures randomInitializeFeature(String type){
        Random random = new Random();
        TerrainFeatures terrainFeature = new TerrainFeatures(type);
        ArrayList<Resources> resources = terrainFeature.getPossibleResources();
            for (int j=0;j<resources.size();j++){
                int randomGenerate = random.nextInt(resources.size()*2);
                if (randomGenerate < 3){//TODO change the possibility
                    String name = resources.get(j).getName();
                    Resources resource = new Resources(name);
                    terrainFeature.addResource(resource);
                }
            }
        return terrainFeature;
    }
}
