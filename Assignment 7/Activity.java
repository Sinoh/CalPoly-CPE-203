public class Activity implements Action{

    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int actionPeriod;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore, int actionPeriod) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.actionPeriod = actionPeriod;
    }


    public void executeActivityAction(EventScheduler scheduler)
    {

        if (this.entity instanceof ActivityEntity){

            ActivityEntity newEntity = (ActivityEntity)this.entity;

            newEntity.executeActivity(this.world,
                        this.imageStore, scheduler);

        }


    }
    public void executeAction(EventScheduler scheduler) {
        executeActivityAction(scheduler);
    }

}
