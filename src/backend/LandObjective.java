package backend;
import utils.Vector2;

public class LandObjective extends Objective {
    private Planet p;
    public LandObjective(Planet p) {
        this.p = p;
    }

    public boolean checkCompleted(Rocket r) {
        if (r.isLanded() && Math.abs(Vector2.distance(p.getPosition(), r.getPosition())-p.getRadius()) < 25) {
            return true;
        } else {
            System.out.println(Math.abs(Vector2.distance(p.getPosition(), r.getPosition())-p.getRadius()));
            return false;
        }
    }

    public Planet getPlanet() {
        return p;
    }
}