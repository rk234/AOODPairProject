package backend;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import frontend.GameView;
import frontend.GameView;
import frontend.LevelSelectView;
import frontend.MenuView;
import utils.InputHandler;
import utils.TextureManager;

public class Main {
    private static JFrame window;

    public static void main(String[] args) {
        BufferedImage planetImg = TextureManager.main.getTexture("planet0");
       
        window = new JFrame();

        //window.setContentPane(new GameView(LevelManager.getLevel(0)));
        changeView("MenuView");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(InputHandler.main);
        window.pack();
        window.setVisible(true);
    }

    public static void changeView(String view, Object... data) {
        switch(view) {
            case "MenuView":
                window.setContentPane(new MenuView());
                break;
            case "LevelSelectView":
                window.setContentPane(new LevelSelectView(LevelManager.getAllLevels()));
            break;
            case "GameView":
                window.setContentPane(new GameView((Level) data[0]));
            break;
        }
        window.requestFocus();
        window.pack();
    }
}