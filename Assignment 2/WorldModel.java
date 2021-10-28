import processing.core.PImage;

import java.util.*;

final class WorldModel
{
    public int numRows;
    public int numCols;
    public Background background[][];
    public Entity occupancy[][];
    public Set<Entity> entities;

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
        if (isOccupied(world, entity.position))
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
        if (withinBounds(world, entity.position))
        {
            setOccupancyCell(world, entity.position, entity);
            world.entities.add(entity);
        }
    }

    public void moveEntity(WorldModel world, Entity entity, Point pos)
    {
        Point oldPos = entity.position;
        if (withinBounds(world, pos) && !pos.equals(oldPos))
        {
            setOccupancyCell(world, oldPos, null);
            removeEntityAt(world, pos);
            setOccupancyCell(world, pos, entity);
            entity.position = pos;
        }
    }

    public void removeEntity(WorldModel world, Entity entity)
    {
        removeEntityAt(world, entity.position);
    }

    private void removeEntityAt(WorldModel world, Point pos)
    {
        if (withinBounds(world, pos)
                && getOccupancyCell(pos) != null)
        {
            Entity entity = getOccupancyCell(pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.position = new Point(-1, -1);
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
            int nearestDistance = pos.distanceSquared(nearest.position, pos);

            for (Entity other : entities)
            {
                int otherDistance = pos.distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public Optional<Entity> findNearest(Point pos,
                                               EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities)
        {
            if (entity.kind == kind)
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }
}
