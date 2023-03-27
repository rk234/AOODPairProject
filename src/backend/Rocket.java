package backend;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import rendering.Renderer;
import utils.InputHandler;
import utils.Vector2;

public class Rocket extends Entity {
    final static float imageScale = 0.1f;
    private float fuelRemaining;
    private BufferedImage sprite;

    private final float GRAVITATIONAL_CONSTANT = 20;

    private float elapsedTime = 0;
    private final float ROTATION_SPEED = 50;
    private final float FUEL_COEFFICIENT = 5;
    private final float ACCELERATION = 1000;

    public Rocket(Vector2 position, float fuelRemaining, BufferedImage sprite) {
        super(position, new Vector2(), new Vector2(), 0.0000001f, 0);
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
        elapsedTime+=dt;
        Vector2 force = calcNetForce(entities, getPosition());

        if(InputHandler.main.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setRotation(getRotation() + ROTATION_SPEED * dt);
        }
        if(InputHandler.main.isKeyPressed(KeyEvent.VK_LEFT)) {
            setRotation(getRotation() - ROTATION_SPEED * dt);
        }
        if(InputHandler.main.isKeyPressed(KeyEvent.VK_SPACE)) {
            Vector2 accelerationForce = direction().scale(ACCELERATION*getMass()*dt);
            force = force.add(accelerationForce);
        }

        

        setNetForce(force);
    }
    public void addForce(Vector2 force) {
        setAcceleration(getAcceleration().add(force.scale(1/getMass())));
    }

    private Vector2 calcNetForce(Entity[] entities, Vector2 p) {
        Vector2 netForce = new Vector2();
        for(Entity e : entities) {
            netForce = netForce.add(Fg(p,e));
        }
        
        return netForce;
    }

    private Vector2 Fg(Vector2 p, Entity e) {
        Vector2 distance =  e.getPosition().subtract(p);
        Vector2 planetDir = distance.normalize();
        return planetDir.scale(getMass() * e.getMass() / (distance.magnitude()*distance.magnitude())).scale(GRAVITATIONAL_CONSTANT);
    }

    public Vector2[] forecast(float dt, int steps, Entity[] entities) {
        Vector2[] futurePos = new Vector2[steps];
        Vector2 p = new Vector2(getPosition().getX(), getPosition().getY());
        Vector2 v = new Vector2(getVelocity().getX(), getVelocity().getY());
        for(int i = 0; i < steps; i++) {
            Vector2 acc = calcNetForce(entities, p).scale(1/getMass());
            v = v.add(acc.scale(dt));
            p = p.add(v.scale(dt));
            futurePos[i] = new Vector2(p.getX(), p.getY());
        }

        return futurePos;
    }
}
