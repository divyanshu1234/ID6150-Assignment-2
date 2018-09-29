import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class GridDrawer implements GLEventListener {

    // Parameters for dragging window functionality
    // And controlling zoom
    private float DRAG_FACTOR = 500.0f;
    private float ZOOM_FACTOR = 1.1f;
    private int MAX_DRAG = 50;

    private float zoom = 2.0f;
    private int prevScreenX = 0;
    private int prevScreenY = 0;
    private int curScreenX = 0;
    private int curScreenY = 0;

    private float windowX = 0.0f;
    private float windowY = 0.0f;

    private GameOfLife game;
    private int gridSize;
    private float cellLength;
    private int frameIndex;


    public GridDrawer() {
        game = new GameOfLife(); // Initializing Game of Life

        gridSize = GameOfLife.getGridSize();
        cellLength = GameOfLife.getCellLength();
        frameIndex = 0;
    }

    public void draw() {
        // Setting up the OpenGL window
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(this);
        glcanvas.setSize(800, 800);

        final JFrame frame = new JFrame ("Game of Life");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        glcanvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                zoom *= e.getWheelRotation() < 0 ? ZOOM_FACTOR : 1 / ZOOM_FACTOR;
            }
        });

        glcanvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                curScreenX = e.getX();
                curScreenY = e.getY();

                if (Math.abs(curScreenX - prevScreenX) > MAX_DRAG
                        || Math.abs(curScreenY - prevScreenY) > MAX_DRAG) {
                    prevScreenX = curScreenX;
                    prevScreenY = curScreenY;
                    return;
                }

                windowX += (curScreenX - prevScreenX) / DRAG_FACTOR / zoom;
                windowY += -(curScreenY - prevScreenY) / DRAG_FACTOR / zoom;
                prevScreenX = curScreenX;
                prevScreenY = curScreenY;
            }
        });

        // Frequency of the simulation is taken to be 10
        FPSAnimator animator = new FPSAnimator(glcanvas, 10, true);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear (GL2.GL_COLOR_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glScalef(zoom, zoom, 1.0f);
        gl.glTranslatef(-0.5f + windowX, -0.5f + windowY, 0);

        boolean grid[][] = game.iterate(); // Getting the next grid data

        // Drawing the grid
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                if (grid[i][j]) {
                    gl.glBegin(GL2.GL_QUADS);

                    gl.glVertex2f(j* cellLength, i* cellLength);
                    gl.glVertex2f((j+1)* cellLength, i* cellLength);
                    gl.glVertex2f((j+1)* cellLength, (i+1)* cellLength);
                    gl.glVertex2f(j* cellLength, (i+1)* cellLength);

                    gl.glEnd();
                }
            }
        }

        gl.glFlush();

        System.out.println("Frame Index: " + ++frameIndex);
    }


    @Override
    public void reshape(GLAutoDrawable glAutoDrawable,
                        int i, int i1, int i2, int i3) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glLoadIdentity();
        gl.glScalef(zoom, zoom, 1.0f);
        gl.glTranslatef(-0.5f + windowX, -0.5f + windowX, 0);
    }
}
