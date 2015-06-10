package cornelius.tessa.victor;

import android.graphics.Rect;

import java.util.Random;

import edu.noctrl.craig.generic.GameObject;
import edu.noctrl.craig.generic.GameSprite;
import edu.noctrl.craig.generic.Point3F;

/**
 * Created by Vic on 6/2/2015.
 */
public class EnemyLaser extends GameSprite {

    protected static final Rect source = new Rect(256, 80, 272, 89);
    protected static final Point3F scale = Point3F.identity();
    private Random rand = new Random();
    private int numShots;

    public EnemyLaser(MyWorld theWorld)
    {
        super(theWorld);
        this.speed = 100;
        this.baseVelocity = new Point3F(1,1,0);
        this.updateVelocity();

        //set up for collision
        this.substance = Collision.SolidAI;
        this.collidesWith = Collision.SolidPlayer;
        this.obj_type = Type.ENEMY_SHOT;
    }

    public void fire()
    {
        float x = rand.nextFloat() * -1;
        float y = rand.nextFloat();
        if(rand.nextBoolean()) y*=-1;
        Point3F vel = new Point3F(x, y, 0);
        this.baseVelocity = vel;
        this.updateVelocity();
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
        synchronized (world)
        {
            MyWorld.enemyShots--;
        }
    }

    @Override
    public void collision(GameObject other)
    {
        if(other != null && other.obj_type == Type.PLAYER)
        {
            other.kill();
        }
    }
}
