package backend;

public abstract class Collideable {
    private BoundingShape shape;

    public Collideable(BoundingShape shape) {
        this.shape = shape;
    }

    public Entity checkCollision(Entity[] entities) {
        for(Entity e : entities) {
            if(e instanceof Collideable) {
                Collideable col = (Collideable) e;
                if(shape.intersects(col.getBoundingShape())) {
                    return e;
                }
            }
        }
        return null;
    }
    public BoundingShape getBoundingShape() {
        return shape;
    }
}
