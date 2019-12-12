package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import hibernate.model.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;


public class JsonLoadTest {

    @Test
    public void Main() {
        System.out.println("Start");
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;

        try {

            // FACTORY NAME HAS TO MATCHED THE ONE FROM PERSISTED.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());


            //Manufacturer
            Manufacturer[] manArray = objectMapper.readValue(new File("manufacturers.json"), Manufacturer[].class);
            //System.out.println(manArray[0].getName());
            //entityManager.merge(manArray[0]);
            for(int i = 0; i < manArray.length; i++)  entityManager.merge(manArray[i]);

            //Model
            Model[] modelArray = objectMapper.readValue(new File("models.json"), Model[].class);
            for(Model m:modelArray)  entityManager.merge(m);

            //Car
            Car[] carArray = objectMapper.readValue(new File("cars.json"), Car[].class);
            for(Car c:carArray)  entityManager.merge(c);

            //License
            License[] licenseArray = objectMapper.readValue(new File("licenses.json"), License[].class);
            for(License l:licenseArray)  entityManager.merge(l);

            //Driver
            Driver[] driverArray = objectMapper.readValue(new File("drivers.json"), Driver[].class);
            for(Driver d:driverArray)  entityManager.merge(d);


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
}