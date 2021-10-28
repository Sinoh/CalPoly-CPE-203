import processing.core.PImage;
import java.util.function.Predicate;
import java.util.*;

final class WorldModel
{
    public int numRows;
    public int numCols;
    public Background background[][];
    public Entity occupancy[][];
    public Set<Entity> entities;

    public static final int PROPERTY_KEY = 0;

    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 4;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;

    public static final String SMITH_KEY = "blacksmith";
    public static final int SMITH_NUM_PROPERTIES = 4;
    public static final int SMITH_ID = 1;
    public static final int SMITH_COL = 2;
    public static final int SMITH_ROW = 3;

    public static final String VEIN_KEY = "vein";
    public static final int VEIN_NUM_PROPERTIES = 5;
    public static final int VEIN_ID = 1;
    public static final int VEIN_COL = 2;
    public static final int VEIN_ROW = 3;
    public static final int VEIN_ACTION_PERIOD = 4;

    public WorldModel(int numRows, int numCols, Background defaultBackground)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++)
        {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public Optional<Point> findOpenAround(WorldModel world, Point pos)
    {
        for (int dy = -Functions.ORE_REACH; dy <= Functions.ORE_REACH; dy++)
        {
            for (int dx = -Functions.ORE_REACH; dx <= Functions.ORE_REACH; dx++)
            {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (withinBounds(world, newPt) &&
                        !isOccupied(world, newPt))
                {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public void tryAddEntity(WorldModel world, Entity entity)
    {
        if (isOccupied(world, entity.getPosition()))
        {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(world, entity);
    }

    public boolean withinBounds(WorldModel world, Point pos)
    {
        return pos.y >= 0 && pos.y < world.numRows &&
                pos.x >= 0 && pos.x < world.numCols;
    }

    public boolean isOccupied(WorldModel world, Point pos)
    {
        return withinBounds(world, pos) &&
                getOccupancyCell(pos) != null;
    }

    public void addEntity(WorldModel world, Entity entity)
    {
        if (withinBounds(world, entity.getPosition()))
        {
            setOccupancyCell(world, entity.getPosition(), entity);
            world.entities.add(entity);
        }
    }

    public void moveEntity(WorldModel world, Entity entity, Point pos)
    {
        Point oldPos = entity.getPosition();
        if (withinBounds(world, pos) && !pos.equals(oldPos))
        {
            setOccupancyCell(world, oldPos, null);
            removeEntityAt(world, pos);
            setOccupancyCell(world, pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(WorldModel world, Entity entity)
    {
        removeEntityAt(world, entity.getPosition());
    }

    private void removeEntityAt(WorldModel world, Point pos)
    {
        if (withinBounds(world, pos)
                && getOccupancyCell(pos) != null)
        {
            Entity entity = getOccupancyCell(pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.setPosition(new Point(-1, -1));
            world.entities.remove(entity);
            setOccupancyCell(world, pos, null);
        }
    }

    public Optional<PImage> getBackgroundImage(WorldModel world,
                                                      Point pos)
    {
        if (withinBounds(world, pos))
        {
            Background bg = getBackgroundCell(pos);
            return Optional.of(bg.images.get(bg.imageIndex));
        }
        else
        {
            return Optional.empty();
        }
    }

    public void setBackground(WorldModel world, Point pos,
                                     Background background)
    {
        if (withinBounds(world, pos))
        {
            setBackgroundCell(world, pos, background);
        }
    }

    public Optional<Entity> getOccupant(WorldModel world, Point pos)
    {
        if (isOccupied(world, pos))
        {
            return Optional.of(getOccupancyCell(pos));
        }
        else
        {
            return Optional.empty();
        }
    }

    private Entity getOccupancyCell(Point pos)
    {
        return this.occupancy[pos.y][pos.x];
    }

    private void setOccupancyCell(WorldModel world, Point pos,
                                        Entity entity)
    {
        world.occupancy[pos.y][pos.x] = entity;
    }

    private Background getBackgroundCell(Point pos)
    {
        return this.background[pos.y][pos.x];
    }

    public void setBackgroundCell(WorldModel world, Point pos,
                                         Background background)
    {
        world.background[pos.y][pos.x] = background;
    }

    private Optional<Entity> nearestEntity(List<Entity> entities,
                                                 Point pos)
    {
        if (entities.isEmpty())
        {
            return Optional.empty();
        }
        else
        {
            Entity nearest = entities.get(0);
            int nearestDistance = pos.distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities)
            {
                int otherDistance = pos.distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
/*
    public Optional<Entity> oldFindNearest(Point pos, int kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities) {
            if (kind == 0) {
                if (entity instanceof Blacksmith) {
                    ofType.add(entity);
                }
            }
            if (kind == 1) {
                if (entity instanceof Ore) {
                    ofType.add(entity);
                }
            }

            if (kind == 2) {
                if (entity instanceof Vein) {
                    ofType.add(entity);
                        }
                    }
        }

        return nearestEntity(ofType, pos);
    }
*/
    public Optional<Entity> findNearest(Point pos, Predicate<Entity> isTarget) {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities) {
            if (isTarget.test(entity)) {
                ofType.add(entity);
            }
        }
        return nearestEntity(ofType, pos);
    }

    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!processLine(in.nextLine(), world, imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }

    public static boolean processLine(String line, WorldModel world,
                                      ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case BGND_KEY:
                    return parseBackground(properties, world, imageStore);
                case MINER_KEY:
                    return parseMiner(properties, world, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(properties, world, imageStore);
                case ORE_KEY:
                    return parseOre(properties, world, imageStore);
                case SMITH_KEY:
                    return parseSmith(properties, world, imageStore);
                case VEIN_KEY:
                    return parseVein(properties, world, imageStore);
            }
        }

        return false;
    }

    public static boolean parseBackground(String [] properties,
                                          WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(world, pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    public static boolean parseMiner(String [] properties, WorldModel world,
                                     ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            Miner_Not_Full entity = new Miner_Not_Full(properties[MINER_ID],
                    Integer.parseInt(properties[MINER_LIMIT]),
                    pt,
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            world.tryAddEntity(world, entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(String [] properties, WorldModel world,
                                        ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Obstacle entity = new Obstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(world, entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseOre(String [] properties, WorldModel world,
                                   ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            Ore entity = new Ore(properties[ORE_ID],
                    pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            world.tryAddEntity(world, entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }


    public static boolean parseSmith(String [] properties, WorldModel world,
                                     ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            Blacksmith entity = new Blacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            world.tryAddEntity(world, entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    public static boolean parseVein(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            Vein entity = new Vein(properties[VEIN_ID],
                    pt,
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            world.tryAddEntity(world, entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }
}
