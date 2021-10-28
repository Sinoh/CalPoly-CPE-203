import processing.core.PImage;

import java.util.List;

public interface Entity {

    Entity createEntity(String id, Point position,
                        List<PImage> images, int resourceLimit, int resourceCount,
                        int actionPeriod, int animationPeriod);

    PImage getCurrentImage();
    Point getPosition();
    void setPosition(Point pos);
    String getId();
    List<PImage> getImages();
    int getActionPeriod();


}
