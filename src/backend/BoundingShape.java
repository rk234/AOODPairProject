package backend;

import utils.Vector2;
import rendering.Renderer;

public abstract class BoundingShape {
    protected Vector2 position;
    
    public BoundingShape(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 pos) {
        this.position = pos;
    }
    public abstract void draw(Renderer r);
    public abstract boolean intersects(BoundingShape other);
}
