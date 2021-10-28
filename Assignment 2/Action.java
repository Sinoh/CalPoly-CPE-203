final class Action
{
    private final ActionKind kind;
    private final Entity entity;
    private final WorldModel world;
    private final ImageStore imageStore;
    private final int repeatCount;

    public Action(ActionKind kind, Entity entity, WorldModel world,
        ImageStore imageStore, int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    private void executeAnimationAction(EventScheduler scheduler) {

        entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    Functions.createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    Entity.getAnimationPeriod(this.entity));
        }
    }

    private void executeActivityAction(EventScheduler scheduler)
    {
        switch (this.entity.kind)
        {
            case MINER_FULL:
                this.entity.executeMinerFullActivity(this.entity, this.world,
                        this.imageStore, scheduler);
                break;

            case MINER_NOT_FULL:
                this.entity.executeMinerNotFullActivity(this.entity, this.world,
                        this.imageStore, scheduler);
                break;

            case ORE:
                this.entity.executeOreActivity(this.entity, this.world, this.imageStore,
                        scheduler);
                break;

            case ORE_BLOB:
                this.entity.executeOreBlobActivity(this.entity, this.world,
                        this.imageStore, scheduler);
                break;

            case QUAKE:
                this.entity.executeQuakeActivity(this.entity, this.world, this.imageStore,
                        scheduler);
                break;

            case VEIN:
                this.entity.executeVeinActivity(this.entity, this.world, this.imageStore,
                        scheduler);
                break;

            default:
                throw new UnsupportedOperationException(
                        String.format("executeActivityAction not supported for %s",
                                this.entity.kind));
        }
    }

    public void executeAction(EventScheduler scheduler)
    {
        switch (this.kind)
        {
            case ACTIVITY:
                executeActivityAction(scheduler);
                break;

            case ANIMATION:
                executeAnimationAction(scheduler);
                break;
        }
    }
}
