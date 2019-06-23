package ru.javawebinar.topjava.model;

public abstract class AbstractBaseEntity {
    /*
    Уникальный идентификатор сущности (Entity), что будет имплиментится на 1 с появлением каждой
    новой Entity.
    Если есть подозрение, что количество Entity перевалит за максимальное значение Integer, то
    имеет смысл использовать long
     */
    protected Integer id;

    /*
    Стандартная сущность (Entity)
     */
    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}