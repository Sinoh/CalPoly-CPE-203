import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Entity
{
    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Entity(EntityKind kind, String id, Point position,
        List<PImage> images, int resourceLimit, int resourceCount,
        int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public static int getAnimationPeriod(Entity entity)
    {
        switch (entity.kind)
        {
            case MINER_FULL:
            case MINER_NOT_FULL:
            case ORE_BLOB:
            case QUAKE:
                return entity.animationPeriod;
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                entity.kind));
        }
    }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public static Entity createBlacksmith(String id, Point position,
                                          List<PImage> images)
    {
        return new Entity(EntityKind.BLACKSMITH, id, position, images,
                0, 0, 0, 0);
    }

    private Entity createMinerFull(String id, int resourceLimit,
                                         Point position, int actionPeriod, int animationPeriod,
                                         List<PImage> images)
    {
        return new Entity(EntityKind.MINER_FULL, id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public static Entity createMinerNotFull(String id, int resourceLimit,
                                            Point position, int actionPeriod, int animationPeriod,
                                            List<PImage> images)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Entity createObstacle(String id, Point position,
                                        List<PImage> images)
    {
        return new Entity(EntityKind.OBSTACLE, id, position, images,
                0, 0, 0, 0);
    }

    public static Entity createOre(String id, Point position, int actionPeriod,
                                   List<PImage> images)
    {
        return new Entity(EntityKind.ORE, id, position, images, 0, 0,
                actionPeriod, 0);
    }

    private Entity createOreBlob(String id, Point position,
                                       int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Entity(EntityKind.ORE_BLOB, id, position, images,
                0, 0, actionPeriod, animationPeriod);
    }

    private Entity createQuake(Point position, List<PImage> images)
    {
        return new Entity(EntityKind.QUAKE, Functions.QUAKE_ID, position, images,
                0, 0, Functions.QUAKE_ACTION_PERIOD, Functions.QUAKE_ANIMATION_PERIOD);
    }

    public static Entity createVein(String id, Point position, int actionPeriod,
                                    List<PImage> images)
    {
        return new Entity(EntityKind.VEIN, id, position, images, 0, 0,
                actionPeriod, 0);
    }

    private boolean transformNotFull(Entity entity, WorldModel world,
                                           EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit)
        {
            Entity miner = createMinerFull(this.id, this.resourceLimit,
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

    private void transformFull(Entity entity, WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = createMinerNotFull(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(world, entity);
        scheduler.unscheduleAllEvents(entity);

        world.addEntity(world, miner);
        scheduleActions(miner, scheduler, world, imageStore);
    }

    private boolean moveToNotFull(Entity miner, WorldModel world,
                                        Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(miner.position, target.position))
        {
            miner.resourceCount += 1;
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
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

    private boolean moveToFull(Entity miner, WorldModel world,
                                     Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(miner.position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos))
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

    private boolean moveToOreBlob(Entity blob, WorldModel world,
                                        Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(blob.position, target.position))
        {
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionOreBlob(world, target.position);

            if (!blob.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(world, nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(world, blob, nextPos);
            }
            return false;
        }
    }

    public PImage getCurrentImage(Object entity)
    {
        if (entity instanceof Background)
        {
            return ((Background)entity).images
                    .get(((Background)entity).imageIndex);
        }
        else if (entity instanceof Entity)
        {
            return ((Entity)entity).images.get(((Entity)entity).imageIndex);
        }
        else
        {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }

    public void executeMinerFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position,
                EntityKind.BLACKSMITH);

        if (fullTarget.isPresent() &&
                moveToFull(entity, world, fullTarget.get(), scheduler))
        {
            transformFull(entity, world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(entity,
                    Functions.createActivityAction(entity, world, imageStore),
                    entity.actionPeriod);
        }
    }

    public void executeMinerNotFullActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(entity.position,
                EntityKind.ORE);

        if (!notFullTarget.isPresent() ||
                !moveToNotFull(entity, world, notFullTarget.get(), scheduler) ||
                !transformNotFull(entity, world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(entity,
                    Functions.createActivityAction(entity, world, imageStore),
                    this.actionPeriod);
        }
    }

    public void executeOreActivity(Entity entity, WorldModel world,
                                          ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;    // store current position before removing

        world.removeEntity(world, entity);
        scheduler.unscheduleAllEvents(entity);

        Entity blob = createOreBlob(this.id + Functions.BLOB_ID_SUFFIX,
                pos, this.actionPeriod / Functions.BLOB_PERIOD_SCALE,
                Functions.BLOB_ANIMATION_MIN +
                        Functions.rand.nextInt(Functions.BLOB_ANIMATION_MAX - Functions.BLOB_ANIMATION_MIN),
                imageStore.getImageList(Functions.BLOB_KEY));

        world.addEntity(world, blob);
        scheduleActions(blob, scheduler, world, imageStore);
    }

    public void executeOreBlobActivity(Entity entity, WorldModel world,
                                              ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(
                this.position, EntityKind.VEIN);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().position;

            if (moveToOreBlob(entity, world, blobTarget.get(), scheduler))
            {
                Entity quake = createQuake(tgtPos,
                        imageStore.getImageList(Functions.QUAKE_KEY));

                world.addEntity(world, quake);
                nextPeriod += this.actionPeriod;
                scheduleActions(quake, scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(entity,
                Functions.createActivityAction(entity, world, imageStore),
                nextPeriod);
    }

    public void executeQuakeActivity(Entity entity, WorldModel world,
                                            ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(entity);
        world.removeEntity(world, entity);
    }

    public void executeVeinActivity(Entity entity, WorldModel world,
                                           ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(world, this.position);

        if (openPt.isPresent())
        {
            Entity ore = createOre(Functions.ORE_ID_PREFIX + this.id,
                    openPt.get(), Functions.ORE_CORRUPT_MIN +
                            Functions.rand.nextInt(Functions.ORE_CORRUPT_MAX - Functions.ORE_CORRUPT_MIN),
                    imageStore.getImageList(Functions.ORE_KEY));
            world.addEntity(world, ore);
            scheduleActions(ore, scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(entity,
                Functions.createActivityAction(entity, world, imageStore),
                this.actionPeriod);
    }

    private Point nextPositionMiner(WorldModel world,
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

    private Point nextPositionOreBlob(WorldModel world,
                                            Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        Optional<Entity> occupant = world.getOccupant(world, newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);
            occupant = world.getOccupant(world, newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().kind == EntityKind.ORE)))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler,
                                       WorldModel world, ImageStore imageStore)
    {
        switch (this.kind)
        {
            case MINER_FULL:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                scheduler.scheduleEvent(entity, Functions.createAnimationAction(entity, 0),
                        getAnimationPeriod(entity));
                break;

            case MINER_NOT_FULL:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                scheduler.scheduleEvent(entity,
                        Functions.createAnimationAction(entity, 0), getAnimationPeriod(entity));
                break;

            case ORE:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                break;

            case ORE_BLOB:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                scheduler.scheduleEvent(entity,
                        Functions.createAnimationAction(entity, 0), getAnimationPeriod(entity));
                break;

            case QUAKE:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                scheduler.scheduleEvent(entity,
                        Functions.createAnimationAction(entity, Functions.QUAKE_ANIMATION_REPEAT_COUNT),
                        getAnimationPeriod(entity));
                break;

            case VEIN:
                scheduler.scheduleEvent(entity,
                        Functions.createActivityAction(entity, world, imageStore),
                        this.actionPeriod);
                break;

            default:
        }
    }
}
