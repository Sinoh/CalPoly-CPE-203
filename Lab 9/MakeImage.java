

/**
 * Make an image by transforming points.
 */

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;
import java.util.stream.Collectors;

public class MakeImage {

    public static Dimension SIZE = new Dimension(500, 500);

    protected List<Point> points;

    public MakeImage() {
    }

    /** 
     * Transform the points.  When this is called, the field points
     * will contain a list of points.  After completion, points should
     * contain a list of points, possibly of a different size, with the
     * transformed points.
     */
    public void transformPoints() {
        // TODO:  Put your transformations here.
        //        Do not change the name of this method.  You may add any
        //        methods you wish to this class and to the Point class, 
        //        but please don't change any of the other given code.
        //
        //        You may wish to add some import statements.


        List<Point> transformed = points.stream().filter(points -> !(points.z > 2.0)).collect(Collectors.toList());
        points.clear();
        points = transformed;
        points.replaceAll(i -> new Point(i.x/2 - 150, i.y/2 -37, i.z/2));
    }

    private void readFile() throws IOException {
        points = new ArrayList<Point>();
        File f = new File("positions.txt");
        Scanner scanner = new Scanner(f);
        try {
            while (scanner.hasNext()) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                double z = scanner.nextDouble();
                points.add(new Point(x, y, z));
            }
        } finally {
            scanner.close();
        }
        System.out.println("Read " + points.size() + " points from " + f);
    }

    final public void paint(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, SIZE.width, SIZE.height);
	g.setColor(Color.WHITE);
        for (Point p : points) {
            g.fillRect((int) p.x, (int) p.y, 1, 1);
        }
    }

    public void run() throws IOException {
	readFile();
        transformPoints();

        BufferedImage image 
            = new BufferedImage(SIZE.width, SIZE.height,
                                BufferedImage.TYPE_INT_ARGB);
        paint(image.createGraphics());
        File output = new File("transformed.png");
        ImageIO.write(image, "png", output);
        System.out.println();
        System.out.println(output + " written.");
        System.out.println();
        System.out.println("I'll now attempt to launch a GUI to show the image.");
        System.out.println("This might fail, e.g. if you're ssh'ing to the Unix server.");
        System.out.println();
        try {
            ShowImage gui = new ShowImage("Kimmy Discovers Pointalism!", this);
            gui.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Creating GUI failed:  " + ex);
        }
    }

    public static void main(String[] args) throws IOException {
        MakeImage maker = new MakeImage();
        maker.run();
    }
}
