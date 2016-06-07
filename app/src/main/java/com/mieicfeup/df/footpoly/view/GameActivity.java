package com.mieicfeup.df.footpoly.view;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.GameController;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Game;

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

    private Button menuButton;

    private ImageButton rollDice;

    private Button mortgageButton;
    private Button buyStadiumButton;
    private Button upgradeStadiumButton;
    private Button endRoundButton;

    private GameController gameController;

    private void loadInterface()
    {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        final Context context = this;

        for (int i = 1; i <= gameController.getPlayerList().size(); i++)
        {
            int resId = getResources().getIdentifier("pino" + String.valueOf(i), "id", getPackageName());
            ImageView tmpImg = (ImageView) findViewById(resId);

            PlayerController currPlayer = gameController.getPlayerList().get(i - 1);

            resId = getResources().getIdentifier("pino" + String.valueOf(i), "drawable", getPackageName());
            tmpImg.setImageResource(resId);

            int imageSize = metrics.widthPixels / 12;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tmpImg.getLayoutParams();
            params.width = imageSize - 2;
            params.height = imageSize - 2;
            if (i == 2 || i == 4)
                params.leftMargin = imageSize - 1;
            if (i == 3 || i == 4)
                params.topMargin = imageSize - 1;

            int startingPosition = currPlayer.getPlayer().getIndex();

            Log.w("Starting Position", Integer.toString(startingPosition));

            int squareSize = imageSize * 2;

            if (startingPosition <= 5) {
                params.leftMargin += squareSize * startingPosition;
            }
            else if (startingPosition <= 10) {
                params.leftMargin += squareSize * 5;
                params.topMargin += squareSize * (startingPosition - 5);
            }
            else if (startingPosition <= 15) {
                params.leftMargin += squareSize * (15 - startingPosition);
                params.topMargin += squareSize * 5;
            }
            else {
                params.topMargin += squareSize * (20 - startingPosition);
            }

            tmpImg.setLayoutParams(params);

            gameController.getPlayerList().get(i - 1).setImage(tmpImg);

            resId = getResources().getIdentifier("player" + String.valueOf(i) + "Balance", "id", getPackageName());
            TextView tmpTxt = (TextView) findViewById(resId);

            currPlayer.setText(tmpTxt);
            currPlayer.updateText();
        }

        gameController.shufflePlayers();

        menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                MenuDialog dialog = new MenuDialog();
                dialog.setContext(context);
                dialog.setGameController(gameController );
                dialog.showDialog();
            }
        });

        rollDice = (ImageButton) findViewById(R.id.rollDice);
        rollDice.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View v)
            {
                rollDice.setClickable(false);
                int dialogType = gameController.startTurn();

                if(dialogType == 1)
                {
                    BuyStadiumDialog dialog = gameController.showBuyStadiumDialog();
                    dialog.setContext(context);
                    dialog.show(getFragmentManager(), "dialog");
                }
                else if(dialogType == 2)
                {
                    LuckDialog dialog = gameController.showLuckDialog();
                    dialog.show(getFragmentManager(), "dialog");
                }
                endRoundButton.setClickable(true);
                mortgageButton.setClickable(true);
                buyStadiumButton.setClickable(true);
                upgradeStadiumButton.setClickable(true);
                menuButton.setClickable(true);
            }
        });

        mortgageButton = (Button) findViewById(R.id.mortgageButton);
        mortgageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MortgageDialog dialog = gameController.showMortgageDialog();
                dialog.show(getFragmentManager(), "dialog");

            }
        });

        buyStadiumButton = (Button) findViewById(R.id.buyStadiumButton);
        buyStadiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyStadiumDialog dialog = gameController.showBuyStadiumDialog();
                if (dialog.getStadium().getOwner() == null) {
                    dialog.setContext(context);
                    dialog.show(getFragmentManager(), "dialog");
                }
            }
        });

        upgradeStadiumButton = (Button) findViewById(R.id.upgradeStadiumButton);
        upgradeStadiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpgradeStadiumDialog dialog = gameController.showUpgradeStadiumDialog();
                dialog.setContext(context);
                dialog.showDialog();
            }
        });

        endRoundButton = (Button) findViewById(R.id.endRoundButton);
        endRoundButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                endRoundButton.setClickable(false);
                mortgageButton.setClickable(false);
                buyStadiumButton.setClickable(false);
                upgradeStadiumButton.setClickable(false);
                menuButton.setClickable(false);

                gameController.endTurn();

                while (!gameController.isCurrentPlayerHuman()) {
                    gameController.startTurn();
                }

                rollDice.setClickable(true);
            }
        });

        endRoundButton.setClickable(false);
        mortgageButton.setClickable(false);
        buyStadiumButton.setClickable(false);
        upgradeStadiumButton.setClickable(false);
        menuButton.setClickable(false);

        InfoDialog infoDialog = new InfoDialog();
        infoDialog.setContext(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hide();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        Game game = (Game) getIntent().getSerializableExtra("game");
        this.gameController = new GameController(game);

        hide();

        loadInterface();

        while (!gameController.isCurrentPlayerHuman()) {
            gameController.startTurn();
        }

        rollDice.setClickable(true);

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
