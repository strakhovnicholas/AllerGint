package ru.allergint.weatherservice.util;

public final class MathUtils {

    public static double gaussian(double x, double sigma) {
        return Math.exp(-(x*x) / (2*sigma*sigma));
    }

    public static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}
