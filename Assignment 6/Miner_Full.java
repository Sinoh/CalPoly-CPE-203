import processing.core.PImage;
import java.util.List;
import java.util.Optional;

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
        Miner_Not_Full miner = new Miner_Not_Full(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(world, entity);
        scheduler.unscheduleAllEvents(entity);

        world.addEntity(world, miner);
        scheduleActions(miner, scheduler, world, imageStore);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), i -> i instanceof Blacksmith
        );

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler)) {
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

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }

    @Override
    public void setPosition(Point pos) {
        this.position = pos;
    }
}

