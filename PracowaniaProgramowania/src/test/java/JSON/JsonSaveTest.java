package JSON;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import hibernate.model.*;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class JsonSaveTest {

    @Test
    public void Main() {

        System.out.println("Start");
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;

        try {

            // FACTORY NAME HAS TO MATCHED THE ONE FROM PERSISTED.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();


            //MANUFACTURER
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


            //MODEL
            Model model1 = new Model();
            model1.setName("206");
            model1.setHp(138);
            model1.setType("hatchback");
            model1.setWeight(1000);
            model1.setManufacturer(brand2);
            entityManager.persist(model1);

            Model model2 = new Model();
            model2.setName("318i");
            model2.setHp(118);
            model2.setType("sedan");
            model2.setWeight(1350);
            model2.setManufacturer(brand1);
            entityManager.persist(model2);


            //CAR
            Car car1 = new Car();
            car1.setYear(2002);
            car1.setModel(model1);
            entityManager.persist(car1);

            Car car2 = new Car();
            car2.setYear(1998);
            car2.setModel(model2);
            entityManager.persist(car2);;


            //LICENSE
            License license1 = new License();
            license1.setNumber(28708847);
            license1.setExpedition(1965);
            license1.setType("truck-B");
            entityManager.persist(license1);

            License license2 = new License();
            license2.setNumber(35623440);
            license2.setExpedition(2012);
            license2.setType("motorcycle-A1");
            entityManager.persist(license2);


            //DRIVER
            Driver driver1 = new Driver();
            driver1.setName("Pepe");
            driver1.setBirth(new DateTime(1965,10,25,0,0,0,0));
            driver1.setCar(car2);
            driver1.setLicense(license1);
            entityManager.persist(driver1);

            Driver driver2 = new Driver();
            driver2.setName("Alberto");
            driver2.setBirth(new DateTime(1997,10,25,0,0,0,0));
            driver2.setCar(car1);
            driver2.setLicense(license2);
            entityManager.persist(driver2);


            //SAVE TO JSON FILES
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());

            List <Manufacturer> manufacturerList = new ArrayList<Manufacturer>();
            manufacturerList.add(brand1);
            manufacturerList.add(brand2);
            objectMapper.writeValue(new File("manufacturers.json"), manufacturerList);

            List <Model> modelList = new ArrayList<Model>();
            modelList.add(model1);
            modelList.add(model2);
            objectMapper.writeValue(new File("models.json"), modelList);

            List <Car> carList = new ArrayList<Car>();
            carList.add(car1);
            carList.add(car2);
            objectMapper.writeValue(new File("cars.json"), carList);

            List <Driver> driverList = new ArrayList<Driver>();
            driverList.add(driver1);
            driverList.add(driver2);
            objectMapper.writeValue(new File("drivers.json"), driverList);

            List <License> licenseList = new ArrayList<License>();
            licenseList.add(license1);
            licenseList.add(license2);
            objectMapper.writeValue(new File("licenses.json"), licenseList);



            System.out.println("Done");

            entityManager.close();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
        } finally {
            entityManagerFactory.close();
        }

    }
}


