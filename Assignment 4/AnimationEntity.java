public interface AnimationEntity {

    int getAnimationPeriod();
    void nextImage();
    Action createAnimationAction(Entity entity, int repeatCount);
}
