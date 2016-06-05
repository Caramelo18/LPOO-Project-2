package com.mieicfeup.df.footpoly.view;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.GameController;
import com.mieicfeup.df.footpoly.controller.ScreenInfo;
import com.mieicfeup.df.footpoly.model.Game;

import java.util.ArrayList;

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

    private ImageView tableIm;

    private ArrayList<ImageView> playerImages;
    private ArrayList<TextView> playerText;

    private ImageButton rollDice;

    private Button mortgageButton;
    private Button buyStadiumButton;
    private Button upgradeStadiumButton;
    private Button endRoundButton;

    private GameController gameController;
    private int orderIndex;

    public void loadInterface()
    {
        playerImages = new ArrayList<ImageView>();
        playerText = new ArrayList<TextView>();

        for (int i = 1; i <= gameController.getPlayerList().size(); i++)
        {
            int resId = getResources().getIdentifier("pino" + String.valueOf(i), "id", getPackageName());
            ImageView tmpImg = (ImageView) findViewById(resId);

            resId = getResources().getIdentifier("pino" + String.valueOf(i), "drawable", getPackageName());
            tmpImg.setImageResource(resId);
            playerImages.add(tmpImg);

            gameController.getPlayerList().get(i - 1).setImage(tmpImg);

            resId = getResources().getIdentifier("player" + String.valueOf(i) + "Balance", "id", getPackageName());
            TextView tmpTxt = (TextView) findViewById(resId);
            playerText.add(tmpTxt);

            gameController.getPlayerList().get(i - 1).setText(tmpTxt);
            gameController.getPlayerList().get(i - 1).updateText();
        }
        gameController.shufflePlayers();

        rollDice = (ImageButton) findViewById(R.id.rollDice);
        rollDice.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View v)
            {
                gameController.rollDice();
                rollDice.setClickable(false);
                endRoundButton.setClickable(true);
                mortgageButton.setClickable(true);
                buyStadiumButton.setClickable(true);
                upgradeStadiumButton.setClickable(true);
            }
        });

        mortgageButton = (Button) findViewById(R.id.mortgageButton);

        buyStadiumButton = (Button) findViewById(R.id.buyStadiumButton);

        upgradeStadiumButton = (Button) findViewById(R.id.upgradeStadiumButton);

        endRoundButton = (Button) findViewById(R.id.endRoundButton);
        endRoundButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                gameController.endTurn();
                rollDice.setClickable(true);
                endRoundButton.setClickable(false);
                mortgageButton.setClickable(false);
                buyStadiumButton.setClickable(false);
                upgradeStadiumButton.setClickable(false);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        ScreenInfo screenInfo = new ScreenInfo(getApplicationContext().getResources().getDisplayMetrics().density);
        Game game = (Game) getIntent().getSerializableExtra("game");
        this.gameController = new GameController(game);

        Log.w("Size", Integer.toString(gameController.getPlayerList().size()));
        Log.w("Player", gameController.getPlayerList().get(0).getPlayer().getName());

        hide();

        loadInterface();




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
