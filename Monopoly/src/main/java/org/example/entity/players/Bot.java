package org.example.entity.players;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс - потомок класса Player. Отличается от него тем, что решения принимает случайным образом.
 */
public class Bot extends Player{
    private final ImageIcon BOT_ICON = new ImageIcon("src/main/resources/monopoly_bot.png");

    /**
     * Создает экземпляр класса бота
     * @param cash начальное количество денег
     * @param tileIndex начальные координаты
     * @param name имя игрока
     * @param playerColor его цвет
     */
    public Bot(int cash, int tileIndex, String name, Color playerColor) {
        super(cash, tileIndex, name, playerColor);
    }

    /**
     * Метод выдает случайный ответ на вопрос
     * @param message сообщение, которое увидит бот в качестве вопроса
     * @param title название окошка
     * @return true, если согласен, false, если не согласен
     */
    @Override
    public boolean ask(String message, String title) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Метод заверщающий ход
     * @return false - ход нужно автоматически завершить
     */
    @Override
    public boolean endTurn() {
        return true;
    }

    @Override
    public Image getImage() {
        return BOT_ICON.getImage();
    }
}
