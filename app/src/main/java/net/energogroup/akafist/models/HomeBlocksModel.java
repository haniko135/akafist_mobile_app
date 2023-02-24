package net.energogroup.akafist.models;

/**
 * Класс-сущность, описывающий основные параметры блоков
 * во фрагментах "Главная" и "Меню"
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class HomeBlocksModel {
    private String date;
    private String dateTxt;
    private String name;
    private String additions;
    private int links;

    /**
     * Конструктор класса с тремя параметрами
     * @param date String - тип блока
     * @param dateTxt String - верхнее название блока
     * @param name String - нижнее название блока
     */
    public HomeBlocksModel(String date, String dateTxt, String name) {
        this.date = date;
        this.dateTxt = dateTxt;
        this.name = name;
    }

    /**
     * Конструктор класса с двумя параметрами
     * @param date String - тип блока
     * @param dateTxt String - верхнее название блока
     */
    public HomeBlocksModel(String date, String dateTxt) {
        this.date = date;
        this.dateTxt = dateTxt;
    }

    /**
     * @return String - Возвращает поле date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return String - Возвращает поле dateTxt
     */
    public String getDateTxt() {
        return dateTxt;
    }

    /**
     * @return String - Возвращает поле name
     */
    public String getName() {
        return name;
    }

    /**
     * Этот метод присваивает ссылки
     * @param links int - Ссылки на фрагменты
     */
    public void setLinks(int links) {
        this.links = links;
    }

    /**
     * @return int - Возвращает поле links
     */
    public int getLinks() {
        return links;
    }

    /**
     * Этот метод присваивает дополнительные атрибуты
     * @param additions String - дополнительные атрибуты
     */
    public void setAdditions(String additions) {
        this.additions = additions;
    }

    /**
     * @return String - Возвращает поле additions
     */
    public String getAdditions() {
        return additions;
    }
}
