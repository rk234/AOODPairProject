package backend;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import rendering.Renderer;
import utils.InputHandler;
import utils.Vector2;
import utils.Constants;

public class Rocket extends Entity { 
    final static float imageScale = 0.1f;
    private float fuelRemaining;
    private float initialFuel;
    private BufferedImage sprite;
    private boolean landed = true;

    private float elapsedTime = 0;
    private final float ROTATION_SPEED = 50;
    private final float FUEL_COEFFICIENT = 5;
    private final float ACCELERATION = 2500;
    private final float MAX_LANDING_VEL = 75;
    private final float MAX_LANDING_THRESHOLD = 0.85f;

    private Vector2 minimumOrbitPoint = null;
    private Vector2 minimumOrbitPointVel = null;

    public Rocket(Vector2 position, float initialFuel, BufferedImage sprite) {
        super(position, new Vector2(), new Vector2(), 0.00001f, 0, new RectangleBounds(position,0, new Vector2(sprite.getWidth(), sprite.getHeight()).scale(imageScale)));
        this.fuelRemaining = initialFuel;
        this.initialFuel = initialFuel;
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
            minimumOrbitPoint = null;
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

    private Vector2 calcNetForce(Entity[] entities, Vector2 pos) {
        Vector2 netForce = new Vector2();
        for(Entity e : entities) {
            if(e instanceof Planet) {
                Planet planet = (Planet) e;
                if(planet.inInfluence(pos))
                    netForce = netForce.add(Fg(pos,planet));
            }
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
        return planetDir.scale(getMass() * e.getMass() / (distance.magnitude()*distance.magnitude())).scale(Constants.GRAVITATIONAL_CONSTANT);
    }

    public Vector2[] forecast(float dt, int steps, Entity[] entities) {
        Vector2[] futurePos = new Vector2[steps];
        Vector2 p = new Vector2(getPosition().getX(), getPosition().getY());
        Vector2 v = new Vector2(getVelocity().getX(), getVelocity().getY());
        Planet startingPlanet = null;
        for(Entity entity : entities) {
            if(entity instanceof Planet) {
                Planet planet = (Planet) entity;
                if(planet.inInfluence(p)) {
                    startingPlanet = planet;
                }
            }
        }

        for(int i = 0; i < steps; i++) {
            Vector2 acc = calcNetForce(entities, p).scale(1/getMass());
            v = v.add(acc.scale(dt));
            p = p.add(v.scale(dt));

            if(startingPlanet != null) {
                //altitude
                float altitude = altitude(startingPlanet);
                if(minimumOrbitPoint == null) {
                    minimumOrbitPoint = p;
                    minimumOrbitPointVel = v;
                } else if(Vector2.distance(minimumOrbitPoint, startingPlanet.getPosition())-startingPlanet.getRadius() > altitude) {
                    minimumOrbitPoint = p;
                    minimumOrbitPointVel = v;
                }
            }
            
            if(!pointInPlanet(p, entities))
                futurePos[i] = new Vector2(p.getX(), p.getY());
            else
                break;
        }

        return futurePos;
    }

    public boolean pointInPlanet(Vector2 p, Entity[] entities) {
        for(Entity e : entities) {
            if(e instanceof Planet) {
                Planet planet = (Planet) e;
                if(Vector2.distance(p, planet.getPosition()) < planet.getRadius()) return true;
            }
        }
        return false;
    }

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public float altitude(Planet p) {
        return Vector2.distance(getPosition(), p.getPosition())-p.getRadius();
    } 

    public Vector2 getMinOrbitPoint() {
        return minimumOrbitPoint;
    }

    public Vector2 getMinOrbitVel() {
        return minimumOrbitPointVel;
    }
    public float getInitialFuel() {
        return initialFuel;
    }
    public void setInitialFuel(float initialFuel) {
        this.initialFuel = initialFuel;
    }
}
