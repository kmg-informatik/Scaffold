package dev.elk.game.spritesheetHandlers;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Saves information about spritesheets like Paths and whether
 * they are animated allowing for easier calling.
 *
 * @author Felix Kunze
 */
public enum SpritesheetInfo {
    TILES(Paths.get("assets/SpriteJson/tiles.json"), Paths.get("assets/Spritesheets/tiles.png"), false),
    TREES(Paths.get("assets/SpriteJson/trees.json"), Paths.get("assets/Spritesheets/trees.png"), false),
    PIPE(Paths.get("assets/SpriteJson/pipe.json"),Paths.get("assets/Spritesheets/pipe.png"),false),
    ANIMATIONS(Paths.get("assets/SpriteJson/animations.json"), Paths.get("assets/Spritesheets/animations.png"), true),
    BIRD(Paths.get("assets/SpriteJson/bird.json"),Paths.get("assets/Spritesheets/bird.png"),true),

    COZETTE(Paths.get("assets/SpriteJson/fonts/cozette.json"), Paths.get("assets/Spritesheets/fonts/cozette.png"), false),
    COZETTE_BLACK(Paths.get("assets/SpriteJson/fonts/cozetteBlack.json"), Paths.get("assets/Spritesheets/fonts/cozetteBlack.png"), false),
    JETBRAINS_MONO(Paths.get("assets/SpriteJson/fonts/jetbrainsMono.json"), Paths.get("assets/Spritesheets/fonts/jetbrainsMono.png"), false),
    TIMES_NEW_ROMAN(Paths.get("assets/SpriteJson/fonts/timesNewRoman.json"), Paths.get("assets/Spritesheets/fonts/timesNewRoman.png"), false);

    public final Path jsonPath;
    public final Path texturePath;
    public final boolean isAnimated;

    SpritesheetInfo(Path jsonPath, Path texturePath, boolean isAnimated) {
        this.jsonPath = jsonPath;
        this.texturePath = texturePath;
        this.isAnimated = isAnimated;
    }
}


