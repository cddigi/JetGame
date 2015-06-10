package cornelius.tessa.victor;

import android.graphics.Rect;

/**
 * Created by Cornelius on 5/21/2015.
 */
public class EnemyBlue extends Enemy
{
    final static Rect enemy = new Rect(192,65,213,79);
    public EnemyBlue(MyWorld theWorld)
    {
        super(theWorld);
        this.value = 800;

    }

    public int killScore()
    {
        return value;
    }

    @Override
    public Rect getSource()
    {
        return enemy;
    }
}
