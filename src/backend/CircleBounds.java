package backend;

import java.awt.BasicStroke;
import java.awt.Color;

import rendering.Renderer;
import utils.Vector2;

public class CircleBounds extends BoundingShape {
    private float radius;

    public CircleBounds(Vector2 position, float radius) {
        super(position);
        this.radius = radius;
    }
    
    @Override
    public boolean intersects(BoundingShape other) {
        return false;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Renderer r) {
        r.drawOval(position, new Vector2(radius*2), Color.BLUE, false);
    }
}
