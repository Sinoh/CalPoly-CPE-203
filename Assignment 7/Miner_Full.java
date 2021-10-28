import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Miner_Full extends Miners {

    public Miner_Full(String id, int resourceLimit,
                      Point position, int actionPeriod, int animationPeriod,
                      List<PImage> images) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;

    }



    public void transformFull(Entity entity, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Random random = new Random();

        Point pt = new Point(0,0);
        Miner_Not_Full miner = new Miner_Not_Full(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);
        Miner_Not_Full miner2 = new Miner_Not_Full(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.actionPeriod, this.images);

        switch (random.nextInt(4)) {
            case 1: miner2.position = new Point(random.nextInt(39), 0); break;
            case 2: miner2.position = new Point(0, random.nextInt(29)); break;
            case 3: miner2.position = new Point(39, random.nextInt(29)); break;
            case 4: miner2.position = new Point(random.nextInt(39), 29); break;
        }

        world.removeEntity(world, entity);
        scheduler.unscheduleAllEvents(entity);
        world.tryAddEntity(world, miner);
        scheduleActions(miner, scheduler, world, imageStore);
        Optional<Entity> wyvern= world.findNearest(this.getPosition(), i -> i instanceof Wyvern);
        if (wyvern.isPresent()) {
            try {
                world.tryAddEntity(world, miner2);
                scheduleActions(miner2, scheduler, world, imageStore);
            } catch (IllegalArgumentException e) {
                 }
        }


    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), i -> i instanceof Blacksmith
        );

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transformFull(this, world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    @Override
    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity,
                createActivityAction(entity, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(entity, this.createAnimationAction(entity, 0),
                getAnimationPeriod());
    }

    @Override
    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

}

