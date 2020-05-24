package com.mygdx.game.quest;

import com.badlogic.gdx.Gdx;

public class TimeManager {
    private static String currentTime = "";

    private static String minuteBlock = "";
    private static String hoursBlock = "";

    private static int minutes = 0;
    private static int hours = 0;
    private static float accumulator = 0;

    private static final float TIME_SCALE = 0.7f;

    public static String getTime() {
        accumulate();
        return currentTime;
    }

    private static void accumulate() {
        accumulator += Gdx.graphics.getDeltaTime() * TIME_SCALE;
        int converted = (int) accumulator;
        minutes += converted;

        calculateHours();
        generateBlock();
        check(converted);
        currentTime = hoursBlock +" : "+minuteBlock;
    }

    private static void generateBlock() {
        if(minutes < 10){
            minuteBlock = "0" + minutes;
        }else{
            minuteBlock = ""+minutes;
        }

        if(hours < 10){
            hoursBlock = "0"+hours;
        }else{
            hoursBlock = ""+hours;
        }
    }

    private static void calculateHours(){
        if(minutes >= 60){
            hours += 1;
            minutes = 0;
        }
        if(hours >=24){
            hours = 0;
        }
    }

    private static void check(int converted){
        if (converted >= 1)
            accumulator = 0;
    }
}
