package backend;

import java.awt.image.BufferedImage;

import rendering.Renderer;
import utils.Vector2;

public class Rocket extends Entity {
    final static float imageScale = 0.1f;
    private float fuelRemaining;
    private BufferedImage sprite;

    private final float GRAVITATIONAL_CONSTANT = 1;

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
    
    public void update(float dt, Entity[] entities) {
        Vector2 netForce = new Vector2();
        for(Entity e : entities) {
            netForce = netForce.add(Fg(e));
        }
        setNetForce(netForce);
        System.out.println(getVelocity().magnitude());
    }

    public Vector2 Fg(Entity e) {
        Vector2 distance =  e.getPosition().subtract(getPosition());
        Vector2 planetDir = distance.normalize();
        return planetDir.scale(getMass() * e.getMass() / distance.magnitude());
    }
}
