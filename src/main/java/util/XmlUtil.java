package util;

import model.Warehouse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlUtil {
    public static void writeToXmlFile(Warehouse warehouse, String nameOfFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Warehouse.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = new File(nameOfFile);
            marshaller.marshal(warehouse, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Warehouse readFromXmlFile(String nameOfFile) {
        try {
            File file = new File(nameOfFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Warehouse.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Warehouse) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new Warehouse();
    }
}
