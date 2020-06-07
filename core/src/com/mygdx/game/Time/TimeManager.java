package com.mygdx.game.Time;

import com.badlogic.gdx.Gdx;
import lombok.Getter;
import lombok.Setter;

public class TimeManager {
    private static String currentTime = "";

    private static String minuteBlock = "";
    private static String hoursBlock = "";

    @Getter
    private static int minutes = 50;
    @Getter
    private static int hours = 8;

    private static float accumulator = 0;
    @Setter
    private static float TIME_SCALE = 0.7f;

    public static String getTime() {
        accumulate();
        return currentTime;
    }

    private static void accumulate() {
        accumulator += Gdx.graphics.getDeltaTime() * TIME_SCALE;
        int converted = (int) accumulator;
        if (converted != 0) {
            minutes += converted;
        }
        calculateHours();
        generateBlock();
        check(converted);
        currentTime = hoursBlock + " : " + minuteBlock;
    }

    private static void generateBlock() {
        if (minutes < 10)
            minuteBlock = "0" + minutes;
        else
            minuteBlock = "" + minutes;


        if (hours < 10)
            hoursBlock = "0" + hours;
        else
            hoursBlock = "" + hours;
    }

    private static void calculateHours() {
        if (minutes >= 60) {
            hours += 1;
            minutes = 0;
        }
        if (hours >= 24) {
            hours = 0;
        }
    }

    private static void check(int converted) {
        if (converted >= 1)
            accumulator = 0;
    }
}
