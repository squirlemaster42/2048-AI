package game;

import graphics.Window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private static Game instance;

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    private Window window;
    private boolean running;
    private BufferStrategy bs;
    private Graphics g;
    private Thread thread;

    private Game(){
        window = new Window(new Dimension(480, 480), "2048");
    }

    private void init(){

    }

    @Override
    public void run() {

    }
}
