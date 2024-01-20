package agh.ics.oop.model;

public enum GrassVariant {
    EQUATORIAL(0),
    NEAR_CORPSES(1);

    private final int value;
    private GrassVariant(int value) {
        this.value = value;
    }

    public static GrassVariant getVariantByValue(int value) {
        for (GrassVariant variant : values()) {
            if (variant.value == value) {
                return variant;
            }
        }
        throw new IllegalArgumentException("No such grass variant with value: " + value);
    }
}
