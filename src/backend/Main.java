package backend;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import frontend.GameView;
import frontend.LevelSelectView;
import frontend.TutorialView;
import frontend.MenuView;
import utils.InputHandler;
import utils.TextureManager;

public class Main {
    private static JFrame window;

    public static void main(String[] args) {       
        window = new JFrame();

        //window.setContentPane(new GameView(LevelManager.getLevel(0)));
        changeView("MenuView");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(InputHandler.main);
        window.pack();
        window.setVisible(true);
    }
    public static void windowRepaint() {
        window.repaint();
        window.revalidate();
        //window.setSize(window.getWidth(), window.getHeight() + 1);
        //window.setSize(window.getWidth(), window.getHeight() - 1);
    }
    public static void requestFocus() {
        window.requestFocus();
    }
    public static void changeView(String view, Object... data) {
        InputHandler.main.reset();
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
            case "TutorialView":
                window.setContentPane(new TutorialView());
            break;
        }
        window.requestFocus();
        window.revalidate();
    }
}