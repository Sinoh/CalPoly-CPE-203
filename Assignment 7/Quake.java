import processing.core.PImage;

import java.util.List;

public class Quake extends AnimationEntity {

    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(Point position, List<PImage> images) {
        super();
        this.images = images;
        this.position = position;
        this.id = "quake";
    }


    @Override
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(world, this);
    }

    @Override
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
    Point getPosition() { return this.position; }

}
