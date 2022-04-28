package Model;

public class Citizen extends Unit {
    private boolean isHostage;

    public Citizen(int x, int y, int Vx, int Vy, int power, int cost, int movementPoint, String unitType, boolean isSleeping, boolean isReady, String era, int HP, int civilizationIndex) {
        super(x, y, Vx, Vy, power, cost, movementPoint, unitType, isSleeping, isReady, era, HP, civilizationIndex);
    }

    public boolean isHostage() {
        return isHostage;
    }

    public void setHostage(boolean hostage) {
        isHostage = hostage;
    }

}
