public class Animation implements Action {

    private Entity entity;
    private int repeatCount;


    public Animation(Entity entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    public void executeAnimationAction(EventScheduler scheduler) {

        if (this.entity instanceof AnimationEntity) {

            AnimationEntity newEntity = (AnimationEntity)this.entity;

            newEntity.nextImage();

            if (this.repeatCount != 1) {
                scheduler.scheduleEvent(this.entity,
                        newEntity.createAnimationAction(this.entity,
                                Math.max(this.repeatCount - 1, 0)),
                        newEntity.getAnimationPeriod());
            }
        }
    }

    public void executeAction(EventScheduler scheduler)
    {
                executeAnimationAction(scheduler);
        }


}
