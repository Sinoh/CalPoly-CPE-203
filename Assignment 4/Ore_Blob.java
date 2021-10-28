import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Ore_Blob implements Entity, ActivityEntity, AnimationEntity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int animationPeriod;
    private int actionPeriod;
    private int imageIndex;
    private final String QUAKE_KEY = "quake";


    public Ore_Blob(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;

    }

    @Override
    public Entity createEntity(String id, Point position,
                               List<PImage> images, int resourceLimit, int resourceCount,
                               int actionPeriod, int animationPeriod)
    {
        return new Ore_Blob(id, position, actionPeriod, animationPeriod, images);
    }

    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(
                this.position, 2);
        long nextPeriod = this.actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(this, world, blobTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(world, quake);
                nextPeriod += this.actionPeriod;
                scheduleActions(quake, scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public boolean moveToOreBlob(Entity blob, WorldModel world,
                                  Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(blob.getPosition(), target.getPosition()))
        {
            world.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }

        else
        {
            Point nextPos = nextPositionOreBlob(world, target.getPosition());

            if (!blob.getPosition().equals(nextPos))
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

    public Point nextPositionOreBlob(WorldModel world,
                                      Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        Optional<Entity> occupant = world.getOccupant(world, newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.getClass().toString().equals("Ore"))))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);
            occupant = world.getOccupant(world, newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.getClass().toString().equals("Ore"))))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    @Override
    public Action createActivityAction(Entity entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    @Override
    public Action createAnimationAction(Entity entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(entity, createAnimationAction(entity, 0), getAnimationPeriod());
    }

    @Override
    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    @Override
    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public Point getPosition() {
        return this.position;
    }

    public PImage getCurrentImage() {
        return this.images.get(imageIndex);
    }

    public void setPosition(Point pos) {
        this.position = pos;
    }

    public String getId() {
        return this.id;
    }

    public List<PImage> getImages() {
        return this.images;
    }
    public int getActionPeriod() {
        return this.actionPeriod;
    }

}
