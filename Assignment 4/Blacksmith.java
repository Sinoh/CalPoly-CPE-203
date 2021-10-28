import processing.core.PImage;
import java.util.List;

public class Blacksmith implements Entity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex = 0;


    public Blacksmith(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;

    }

    public Blacksmith createEntity(String id, Point position,
                                   List<PImage> images, int resourceLimit, int resourceCount,
                                   int actionPeriod, int animationPeriod){

        return new Blacksmith(id, position, images);
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

}
