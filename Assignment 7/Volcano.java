import processing.core.PImage;
import java.util.Random;

import java.util.List;

public class Volcano extends ActivityEntity {

    private boolean volcanoErupted = false;
    private int volcanoEruptingStep = 0;


    public Volcano(String id, Point position, int actionPeriod,
               List<PImage> images) {
        super();
        this.id = id;
        this.position = position;
        this.images = images;
        this.actionPeriod = actionPeriod;
    }

    @Override
    public void executeActivity (WorldModel world, ImageStore imageStore, EventScheduler scheduler) {


        if (!volcanoErupted) {
            Lava lava = new Lava("lava", position, imageStore.getImageList("lava"));
            switch (volcanoEruptingStep) {
                case 1:
                    try {
                        lava.position = new Point(getPosition().x, getPosition().y - 1);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                            System.out.println("Position occupied!"); }break;
                case 2:
                    try {
                        lava.position = new Point(getPosition().x + 1, getPosition().y);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 3:
                    try {
                        lava.position = new Point(getPosition().x, getPosition().y + 1);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 4:
                    try {
                        lava.position = new Point(getPosition().x - 1, getPosition().y);
                        world.tryAddEntity(world,lava);}
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 5:
                    try {
                        lava.position = new Point(getPosition().x + 1, getPosition().y - 1);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 6:
                    try {
                        lava.position = new Point(getPosition().x - 1, getPosition().y - 1);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 7:
                    try {
                        lava.position = new Point(getPosition().x + 1, getPosition().y + 1);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 8:
                    try {
                        lava.position = new Point(getPosition().x - 1, getPosition().y + 1);
                        world.tryAddEntity(world,lava);}
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 9:
                    try {
                        lava.position = new Point(getPosition().x, getPosition().y - 2);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 10:
                    try {
                        lava.position = new Point(getPosition().x + 2, getPosition().y);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 11:
                    try {
                        lava.position = new Point(getPosition().x, getPosition().y + 2);
                        world.tryAddEntity(world,lava); }
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }break;
                case 12:
                    try {
                        lava.position = new Point(getPosition().x - 2, getPosition().y);
                        world.tryAddEntity(world,lava);}
                    catch (IllegalArgumentException e) {
                        System.out.println("Position occupied!"); }
                    volcanoErupted = true;
                    break;
            }

            volcanoEruptingStep++;
        }

        else{
            Random random = new Random();
            if (random.nextInt(100) <= 10) {
                Wyvern wyvern = new Wyvern(new Point(getPosition().x + random.nextInt(3), getPosition().y + random.nextInt(3)),
                        imageStore.getImageList("wyvern"), imageStore);
                try {
                    world.tryAddEntity(world, wyvern);
                    Animation wyvernAnimation = new Animation(wyvern, -1);
                    wyvernAnimation.executeAnimationAction(scheduler);
                    wyvern.scheduleActions(wyvern,scheduler,world, imageStore);
                } catch (IllegalArgumentException e) {
                    return; }

                }
            }

            scheduler.scheduleEvent(this, createActivityAction(this, world, imageStore), this.actionPeriod);
        }


    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(entity, createActivityAction(entity, world, imageStore), this.actionPeriod);
    }

}
