package Server.Model;

public class Citizen extends Unit {
    protected boolean isHostage;
    protected boolean isAssigned;

    public Citizen(int x, int y, String unitType, int HP, int civilizationIndex, boolean isAssigned) {
        super(x, y, unitType, HP, civilizationIndex, 0);
        this.isAssigned = false;
    }

    public boolean isHostage() {
        return isHostage;
    }

    public void setHostage(boolean hostage) {
        isHostage = hostage;
    }

    public boolean isCombatUnit() {
        return false;
    }

    @Override
    public boolean isSettler() {
        return true;
    }

    @Override
    public boolean isWorker() {
        return false;
    }

    @Override
    public String toString() {
        return "Citizen on X: " + this.x + " Y: " + this.y;
    }

    public void assign() {
        this.isAssigned = true;
    }

    public void removeFromWork() {
        this.isAssigned = false;
    }

    public boolean isAssigned() {
        return isAssigned;
    }
}
