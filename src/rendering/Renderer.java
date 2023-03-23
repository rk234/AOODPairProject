package rendering;

import java.awt.Color;
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
        AffineTransform transform = createTransform();
        transform.rotate(Math.toRadians(rotation), pos.getX(), -pos.getY());
        g2d.setTransform(transform);
        g2d.drawImage(img, (int) (pos.getX() - (imgSize.getX() / 2)), (int) (-pos.getY() - (imgSize.getY() / 2)), (int) imgSize.getX(), (int) imgSize.getY(), null);
    }

    /*
    public void drawCircle(Vector2 pos, float radius, float rotation, Color color) {
        AffineTransform transform = createTransform();
        transform.rotate(Math.toRadians(rotation), pos.getX(), pos.getY());
        g2d.setTransform(transform);
        g2d.setColor(color);
        g2d.fillOval((int)(pos.getX()-radius), (int)(pos.getY()-radius), (int)radius*2, (int) radius*2);
    }
    */

    public AffineTransform createTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate((panelWidth)-cam.getPosition().getX(), (panelHeight)+cam.getPosition().getY());
        transform.scale(cam.getZoom(), cam.getZoom());
        return transform;
    }
}
