package backend;

import java.awt.image.BufferedImage;

import rendering.Renderer;
import utils.Vector2;

public class Rocket extends Entity {
    final static float imageScale = 2;
    private float fuelRemaining;
    private BufferedImage sprite;

    public Rocket(Vector2 position, float fuelRemaining, BufferedImage sprite) {
        super(position, new Vector2(), new Vector2(), 0.001f, 0);
        this.fuelRemaining = fuelRemaining;
        this.sprite = sprite;
    }
    public void setFuel(float fuelRemaining) {
        this.fuelRemaining = fuelRemaining;
    }
    public float getFuel() {
        return fuelRemaining;
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.drawImage(getPosition(), new Vector2(sprite.getWidth(), sprite.getHeight()).scale(imageScale), getRotation(), sprite);
    }
    
}
