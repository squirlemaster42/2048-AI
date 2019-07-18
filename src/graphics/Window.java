package graphics;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;

public class Window {

    private final Dimension size;
    private final String title;

    private final JFrame frame;
    private final Canvas canvas;

    public Window(final Dimension size, final String title){
        this.size = size;
        this.title = title;

        frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setFocusable(false);

        frame.add(canvas);
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);

        frame.pack();
    }

    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public Dimension getSize(){
        return size;
    }
}
