package hibernate.model;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "CAR")//, uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"brand_name"})})
public class Car {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    private Model model;



    public Car() {}

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    public Model getModel() {
        return model;
    }
}