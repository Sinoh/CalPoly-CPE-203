import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Ore implements Entity, ActivityEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int actionPeriod;
    private final String BLOB_KEY = "blob";
    private final String BLOB_ID_SUFFIX = " -- blob";
    private final int BLOB_ANIMATION_MIN = 50;
    private final int BLOB_ANIMATION_MAX = 150;
    public static final int BLOB_PERIOD_SCALE = 4;
    private Random rand = new Random();



    public Ore(String id, Point position, int actionPeriod,
               List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
    }

    @Override
    public Entity createEntity(String id, Point position,
                               List<PImage> images, int resourceLimit, int resourceCount,
                               int actionPeriod, int animationPeriod) {
        return new Ore(id, position, actionPeriod, images);
    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(world, this);
        scheduler.unscheduleAllEvents(this);

        Ore_Blob blob = new Ore_Blob(this.id + BLOB_ID_SUFFIX,
                pos, this.actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(world, blob);
        scheduleActions(blob, scheduler, world, imageStore);
    }

    @Override
    public Action createActivityAction(Entity entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
    }


    public Point getPosition() {
        return this.position;
    }

    public PImage getCurrentImage() {
        return this.images.get(0);
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
        return this.actionPeriod;
    }



}
