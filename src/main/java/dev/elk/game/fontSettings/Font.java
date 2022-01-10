package dev.elk.game.fontSettings;

public enum Font {
    COZETTE("cozette", 18f/16f),
    JETBRAINS_MONO("jetbrainsMono", 20f/16f),
    TIMES_NEW_ROMAN("timesNewRoman", 20f/16f);

    public final String id;
    public final float heightWidthRatio;

    Font(String id, float heightWidthRatio) {
        this.id = id;
        this.heightWidthRatio = heightWidthRatio;
    }

}
