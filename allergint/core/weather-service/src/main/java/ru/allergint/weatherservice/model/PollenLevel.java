package ru.allergint.weatherservice.model;

public enum PollenLevel {
    CRITICAL("Критический", 100),
    HIGH("Высокий", 50),
    MEDIUM("Средний", 25),
    LOW("Низкий", 0);

    private final String description;
    private final int minDensity;

    PollenLevel(String description, int minDensity) {
        this.description = description;
        this.minDensity = minDensity;
    }

    public String getDescription() { return description; }
    public int getMinDensity() { return minDensity; }

    public static PollenLevel fromDensity(int density) {
        if (density >= CRITICAL.minDensity) return CRITICAL;
        if (density >= HIGH.minDensity) return HIGH;
        if (density >= MEDIUM.minDensity) return MEDIUM;
        return LOW;
    }
}
