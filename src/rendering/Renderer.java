package rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
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

    public void drawOval(Vector2 pos, Vector2 size, Color c, boolean dotted) {
        g2d.setTransform(createTransform());
        g2d.setColor(c);
        if (dotted) {
            //g2d.setStroke(new BasicStroke(5/cam.getZoom(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,0, new float[]{20/cam.getZoom(), 50/cam.getZoom()}, 0.0f));
            g2d.setStroke(new BasicStroke(5/cam.getZoom(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,0, new float[]{20, 50}, 0.0f));
        } else {
            g2d.setStroke(new BasicStroke(4/cam.getZoom(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        g2d.drawOval((int) (pos.getX() - (size.getX() / 2)), (int) (-pos.getY() - (size.getY() / 2)), (int) size.getX(), (int) size.getY());
        g2d.setStroke(new BasicStroke());
    }

    public void drawTriangle(Vector2 pos, Vector2 size, float rotation, Color color) {
        AffineTransform transform = createTransform();
        transform.rotate(Math.toRadians(rotation), pos.getX(), -pos.getY());
        g2d.setTransform(transform);
        g2d.setColor(color);
        int[] x = new int[] {(int) pos.getX(), (int) (pos.getX()-size.getX()/3), (int) (pos.getX()+size.getX()/3)};
        int[] y = new int[] {(int) (pos.getY()+size.getY()/2), (int) (pos.getY()-size.getY()/2), (int) (pos.getY()-size.getY()/2)};
        for (int i = 0; i < 3; i++) {
            y[i] = -y[i];
        }
        g2d.fillPolygon(x, y, 3);
    }

    public void drawLine(Vector2 p1, Vector2 p2, Color c) {
        if(!(p1 == null || p2 == null)) {
            g2d.setTransform(createTransform());
            g2d.setColor(c);
            g2d.setStroke(new BasicStroke(5/cam.getZoom(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine((int)p1.getX(), (int)-p1.getY(), (int)p2.getX(), (int)-p2.getY());
        }
    }

    public void drawRect(Vector2 pos, Vector2 size, Color color) {
        AffineTransform transform = createTransform();
        //transform.rotate(Math.toRadians(rotation), pos.getX(), -pos.getY());
        g2d.setTransform(transform);
        g2d.setColor(color);
        g2d.drawRect((int) (pos.getX() - (size.getX() / 2)), (int) (-pos.getY() - (size.getY() / 2)), (int) size.getX(), (int) size.getY());
    }

    public void drawVertices(Vector2[] vertices, Color c) {
        for(int i = 0; i < vertices.length-1; i++) {
            drawLine(vertices[i], vertices[i+1], c);
        }
        drawLine(vertices[vertices.length-1], vertices[0], c);
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
        transform.translate((panelWidth/2f)-(cam.getPosition().getX()*cam.getZoom()), (panelHeight/2f)+(cam.getPosition().getY()*cam.getZoom()));
        transform.scale(cam.getZoom(), cam.getZoom());
        return transform;
    }
}
