package backend;
import utils.Vector2;
import rendering.Renderer;

public abstract class Entity {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float mass;
    private float rotation;
   
    public Entity(Vector2 position, Vector2 velocity, Vector2 acceleration, float mass, float rotation) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;
        this.rotation = rotation;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public Vector2 getPosition() {
        return position;
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public Vector2 getVelocity() {
        return velocity;
    }
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    public Vector2 getAcceleration() {
        return acceleration;
    }
    public void setMass(float mass) {
        this.mass = mass;
    }
    public float getMass() {
        return mass;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public float getRotation() {
        return rotation;
    }
    public void addForce(Vector2 force) {
        acceleration = acceleration.add(force.scale(1/mass));
    }
    public abstract void draw(Renderer renderer);
}