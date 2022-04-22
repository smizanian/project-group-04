package Model;

import java.util.ArrayList;

public class Resources {
    private String name;
    private String type;
    private int gold;
    private int foodNum;
    private int production;
    private ArrayList<BaseTerrain> canBeFoundOnBaseTerrain;
    private ArrayList<TerrainFeatures> canBeFoundOnTerrainFeature;
    private Improvement improvementNeeded;

    public Resources(String name) {
        this.name = name;
        this.canBeFoundOnBaseTerrain = new ArrayList<BaseTerrain>();
        this.canBeFoundOnTerrainFeature = new ArrayList<TerrainFeatures>();
        switch (name) {
            case "banana":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Dense_Jungle"));
                //
                break;
            case "Cow":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                //
                break;
            case "Gazelle":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Jungle"));
                //
                break;
            case "Sheep":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Wheat":
                this.type = "bonus";
                this.foodNum = 1;
                this.production = 0;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Prairie"));
                //
                break;
            case "Cotton":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                //
                break;
            case "Color":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Jungle"));
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Dense_Jungle"));
                //
                break;
            case "Fur":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Jungle"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                //
                break;
            case "Gemstone":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 3;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Dense_Jungle"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Gold":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Incense":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                //
                break;
            case "Ivory":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                //
                break;
            case "Marble":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Silk":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Jungle"));
                //
                break;
            case "Silver":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Sugar":
                this.type = "luxury";
                this.foodNum = 0;
                this.production = 0;
                this.gold = 2;
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Prairie"));
                canBeFoundOnTerrainFeature.add(new TerrainFeatures("Swamp"));
                //
                break;
            case "Coal":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                //
                break;
            case "Horse":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                //
                break;
            case "Iron":
                this.type = "Strategics";
                this.foodNum = 0;
                this.production = 1;
                this.gold = 0;
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Tundra"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Plain"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Desert"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Hill"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Meadow"));
                canBeFoundOnBaseTerrain.add(new BaseTerrain("Snow"));
                //
                break;
        }
    }
}

