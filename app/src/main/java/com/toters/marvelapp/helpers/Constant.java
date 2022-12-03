package com.toters.marvelapp.helpers;

import com.toters.marvelapp.models.Characters;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static Characters lastCharacter = null;
    public static final int STATUS_FAILED = 0;
    public static final int STATUS_OK = 1;
    public static final String CHARACTER_ID = "characterId";
    public static String URL = "http://gateway.marvel.com/v1/public/";
    public static String API_PUBLIC_KEY = "e13c874eec8925dcdeede9e8cb8d96b1";
    public static String API_PRIVATE_KEY = "0dac88bb73fc5d574ff85124d09303cb213511f5";

    public static String CHARACTERS = "characters";
    public static String COMICS = "comics";
    public static String STORIES = "stories";
    public static String EVENTS = "events";
    public static String SERIES = "series";

    public static List<Characters> charactersList = new ArrayList<>();
}
