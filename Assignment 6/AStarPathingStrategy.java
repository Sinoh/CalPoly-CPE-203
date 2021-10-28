import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.HashMap;
import java.util.Collections;

public class AStarPathingStrategy implements PathingStrategy {

    @Override
    public List<Point> computePath(
            final Point start, final Point end,
            Predicate<Point> canPassThrough,
            Function<Point, List<Point>> potentialNeighbors)
    {
            ArrayList<Point> openList = new ArrayList<>();
            ArrayList<Point> closedList = new ArrayList<>();
            openList.add(start);

            HashMap<Point, Point> cameFrom = new HashMap<>();

            HashMap<Point, Integer> fValue = new HashMap<>();
            HashMap<Point, Integer> gValue = new HashMap<>();
            gValue.put(start, 0);
            fValue.put(start, distance(start, end));

            while (!openList.isEmpty()) {
                Point current = openList.get(0);
                Integer lowest = fValue.get(current);

                for (Point x : openList)
                {
                    if (fValue.get(x) < lowest)
                    {
                        current = x;
                        lowest = fValue.get(current);
                    }
                }

                if (current.equals(end))
                {
                    List<Point> repath = rePath(cameFrom, current);
                    Collections.reverse(repath);
                    repath.remove(0);
                    return repath;
                }

                openList.remove(current);
                closedList.add(current);

                List<Point> neighbors = potentialNeighbors.apply(current);

                for (Point neighbor : neighbors)
                {
		    if (!closedList.contains(neighbor))
                    {
                        gValue.put(neighbor, 10000);
                        fValue.put(neighbor, distance(current, neighbor));
                    }

                    if (!neighbor.equals(end) && !canPassThrough.test(neighbor))
                    {
                        continue;
                    }

                    if (closedList.contains(neighbor))
                    {
                        continue;
		    }

                    if (!openList.contains(neighbor))
                    {
                        openList.add(neighbor);
                    }

                    Integer GValue = gValue.get(current) + distance(current, neighbor);
                    if (gValue.containsKey(neighbor))
                    {
                        if (GValue >= gValue.get(neighbor))
                        {
                            continue;
                        }
                    }
                    cameFrom.put(neighbor, current);
                    gValue.put(neighbor, GValue);
                    fValue.put(neighbor, gValue.get(neighbor) + distance(neighbor, end));
                }

            }
            return new ArrayList<>();

    }


    public List<Point> rePath(HashMap<Point, Point> cameFrom, Point current)
    {
        List<Point> Path = new ArrayList<>();
        Path.add(current);
        while (cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            Path.add(current);
        }
        return Path;
    }

    public static Integer distance(Point start, Point end)
    {
        return (Integer) Math.abs(start.y - end.y) + (Integer) Math.abs(start.x - end.x);
    }
}
