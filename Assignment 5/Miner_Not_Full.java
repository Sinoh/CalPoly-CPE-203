import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Miner_Not_Full extends Miners{


    public Miner_Not_Full(String id, int resourceLimit,
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

    private boolean transformNotFull(Entity entity, WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Miner_Full miner = new Miner_Full(this.id, this.resourceLimit,
                    this.position, this.actionPeriod, this.animationPeriod,
                    this.images);

            world.removeEntity(world, entity);
            scheduler.unscheduleAllEvents(entity);

            world.addEntity(world, miner);
            scheduleActions(miner, scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    @Override
    public Boolean moveTo(Entity miner, WorldModel world,
                                  Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(miner.getPosition(), target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());
            if (!miner.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(world, nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(world, miner, nextPos);
            }
            return false;
        }
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(),
                1);
        if (!notFullTarget.isPresent() ||
                !moveTo(this, world, notFullTarget.get(), scheduler) ||
                !transformNotFull(this, world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    @Override
    public Point nextPosition(WorldModel world,
                                    Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        if (horiz == 0 || world.isOccupied(world, newPos))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x,
                    this.position.y + vert);

            if (vert == 0 || world.isOccupied(world, newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }


    @Override
    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(entity, createAnimationAction(entity, 0), getAnimationPeriod());
    }


    @Override
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    @Override
    public Point getPosition() { return this.position; }

    @Override
    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }

    @Override
    public void setPosition(Point pos) { this.position = pos; }

}
