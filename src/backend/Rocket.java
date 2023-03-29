package backend;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import rendering.Renderer;
import utils.InputHandler;
import utils.Vector2;

public class Rocket extends Entity { 
    final static float imageScale = 0.1f;
    private float fuelRemaining;
    private BufferedImage sprite;
    private boolean landed = true;

    private final float GRAVITATIONAL_CONSTANT = 20;

    private float elapsedTime = 0;
    private final float ROTATION_SPEED = 50;
    private final float FUEL_COEFFICIENT = 5;
    private final float ACCELERATION = 2500;
    private final float MAX_LANDING_VEL = 75;
    private final float MAX_LANDING_THRESHOLD = 0.85f;

    public Rocket(Vector2 position, float fuelRemaining, BufferedImage sprite) {
        super(position, new Vector2(), new Vector2(), 0.00001f, 0, new RectangleBounds(position,0, new Vector2(sprite.getWidth(), sprite.getHeight()).scale(imageScale)));
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
        renderer.drawImage(getPosition(), size(), getRotation(), sprite);
        //renderer.drawRect(getPosition(), ((RectangleBounds) getBoundingShape()).getSize(), Color.RED);
        getBoundingShape().draw(renderer);
    }

    public Vector2 size() {
        return new Vector2(sprite.getWidth(), sprite.getHeight()).scale(imageScale);
    }
    
    public void update(float dt, Entity[] entities) {
        //System.out.println(getVelocity().magnitude());
        elapsedTime+=dt;
        Vector2 force = calcNetForce(entities, getPosition());

        if(InputHandler.main.isKeyPressed(KeyEvent.VK_RIGHT) && !landed) {
            setRotation(getRotation() + ROTATION_SPEED * dt);
        }
        if(InputHandler.main.isKeyPressed(KeyEvent.VK_LEFT) && !landed) {
            setRotation(getRotation() - ROTATION_SPEED * dt);
        }

        Entity collidingWith = checkCollision(entities);
        if (collidingWith != null) {
            onCollision(collidingWith, getLevel().getObjective());
            force = getVelocity().scale(-getMass()*(15f));
        } else {
            landed = false;
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

    public void onCollision(Entity collidingWith, Objective objective) {
        if(getVelocity().magnitude() > MAX_LANDING_VEL) {
            // Restart level
            System.out.println("velocity too big");
        } else {
            if(getPosition().subtract(collidingWith.getPosition()).normalize().dot(direction()) > MAX_LANDING_THRESHOLD) {
                landed = true;
            } else {
                //bad landing
                objective.setFailed(true);
            }
        }
    }

    private Vector2 calcNetForce(Entity[] entities, Vector2 p) {
        Vector2 netForce = new Vector2();
        for(Entity e : entities) {
            netForce = netForce.add(Fg(p,e));
        }
        
        return netForce;
    }

    public void afterPhysics(float dt) {
        getBoundingShape().setPosition(getPosition());
        ((RectangleBounds)getBoundingShape()).setRotation(getRotation());
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

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }
}
