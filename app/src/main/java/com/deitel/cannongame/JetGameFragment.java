// CannonGameFragment.java
// JetGameFragment creates and manages a CannonView
package com.deitel.cannongame;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cornelius.tessa.victor.MyWorld;


public class JetGameFragment extends Fragment {
    private JetGameView jetGameView; // custom view to display the game
    private MyWorld myWorld;
    // called when Fragment's view needs to be created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // get the view
        jetGameView = (JetGameView) view.findViewById(R.id.cannonView);
        setHasOptionsMenu(true);
        return view;
    }

    // set up volume control once Activity is created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // allow volume keys to set game volume
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    // when MainActivity is paused, JetGameFragment terminates the game
    @Override
    public void onPause() {
        super.onPause();
        jetGameView.stopGame(); // terminates the game
    }

    // when MainActivity is paused, JetGameFragment releases resources
    @Override
    public void onDestroy() {
        super.onDestroy();
        jetGameView.releaseResources();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.ss
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.highScores:
                jetGameView.stopGame();
                jetGameView.newGame(jetGameView.getHolder());
                myWorld.retrieveHighScores();
                return true;

            case R.id.stage1:
                jetGameView.stopGame();
                jetGameView.newGame(jetGameView.getHolder());
                myWorld.setStage(1);
                return true;

            case R.id.stage2:
                myWorld.stage = 2;
                return true;

            case R.id.stage3:
                myWorld.stage = 3;
                return true;

            case R.id.stageSelection:
                return true;

            case R.id.about:
                about();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Displays about message
    public void about() {
        new AlertDialog.Builder(getActivity())
                .setTitle("About")
                .setMessage("This game was created by Cornelius, Tessa, & Victor. Homage to Brain Craig, for game engine")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
} // end class JetGameFragment

/*********************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and * Pearson Education, *
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this   *
 * book have used their * best efforts in preparing the book. These efforts      *
 * include the * development, research, and testing of the theories and programs *
 * * to determine their effectiveness. The authors and publisher make * no       *
 * warranty of any kind, expressed or implied, with regard to these * programs   *
 * or to the documentation contained in these books. The authors * and publisher *
 * shall not be liable in any event for incidental or * consequential damages in *
 * connection with, or arising out of, the * furnishing, performance, or use of  *
 * these programs.                                                               *
 *********************************************************************************/
