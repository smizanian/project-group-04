package Server.Model;

import Client.Database.GameDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Tile {
    protected String type; //fogOfWar , Visible , Clear
    protected BaseTerrain baseTerrain;
    private ArrayList<Resources> discoveredResources;
    protected int x;
    protected int y;
    protected String contains;
    protected Worker worker;
    protected Settler settler;
    protected ArrayList<Unit> units;
    protected ArrayList<Building> buildings;
    protected ArrayList<Improvement> improvements;
    protected boolean isRaided;
    protected boolean[] isRiver;
    protected boolean hasRoad;
    protected boolean hasRailroad;
    ArrayList<Tile> neighbors = new ArrayList<Tile>();
    protected int[] roundsTillFinish;
    protected boolean isGettingWorkedOn;
    protected boolean isRoadBroken;
    protected boolean isRailroadBroken;
    protected City city;
    protected ArrayList<Citizen> citizens;
    public Ruin ruin;

    public void setRuin(Ruin ruin) {
        this.ruin = ruin;
    }

    public Ruin getRuin() {
        return ruin;
    }

    public boolean[] getIsRiver() {
        return isRiver;
    }


    boolean visited;
    Tile prev;


    public Tile(String type, String baseTerrainType, int x, int y) throws IOException {
        System.out.println(1);
        this.type = type;
        System.out.println(2);
        this.hasRoad = false;
        System.out.println(3);
        this.baseTerrain = new BaseTerrain(baseTerrainType);
        this.x = x;
        System.out.println(4);
        this.y = y;
        System.out.println(5);
        this.units = new ArrayList<Unit>();
        System.out.println(6);
        this.improvements = new ArrayList<Improvement>();
        System.out.println(7);
        this.discoveredResources = new ArrayList<Resources>();
        System.out.println(8);
        this.isRiver = new boolean[6];
        for (int i = 0; i < this.isRiver.length; i++) {
            this.isRiver[i] = false;
        }
        System.out.println(9);
        //setNeighbors(getAdjacentTiles());//set neighbours
        System.out.println(10);
        initializeRoundsTillFinish(-1);
    }

    public String getType() {
        return type;
    }

    public boolean hasRailroad() {
        return hasRailroad;
    }


    public ArrayList<Resources> getDiscoveredResources() {
        return discoveredResources;
    }


    public void addResource(Resources resources) throws IOException {
        discoveredResources.add(resources);
        if (resources.getType().equals("luxury")
                && GameDatabase.getCivilizationByTile(this).isResourceNew(resources)) {
            GameDatabase.getCivilizationByTile(this).addHappiness(4);
        }
    }

    public Unit getNonCombatUnit() {
        for (Unit unit : this.units) {
            if (unit.getUnitType().equals("Settler") || unit.getUnitType().equals("worker")) {
                return unit;
            }
        }
        if (this.settler != null) {
            return this.settler;
        }
        if (this.worker != null) {
            return this.worker;
        }
        return null;
    }

    public Unit getCombatUnit() {
        for (Unit unit : this.units) {
            if (!(unit.getUnitType().equals("Settler") || unit.getUnitType().equals("worker"))) {
                return unit;
            }
        }
        return null;
    }

    public void discoverResource() throws IOException {

        if (this.baseTerrain.getResources() == null) {
            return;
        }
        if(Resources.isResourceOnTileValidForDiscovering(this)) {
            addResource(this.baseTerrain.getResources());
            this.baseTerrain.getResources().discover(this);
            this.baseTerrain.discoverResource();
        }
    }

    public boolean isTileValidForAddingToCity() throws IOException {
        return GameDatabase.isTileForACity(this);
    }

    public boolean isResourceDiscoveredByThisTile(String resourceName) {
        for (Resources resource : this.discoveredResources) {
            if (resource.getName().equals(resourceName)) {
                return true;
            }
        }
        return false;
    }


    public void initializeRoundsTillFinish(int flag) {
        int[] base = new int[16];
        //build road - railroad - farm - mine
        base[0] = 3;
        base[1] = 3;
        base[2] = 6;
        base[3] = 6;
        //improvements
        base[4] = 6;
        base[5] = 6;
        base[6] = 6;
        base[7] = 6;
        base[8] = 6;
        base[9] = 6;
        //remove
        base[10] = 4;
        base[11] = 7;
        base[12] = 6;
        base[13] = 3;
        base[14] = 3;
        //repair
        base[15] = 3;
        if (flag == -1) {
            roundsTillFinish = new int[16];
            for (int i = 0; i < 16; i++) {
                roundsTillFinish[i] = base[i];
            }
        } else {
            roundsTillFinish[flag] = base[flag];
        }
    }

    public String getBaseTerrainType() {
        return this.baseTerrain.getType();
    }

    public boolean hasRoad() {
        return this.hasRoad;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public ArrayList<Tile> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Tile> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tile getPrev() {
        return prev;
    }

    public void setPrev(Tile prev) {
        this.prev = prev;
    }

    public void setRoadBroken(boolean roadBroken) {
        isRoadBroken = roadBroken;
    }

    public void setRailroadBroken(boolean railroadBroken) {
        isRailroadBroken = railroadBroken;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setRaidedModel(boolean isRaided) {
        this.isRaided = isRaided;
    }

    public void addUnit(Unit newUnit) {
        this.units.add(newUnit);
    }

    public void removeUnit(Unit unit) {
        for (int i = 0; i < this.units.size(); i++) {
            if (this.units.get(i).equals(unit)) {
                this.units.remove(i);
                return;
            }
        }
    }


    public ArrayList<Improvement> getImprovements() {
        return improvements;
    }

    public void addImprovement(Improvement newImprovement) {
        this.improvements.add(newImprovement);
    }


    public boolean isRiverByNumberOfEdge(int indexOfEdge) {
        return isRiver[indexOfEdge];
    }

    public void setRiverByEdgeIndex(int edgeIndex) {
        this.isRiver[edgeIndex] = true;
    }

    public BaseTerrain getBaseTerrain() {
        return baseTerrain;
    }

    public boolean canBePassed() {
        return this.baseTerrain.IsMovementPossible();
    }

    public int movementPriceForTile() {
        int movementPriceSum = getBaseTerrain().getMovementPrice();
        if (getBaseTerrain().getFeature() != null) {
            if (getBaseTerrain().getFeature().getMovementPrice() > movementPriceSum)
                movementPriceSum = getBaseTerrain().getFeature().getMovementPrice();
        }
        return movementPriceSum;
    }

    public boolean canBeSeenByUnitOrBuilding() {
        return false;
    }

    @Override
    public String toString() {
        String result = "type = " + this.baseTerrain.getType() + "\n";
        result += "X = " + Integer.toString(this.x) + " Y = " + Integer.toString(this.y);
        return result;
    }

    public String getInformation() {
        String result = this.toString();
        if (city == null) {
            result += "\n Food = 0";
        } else {
            result += "\n Food = " + Integer.toString(city.getFood());
        }
        result += "\n Production = " + baseTerrain.getProduction();
        result += "\n Gold = " + baseTerrain.getGold();
        result += "\n Combat Modification = 0";
        result += "\n Movement Point = " + baseTerrain.getMovementPrice();
        if (baseTerrain.getFeature() != null) {
            result += addFeatureData();
        }
        if(getNonCombatUnit() != null){
            result += "\n" +getNonCombatUnit().unitType;
        }
        if (getCombatUnit() != null){
            result += "\n" + getCombatUnit().unitType;
        }
        return result;
    }

    private String addFeatureData() {
        TerrainFeatures terrainFeatures = baseTerrain.getFeature();
        String result = "\nFeatures Data:\n";
        result += "\t Name: " + terrainFeatures.getType();
        result += "\n\t Food: " + terrainFeatures.getFoodNum();
        result += "\n\t Production: " + terrainFeatures.getProduction();
        result += "\n\t Gold: " + terrainFeatures.getGold();
        result += "\n\t Combat Modification: 0";
        result += "\n\t Movement Point: " + terrainFeatures.getMovementPrice();
        return result;
    }

    public void dryUpByEdge(int edge) {
        if (!isEdgeValid(edge)) {
            return;
        }
        this.isRiver[edge] = false;
    }

    private boolean isEdgeValid(int edge) {
        return edge >= 0 && edge < this.isRiver.length;
    }

    private void dryInNeighbors() throws IOException {
        for (Tile tile : getAdjacentTiles()) {
            if (tile != null) {
                int edge = tile.getTileEdge(GameDatabase.getTileByXAndY(this.x, this.y));
                if (isEdgeValid(edge)) {
                    tile.dryUpByEdge(edge);
                }
            }
        }
    }

    public int getTileEdge(Tile tile) {
        if (tile == null) {
            return -1;
        }
        int tileX = tile.getX();
        int tileY = tile.getY();
        if (this.y % 2 != 0) {
            if (tileX == this.x - 1 && tileY == this.y) {
                return 0;
            }
            if (tileX == this.x - 1 && tileY == this.y + 1) {
                return 1;
            }
            if (tileX == this.x && tileY == this.y + 1) {
                return 2;
            }
            if (tileX == this.x + 1 && tileY == this.y) {
                return 3;
            }
            if (tileX == this.x && tileY == this.y - 1) {
                return 4;
            }
            if (tileX == this.x - 1 && tileY == this.y - 1) {
                return 5;
            }
        } else {
            if (tileX == this.x - 1 && tileY == this.y) {
                return 0;
            }
            if (tileX == this.x && tileY == this.y + 1) {
                return 1;
            }
            if (tileX == this.x + 1 && tileY == this.y + 1) {
                return 2;
            }
            if (tileX == this.x + 1 && tileY == this.y) {
                return 3;
            }
            if (tileX == this.x + 1 && tileY == this.y - 1) {
                return 4;
            }
            if (tileX == this.x && tileY == this.y - 1) {
                return 5;
            }
        }
        return -1;
    }

    public void dryUp() throws IOException {
        for (int i = 0; i < this.isRiver.length; i++) {
            dryUpByEdge(i);
        }
        dryInNeighbors();
    }

    public ArrayList<Tile> getAdjacentTiles() throws IOException {
        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        if (this.y % 2 == 0) {
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x, this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y - 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x, this.y - 1));
        } else {
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x, this.y + 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x + 1, this.y));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x, this.y - 1));
            adjacentTiles.add(GameDatabase.getTileByXAndY(this.x - 1, this.y - 1));
        }
        return adjacentTiles;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tile)) {
            return false;
        }
        if (this.x == ((Tile) object).getX()
                && this.y == ((Tile) object).getY()) {
            return true;
        }
        return false;
    }

    public ArrayList<Tile> getAdjacentTilesByLayer(int n) throws IOException {

        ArrayList<Tile> adjacentTiles = new ArrayList<>();
        if (n == 1) {
            return getAdjacentTiles();
        } else if (n == 2){
            if (this.y % 2 == 0){
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 2, y));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y + 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y + 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x , y + 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 1, y + 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 2, y + 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 2, y));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 2, y - 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 1, y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x , y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y - 1));
            } else {
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 2, y));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 2, y + 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y + 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x , y + 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 1, y + 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 2, y));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 1, y - 1));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x + 1, y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x , y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 1, y - 2));
                adjacentTiles.add(GameDatabase.getTileByXAndY(x - 2, y - 1));

            }
        } else if (n == 3){

        }
        return adjacentTiles;

    }

    public int getRoundsTillFinishProjectByIndex(int indexOfProject) {
        if (indexOfProject >= 0) {
            return this.roundsTillFinish[indexOfProject];
        }
        return 0;
    }

    public void reduceRoundsByIndex(int indexOfProject) {
        this.roundsTillFinish[indexOfProject]--;
    }

    public boolean isImprovementForThisTile(String improvementName) {
        for (Improvement improvement : this.improvements) {
            if (improvementName.equals(improvement.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isThisFeatureForThisTile(String featureName) {
        return this.getBaseTerrain().getFeature().getType().equals(featureName);
    }

    public void nextTurn() throws IOException {
        if(worker != null) {
            worker.nextTurn();
        }
        if(settler != null) {
            settler.nextTurn();
        }

    }

    public Settler getSettler() {
        return settler;
    }

    public void fixImprovementByName(String nameOfImprovement) {
        for (Improvement improvement : improvements) {
            if (improvement.getName().equals(nameOfImprovement)) improvement.fix();
        }
    }

    public void setIsGettingWorkedOn(boolean workedOn) {
        this.isGettingWorkedOn = workedOn;
    }

    public boolean getIsGettingWorkedOn() {
        return isGettingWorkedOn;
    }

    public boolean isImprovementBroken(String improvementName) {
        for (Improvement improvement : this.improvements) {
            if (improvementName.equals(improvement.getName()) && improvement.isBroken()) {
                return true;
            }
        }
        return false;
    }

    public boolean isRoadBroken() {
        return isRoadBroken;
    }

    public boolean isRailroadBroken() {
        return isRailroadBroken;
    }

    public Worker getWorker() {
        return worker;
    }

    public Worker getAvailableWorker() {
        if (worker.isAssigned) return null;
        return worker;
    }

    public Worker getActiveWorker() {
        if (!worker.isAssigned) return null;
        return worker;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    protected void removeCitizen(Citizen citizen) {
        if (citizens.contains(citizen)) citizens.remove(citizen);
    }

    public void addWorker(Worker worker) {
        this.worker = worker;
    }

    public void removeWorker(Worker worker) {
        if (this.worker.equals(worker))
            this.worker = null;
    }

    public void addSettler(Settler settler) {
        this.settler = settler;
    }


    public void removeSettler(Settler settler) throws IOException {
        if (settler.equals(this.settler)) {
            GameDatabase.getCivilizationByTurn(GameDatabase.getTurn()).setSelectedUnit(null);
            this.settler = null;
        }
    }

    public void fixBrokens() {
        for (Improvement improvement : improvements) {
            improvement.setIsBroken(false);
        }
        isRoadBroken = false;
        isRailroadBroken = false;
    }

    public boolean isRaided() {
        return isRaided;
    }

    public void arriveRuin(Unit unit) throws IOException {
        ruin.arrive(unit);
        ruin = null;
    }

    public Citizen getCitizen() throws IOException {
        for (Unit unit : units) {
            if(unit.getUnitType().equals("Citizen") && unit.getCivilizationIndex() == GameDatabase.getCivilizationIndex(GameDatabase.getCivilizationByTile(this).getNickname())) {
                return (Citizen) unit;
            }
        }
        City city = GameDatabase.getCityByXAndY(this.x, this.y);
        if(city != null) {
            for (Unit unit : city.units) {
                if(unit.getUnitType().equals("Citizen") && unit.getCivilizationIndex() == GameDatabase.getCivilizationIndex(GameDatabase.getCivilizationByTile(this).getNickname())) {
                    return (Citizen) unit;
                }
            }
        }
        return null;
    }

    public City isCityInTile() {
        for (Civilization player : GameDatabase.players) {
            for (City playerCity : player.getCities()) {
                if(playerCity.isTileForThisCity(this)) {
                    return playerCity;
                }
            }
        }
        return null;
    }

    public void setCombatUnit(Unit unit) {
        this.units = new ArrayList<>();
    }
}
