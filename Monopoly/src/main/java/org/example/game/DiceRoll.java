package org.example.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс, отвечающий за бросание пары кубиков.
 */
public class DiceRoll {

    /**
     * Первая выпавшая кость
     */
    private final int firstRoll;

    /**
     * Вторая выпавшая кость
     */
    private final int secondRoll;

    /**
     * Создает экземпляр класса (бросает кубики)
     */
    public DiceRoll() {
        Random random = ThreadLocalRandom.current();
        firstRoll = random.nextInt(1, 7);
        secondRoll = random.nextInt(1, 7);
    }

    /**
     * Создает экземпляр класса с заданными значениями (для тестов)
     * @param first первый кубик
     * @param second второй кубик
     */
    public DiceRoll(int first, int second) {
        firstRoll = first;
        secondRoll = second;
    }
    public int getFirstRoll() {
        return firstRoll;
    }

    public int getSecondRoll() {
        return secondRoll;
    }

    /**
     * @return true, если выпал дубль
     */
    public boolean isDouble() {
        return firstRoll == secondRoll;
    }

    public int getSum() {
        return firstRoll + secondRoll;
    }
}
