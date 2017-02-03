package model;

import java.util.ArrayList;

/**
 * Created by Михаил on 03.02.2017.
 */
public class Manager extends Employee {

    private ArrayList<Employee> employees;

    public Manager(String name, int salary, ArrayList<Competence> competences, ArrayList<Employee> employees) {
        super(name, salary, competences);
        this.employees = employees;
    }
}
