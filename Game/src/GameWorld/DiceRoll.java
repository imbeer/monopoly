package GameWorld;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoll {

    private final int firstRoll;

    private final int secondRoll;

    public DiceRoll() {
        Random random = ThreadLocalRandom.current();
        firstRoll = random.nextInt(1, 7);
        secondRoll = random.nextInt(1, 7);
    }

    public int getFirstRoll() {
        return firstRoll;
    }

    public int getSecondRoll() {
        return secondRoll;
    }

    public boolean isDouble() {
        return firstRoll == secondRoll;
    }

    public int getSum() {
        return firstRoll + secondRoll;
    }
}
