package backend;

public abstract class Collideable {
    private BoundingShape shape;

    public boolean checkCollision(Entity[] entities) {
        for(Entity e : entities) {
            if(e instanceof Collideable) {
                Collideable col = (Collideable) e;
                if(col.getBoundingShape().intersects(shape)) {
                    return true;
                }
            }
        }
        return false;
    }
    public BoundingShape getBoundingShape() {
        return shape;
    }
}
