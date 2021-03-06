import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

class SingleStepPathingStrategy implements PathingStrategy
{
    @Override
    public List<Point> computePath(
                            final Point start, final Point end,
                            Predicate<Point> canPassThrough,
                            Function<Point, List<Point>> potentialNeighbors)
    {
        ArrayList<Point> result = new ArrayList<>();

        // First, generate our list of neighbors, and see if the endpoint
        // is one of them.  If so, we're one move away from our goal.
        List<Point> neighbors = potentialNeighbors.apply(start);
        if (neighbors.contains(end)) {

            // By our contract, we include neither the start nor the end 
            // point, so if we're within reach of the end, the correct
            // "path" is empty.
            return result;
        }

        // Now, see which of our neighbors moves us closer (or at least not
        // further away), in either x or y.
        for (Point neighbor : potentialNeighbors.apply(start)) {
            if (!canPassThrough.test(neighbor)) {
                // ignore
            } else if (neighbor.equals(start) || neighbor.equals(end)) {
                // ignore
            } else if (Math.abs(end.x-neighbor.x) > Math.abs(end.x-start.x)) {
                // ignore
            } else if (Math.abs(end.y-neighbor.y) > Math.abs(end.y-start.y)) {
                // ignore
            } else {
                result.add(neighbor);
                return result;
                // Our "path" is only really one step toward the goal.  That's
                // OK, because our contract says it's the *prefix* of the
                // path.  A prefix can be just one step!
            }
        }
        return result;
    }
}
