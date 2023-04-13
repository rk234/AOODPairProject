package backend;

public class Level {
    private int id;
    private Rocket rocket;
    private Entity[] entities;
    private Objective objective;
    private boolean complete;

    public Level(Rocket rocket, Entity[] entities, Objective objective, boolean complete, int id) {
        this.rocket = rocket;
        this.entities = entities;
        this.objective = objective;
        this.complete = complete;
        this.id = id;

        for(Entity e : entities) {
            e.setLevel(this);
        }
        rocket.setLevel(this);
    }
    
    public Rocket getRocket() {
        return rocket;
    }
    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }
    public Entity[] getEntities() {
        return entities;
    }
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    public Objective getObjective() {
        return objective;
    }
    public String toString() {
        String objectiveType = "";
        if (objective instanceof AltitudeObjective) {
            objectiveType = "Altitude";
        } else if (objective instanceof LandObjective) {
            objectiveType = "Land";
        } else if (objective instanceof OrbitObjective) {
            objectiveType = "Orbit";
        }
        return ("Level has " + entities.length + " entities, " + objectiveType + " objective, " + " rocket has position " + rocket.getPosition().toString() + " and " + rocket.getFuel() + " fuel.");
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
