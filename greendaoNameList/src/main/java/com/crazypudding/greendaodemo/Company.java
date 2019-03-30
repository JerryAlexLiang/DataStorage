package com.crazypudding.greendaodemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Company {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Property(nameInDb = "EMPLOYEE_NAME")
    private String name;

    private int salary;

    @Transient
    private int tempUsageCount;

    @Generated(hash = 2028949425)
    public Company(Long id, @NotNull String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Generated(hash = 1096856789)
    public Company() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
