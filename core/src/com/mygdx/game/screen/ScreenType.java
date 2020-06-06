package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    GAME(GameScreen.class),
    MENU(MenuScreen.class),
    LOADING(LoadingScreen.class),
    RESTART(RestartScreen.class),
    WIN(WinScreen.class);
    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(final Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return this.screenClass;
    }
}