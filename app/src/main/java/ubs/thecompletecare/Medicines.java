package ubs.thecompletecare;

/**
 * Created by ullas on 2/25/2017.
 */

public class Medicines {

    int days;
    boolean dosage[];
    String name;

    public Medicines(int days, boolean dosage[],String name)
    {

        this.days =days;
        this.dosage = dosage;
        this.name = name;

    }

    public int getDays()
    {
        return days;
    }
    public boolean[] getDosage()
    {
        return dosage;
    }
    public String getName()
    {
        return name;
    }
    public boolean morningDosage()
    {
        return dosage[0];
    }
    public boolean afternoonDosage()
    {
        return dosage[1];
    }
    public boolean nightDosage()
    {
        return dosage[2];
    }
}
