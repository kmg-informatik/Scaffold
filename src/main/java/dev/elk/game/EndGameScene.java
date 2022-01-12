package dev.elk.game;

import dev.elk.scaffold.components.Scene;
import dev.elk.scaffold.gl.Window;

import java.io.IOException;

public class EndGameScene extends Scene {

    public EndGameScene(Window window) {
        super(window);
    }

    @Override
    public void init() throws InstantiationException, IOException {
        window.setGameShouldEnd(true);
    }
}
