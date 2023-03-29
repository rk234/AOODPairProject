import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import backend.Entity;
import backend.Level;
import backend.Planet;
import backend.Rocket;
import backend.LandObjective;
import frontend.GameView;
import utils.TextureManager;
import utils.Vector2;

public class Main {
    public static void main(String[] args) {
        BufferedImage planetImg = TextureManager.main.getTexture("planet0");
       
        Planet test = new Planet(new Vector2(), 1000000, 500, planetImg);
        Planet moon = new Planet(new Vector2(0,-10000), 500000, 300, TextureManager.main.getTexture("planet1"));
        Rocket rocket = new Rocket(new Vector2(0, 500+10), 10, TextureManager.main.getTexture("rocket"));
        //rocket.setVelocity(new Vector2((float)Math.sqrt(20*test.getMass()/300),0));
        JFrame frame = new JFrame();

        frame.setContentPane(new GameView(new Level(rocket, new Entity[] {test, moon}, new LandObjective(moon))));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}