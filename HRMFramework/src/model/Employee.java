package model;

import java.util.ArrayList;

/**
 * Created by Михаил on 03.02.2017.
 */
public class Employee {

    private String name;
    private int salary;
    ArrayList<Competence> competences = new ArrayList<>();

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
}
