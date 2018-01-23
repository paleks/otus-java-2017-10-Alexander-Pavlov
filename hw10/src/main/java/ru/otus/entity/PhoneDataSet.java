package ru.otus.entity;

import javax.persistence.*;

@Entity
@Table(name = "table_phone")
public class PhoneDataSet extends DataSet {

    @Column
    private String phone;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "phone='" + phone + '\'' +
                '}';
    }
}