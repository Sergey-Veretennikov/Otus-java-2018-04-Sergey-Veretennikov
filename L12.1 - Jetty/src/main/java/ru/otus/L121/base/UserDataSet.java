package ru.otus.L121.base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "tuser")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet addressDataSet;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phoneDataSets = new ArrayList<>();

    public UserDataSet() {
    }

    public UserDataSet(String name, int age, AddressDataSet addressDataSet, PhoneDataSet... phones) {
        this.name = name;
        this.age = age;
        this.addressDataSet = addressDataSet;
        List<PhoneDataSet> userPhones = Arrays.asList(phones);
        this.setPhoneDataSets(userPhones);
        userPhones.forEach(phone -> phone.setUser(this));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return super.getId();
    }

    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressDataSet addressDataSet) {
        this.addressDataSet = addressDataSet;
    }

    public List<PhoneDataSet> getPhoneDataSets() {
        return phoneDataSets;
    }

    public void setPhoneDataSets(List<PhoneDataSet> phoneDataSets) {
        this.phoneDataSets = phoneDataSets;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + getId() +
                " name='" + name + '\'' +
                ", age=" + age +
                ", addressDataSet=" + addressDataSet +
                ", phones=" + phoneDataSets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDataSet that = (UserDataSet) o;

        if (age != that.age) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
