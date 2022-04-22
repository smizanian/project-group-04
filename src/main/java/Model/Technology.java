package Model;

import java.util.ArrayList;

public class Technology {

    private String name;
    private int cost;
    private ArrayList<Technology> prerequisiteTechnologies;

    public Technology(String name) {
        this.name = name;
        this.prerequisiteTechnologies = new ArrayList<Technology>();
        switch (name) {
            case "Agriculture":
                this.cost = 20;
                break;
            case "AnimalHusbandry":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "Archery":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case " BronzeWorking":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Mining"));
                break;
            case "Calendar":
                this.cost = 70;
                this.prerequisiteTechnologies.add(new Technology("Pottery"));
                break;
            case "Masonry":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Mining"));
                break;
            case "Mining":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "Pottery":
                this.cost = 35;
                this.prerequisiteTechnologies.add(new Technology("Agriculture"));
                break;
            case "TheWheel":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("AnimalHusbandry"));
                break;
            case "Trapping":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("AnimalHusbandry"));
                break;
            case "Writing":
                this.cost = 55;
                this.prerequisiteTechnologies.add(new Technology("Pottery"));
                break;
            case "Construction":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("Masonry"));
                break;
            case "HorsebackRiding":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("TheWheel"));
                break;
            case "IronWorking":
                this.cost = 159;
                this.prerequisiteTechnologies.add(new Technology("BronzeWorking"));
                break;
            case "Mathematics":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("TheWheel"));
                this.prerequisiteTechnologies.add(new Technology("Archery"));
                break;
            case "Philosophy":
                this.cost = 100;
                this.prerequisiteTechnologies.add(new Technology("Writing"));
                break;
            case "Chivalry":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("CivilService"));
                this.prerequisiteTechnologies.add(new Technology("HorsebackRiding"));
                this.prerequisiteTechnologies.add(new Technology("Currency"));
                break;
            case "CivilService":
                this.cost = 400;
                this.prerequisiteTechnologies.add(new Technology("Philosophy"));
                this.prerequisiteTechnologies.add(new Technology("Trapping"));
                break;
            case "Currency":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Mathematics"));
                break;
            case "Education":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Theology"));
                break;
            case "Engineering":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Mathematics"));
                this.prerequisiteTechnologies.add(new Technology("Construction"));
                break;
            case "Machinery":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Engineering"));
                break;
            case "MetalCasting":
                this.cost = 240;
                this.prerequisiteTechnologies.add(new Technology("IronWorking"));
                break;
            case "Physics":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("Engineering"));
                this.prerequisiteTechnologies.add(new Technology("MetalCasting"));
                break;
            case "Steel":
                this.cost = 440;
                this.prerequisiteTechnologies.add(new Technology("MetalCasting"));
                break;
            case "Theology":
                this.cost = 250;
                this.prerequisiteTechnologies.add(new Technology("Calendar"));
                this.prerequisiteTechnologies.add(new Technology("Philosophy"));
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Technology> getPrerequisiteTechnologies() {
        return prerequisiteTechnologies;
    }

    public boolean isInPrerequisiteTechnologies(Technology technology) {
        for (int i = 0; i < this.prerequisiteTechnologies.size(); i++) {
            if(this.prerequisiteTechnologies.get(i).equals(technology)) {
                return true;
            }
        }
        return false;
    }
}

