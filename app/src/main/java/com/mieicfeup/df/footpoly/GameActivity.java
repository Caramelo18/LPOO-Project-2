package com.mieicfeup.df.footpoly;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 500;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 5;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private Player[] players;
    private ImageView tableIm;
    private Dice dice;
    private Table table;
    private int orderIndex;
    private int currPlayer;

    public void loadInterface()
    {
        /*Thread load = new Thread()
        {
            @Override
            public void run()
            {*/
                tableIm = (ImageView) findViewById(R.id.table);
                int tableID = GameActivity.this.getResources().getIdentifier("table", "drawable", GameActivity.this.getPackageName());
                Integer id = tableID;

                tableIm.setImageResource(tableID);
       /*     }
        };
        load.start();*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        hide();

        loadInterface();

        players = new Player[4];

        ImageView tok = (ImageView) findViewById(R.id.pino);
        TextView player1Text = (TextView) findViewById(R.id.player1Balance);
        players[0] = new Player(tok, player1Text, 1);

        TextView player2Text = (TextView) findViewById(R.id.player2Balance);
        players[1] = new Player(tok, player2Text, 2);

        TextView player3Text = (TextView) findViewById(R.id.player3Balance);
        players[2] = new Player(tok, player3Text, 3);

        TextView player4Text = (TextView) findViewById(R.id.player4Balance);
        players[3] = new Player(tok, player4Text, 4);

        dice = new Dice();
        table = new Table();
        final BuyStadiumDialog dialog = new BuyStadiumDialog();

        // generates the playing order
        final ArrayList<Integer> order = new ArrayList<>();
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(3);
        Collections.shuffle(order);

        orderIndex = 0;
        currPlayer = order.get(orderIndex);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int movement = dice.rollDice();
                players[currPlayer].increaseIndex(movement);
                Stadium current = table.getStadium(players[currPlayer].getIndex());
                if(current == null)
                    return;
                dialog.setData(current, players[currPlayer]);
                if(current.getOwner() == null)
                {
                    dialog.show(getFragmentManager(), "buyStadium");
                    hide();
                }
                else if(current.getOwner() != players[currPlayer])
                {
                    players[currPlayer].decBalance(table.getRent(movement));
                }
                players[currPlayer].updateText();

                Log.w(String.valueOf(orderIndex), String.valueOf(currPlayer));
                ++orderIndex;
                if(orderIndex > 3)
                    orderIndex = 0;
                currPlayer = order.get(orderIndex);

            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        hide();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
