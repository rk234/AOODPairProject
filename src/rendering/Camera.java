package rendering;

import utils.Vector2;

public class Camera {
    private Vector2 position;
    private float zoom;

    public Camera(Vector2 position, float zoom) {
        this.position = position;
        this.zoom = zoom;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
