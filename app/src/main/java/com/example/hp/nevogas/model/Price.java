package com.example.hp.nevogas.model;

/**
 * Created by HP on 3/22/2019.
 */

public class Price {
    String kg;
    int price;

    public Price(int price,String kg) {
        this.price = price;
        this.kg = kg;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
