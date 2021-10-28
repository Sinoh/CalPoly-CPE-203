abstract public class Moves extends AnimationEntity {

    abstract Boolean moveTo(Entity blob, WorldModel world, Entity target, EventScheduler scheduler);
    abstract Point nextPosition(WorldModel world, Point destPos);
}