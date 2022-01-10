package dev.elk.game.fontSettings;

/**
 * Saves information about specific fonts
 * @author Felix Kunze
 * @author Louis Schell
 */
public enum Font {
    COZETTE("cozette", 18f/16f),
    JETBRAINS_MONO("jetbrainsMono", 20f/16f),
    TIMES_NEW_ROMAN("timesNewRoman", 20f/16f);

    public final String id;
    public final float heightWidthRatio;

    /**
     * Generator for fonts
     * @param id The id that is used to get file names
     * @param heightWidthRatio The ratio of height to width since most fonts have
     *                         glyphs are taller than they are wide.
     */
    Font(String id, float heightWidthRatio) {
        this.id = id;
        this.heightWidthRatio = heightWidthRatio;
    }

}
