package com.cs442_skatkar.geoguidemod1.beans;

/**
 * Created by Trofey on 4/18/2015.
 */
public class Category {
    private int id;
    private String name;


    public Category() {
        super();
    }

    public Category(int id, String name) {
        super();
        this.id = id;
        this.name = name;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name +  "]";
    }
}
