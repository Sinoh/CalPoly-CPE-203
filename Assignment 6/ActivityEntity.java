abstract public class ActivityEntity extends Entity {

    int actionPeriod;

    abstract void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore)     {
        return new Activity(entity, world, imageStore, 0);
    }

    int getActionPeriod() { return actionPeriod; }
}
