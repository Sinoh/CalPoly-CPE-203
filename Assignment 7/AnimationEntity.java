abstract public class AnimationEntity extends ActivityEntity{

    int animationPeriod;

    public int getAnimationPeriod() { return animationPeriod; }

    public Action createAnimationAction(Entity entity, int repeatCount) { return new Animation(entity, repeatCount); }

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }
}
