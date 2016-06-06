package com.mieicfeup.df.footpoly.model;

import java.io.Serializable;

/**
 * Created by fabio on 01/06/2016.
 */
public abstract class Place implements Serializable
{
    public enum dialogType {NODIALOG, BUYSTADIUM, LUCK};
    public abstract dialogType trigger(Player player);
}
