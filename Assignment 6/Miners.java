import java.util.List;

abstract public class Miners extends Moves{

    int resourceLimit;
    int resourceCount;

    public List<Point> createPath(Point position, Point destPos, WorldModel world, PathingStrategy AStar){
        return AStar.computePath(position, destPos, i -> world.withinBounds(world, i) && !(world.isOccupied(world, i)),
                f -> PathingStrategy.possiblePositions(f));

    }
}
