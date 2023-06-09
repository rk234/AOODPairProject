package utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputHandler implements KeyListener {
    public static final InputHandler main = new InputHandler();
    private boolean[] keys = new boolean[512];

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    public void reset() {
        for(int i = 0; i < keys.length; i++) {
            keys[i] = false;
        }
    }
}
