package hibernate.queries;

import hibernate.model.Employee;
import hibernate.model.Model;

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
}
