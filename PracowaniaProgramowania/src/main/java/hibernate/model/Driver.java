package hibernate.model;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "DRIVER")//, uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"brand_name"})})
public class Driver {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "driver_name", nullable = false)//, unique = true)
    private String name;

    @Column(name = "birth")
    private ZonedDateTime birth;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="CAR_id", referencedColumnName = "id")
    private Car car;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="LICENSE_id", referencedColumnName = "id")
    private License license;



    public Driver() {}

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setBirth(ZonedDateTime birth) {
        this.birth = birth;
    }
    public ZonedDateTime getBirth() {
        return birth;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }

    public void setLicense(License license) {
        this.license = license;
    }
    public License getLicense() {
        return license;
    }
}