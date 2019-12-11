package hibernate.queries;

import hibernate.model.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Queries {

    EntityManager entityManager;

    public Queries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Employee> getEmployeeByName(String name) {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT c FROM Employee c WHERE c.lastName LIKE :name", Employee.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    }

    public List<Model> getModelByName(String name) {
        TypedQuery<Model> query = entityManager.createQuery(
                "SELECT c FROM Model c WHERE c.name LIKE :name", Model.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    }

    public List<Manufacturer> getAllManufacturer() {
        TypedQuery<Manufacturer> query = entityManager.createQuery(
                "SELECT m FROM Manufacturer m", Manufacturer.class);
        return query.getResultList();
    }
    public List<Model> getAllModel() {
        TypedQuery<Model> query = entityManager.createQuery(
                "SELECT m FROM Model m", Model.class);
        return query.getResultList();
    }
    public List<Car> getAllCar() {
        TypedQuery<Car> query = entityManager.createQuery(
                "SELECT c FROM Car c", Car.class);
        return query.getResultList();
    }
    public List<License> getAllLicense() {
        TypedQuery<License> query = entityManager.createQuery(
                "SELECT l FROM License l", License.class);
        return query.getResultList();
    }
    public List<Driver> getAllDriver() {
        TypedQuery<Driver> query = entityManager.createQuery(
                "SELECT d FROM Driver d", Driver.class);
        return query.getResultList();
    }


}
