package com.smoftware.bensrpg.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Aurelien on 22/12/2015.
 */
public class Musics
{
    public static Music exterieur;

    public static void registerMusics()
    {
        exterieur = Gdx.audio.newMusic(Gdx.files.internal("musics/exterieur.mp3"));
    }
}
