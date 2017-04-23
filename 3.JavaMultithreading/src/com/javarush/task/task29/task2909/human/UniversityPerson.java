package com.javarush.task.task29.task2909.human;

/**
 * Created by Пользователь on 31.03.2017.
 */
public class UniversityPerson extends Human {

    private University university;

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public void live() {
        super.live();
    }

    public UniversityPerson(String name, int age) {
        super(name, age);
        //this.university = university;
    }
}
