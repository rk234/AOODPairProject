package rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import utils.Vector2;

public class Renderer {
    private Camera cam;
    private Graphics2D g2d;
    private int panelWidth;
    private int panelHeight;

    public Renderer(Camera cam, int panelWidth, int panelHeight, Graphics2D g2d) {
        this.cam = cam;
        this.g2d = g2d;
        this.panelHeight = panelHeight;
        this.panelWidth = panelWidth;
    }

    public void clear() {
        g2d.clearRect(0, 0, panelWidth, panelHeight);
    }

    public void drawImage(Vector2 pos, Vector2 imgSize, float rotation, BufferedImage img) {
        AffineTransform transform = new AffineTransform();
        transform.translate(-cam.getPosition().getX(), cam.getPosition().getY());
        transform.scale(cam.getZoom(), cam.getZoom());
        transform.rotate(Math.toRadians(rotation), pos.getX(), pos.getY());
        g2d.setTransform(transform);
        
        g2d.drawImage(img, (int) pos.getX(), (int) pos.getY(), (int) imgSize.getX(), (int) imgSize.getY(), null);
    }
}
