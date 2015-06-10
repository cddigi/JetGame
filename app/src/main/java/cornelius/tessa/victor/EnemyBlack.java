package cornelius.tessa.victor;

import android.graphics.Rect;

/**
 * Created by Cornelius on 5/21/2015.
 */
public class EnemyBlack extends Enemy
{
    final static Rect enemy = new Rect(192,90,213,104);
    public EnemyBlack(MyWorld theWorld)
    {
        super(theWorld);
        this.value = 500;
    }

    @Override
    public Rect getSource()
    {
        return enemy;
    }
}
