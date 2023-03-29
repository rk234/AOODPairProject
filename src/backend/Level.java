package backend;

public class Level {
    private Rocket rocket;
    private Entity[] entities;
    private Objective objective;

    public Level(Rocket rocket, Entity[] entities, Objective objective) {
        this.rocket = rocket;
        this.entities = entities;
        this.objective = objective;

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
}
