package com.mieicfeup.df.footpoly.model;

import java.util.Random;

/**
 * Created by fabio on 30/04/2016.
 */
public class Dice
{
    private final Random rand;

    /**
     * Default constructor
     */
    public Dice()
    {
        rand = new Random();
    }

    /**
     * Rolls the dice and returns a random number between 1 and 6
     * @return the number generated
     */
    public int rollDice()
    {
        return rand.nextInt(6) + 1;
    }
}
