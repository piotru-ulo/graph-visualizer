package pl.edu.tcs.graph.view;

public class Colors {
    public static int[] rainbow(double x) {
        x = x * 255;
        x = x % 255;

        double angle = (x / 255.0) * 2 * Math.PI; // Convert x to radians

        // Red component
        int r = (int) (255 * (Math.pow(Math.sin(angle), 2)));

        // Green component
        int g = (int) (255 * (Math.pow(Math.sin(angle + (2 * Math.PI / 3)), 2)));

        // Blue component
        int b = (int) (255 * (Math.pow(Math.sin(angle + (4 * Math.PI / 3)), 2)));

        return new int[] { r, g, b };
    }

    public static int[] white = new int[] { 255, 255, 255 };

    public static int[] black = new int[] { 0, 0, 0 };

    public static int[] source = new int[] { 30, 99, 48 };

    public static int[] target = new int[] { 67, 60, 117 };

}