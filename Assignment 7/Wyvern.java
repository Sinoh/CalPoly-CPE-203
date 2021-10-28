import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Wyvern extends Moves {

    private ImageStore imageStore;

    public Wyvern(Point position, List<PImage> images, ImageStore imageStore) {
        this.id = "wyvern";
        this.position = position;
        this.images = images;
        this.actionPeriod = 500;
        this.animationPeriod = 50;
        this.imageStore = imageStore;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.getPosition(), (i) -> i instanceof Miner_Full);
        if (target.isPresent()) {
            moveTo(world, target.get(), scheduler);
        }
        scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
    }

    public List<Point> createPath(Point position, Point destPos, WorldModel world, PathingStrategy AStar) {
        return AStar.computePath(position, destPos, i -> world.withinBounds(world, i) && !(world.isOccupied(world, i)),
                f -> PathingStrategy.possiblePositions(f));
    }

    @Override
    public Point getPosition() {return this.position;}

    @Override
    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        Point oldPos = target.position;
        if (position.adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);
            world.addEntity(world, new Ice("ice", oldPos, imageStore.getImageList("ice")));
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(world, nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(world, this, nextPos);
            }
            return false;
        }
    }
}
