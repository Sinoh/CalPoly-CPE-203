import processing.core.PImage;

import java.util.List;



public class Lava extends Entity{

    public Lava(String id, Point position, List<PImage> images) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        }


    }

