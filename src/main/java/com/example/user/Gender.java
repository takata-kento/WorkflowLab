package com.example.user;

public enum Gender {
    MAN(0),
    WOMAN(1),
    OTHER(2);

    private int genderId;

    private Gender(int genderId) {
        this.genderId = genderId;
    }

    int getId() {
        return genderId;
    }
}
