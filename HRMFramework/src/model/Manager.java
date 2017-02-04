package model;

import java.util.ArrayList;

/**
 * Created by Михаил on 03.02.2017.
 */
public class Manager extends Employee {

    ArrayList<Employee> employees = new ArrayList<>();

    public Manager(String name, int salary) {
        super(name, salary);
    }

    public void addEmployeeToList(Employee employee) {
        employees.add(employee);
    }

    public int getNumOfEmployees() {
        return employees.size();
    }

    public String managerToString() {

    }
}
