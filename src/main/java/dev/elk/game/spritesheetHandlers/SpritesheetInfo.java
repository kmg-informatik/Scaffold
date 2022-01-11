package dev.elk.game.spritesheetHandlers;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Saves information about spritesheets like Paths and whether
 * they are animated allowing for easier calling.
 * @author Felix Kunze
 */
public enum SpritesheetInfo {
    TILES(Paths.get("Assets/SpriteJson/tiles.json"), Paths.get("Assets/Spritesheets/tiles.png"), false),
    TREES(Paths.get("Assets/SpriteJson/trees.json"), Paths.get("Assets/Spritesheets/trees.png"), false),
    ANIMATIONS(Paths.get("Assets/SpriteJson/animations.json"), Paths.get("Assets/Spritesheets/animations.png"), true),
    COZETTE(Paths.get("Assets/SpriteJson/fonts/cozette.json"), Paths.get("Assets/Spritesheets/fonts/cozette.png"), false),
    JETBRAINS_MONO(Paths.get("Assets/SpriteJson/fonts/jetbrainsMono.json"), Paths.get("Assets/Spritesheets/fonts/jetbrainsMono.png"), false),
    TIMES_NEW_ROMAN(Paths.get("Assets/SpriteJson/fonts/timesNewRoman.json"), Paths.get("Assets/Spritesheets/fonts/timesNewRoman.png"), false);

    public final Path jsonPath;
    public final Path texturePath;
    public final boolean isAnimated;

    SpritesheetInfo(Path jsonPath, Path texturePath, boolean isAnimated) {
        this.jsonPath = jsonPath;
        this.texturePath = texturePath;
        this.isAnimated = isAnimated;
    }
}


