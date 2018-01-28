package ru.otus.web.model.entity;

public abstract class DataSet {
    private long id;

    abstract public Class getDataAccessObjectClass();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
