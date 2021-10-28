import processing.core.PImage;

import java.util.Optional;
import java.util.Random;
import java.util.List;


public class Vein implements Entity, ActivityEntity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int actionPeriod;
    private final String ORE_KEY = "ore";
    private final String ORE_ID_PREFIX = "ore -- ";
    private final int ORE_CORRUPT_MIN = 20000;
    private final int ORE_CORRUPT_MAX = 30000;
    private Random rand = new Random();



    public Vein(String id, Point position, int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
    }

    @Override
    public Entity createEntity(String id, Point position,
                               List<PImage> images, int resourceLimit, int resourceCount,
                               int actionPeriod, int animationPeriod)
    {
        return new Vein(id, position, actionPeriod, images);
    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(world, this.position);

        if (openPt.isPresent())
        {
            Ore ore = new Ore(ORE_ID_PREFIX + this.id,
                    openPt.get(), ORE_CORRUPT_MIN +
                    rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(world, ore);
            scheduleActions(ore, scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    @Override
    public Action createActivityAction(Entity entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore){

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
