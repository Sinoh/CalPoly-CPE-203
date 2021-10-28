import processing.core.PImage;

import java.util.List;

abstract public class Entity {

    String id;
    Point position;
    List<PImage> images;
    int imageIndex;

    PImage getCurrentImage() { return this.images.get(imageIndex); }

    Point getPosition() { return this.position; }

    void setPosition(Point pos) { this.position = pos; }

    String getId() { return  this.id; }

    List<PImage> getImages()
    {
        return this.images;
    }



}
