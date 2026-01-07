import Fillers.BasicFiller;
import Fillers.Filler;
import models.Line;
import models.LineCanvas;
import models.Point;
import rasterizers.Rasterizer;
import rasterizers.TrivialLineRasterizer;
import rasters.Raster;
import rasters.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;

public class App {

    private final JPanel panel;
    private final Raster raster;
    private MouseAdapter mouseAdapter;
    private KeyAdapter keyAdapter;
    private Point point;
    private Rasterizer rasterizer;
    private LineCanvas canvas;
    private boolean ctrlMode = false;
    private Filler filler;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(800, 600).start());
    }

    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();
    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        clear(0xaaaaaa);
        panel.repaint();
    }

    public App(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);

        panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        rasterizer = new TrivialLineRasterizer(raster);
        canvas = new LineCanvas();
        filler = new BasicFiller(raster);

        JToolBar toolBar = new JToolBar();

        panel.add(toolBar, BorderLayout.NORTH);

        createAdapters();
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);

        panel.requestFocus();
        panel.requestFocusInWindow();
    }

    private void createAdapters() {
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    point = new Point(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    point = new Point(e.getX(), e.getY());

                    filler.fill(point, Color.GREEN);
                    panel.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point point2 = new Point(e.getX(), e.getY());

                    Line line = new Line(point, point2, Color.cyan);

                    raster.clear();
                    canvas.addLine(line);

                    rasterizer.rasterizeArray(canvas.getLines());

                    panel.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point point2 = new Point(e.getX(), e.getY());

                    Line line = new Line(point, point2, Color.cyan);

                    raster.clear();

                    rasterizer.rasterizeArray(canvas.getLines());
                    rasterizer.rasterize(line);

                    panel.repaint();
                }
            }
        };
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlMode = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlMode = false;
                }
            }
        };
        // Uložit invormaci, zda je line tečkovaná nebo ne (line, canvas)
        // Vytvořit dotted line rasterizer
        // vykreslovat vše jak by mělo vypadat


    }

}
