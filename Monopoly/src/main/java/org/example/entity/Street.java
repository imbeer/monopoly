package org.example.entity;

import org.example.entity.players.Player;
import org.example.utils.Config;

import java.awt.*;
import java.util.HashMap;

/**
 * Класс улицы (скопления клеток). Знает хозяев всех клеток на улице и знает координаты клеток, но для избежания циклической зависимости не знает сами клетки.
 */
public class Street {
    /**
     * Массив всех хозяев клеток
     */
    private final Player[] owners;
    /**
     * Ассоциативный словарь, индекс клетки -> индекс хозяина в массиве <b>owners</b>
     */
    private final HashMap<Integer, Integer> tileIndexes;
    public final Color streetColor;

    /**
     * Создает улицу нужного цвета
     * @param streetColor передаваемый цвет
     */
    public Street(Color streetColor) {
        this.streetColor = streetColor;
        owners = new Player[Config.STREET_CAPACITY];
        tileIndexes = new HashMap<>();
    }

    /**
     * Метод добавляет игрока в массив хозяев. Для этого он берет индекс в массиве из tileIndexes (если он там есть) и присваивает в owners нового игрока. Иначе - просто кладет на первое своодное место.
     * @param player добавляемый игрок
     * @param tileIndex координата клетки
     */
    public void addOwner(Player player, int tileIndex) {
        if (tileIndexes.containsKey(tileIndex)) {
            owners[tileIndexes.get(tileIndex)] = player;
            return;
        }
        for (int i = 0; i < owners.length; i++) {
            if (owners[i] == null) {
                owners[i] = player;
                tileIndexes.put(tileIndex, i);
                return;
            }
        }
    }

    /**
     * Метод показывает, может ли игрок улучшить что-либо на этой улице.
     * @param player проверяемый игрок
     * @return true, если хозяин всех клеток на улице, false, если хотя бы у одной клетки другой хозяин
     */
    public boolean canBeUpgraded(Player player) {
        if (player == null) {
            return false;
        }
        for (Player owner : owners) {
            if (player != owner) {
                return false;
            }
        }
        return true;
    }
}
