package util;

import model.Warehouse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;


public class XmlUtil implements FileUtil{

    public void serealise(Warehouse warehouse, String nameOfFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Warehouse.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            File file = new File(nameOfFile);
            marshaller.marshal(warehouse, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Warehouse deserealise(String nameOfFile) {
        try {
            File file = new File(nameOfFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Warehouse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Warehouse) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
