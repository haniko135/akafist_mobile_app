package net.energogroup.akafist.models;

/**
 * Класс-сущность, описывающий молитву
 * @author Nastya  Izotina
 * @version 1.0.0
 */
public class PrayersModels {
    private final String namePrayer;
    private final String textPrayer;
    private final int prev;
    private final int next;

    /**
     * Конструктор класса
     * @param namePrayer String - название молитвы
     * @param textPrayer String - текст молитвы
     * @param prev int - ссылка на предыдущую молитву
     * @param next int - ссылка на следующую молитву
     */
    public PrayersModels(String namePrayer, String textPrayer, int prev, int next) {
        this.namePrayer = namePrayer;
        this.textPrayer = textPrayer;
        this.prev = prev;
        this.next = next;
    }

    /**
     * @return String - возвращает название молитвы
     */
    public String getNamePrayer() {
        return namePrayer;
    }

    /**
     * @return String - возвращает текст молитвы
     */
    public String getTextPrayer() {
        return textPrayer;
    }

    /**
     * @return int - возвращает ссылку на предыдущуюю молитву
     */
    public int getPrev() {
        return prev;
    }

    /**
     * @return int - возвращает ссылку на предыдущуюю молитву
     */
    public int getNext() {
        return next;
    }
}
