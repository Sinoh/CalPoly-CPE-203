public interface ActivityEntity {

    void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore);
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
