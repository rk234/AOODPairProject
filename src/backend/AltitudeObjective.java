package backend;

public class AltitudeObjective extends Objective {
    private Planet p;
    private float altitude;
    
    public AltitudeObjective(Planet p, float altitude) {
        this.p = p;
        this.altitude = altitude;
    }
    public boolean checkCompleted(Rocket r) {
        if(r.altitude(p) >= altitude) {
            return true;
        } else {
            return false;
        }
    }

    public float altitude() {
        return altitude;
    }

    public Planet getPlanet() {
        return p;
    }
}