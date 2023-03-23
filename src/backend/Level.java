package backend;

public class Level {
    private Rocket rocket;
    private Entity[] entities;

    public Level(Rocket rocket, Entity[] entities) {
        this.rocket = rocket;
        this.entities = entities;
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
}
