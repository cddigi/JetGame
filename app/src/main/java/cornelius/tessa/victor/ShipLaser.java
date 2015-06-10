package cornelius.tessa.victor;

import android.graphics.Rect;

import edu.noctrl.craig.generic.GameObject;
import edu.noctrl.craig.generic.GameSprite;
import edu.noctrl.craig.generic.Point3F;

/**
 * Created by Vic on 5/30/2015.
 */
public class ShipLaser extends GameSprite
{
    protected static final Rect source = new Rect(256, 69, 272, 76);
    protected static final Point3F scale = Point3F.identity();
    protected MyWorld world;

    public ShipLaser(MyWorld theWorld)
    {
        super(theWorld);
        this.speed = 0;
        this.baseVelocity = new Point3F(1,1,0);
        this.updateVelocity();

        //set up for collision
        this.substance = Collision.SolidPlayer;
        this.collidesWith = Collision.SolidAI;
        this.obj_type = Type.PLAYER_SHOT;
    }

    public void speedUp() {
        this.speed = this.speed + 1500;
    }

    public void fireAtPos(Point3F touch)
    {
        Point3F currentVelocity = touch.subtract(this.position).normalize();
        this.baseVelocity = currentVelocity;
        this.speedUp();
        this.updateVelocity();
    }

    /* For some reason, the fire() method still fires in a straight line
     * when used in MyWorld.java, despite having the same code as fireAtPos()
     * while this is convenient since we're pressed for time,
     * it shouldn't be the case. Making a note to fix in the future
     */
    public void fire(Point3F touch)
    {
        Point3F currentVelocity = touch.subtract(this.position).normalize();
        this.baseVelocity = currentVelocity;
        this.speedUp();
        this.updateVelocity();
        //myWorld.shotsFired++;
    }

    @Override
    public Rect getSource() {
        return source;
    }

    @Override
    public Point3F getScale() {
        return scale;
    }

    @Override
    public void cull()
    {
        world.shots--;
    }

    @Override
    public void collision(GameObject other)
    {
        other.kill();
        if(other.obj_type == Type.ENEMY)
        {
            world.added_time += 1;
            world.numKills++;
            world.enemyKill++;
        }
//        synchronized (other)
//        {
//            if (other != null && other.obj_type == Type.ENEMY)
//            {
//                Enemy victim = (Enemy) other;
//                //victim.die();
//                victim.kill();
//                //myWorld.calculateScore();
//            }
//        }
    }
}
