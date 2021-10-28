import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

final class WorldView
{
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
        int tileWidth, int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void shiftView(WorldView view, int colDelta, int rowDelta)
    {
        int newCol = Functions.clamp(this.viewport.col + colDelta, 0,
                this.world.numCols - this.viewport.numCols);
        int newRow = Functions.clamp(this.viewport.row + rowDelta, 0,
                this.world.numRows - this.viewport.numRows);

        this.viewport.shift(this.viewport, newCol, newRow);
    }

    public void drawBackground(WorldView view)
    {
        for (int row = 0; row < this.viewport.numRows; row++)
        {
            for (int col = 0; col < this.viewport.numCols; col++)
            {
                Point worldPoint = this.viewport.viewportToWorld(this.viewport, col, row);
                Optional<PImage> image = this.world.getBackgroundImage(this.world,
                        worldPoint);
                if (image.isPresent())
                {
                    this.screen.image(image.get(), col * this.tileWidth,
                            row * this.tileHeight);
                }
            }
        }
    }

    public void drawEntities(WorldView view)
    {
        for (Entity entity : view.world.entities)
        {
            Point pos = entity.getPosition();

            if (this.viewport.contains(view.viewport, pos))
            {
                Point viewPoint = this.viewport.worldToViewport(view.viewport, pos.x, pos.y);
                view.screen.image(entity.getCurrentImage(),
                        viewPoint.x * view.tileWidth, viewPoint.y * view.tileHeight);
            }
        }
    }

    public void drawViewport(WorldView view)
    {
        drawBackground(view);
        drawEntities(view);
    }
}
