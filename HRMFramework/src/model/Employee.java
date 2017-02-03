package model;

import java.util.ArrayList;

/**
 * Created by Михаил on 03.02.2017.
 */
public class Employee {

    private String name;
    private int salary;
    private ArrayList<Competence> competences;

    public Employee(String name, int salary, ArrayList<Competence> competences) {
        this.name = name;
        this.salary = salary;
        this.competences = competences;
    }
}
