import processing.core.PImage;

import java.util.List;

public class Quake implements Entity, ActivityEntity, AnimationEntity {

    private List<PImage> images;
    private int imageIndex;
    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private Point position;


    public Quake(Point position, List<PImage> images) {
        this.images = images;
        this.position = position;
    }

    public Entity createEntity(String id, Point position,
                               List<PImage> images, int resourceLimit, int resourceCount,
                               int actionPeriod, int animationPeriod)
    {
        return new Quake(position, images);
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(world, this);
    }

    public void scheduleActions(Entity entity, EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(entity,
                createActivityAction(entity, world, imageStore),
                0);
        scheduler.scheduleEvent(entity,
                createAnimationAction(entity, QUAKE_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }

    @Override
    public Action createActivityAction(Entity entity, WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

    @Override
    public Action createAnimationAction(Entity entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }


    @Override
    public int getAnimationPeriod() {
        return 0;
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
        return "quake";
    }

    public List<PImage> getImages() {
        return this.images;
    }
    public int getActionPeriod() {
        return 0;
    }

}
