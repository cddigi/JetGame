package cornelius.tessa.victor;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import com.deitel.cannongame.R;

import java.util.ArrayList;
import java.util.Random;

import edu.noctrl.craig.generic.GameSprite;
import edu.noctrl.craig.generic.Point3F;
import edu.noctrl.craig.generic.SoundManager;
import edu.noctrl.craig.generic.World;

/**
 * Created by Cornelius on 5/21/2015.
 */

public class MyWorld extends World implements MediaPlayer.OnCompletionListener
{
    private GameSprite enemy;
    private Random rand = new Random();
    public int stage;
    public int score;
    public int enemyKill;
    public int enemyHit;
    public int shotsFired;
    private ArrayList<Integer> highScores;
    public static int shots;
    public static int enemyShots;
    protected MyShip ship;
    protected Context context;
    public static int numKills = 0;
    final int HIGH_SCORE_MAX = 5;
    final int MAX_SHOTS_ONSCREEN = 5;
    final MyWorld world = this;

    // Motion Variables
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId;
    float mLastTouchY;

    final int MAX_LIST_SIZE = 5;
    final String PREF_NAME = "JetGame Scores";

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public MyWorld(final StateListener listener, SoundManager sounds, Context context)
    {
        super(listener, sounds);
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        highScores = new ArrayList<>();
        shots = 0;
        enemyShots = 0;

        // Sound initialization
        MediaPlayer mediaPlayer = MediaPlayer.create(this.context, R.raw.game_music);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start(); // no need to call prepare() here because create() does that for you

        // Enivronment initialization
        stage = 1;
        ship = new MyShip(this);
        ship.position.X = 128;
        ship.position.Y += 765 / 2;
        mLastTouchY = ship.position.Y;
        this.addObject(ship);
        retrieveHighScores();

        // Populates the screen with ten enemies in random positions on a separate thread
        // Enemies spawn 300px from the left edge of the screen
        // TODO: change spawn location based off of right edge of player ship
        new Thread(new Runnable()
        {
            public void run()
            {
                ArrayList<Enemy> enemies = new ArrayList<>();
                while(!ship.isDead())
                {
                    if (stage == 1)
                    {
                        while (numKills <= 10)
                        {
                            switch (rand.nextInt(3))
                            {
                                case 0:
                                    enemy = new EnemyBlue(MyWorld.this);
                                    break;
                                case 1:
                                    enemy = new EnemyBlack(MyWorld.this);
                                    break;
                                default:
                                    enemy = new EnemyYellow(MyWorld.this);
                            }

                            enemy.position.X = width * rand.nextFloat();
                            while (enemy.position.X < 500)
                                enemy.position.X = width * rand.nextFloat();
                            enemy.position.Y = height * rand.nextFloat();
                            enemies.add((Enemy) enemy);
                            addObject(enemy);
                            try
                            {
                                Thread.sleep(rand.nextInt(1000) + 300);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        for (Enemy e : enemies)
                            synchronized (e)
                            {
                                e.kill();   //kill the remaining enemies onscreen
                            }
                        enemies.clear();
                        synchronized (this)
                        {
                            numKills = 0;
                        }
                        stage++;
                    } else if (stage == 2)
                    {
                        while (numKills <= 10)
                        {
                            switch (rand.nextInt(3))
                            {
                                case 0:
                                    enemy = new EnemyBlue(MyWorld.this);
                                    break;
                                case 1:
                                    enemy = new EnemyBlack(MyWorld.this);
                                    break;
                                default:
                                    enemy = new EnemyYellow(MyWorld.this);
                            }

                            enemy.position.X = width * rand.nextFloat();
                            while (enemy.position.X < 500)
                                enemy.position.X = width * rand.nextFloat();
                            enemy.position.Y = height * rand.nextFloat();
                            enemies.add((Enemy) enemy);
                            addObject(enemy);
                            try
                            {
                                Thread.sleep(rand.nextInt(1700) + 300);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            for (Enemy e : enemies)
                            {
                                float x = rand.nextFloat();
                                float y = rand.nextFloat();
                                if(rand.nextBoolean()) x*=-1;
                                if(rand.nextBoolean()) y*=-1;
                                Point3F vel = new Point3F(x, y, 0);
                                e.baseVelocity = vel;
                                e.speed = 300;
                                e.updateVelocity();
                                if(enemyShots <= MAX_SHOTS_ONSCREEN)
                                {
                                    EnemyLaser enemyLaser = new EnemyLaser(world);
                                    enemyLaser.position.Y = e.position.Y;
                                    enemyLaser.position.X = e.position.X;
                                    addObject(enemyLaser);
                                    enemyLaser.fire();
                                }
                            }
                        }
                        for (Enemy e : enemies)
                            synchronized (e)
                            {
                                e.kill();   //kill the remaining enemies onscreen
                            }
                        enemies.clear();
                        synchronized (this)
                        {
                            numKills = 0;
                        }
                        stage++;
                    }
                    else if (stage == 3)
                    {
                        while (!ship.isDead())
                        {
                            switch (rand.nextInt(3))
                            {
                                case 0:
                                    enemy = new EnemyBlue(MyWorld.this);
                                    break;
                                case 1:
                                    enemy = new EnemyBlack(MyWorld.this);
                                    break;
                                default:
                                    enemy = new EnemyYellow(MyWorld.this);
                            }

                            enemy.position.X = width * rand.nextFloat();
                            while (enemy.position.X < 500)
                                enemy.position.X = width * rand.nextFloat();
                            enemy.position.Y = height * rand.nextFloat();
                            enemies.add((Enemy) enemy);
                            addObject(enemy);
                            Point3F vel = new Point3F(-1f, 0, 0);
                            enemy.baseVelocity = vel;
                            enemy.speed = 500;
                            enemy.updateVelocity();
                            try
                            {
                                Thread.sleep(rand.nextInt(1000) + 300);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        for (Enemy e : enemies)
                            synchronized (e)
                            {
                                e.kill();   //kill the remaining enemies onscreen
                            }
                        enemies.clear();
                        synchronized (this)
                        {
                            numKills = 0;
                        }
                    }
                }
                listener.onGameOver(true);
            }
        }).start();
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        ShipLaser shipLaser = new ShipLaser(this);
        shipLaser.position.Y = ship.position.Y;
        shipLaser.position.X = ship.position.X;
        Point3F touch = new Point3F(event.getX(), event.getY(), 0);
        
        switch(event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                if(stage == 1)
                {
                    // have to add object in switch case in order to avoid animation bug
                    if(shots <= MAX_SHOTS_ONSCREEN)
                    {
                        this.addObject(shipLaser);
                        shipLaser.fireAtPos(touch);
                        shots++;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(stage == 2 || stage == 3)
                {
                    // Find the index of the active pointer and fetch its position
                    final int pointerIndex = event.findPointerIndex(mActivePointerId);
                    final float y = event.getY(pointerIndex);
                    // Calculate the distance moved
                    final float dy = y - mLastTouchY;
                    // Move the object
                    ship.position.Y += dy;
                    // Remember this touch position for the next move event
                    mLastTouchY = y;
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if(stage == 2)
                {
                    // have to add object in switch case in order to avoid animation bug
                    if(shots <= MAX_SHOTS_ONSCREEN)
                    {
                        this.addObject(shipLaser);
                        shipLaser.fire(touch);
                        shots++;
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        try
        {
            mp.start();
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    public void updateHighScores()
    {
        if(highScores.size() == 0)
            highScores.add(score);
        else
        {
            Boolean openSpaces = highScores.size() < HIGH_SCORE_MAX ? true : false;

            for(int highScore : highScores)
            {
                if(score > highScore)
                {
                    int pos = highScores.indexOf(highScore);
                    highScores.add(pos, score);
                    break;
                }
            }

            // current score may not be the highest
            if(openSpaces)
                highScores.add(score);
        }

        //clear out extras
        if(highScores.size() >= HIGH_SCORE_MAX)
            for(int i = highScores.size() - 1; highScores.size() != HIGH_SCORE_MAX; i--)
                highScores.remove(i);
    }

    public void storeHighScores()
    {
        if(highScores.size() >= 1)
        {
            String highScoreString = "";
            for(int highScore : highScores)
                highScoreString += Integer.toString(highScore).trim() + " ";
            editor.putString("highScores", highScoreString);
            editor.commit();
        }
    }

    public void retrieveHighScores()
    {
        String highScoreString = sharedPref.getString("highScores","");
        if(!highScoreString.isEmpty())
        {
            String[] highScoreStringArray = highScoreString.split(" ");
            for(int i = 0; i < highScoreStringArray.length; i++)
                highScores.add(Integer.parseInt(highScoreStringArray[i].trim()));
        }
    }

    //Calculates the player's score
    public void calculateScore()
    {
        enemyKill ++;
        score ++;

    }
}
