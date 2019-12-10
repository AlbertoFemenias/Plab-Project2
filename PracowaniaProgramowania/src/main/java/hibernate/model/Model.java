package hibernate.model;

import javax.persistence.*;


@Entity
@Table(name = "MODEL")//, uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"brand_name"})})
public class Model {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manufacturer manufacturer;

    @Column(name = "model_name", nullable = false)//, unique = true)
    private String name;

    @Column(name = "type") //cabrio, coupe, wagon
    private String type;

    @Column(name = "hp")
    private int hp;

    @Column(name = "weight")
    private int weight;


    public Model() {}

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

    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getHp() {
        return hp;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Model Clone() {
        return this;
    }
}