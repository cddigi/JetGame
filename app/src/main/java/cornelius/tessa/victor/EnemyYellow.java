package cornelius.tessa.victor;

import android.graphics.Rect;

/**
 * Created by Cornelius on 5/21/2015.
 */
public class EnemyYellow extends Enemy
{
    final static Rect enemy = new Rect(150,90,171,104);
    public EnemyYellow(MyWorld theWorld)
    {
        super(theWorld);
        this.value = 1000;
    }

    @Override
    public Rect getSource()
    {
        return enemy;
    }
}
