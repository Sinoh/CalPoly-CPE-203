import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;

public class PathingMain extends PApplet
{
    // Position of the "wyverin," the mythical animal that's trying
    // to get to the goal.
    private Point wPos;

    // Images for the wyverin
    private List<PImage> imgs;
    private int currentImage;

    private PImage background;
    private PImage obstacle;
    private PImage goal;
    private LinkedList<Point> path;
    // Path, starting from a neighbor of the wyverin, and (hopefully
    // eventually) ending at a neighbor of the goal.

    private static final int TILE_SIZE = 32;

    private static final int ANIMATION_TIME = 100;

    private GridValue[][] grid;
    private static final int ROWS = 15;
    private static final int COLS = 20;

    private static enum GridValue { BACKGROUND, OBSTACLE, GOAL, SEARCHED };


    @Override
    public void settings() {
        size(640,480);
    }
          
          /* runs once to set up world */
    @Override
    public void setup()
    {

        path = new LinkedList<>();
        wPos = new Point(2, 2);
        imgs = new ArrayList<>();
        imgs.add(loadImage("images/wyvern1.bmp"));
        imgs.add(loadImage("images/wyvern2.bmp"));
        imgs.add(loadImage("images/wyvern3.bmp"));

        background = loadImage("images/grass.bmp");
        obstacle = loadImage("images/vein.bmp");
        goal = loadImage("images/water.bmp");

        grid = new GridValue[ROWS][COLS];
        initialize_grid(grid);

        currentImage = 0;
        noLoop();
        draw();
    }

          /* set up a 2D grid to represent the world */
    private static void initialize_grid(GridValue[][] grid)
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = GridValue.BACKGROUND;
            }
        }

                     //set up some obstacles
        for (int row = 2; row < 8; row++)
        {
            grid[row][row + 5] = GridValue.OBSTACLE;
        }

        for (int row = 8; row < 12; row++)
        {
            grid[row][19 - row] = GridValue.OBSTACLE;
        }

        for (int col = 1; col < 8; col++)
        {
            grid[11][col] = GridValue.OBSTACLE;
        }

        grid[13][14] = GridValue.GOAL;
    }

    private void nextImage()
    {
        currentImage = (currentImage + 1) % imgs.size();
    }

          /* runs over and over */
    @Override
    public void draw()
    {
        // A simplified action scheduling handler
        nextImage();

        drawGrid();
        drawPath();

        image(imgs.get(currentImage), wPos.x * TILE_SIZE, wPos.y * TILE_SIZE);
    }

    private void drawGrid()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                drawTile(row, col);
            }
        }
    }

    private void drawPath()
    {
        for (Point p : path)
        {
            fill(128, 0, 0);
            rect(p.x * TILE_SIZE + TILE_SIZE * 3 / 8,
                p.y * TILE_SIZE + TILE_SIZE * 3 / 8,
                TILE_SIZE / 4, TILE_SIZE / 4);
        }
    }

    private void drawTile(int row, int col)
    {
        switch (grid[row][col])
        {
            case BACKGROUND:
                image(background, col * TILE_SIZE, row * TILE_SIZE);
                break;
            case OBSTACLE:
                image(obstacle, col * TILE_SIZE, row * TILE_SIZE);
                break;
            case SEARCHED:
                fill(0, 128, 128);
                rect(col * TILE_SIZE + TILE_SIZE / 4,
                    row * TILE_SIZE + TILE_SIZE / 4,
                    TILE_SIZE / 2, TILE_SIZE / 2);
                break;
            case GOAL:
                image(goal, col * TILE_SIZE, row * TILE_SIZE);
                break;
        }
    }

    public static void main(String args[])
    {
        PApplet.main("PathingMain");
        System.out.println("Click on the graphical window, then:");
        System.out.println("    Press 'c' to clear the current path.");
        System.out.println("    Press 'e' to explore a new path.");
        System.out.println("    Press 'q' to quit.");

    }

    @Override
    public void keyPressed()
    {
        if (key == 'c')
        {
            //clear out prior path
            path.clear();
            redraw();
        }
        else if (key == 'e')
        {
            // Explore a path one more step
            //
            if (!moveOnce(wPos)) {
                System.out.println("I'm either stuck, or I found the goal.");
            }
            redraw();
        } else if (key == 'q') {
            System.exit(0);
        }
    }

          /* replace the below with one that takes one step along
             a depth first search .
             this code provided only as an example of moving either
             down or to the right - it mostly is for illustrating
             how you might test the occupancy grid and add nodes to path!

             Returns true if something was added to the path
          */
    private boolean moveOnce(Point pos) {
        // Pos is the position where we are in the path.  If the path
        // is empty, it's the starting position.
        if (!path.isEmpty()) {
            pos = path.getLast();
        }

        // The nodes to try:  to the right, and down one
        Point[] tryNodes = new Point[] {
            new Point(pos.x + 1, pos.y ),
            new Point(pos.x, pos.y + 1)
        };

        for (Point node : tryNodes) {
                     //test if this is a valid grid cell 
            if (!withinBounds(node, grid)) {
                continue;
            }
            if (grid[node.y][node.x] == GridValue.GOAL) {
                // We found the goal!
                return false;
            }
            if (grid[node.y][node.x] != GridValue.OBSTACLE && 
                grid[node.y][node.x] != GridValue.SEARCHED)
            {
                grid[node.y][node.x] = GridValue.SEARCHED;
                path.addLast(node);
                return true;
            }
        }
        return false;
    }

    private static boolean withinBounds(Point p, GridValue[][] grid)
    {
        return p.y >= 0 && p.y < grid.length &&
            p.x >= 0 && p.x < grid[0].length;
    }
}
