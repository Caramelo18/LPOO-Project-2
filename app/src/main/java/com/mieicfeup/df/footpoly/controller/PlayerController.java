package com.mieicfeup.df.footpoly.controller;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;

/**
 * Created by Diogo on 01/06/2016.
 */
public class PlayerController
{
    /**
     * Int Wrapper, used to be able to modify int in a function
     */
    private class WrapInt {
        private int value;

        public WrapInt(int value) {
            this.value = value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Player player;
    private ImageView playerImage;
    private TextView playerText;
    private int movement;

    public PlayerController(Player player) {
        this.player = player;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        this.movement = metrics.widthPixels / 6;
    }
    /**
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param playerImage
     */
    public void setImage(ImageView playerImage) {
        this.playerImage = playerImage;
    }

    public void setText(TextView playerText)
    {
        this.playerText = playerText;
    }

    public void updateText()
    {
        String newText = player.getName() + ": " + player.getBalance();
        playerText.setText(newText);
    }

    public void clearStroke()
    {
        playerText.setBackgroundResource(0);
    }

    public void strokeText()
    {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0x00000000);
        gd.setCornerRadius(5);
        gd.setStroke(3, 0xFF000000);
        playerText.setBackground(gd);
    }

    /**
     * Handles the movement of the player and the animation of the token
     * @param inc value to increment
     * @return true if passes by starting point, false otherwise
     */
    public boolean movePlayer(int inc) {

        boolean throughStart = false;
        AnimationSet set = new AnimationSet(false);

        WrapInt xDist = new WrapInt(0);
        WrapInt yDist = new WrapInt(0);

        if(addAnimationToSet(set, 0, xDist, yDist, inc))
            throughStart = true;

        if (player.getIndex() == 15)
            addAnimationToSet(set, inc, xDist, yDist, 10);

        set.setAnimationListener(animationListener(xDist.getValue(), yDist.getValue()));
        playerImage.startAnimation(set);

        Log.w(player.getName() + " New Position", String.valueOf(this.player.getIndex()));
        return throughStart;
    }

    /**
     * Adds the necessary animations to move inc places to the animationSet
     * @param set AnimationSet to add the animations
     * @param startingOffset starting offset for the animations
     * @param xDist x axis distance
     * @param yDist y axis distance
     * @param inc number of places to move
     * @return true if it goes through Starting Point and false otherwise
     */
    private boolean addAnimationToSet(AnimationSet set, long startingOffset, WrapInt xDist, WrapInt yDist, int inc) {
        boolean throughStart = false;
        long animDuration = 200;

        int xValue = xDist.getValue();
        int yValue = yDist.getValue();

        for (int i = 0; i < inc; i++) {
            TranslateAnimation anim;

            int playerIndex = player.getIndex();

            if (playerIndex == 19) {
                anim = new TranslateAnimation(0, 0, 0, -movement);
                yValue -= movement;

                player.incrementIndex(-19);
                throughStart = true;
            }
            else {
                if (playerIndex < 5) {
                    anim = new TranslateAnimation(0, movement, 0, 0);
                    xValue += movement;
                }
                else if (playerIndex < 10) {
                    anim = new TranslateAnimation(0, 0, 0, movement);
                    yValue += movement;
                }
                else if (playerIndex < 15) {
                    anim = new TranslateAnimation(0, -movement, 0, 0);
                    xValue -= movement;
                }
                else {
                    anim = new TranslateAnimation(0, 0, 0, -movement);
                    yValue -= movement;
                }

                player.incrementIndex(1);
            }

            anim.setDuration(animDuration);
            anim.setStartOffset(animDuration * (i + startingOffset));

            set.addAnimation(anim);

            xDist.setValue(xValue);
            yDist.setValue(yValue);
        }

        return throughStart;
    }

    /**
     * Creates the AnimationListener that handles the translation of the token after the Animation
     * @param translateX x axis translation
     * @param translateY y axis translation
     * @return AnimationListener
     */
    private TranslateAnimation.AnimationListener animationListener(final int translateX, final int translateY) {
        return new TranslateAnimation.AnimationListener() {

            private boolean ended = false;

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                if (ended)
                    return;

                ended = true;

                playerImage.clearAnimation();

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerImage.getLayoutParams();
                params.leftMargin += translateX;
                params.topMargin += translateY;
                playerImage.setLayoutParams(params);
            }
        };
    }

    /**
     * Buys the stadium, if the player has enough money
     * @param stadium stadium to buy
     * @return true if stadium was bought and false if the player doesn't have enough money
     */
    public boolean buyStadium(Stadium stadium)
    {
        if(this.player.decBalance(stadium.getCost())) {
            stadium.setOwner(this.player);
            return true;
        }

        return false;
    }
}
