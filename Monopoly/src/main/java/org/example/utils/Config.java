package org.example.utils;

/**
 * Хранилище статических констант
 */
public class Config {
    /**
     * Деньги игрока при старте игры
     */
    public static final int START_CASH = 1000;
    /**
     * Деньги, начисляемые за круг
     */
    public static final int ROUND_CASH = 50;
    /**
     * Штраф для выхода из тюрьмы
     */
    public static final int JAIL_CASH = 50;
    /**
     * Размер поля, количество клеток на нем
     */
    public static final int MAP_SIZE = 24;
    /**
     * Расположение начальной клетки
     */
    public static final int START_INDEX = 0;
    /**
     * Расположение клетки тюрьмы на поле
     */
    public static final int JAIL_INDEX = 6;
    /**
     * Расположение клетки парковки на поле
     */
    public static final int PARK_INDEX = 12;
    /**
     * Расположение клетки, отправляющей в тюрьму, на поле
     */
    public static final int GO_TO_JAIL_INDEX = 18;
    /**
     * Размер улицы (количество клеток)
     */
    public static final int STREET_CAPACITY = 2;
}
