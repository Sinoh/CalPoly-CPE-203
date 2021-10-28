import processing.core.PImage;

import java.util.Optional;
import java.util.Random;
import java.util.List;


public class Vein extends ActivityEntity{

    private final String ORE_KEY = "ore";
    private final String ORE_ID_PREFIX = "ore -- ";
    private final int ORE_CORRUPT_MIN = 20000;
    private final int ORE_CORRUPT_MAX = 30000;
    private Random rand = new Random();



    public Vein(String id, Point position, int actionPeriod, List<PImage> images) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
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
    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore){

        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
    }


}
