package XML;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import hibernate.model.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.ArrayList;


public class XMLLoadTest {

    @Test
    public void Main() {

        System.out.println("Start");

        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JodaModule());

        try {

            // FACTORY NAME HAS TO MATCHED THE ONE FROM PERSISTED.XML !!!
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            File file = null;
            String xml = null;

            //Manufacturer
            file = new File("manufacturers.xml");
            xml = XMLinputStreamToString(new FileInputStream(file));
            ArrayList<Manufacturer> manufacturers = xmlMapper.readValue(xml, new TypeReference<ArrayList<Manufacturer>>() {} );
            for(Manufacturer m : manufacturers) entityManager.merge(m);

            //Model
            file = new File("models.xml");
            xml = XMLinputStreamToString(new FileInputStream(file));
            ArrayList<Model> models = xmlMapper.readValue(xml, new TypeReference<ArrayList<Model>>() {} );
            for(Model m : models) entityManager.merge(m);

            //Car
            file = new File("cars.xml");
            xml = XMLinputStreamToString(new FileInputStream(file));
            ArrayList<Car> cars = xmlMapper.readValue(xml, new TypeReference<ArrayList<Car>>() {} );
            for(Car c : cars) entityManager.merge(c);

            //License
            file = new File("licenses.xml");
            xml = XMLinputStreamToString(new FileInputStream(file));
            ArrayList<License> licenses = xmlMapper.readValue(xml, new TypeReference<ArrayList<License>>() {} );
            for(License l : licenses) entityManager.merge(l);

            //Driver
            file = new File("drivers.xml");
            xml = XMLinputStreamToString(new FileInputStream(file));
            ArrayList<Driver> drivers = xmlMapper.readValue(xml, new TypeReference<ArrayList<Driver>>() {} );
            for(Driver d : drivers) entityManager.merge(d);


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

    private static String XMLinputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}