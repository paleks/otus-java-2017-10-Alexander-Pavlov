package ru.otus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "table_address")
public class AddressDataSet extends DataSet {
    @Column
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "street='" + street + '\'' +
                '}';
    }
}
