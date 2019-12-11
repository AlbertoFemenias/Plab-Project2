package hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import hibernate.model.*;
import hibernate.queries.Queries;

import javax.persistence.*;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* RELATIONS
Brand
Model   - one to many with Brand
Car     - one to many with Model
Driver  - one to one  with Car and Driver
License -
 */





class Manager {

    public static void main(String[] args) {

        System.out.println("Start");

        EntityManager entityManager = null;

        EntityManagerFactory entityManagerFactory = null;

        try {

            // FACTORY NAME HAS TO MATCHED THE ONE FROM PERSISTED.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");

            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Manufacturer brand1 = new Manufacturer();
            brand1.setName("BMW");
            brand1.setCountry("Germany");
            brand1.setYear(1914);
            entityManager.persist(brand1);

            Manufacturer brand2 = new Manufacturer();
            brand2.setName("Peugeot");
            brand2.setCountry("France");
            brand2.setYear(1905);
            entityManager.persist(brand2);



            /*ObjectMapper objectMapper = new ObjectMapper();
            Manufacturer brand1 = objectMapper.readValue(new File("brand1.json"), Manufacturer.class);
            System.out.println(brand1.getName());
            brand1.setName("Audi");
            //brand2.setYear(1920);
            entityManager.merge(brand1);*/




            Model model1 = new Model();
            model1.setName("328i");
            model1.setHp(198);
            model1.setType("coupe");
            model1.setWeight(1400);
            model1.setManufacturer(brand1);
            entityManager.persist(model1);

            brand1.addModel(model1);

            Car car1 = new Car();
            car1.setYear(2002);
            car1.setModel(model1);
            entityManager.persist(car1);;

            Driver driver1 = new Driver();
            driver1.setName("Pepe");
            //driver1.setBirth(ZonedDateTime.of(1985,10,25,0,0,0,0, ZoneId.of("UTC")));
            driver1.setCar(car1);
            entityManager.persist(driver1);

            License license1 = new License();
            license1.setNumber(35623440);
            license1.setExpedition(2012);
            license1.setType("truck-B");
            entityManager.persist(license1);

            driver1.setLicense(license1);


            List<Model> models = new Queries(entityManager).getModelByName("328i");
            Model model2 = new Model();
            model2.setManufacturer(models.get(0).getManufacturer());
            model2.setWeight(models.get(0).getWeight());
            model2.setType(models.get(0).getType());
            model2.setName("M3-CSL");
            model2.setHp(360);
            entityManager.persist(model2);


/*
            Employee emp = new Employee();
            emp.setFirstName("Jan");
            emp.setLastName("Polak" + new Random().nextInt());
            emp.setSalary(100);
            emp.setPesel(new Random().nextInt());

            entityManager.persist(emp);

            Employee employee = entityManager.find(Employee.class, emp.getId());
            if (employee == null) {
                System.out.println(emp.getId() + " not found! ");
            } else {
                System.out.println("Found " + employee);
            }

            System.out.println("Employee " + employee.getId() + " " + employee.getFirstName() + employee.getLastName());

            //entityManager.remove(emp);
            //changeFirstGuyToNowak(entityManager);
*/

            List <Manufacturer> manufacturerList = new ArrayList<Manufacturer>();
            manufacturerList.add(brand1);
            manufacturerList.add(brand2);


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("manufacturer.json"), manufacturerList);

            entityManager.getTransaction().commit();

            System.out.println("Done");

            entityManager.close();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
        } finally {
            entityManagerFactory.close();
        }

    }

    static void changeFirstGuyToNowak(EntityManager entityManager) {

        List<Employee> employees = new Queries(entityManager).getEmployeeByName("Polak");

        employees.get(0).setLastName("NowakPRE" + new Random().nextInt());

    }


}