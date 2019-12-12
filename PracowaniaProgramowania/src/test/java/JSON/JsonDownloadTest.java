package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import hibernate.model.*;
import hibernate.queries.Queries;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.List;


public class JsonDownloadTest {

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


            //Manufacturers
            List <Manufacturer> manufacturerList = new Queries(entityManager).getManPag();//Queries(entityManager).getAllManufacturer();
            objectMapper.writeValue(new File("manufacturers2.json"), manufacturerList);
            //Models
            List <Model> modelList = new Queries(entityManager).getAllModel();
            objectMapper.writeValue(new File("models2.json"), modelList);
            //Cars
            List <Car> carList = new Queries(entityManager).getAllCar();
            objectMapper.writeValue(new File("cars2.json"), modelList);
            //License
            List <License> licenseList = new Queries(entityManager).getAllLicense();
            objectMapper.writeValue(new File("licenses2.json"), licenseList);
            //Driver
            List <Driver> driverList = new Queries(entityManager).getAllDriver();
            objectMapper.writeValue(new File("drivers2.json"), driverList);

            //Queries
            List<Model> models = new Queries(entityManager).getModelByName("318i");
            Model model3 = new Model();
            model3.setManufacturer(models.get(0).getManufacturer());
            model3.setWeight(models.get(0).getWeight());
            model3.setType(models.get(0).getType());
            model3.setName("M3-CSL");
            model3.setHp(360);
            entityManager.persist(model3);

            //Queries
            for (Manufacturer m:manufacturerList){
                List <String> modelsof = new Queries(entityManager).getModelsOfManufacturer(m.getId());
                System.out.println(m.getName()+" tiene los siguientes modelos:");
                for (String i:modelsof){
                    System.out.println(i);
                }
            }



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