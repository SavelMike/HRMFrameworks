package model;

/**
 * Created by Михаил on 03.02.2017.
 */
public class Competence {

    private int competenceLevel;
    private String competenceName;

    public Competence(int competenceLevel, String competenceName) {
        this.competenceLevel = competenceLevel;
        this.competenceName = competenceName;
    }

    public int getCompetenceLevel() {
        return competenceLevel;
    }

    public String getCompetenceName() {
        return competenceName;
    }

    public String toString(Competence c) {
        return competenceName + " (" + competenceLevel + ")";
    }
}
