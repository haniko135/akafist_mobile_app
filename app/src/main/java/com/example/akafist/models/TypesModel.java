package com.example.akafist.models;

/**
 * Класс-сущность, описывающий тип, к которому
 * относится класс-сущность {@link ServicesModel}
 *
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class TypesModel {
    private final int id;
    private final String name;

    /**
     * Конструктор класса
     * @param id int - индивидуальный номер
     * @param name String - имя типа
     */
    public TypesModel(int id, String name) {
        this.id = id;
        this.name = name;
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
}
