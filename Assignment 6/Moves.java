import java.util.List;
import java.util.Optional;

abstract public class Moves extends AnimationEntity {

    abstract List<Point> createPath(Point position, Point destPos, WorldModel world, PathingStrategy AStar);

    public Point nextPosition(WorldModel world,
                              Point destPos)
    {
        PathingStrategy AStar = new AStarPathingStrategy();

        List<Point> path = createPath(position, destPos, world, AStar);

        if (path.isEmpty())
        {
            return position;
        }

        return path.get(0);
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);
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