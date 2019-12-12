package XML;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import hibernate.model.*;
import hibernate.queries.Queries;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.List;


public class XMLDownloadTest {

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

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JodaModule());


            //Manufacturers
            List <Manufacturer> manufacturerList = new Queries(entityManager).getManPag();//Queries(entityManager).getAllManufacturer();
            xmlMapper.writeValue(new File("manufacturers2.xml"), manufacturerList);
            //Models
            List <Model> modelList = new Queries(entityManager).getAllModel();
            xmlMapper.writeValue(new File("models2.xml"), modelList);
            //Cars
            List <Car> carList = new Queries(entityManager).getAllCar();
            xmlMapper.writeValue(new File("cars2.xml"), modelList);
            //License
            List <License> licenseList = new Queries(entityManager).getAllLicense();
            xmlMapper.writeValue(new File("licenses2.xml"), licenseList);
            //Driver
            List <Driver> driverList = new Queries(entityManager).getAllDriver();
            xmlMapper.writeValue(new File("drivers2.xml"), driverList);

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