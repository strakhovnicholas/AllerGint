package ru.allergint.weatherservice.util;

/**
 * Набор географических утилит для расчёта расстояний, векторов и направления ветра.
 * Используется в сервисе прогноза пыльцы для вычисления:
 * <ul>
 *     <li>дистанций между датчиками, источниками и пользователями;</li>
 *     <li>векторов смещения между географическими точками в метрах;</li>
 *     <li>вектора переноса пыльцы по направлению ветра;</li>
 *     <li>скалярного произведения и норм векторов для оценки попадания в “конус ветра”.</li>
 * </ul>
 *
 * Все методы работают с координатами широты/долготы в градусах и переводят их в метры.
 */
public final class GeoUtils {

    /** Радиус Земли в метрах. Используется в формуле гаверсинусов для расчёта расстояния. */
    private static final double EARTH_RADIUS = 6371000;

    private GeoUtils() {}

    /**
     * Вычисляет расстояние между двумя точками по поверхности Земли
     * с использованием формулы гаверсинусов (Haversine).
     * <p>
     * Подходит для коротких расстояний (в пределах города) и даёт точность до метров.
     * </p>
     *
     * @param lat1 широта первой точки (в градусах)
     * @param lon1 долгота первой точки (в градусах)
     * @param lat2 широта второй точки (в градусах)
     * @param lon2 долгота второй точки (в градусах)
     * @return расстояние между точками в метрах
     */
    public static double distanceMeters(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }

    /**
     * Преобразует географические координаты двух точек в локальный метрический вектор.
     * <p>
     * Результат возвращается в виде:
     * <pre>
     * [ dx , dy ] где:
     *   dx — смещение по оси «восток–запад» (в метрах),
     *   dy — смещение по оси «север–юг» (в метрах).
     * </pre>
     * Метод необходим для:
     * <ul>
     *     <li>определения направления от источника пыльцы к пользователю/ячейке карты;</li>
     *     <li>расчёта попадания точки в поток ветра;</li>
     *     <li>построения двумерного поля концентрации пыльцы.</li>
     * </ul>
     *
     * @param lat1 широта исходной точки
     * @param lon1 долгота исходной точки
     * @param lat2 широта целевой точки
     * @param lon2 долгота целевой точки
     * @return массив {dx, dy} — сдвиг в метрах на восток и на север
     */
    public static double[] vectorMeters(double lat1, double lon1, double lat2, double lon2) {
        double dNorth = distanceMeters(lat1, lon1, lat2, lon1);
        if (lat2 < lat1) dNorth = -dNorth;

        double dEast = distanceMeters(lat1, lon1, lat1, lon2);
        if (lon2 < lon1) dEast = -dEast;

        return new double[] { dEast, dNorth };
    }

    /**
     * Преобразует метеорологический угол направления ветра (в градусах)
     * в двумерный вектор скорости ветра в метрах/секунда по осям:
     * <pre>
     * vx — восточное направление
     * vy — северное направление
     * </pre>
     * <p>
     * Метеорологический формат:
     * <ul>
     *     <li>0° — ветер дует <b>с</b> севера</li>
     *     <li>90° — <b>с</b> востока</li>
     *     <li>180° — <b>с</b> юга</li>
     *     <li>270° — <b>с</b> запада</li>
     * </ul>
     * Но вектор должен указывать <b>туда, куда ветер дует</b>, поэтому направление инвертируется.
     *
     * @param directionDeg направление ветра "ОТКУДА дует", в градусах
     * @param speed скорость ветра в м/с
     * @return массив {vx, vy} — вектор скорости ветра в м/с
     */
    public static double[] windVector(double directionDeg, double speed) {
        double dirRad = Math.toRadians((directionDeg + 180) % 360);
        double vx = Math.sin(dirRad) * speed;
        double vy = Math.cos(dirRad) * speed;
        return new double[] { vx, vy };
    }

    /**
     * Скалярное произведение двух двумерных векторов.
     * Используется для проверки того, лежит ли точка "впереди" по направлению ветра.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @return скалярное произведение a · b
     */
    public static double dot(double[] a, double[] b) {
        return a[0]*b[0] + a[1]*b[1];
    }

    /**
     * Вычисляет длину (норму) двумерного вектора.
     *
     * @param a вектор
     * @return длина вектора
     */
    public static double norm(double[] a) {
        return Math.sqrt(a[0]*a[0] + a[1]*a[1]);
    }
}
