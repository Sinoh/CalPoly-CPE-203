abstract public class AnimationEntity extends ActivityEntity{

    int animationPeriod;
    Point position;

    int getAnimationPeriod() { return animationPeriod; }

    Action createAnimationAction(Entity entity, int repeatCount) { return new Animation(entity, repeatCount); }

    void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
}
