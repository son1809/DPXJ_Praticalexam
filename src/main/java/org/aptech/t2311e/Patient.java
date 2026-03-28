package org.aptech.t2311e;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String name;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender;
    private LocalDate birthDate;

    public Patient() {}

    public Patient(int id, String name, int weight, float height,
                   char bloodType, boolean gender, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public int getWeight() { return weight; }
    public float getHeight() { return height; }
    public char getBloodType() { return bloodType; }
    public boolean isGender() { return gender; }
    public LocalDate getBirthDate() { return birthDate; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + weight + "kg | "
                + height + "m | " + bloodType + " | "
                + (gender ? "Nam" : "Nữ") + " | " + birthDate;
    }
}
