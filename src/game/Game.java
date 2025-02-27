package game;

import graphics.Window;
import input.KeyManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private Random rand;

    private Game(){
        window = new Window(new Dimension(518, 518), "2048");
    }

    private int[][] board = new int[4][4];

    private Map<Integer, Color> colorMap;

    private enum Move {
            LEFT,
            RIGHT,
            UP,
            DOWN
    }

    private boolean lostGame = false;

    private Move lastMove;

    private void init(){
        rand = new Random();
        window.getFrame().addKeyListener(KeyManager.getInstance());

        colorMap = new HashMap<>();
        //TODO Add and implement colorMap

        //Run Game
        addNewTile();
        addNewTile();
    }

    private boolean lock = false;

    private void tick(){
        KeyManager.getInstance().tick();

        if(KeyManager.getInstance().up && !lock && lastMove != Move.UP){
            lastMove = Move.UP;
            moveUp();
            lock = true;
        }else if(KeyManager.getInstance().down && !lock && lastMove != Move.DOWN){
            lastMove = Move.DOWN;
            moveDown();
            lock = true;
        }else if(KeyManager.getInstance().right && !lock && lastMove != Move.RIGHT){
            lastMove = Move.RIGHT;
            moveRight();
            lock = true;
        }else if(KeyManager.getInstance().left && !lock && lastMove != Move.LEFT){
            lastMove = Move.LEFT;
            moveLeft();
            lock = true;
        }

        if(!KeyManager.getInstance().up && !KeyManager.getInstance().down && !KeyManager.getInstance().left && !KeyManager.getInstance().right) {
            lock = false;
        }
    }

    //TODO Fix [2][2][2][2] -> [8] error
    private void moveUp(){
        System.out.println("Move Up");
        for (int k = 0; k < board.length; k++) {
            for(int i = 1; i < board[0].length; i++){
                for(int j = 0; j < board.length; j++){
                    int current = board[j][i];
                    int above = board[j][i - 1];

                    if(current == above && current != 0){
                        above += current;
                        board[j][i - 1] = above;
                        board[j][i] = 0;
                    }else if(above == 0){
                        board[j][i - 1] = current;
                        board[j][i] = 0;
                    }
                }
            }
        }
        addNewTile();
    }

    private void moveDown(){
        System.out.println("Move Down");
        for (int k = 0; k < board.length; k++) {
            for(int i = board[0].length - 2; i >= 0; i--){
                for(int j = 0; j < board.length; j++){
                    int current = board[j][i];
                    int below = board[j][i + 1];

                    if(current == below && current != 0){
                        below += current;
                        board[j][i] = below;
                        board[j][i + 1] = 0;
                    }else if(below == 0){
                        board[j][i + 1] = current;
                        board[j][i] = 0;
                    }
                }
            }
        }
        addNewTile();
    }

    private void moveRight(){
        System.out.println("Move Right");
        for(int k = 0; k < board.length; k++){
            for(int i = board.length - 2; i >= 0; i--){
                for(int j = 0; j < board[0].length; j++){
                    int current = board[i][j];
                    int left = board[i + 1][j];

                    if(current == left && current != 0){
                        left += current;
                        board[i][j] = left;
                        board[i + 1][j] = 0;
                    }else if(left == 0){
                        board[i + 1][j] = current;
                        board[i][j] = 0;
                    }
                }
            }
        }
        addNewTile();
    }

    private void moveLeft(){
        System.out.println("Move Left");
        for(int k = 0; k < board.length; k++){
            for(int i = board.length - 1; i > 0; i--){
                for(int j = 0; j < board[0].length; j++){
                    int current = board[i][j];
                    int left = board[i - 1][j];

                    if(current == left && current != 0){
                        left += current;
                        board[i][j] = left;
                        board[i - 1][j] = 0;
                    }else if(left == 0){
                        board[i - 1][j] = current;
                        board[i][j] = 0;
                    }
                }
            }
        }
        addNewTile();
    }

    private boolean boardFull(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private void addNewTile(){
        if(boardFull()){
            return;
        }
        int random = rand.nextInt(4);
        if(random == 1){
            addTileAtRandLocation(4);
        }else{
            addTileAtRandLocation(2);
        }
    }

    private void addTileAtRandLocation(int value){
        int i = rand.nextInt(4);
        int j = rand.nextInt(4);
        if(board[i][j] == 0){
            board[i][j] = value;

        }else{
            addTileAtRandLocation(value);
        }
    }

    private void render(){
        bs = window.getCanvas().getBufferStrategy();

        if(bs == null){
            window.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();

        g.clearRect(0, 0, (int) window.getSize().getWidth(), (int) window.getSize().getHeight());

        //Add stuff to render here
        drawBoard(g);

        g.setColor(Color.black);
        if(lostGame){
            //TODO Fix position
            g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g.drawString("You Loose", 100, 100);
        }

        bs.show();
        g.dispose();
    }

    private void drawBoard(Graphics g){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

        for(int i = 0; i < 4; i++){
            g.fillRect(130 * i, 0, 2, 518);
            g.fillRect(0, 130 * i, 518, 2);
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != 0){
                    g.setColor(Color.red);
                    g.fillRect((130 * i) + 2, (130 * j) + 2, 128, 128);
                    g.setColor(Color.WHITE);
                    g.drawString(board[i][j] + "", 54 + (130 * i), 74 + (130 * j));
                }
            }
        }
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000.0 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println(ticks);
                ticks = 0;
                timer = 0;
            }

        }

        stop();
    }

    public synchronized void start(){
        if(running)return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
