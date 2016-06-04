package com.mieicfeup.df.footpoly.controller;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mieicfeup.df.footpoly.model.Player;

/**
 * Created by Diogo on 01/06/2016.
 */
public class PlayerController
{
    private Player player;
    private ImageView playerImage;
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

    /**
     * Handles the movement of the player and the animation of the token
     * @param inc value to increment
     */
    public void movePlayer(int inc) {
        Log.w(player.getName() + " Old Position", String.valueOf(this.player.getIndex()));
        Log.w(player.getName() + " Increment", String.valueOf(inc));

        AnimationSet set = new AnimationSet(false);
        long animDuration = 200;

        int xDist = 0;
        int yDist = 0;

        for (int i = 0; i < inc; i++) {
            TranslateAnimation anim;

            int playerIndex = player.getIndex();

            if (playerIndex == 19) {
                anim = new TranslateAnimation(0, 0, 0, -movement);
                yDist -= movement;

                player.incrementIndex(-19);
            }
            else {
                if (playerIndex < 5) {
                    anim = new TranslateAnimation(0, movement, 0, 0);
                    xDist += movement;
                }
                else if (playerIndex < 10) {
                    anim = new TranslateAnimation(0, 0, 0, movement);
                    yDist += movement;
                }
                else if (playerIndex < 15) {
                    anim = new TranslateAnimation(0, -movement, 0, 0);
                    xDist -= movement;
                }
                else {
                    anim = new TranslateAnimation(0, 0, 0, -movement);
                    yDist -= movement;
                }

                player.incrementIndex(1);
            }

            anim.setDuration(animDuration);
            anim.setStartOffset(animDuration * i);

            set.addAnimation(anim);
        }

        set.setAnimationListener(animationListener(xDist, yDist));

        playerImage.startAnimation(set);

        Log.w(player.getName() + " New Position", String.valueOf(this.player.getIndex()));
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
}
