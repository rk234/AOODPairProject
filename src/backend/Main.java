package backend;

import javax.swing.JFrame;

import frontend.GameView;
import frontend.LevelSelectView;
import frontend.MenuView;
import frontend.TutorialView;
import utils.InputHandler;

public class Main {
    private static JFrame window;

    public static void main(String[] args) {   
        window = new JFrame();

        changeView("MenuView");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(InputHandler.main);
        window.pack();
        window.setVisible(true);
    }
    public static void windowRepaint() {
        window.repaint();
        window.revalidate();
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