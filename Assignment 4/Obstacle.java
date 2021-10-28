import processing.core.PImage;

import java.util.List;

public class Obstacle implements Entity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Obstacle(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    @Override
    public Obstacle createEntity(String id, Point position,
                                 List<PImage> images, int resourceLimit, int resourceCount,
                                 int actionPeriod, int animationPeriod) {
        return new Obstacle(id, position, images);
    }

    public Point getPosition() {
        return this.position;
    }

    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }

    public void setPosition(Point pos) {
        this.position = pos;
    }

    public String getId() {
        return this.id;
    }

    public List<PImage> getImages() {
        return this.images;
    }

    public int getActionPeriod() {
        return 0;
    }

    public int getResourceLimit() {
        return 0;
    }

}
