package net.energogroup.akafist.models;

import net.energogroup.akafist.fragments.ChurchFragment;
import net.energogroup.akafist.viewmodel.ChurchViewModel;


/**
 * Класс-сущность, описывающий название блока молитвы.
 * Есть только в классах {@link ChurchFragment}
 * и {@link ChurchViewModel}
 *
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class ServicesModel {
    private final int id;
    private final String name;
    private final int type;
    private final String date;

    /**
     * Конструктор класса
     * @param id int - индивидуальный номер
     * @param name String - название молитвы
     * @param type int - ссылка на id типа
     * @param date String - тип
     */
    public ServicesModel(int id, String name, int type, String date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
    }

    /**
     * @return int - возвращает поле id
     */
    public int getId() {
        return id;
    }

    /**
     * @return String - возвращает поле name
     */
    public String getName() {
        return name;
    }

    /**
     * @return int - возвращает поле type
     */
    public int getType() {
        return type;
    }

    /**
     * @return String - возвращает поле date
     */
    public String getDate() {
        return date;
    }
}
