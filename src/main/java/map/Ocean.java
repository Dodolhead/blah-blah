package map;

public class Ocean {
    final int oceannWidth = 24;
    final int oceannHeight = 24;
private static final char[][] DEFAULT_OCEAN_DISPLAY = {
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'Q','L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'L','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'},
    {'U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U','U'}
};



    private final char[][] oceanDisplay;

    public Ocean() {
        oceanDisplay = DEFAULT_OCEAN_DISPLAY;
    }

    public char[][] getOceanDisplay() {
        return oceanDisplay;
    }
}
