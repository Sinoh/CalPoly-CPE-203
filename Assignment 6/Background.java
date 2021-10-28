import java.util.List;
import processing.core.PImage;

final class Background
{
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images)
    {
        this.id = id;
        this.images = images;
    }

    private void setBackground(WorldModel world, Point pos,
                                     Background background)
    {
        if (world.withinBounds(world, pos))
        {
            world.setBackgroundCell(world, pos, background);
        }
    }
}
