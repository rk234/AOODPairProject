package backend;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import rendering.Renderer;
import utils.Vector2;

public class RectangleBounds extends BoundingShape {
    private Vector2 size;
    private float rotation;

    public RectangleBounds(Vector2 position, float rotation, Vector2 size) {
        super(position);
        this.size = size;
        this.rotation = rotation;
    }

    @Override
    public boolean intersects(BoundingShape other) {
        if(other instanceof CircleBounds) {
            CircleBounds circle = (CircleBounds) other;
            for (Vector2 vertex : vertices()) {
                if (Vector2.distance(vertex, circle.getPosition()) < circle.getRadius()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Vector2[] vertices() {
        return new Vector2[] {
            position.add(rotatePoint(size.scale(0.5f))),
            position.add(rotatePoint(size.scale(0.5f).multiply(new Vector2(-1,1)))),
            position.add(rotatePoint(size.scale(-0.5f))),
            position.add(rotatePoint(size.scale(0.5f).multiply(new Vector2(1,-1))))
        };
    }

    public Vector2 rotatePoint(Vector2 pt) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(-rotation));
        Point2D.Float dest = new Point2D.Float();

        transform.transform(new Point2D.Float(pt.getX(), pt.getY()), dest);

        return new Vector2((float)dest.getX(),(float) dest.getY());
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float r) {
        rotation = r;
    }

    @Override
    public void draw(Renderer r) {
        r.drawVertices(vertices(), Color.GREEN);
    }
}
