package backend;

import java.awt.Color;
import java.awt.image.BufferedImage;
import rendering.Renderer;
import utils.Vector2;

public class Planet extends Entity {
    private float radius;
    private BufferedImage image;

    public Planet(Vector2 position, float mass, float radius, BufferedImage image) {
        super(position, new Vector2(), new Vector2(), mass, 0);
        this.radius = radius;
        this.image = image;
    }

    public void draw(Renderer renderer) {
        renderer.drawImage(getPosition(), new Vector2(radius*2, radius*2), getRotation(), image);
    }
}