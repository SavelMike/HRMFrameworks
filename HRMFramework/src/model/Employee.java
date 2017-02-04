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

    public String getName() {
        return name;
    }

    public void addCompitanceToList(Competence c) {
        competences.add(c);
    }

    public String competenceToString(int index) {
        String rc = name + " (" + index + ")";
        if (competences.size() == 0) {
            rc += "has not competence";
        } else {
            for (int i = 0; i < competences.size(); i++) {
                if (i != 0) {
                    rc += ", ";
                } else {
                    rc += " has competences: ";
                }
                rc += (competences.get(i).toString());
            }
        }
        return rc;
    }

    public String employeeToString(int index) {
        String rc = name + " (" + index + ")";
        rc += " with salary " + salary;
        return rc;
    }
}
