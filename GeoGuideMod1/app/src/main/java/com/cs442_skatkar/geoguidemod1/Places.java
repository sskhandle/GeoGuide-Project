package com.cs442_skatkar.geoguidemod1;


public class Places {

    private int _id;
    private String _name;
    private String _vincinity;


    public Places(){
        super();
    }

    public Places(String name, String vincinity) {
        super();
        this._name = name;
        this._vincinity = vincinity;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_vincinity() {
        return _vincinity;
    }

    public void set_vincinity(String vincinity) {
        this._vincinity = vincinity;
    }

    @Override
    public String toString() {
        return "Places [name=" + _name + ", vicinity="
                + _vincinity + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Places other = (Places) o;
        return true;
    }
}
