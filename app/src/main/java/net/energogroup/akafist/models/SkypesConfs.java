package net.energogroup.akafist.models;

/**
 * Класс-сущность, описывающий конференции
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class SkypesConfs {
    private final int id;
    private final String name;
    private final String url;

    /**
     * Конструктор класса с тремя параметрами
     * @param id int - индивидуальный номер
     * @param name String - название конференции
     * @param url String - ссылка на конференцию
     */
    public SkypesConfs(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    /**
     * Конструктор класса с двумя параметрами
     * @param id int - индивидуальный номер
     * @param name String - название конференции
     */
    public SkypesConfs(int id, String name) {
        this.id = id;
        this.name = name;
        this.url = null;
    }

    /**
     * @return String - возвращает поле name
     */
    public String getName() {
        return name;
    }

    /**
     * @return String - Возвращает поле url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return int - возвращает поле id
     */
    public int getId() {
        return id;
    }

    /**
     * Этот методо проверяет на наличие ссылки
     * @return
     */
    public boolean isUrl(){
        if(url != null)
            return true;
        return false;
    }
}
