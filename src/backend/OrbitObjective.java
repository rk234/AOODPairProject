package backend;
import utils.Constants;
import utils.Vector2;;

public class OrbitObjective extends Objective {
    private Planet p;
    private float minimumAltitude;
    public OrbitObjective (Planet p, float minimumAltitude) {
        this.p = p;
        this.minimumAltitude = minimumAltitude;
    }
    public boolean checkCompleted(Rocket r) {
        Vector2 minimumPoint = r.getMinOrbitPoint();
        Vector2 minimumPointVel = r.getMinOrbitVel();
        if(p.inInfluence(r.getPosition()) && minimumPoint != null && minimumPointVel != null) {
            float minPtAlt = Vector2.distance(minimumPoint, p.getPosition())-p.getRadius();
            if(minPtAlt >= minimumAltitude) {
                float minV = (float) Math.sqrt((Constants.GRAVITATIONAL_CONSTANT * p.getMass()) / (minPtAlt+p.getRadius()));
                
                return minimumPointVel.magnitude() >= minV;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}