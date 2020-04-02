import javafx.scene.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;


@SuppressWarnings("serial")
public class Skeleton extends JPanel implements ActionListener {
    private static int maxWidth;
    private static int maxHeight;

    private double angle = 0;

    private double dx = -1;
    private double dy = 1;

    private double tx = 0;
    private double ty = 0;

    private int padding = 15;
    public static void main(String[] args) {

        JFrame frame = new JFrame("Привіт, Java 2D!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new Skeleton());
        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }

    public Skeleton() {
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.setBackground(Color.black);
        g2d.clearRect(0, 0, maxWidth, maxHeight);
        drawBorder(g2d);
        g2d.translate(maxWidth/2, maxHeight/2);

        g2d.translate(tx, ty);

        GeneralPath bug = createPicture();
        Polygon tail = createTail();
        Rectangle[] eyes = createEyes();
        Line2D[] horns = createHorns();

        g2d.rotate(angle, bug.getBounds2D().getCenterX(),
                bug.getBounds2D().getCenterY());

        g2d.setColor(Color.GREEN);
        g2d.fill(bug);

        g2d.setColor(Color.darkGray);
        g2d.fill(eyes[0]);
        g2d.fill(eyes[1]);

        GradientPaint gp = new GradientPaint(25, 25,
                Color.YELLOW, 20, 20, Color.DARK_GRAY, true);
        g2d.setPaint(gp);
        g2d.fill(tail);

        g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        g2d.draw(horns[0]);
        g2d.draw(horns[1]);

        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.black);
        g2d.drawLine(-100, 0, 23, 23);
    }

    private void drawBorder(Graphics2D g2d) {
        g2d.setBackground(new Color(0, 128, 128));
        g2d.setPaint(new Color(0, 128, 128));
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.drawRect( padding, padding, maxWidth - padding * 2, maxHeight - padding * 2);
        g2d.clearRect(padding, padding, maxWidth - padding * 2, maxHeight - padding * 2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        double delta = 0.01;
        angle += delta;
        double limit = maxWidth / 5.0;

        if (tx > limit && ty >- limit)
            moveUp();
        else if (ty < -limit && tx > -limit)
            moveLeft();
        else if (tx < -limit && ty < limit)
            moveDown();
        else moveRight();

        repaint();
    }

    private void moveUp(){ ty -= dy; }
    private void moveDown(){ ty += dy; }
    private void moveLeft(){ tx += dx; }
    private void moveRight(){ tx -= dx; }

    private GeneralPath createPicture() {
        double[][] points = {
            {-100, 0},
            {-20, -62},
            {100 , -10},
            {23, 23},
            {49, 75},
            {-58, 84},
        };
        GeneralPath bug = new GeneralPath();
        bug.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            bug.lineTo(points[k][0], points[k][1]);

        bug.closePath();

        return bug;
    }
    private Polygon createTail () {
        // 179 87
        Polygon triangle = new Polygon();
        triangle.addPoint(  57, 63);
        triangle.addPoint( 40, 25);
        triangle.addPoint(  86, 13);
        return triangle;
    }

    private Rectangle[] createEyes() {
        //179 87
        Rectangle eye1 = new Rectangle(-47, -19, 6, 6);
        Rectangle eye2 = new Rectangle(-59, 26, 6, 6 );

        return new Rectangle[] { eye1, eye2 };
    }

    private Line2D[] createHorns() {
        Line2D line1 = new Line2D.Double(-78, -16, -129, -59);
        Line2D line2 = new Line2D.Double(-76, 41, -136, 62);

        return new Line2D[] { line1, line2 };
    }
}
