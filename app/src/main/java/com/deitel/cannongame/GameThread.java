package com.deitel.cannongame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import edu.noctrl.craig.generic.World;

/**
 * Created by craig_000 on 5/9/2015.
 */
public class GameThread extends Thread {
    private static final long FPS = (long)(1000.0 / 30.0);
    private SurfaceHolder surfaceHolder; // for manipulating canvas
    private World world;
    private boolean threadIsRunning = true; // running by default

    public GameThread(SurfaceHolder holder, World world) {
        surfaceHolder = holder;
        setName("GameThread");
        this.world = world;
    }

    @Override
    public synchronized void start() {
        threadIsRunning = true;
        super.start();
    }

    public void stopGame() {
        threadIsRunning = false;
    }

    // controls the game loop
    @Override
    public void run() {
        Canvas canvas = null; // used for drawing
        long previousFrameTime = System.currentTimeMillis();
        while (threadIsRunning) {
            try {
                // get Canvas for exclusive drawing from this thread
                canvas = surfaceHolder.lockCanvas(null);
                long currentTime = System.currentTimeMillis();
                float elapsedTime = currentTime - previousFrameTime;
                world.totalElapsedTime += elapsedTime / 1000.0;
                world.update(elapsedTime); // update game state
                // lock the surfaceHolder for drawing
                synchronized (surfaceHolder) {
                    world.draw(canvas); // draw using the canvas
                }
                world.cullAndAdd();
                previousFrameTime = currentTime; // update previous time
                try {
                    sleep(Math.max(0, FPS - (System.currentTimeMillis() - currentTime)));
                } catch(Exception ex){
                    //drawing faster isn't too bad
                }
            } finally {
                // display canvas's contents on the CannonView
                // and enable other threads to use the Canvas
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        } // end while
    } // end method run
}
