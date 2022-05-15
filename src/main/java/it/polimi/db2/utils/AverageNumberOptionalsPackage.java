package it.polimi.db2.utils;

import javax.persistence.*;

@Entity
@Table(name = "averagenumoptionalsperpackage", schema = "db2_project")
@NamedQuery(name = "ANOP", query = "SELECT new it.polimi.db2.utils.AverageNumberOptionalsPackage(b.title, av.averageNumOptionals) FROM AverageNumberOptionalsPackage av JOIN BundleEntity b on av.bundleId = b.id")
public class AverageNumberOptionalsPackage {
    @Id
    @Column(name = "bundleId", nullable = false)
    private int bundleId;

    private String title;

    @Basic
    @Column(name = "averageNumOptionals")
    private float averageNumOptionals;

    public AverageNumberOptionalsPackage(String title, float average) {
        this.title = title;
        this.averageNumOptionals = average;
    }

    public AverageNumberOptionalsPackage() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getAverage() {
        return averageNumOptionals;
    }

    public void setAverage(int averageNumOptionals) {
        this.averageNumOptionals = averageNumOptionals;
    }

    @Override
    public String toString() {
        return "AverageNumberOptionalsPackage{" +
                "title='" + title + '\'' +
                ", averageNumOptionals=" + averageNumOptionals +
                '}';
    }
}
