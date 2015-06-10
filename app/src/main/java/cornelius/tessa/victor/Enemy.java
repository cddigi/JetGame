package cornelius.tessa.victor;

import android.content.Context;

import edu.noctrl.craig.generic.GameObject;
import edu.noctrl.craig.generic.GameSprite;
import edu.noctrl.craig.generic.Point3F;

/**
 * Created by Cornelius on 6/4/2015.
 */
public abstract class Enemy extends GameSprite
{
    protected final static Point3F scale = Point3F.identity();
    protected MyWorld world;
    protected Context context;

    public Enemy(MyWorld theWorld)
    {
        super(theWorld);
        world = theWorld;
        this.position = new Point3F(1, 1, 0);
        this.speed = 0;
        this.baseVelocity = new Point3F(1,1,0);
        this.updateVelocity();
        this.context = world.context;
        //set up for collision
        this.substance = Collision.SolidAI;
        this.collidesWith = Collision.SolidPlayer;
        this.obj_type = Type.ENEMY;
    }

    @Override
    public Point3F getScale()
    {
        return scale;
    }

    @Override
    public void cull()
    {
        synchronized (world)
        {
            world.numKills++;
        }
        new Explosion(world, this);
        this.kill();
    }

    @Override
    public void collision(GameObject other)
    {
        other.kill();
    }
}
