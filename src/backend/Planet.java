package backend;

import java.awt.image.BufferedImage;
import rendering.Renderer;
import utils.Vector2;

public class Planet extends Entity {
    private float radius;
    private BufferedImage image;
    private boolean stationary = true;

    public Planet(Vector2 position, float mass, float radius, BufferedImage image) {
        super(position, new Vector2(), new Vector2(), mass, 0, new CircleBounds(position, radius));
        this.radius = radius;
        this.image = image;
    }

    public void draw(Renderer renderer) {
        renderer.drawImage(getPosition(), new Vector2(radius*2, radius*2).scale(1.25f), getRotation(), image);
        //renderer.debugDrawOval(getPosition(), new Vector2(radius*2, radius*2));
    }

    public void update(float dt, Entity[] entities) {
        //blank for now, only stationary planets
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}