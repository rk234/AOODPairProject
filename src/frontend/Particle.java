package frontend;

import java.awt.Color;
import java.awt.Graphics;

import utils.Vector2;

public class Particle {
    private Vector2 position;
    private Vector2 velocity;
    private final float SIZE = 5;
    private Color c;

    public Particle(float mag, float width, float height) {
        this.position = new Vector2(Math.random() * width, Math.random() * height);
        velocity = position.subtract(new Vector2(width/2,height/2));
        velocity = velocity.normalize();
        velocity = velocity.scale(mag);
        c = new Color(1f, 1f, 1f, 0.3f);
    }

    public void step(float dt) {
        position = position.add(velocity.scale(dt));
        velocity = velocity.scale(1+(dt/10));
        c = new Color(1, 1, 1, Math.min((c.getAlpha()/255f)*1.1f, 1));
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect((int) (position.getX()+(SIZE/2)), (int) (position.getY()+(SIZE/2)), (int) SIZE, (int) SIZE);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}  
