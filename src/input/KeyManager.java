package input;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private static KeyManager instance;

    public static KeyManager getInstance(){
        if(instance == null){
            instance = new KeyManager();
        }
        return instance;
    }

    private final boolean[] keys;
    public boolean w, a, s, d, e, esc, left, right, up, down;

    private KeyManager(){
        keys = new boolean[256];
    }

    public void tick(){
        w = keys[KeyEvent.VK_W];
        s = keys[KeyEvent.VK_S];
        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        e = keys[KeyEvent.VK_E];
        esc = keys[KeyEvent.VK_ESCAPE];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}