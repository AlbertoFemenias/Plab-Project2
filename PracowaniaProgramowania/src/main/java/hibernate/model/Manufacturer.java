package hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Entity
@Table(name = "MANUFACTURER")//, uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"brand_name"})})
public class Manufacturer {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "brand_name", nullable = false)//, unique = true)
    private String name;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private List<Model> models = new ArrayList<Model>();

    @Column(name = "country")
    private String country;

    @Column(name = "year")
    private int year;


    public Manufacturer() {}

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void addModel(Model m){
        this.models.add(m);
    }

}