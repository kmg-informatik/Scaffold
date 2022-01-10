package dev.elk.game.fontSettings;

/**
 * Saves information about specific fonts
 * @author Felix Kunze
 * @author Louis Schell
 */
public enum Font {
    COZETTE("cozette","coz", 18f/16f),
    JETBRAINS_MONO("jetbrainsMono", "jbm",20f/16f),
    TIMES_NEW_ROMAN("timesNewRoman", "tnr", 20f/16f);

    public final String name;
    public final float heightWidthRatio;
    public String fontID;

    /**
     * Generator for fonts
     * @param name The id that is used to get file names
     * @param heightWidthRatio The ratio of height to width since most fonts have
     *                         glyphs are taller than they are wide.
     */
    Font(String name, String fontID, float heightWidthRatio) {
        this.name = name;
        this.fontID = fontID;
        this.heightWidthRatio = heightWidthRatio;
    }

}
