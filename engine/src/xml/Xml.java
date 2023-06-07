//package schema;
//
//import schema.generated.AbsDescriptor;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//public class Xml {
//
//    private final static String JAXB_XML_GAME_PACKAGE_NAME = "schema.generated";
//
//    public void deserialization(String pathName) {
//        try {
//            InputStream inputStream = new FileInputStream(new File(pathName));
//            AbsDescriptor descriptor = deserializeFrom(inputStream);
//
//            //return deserializeFrom(inputStream);
//            System.out.println("name of first category is: " + descriptor.getAbsCategories().getAbsCategory().get(0));
//            System.out.println("name of first customer is: " + descriptor.getAbsCustomers().getAbsCustomer().get(0).getName());
//            System.out.println("name of first loaner is: " + descriptor.getAbsLoans().getAbsLoan().get(0).getAbsOwner());
//        } catch (JAXBException | FileNotFoundException e) {
//            //e.printStackTrace();
//            // return null;
//        }
//    }
//    private static AbsDescriptor deserializeFrom(InputStream in) throws JAXBException {
//        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
//        Unmarshaller u = jc.createUnmarshaller();
//        return (AbsDescriptor) u.unmarshal(in);
//    }
//}
package xml;

import schema.generated.AbsDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class Xml implements Serializable {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "schema.generated";
    private boolean checkYaz;
    private boolean checkDuplicateLoans;

    public Xml() {
        checkYaz = checkDuplicateLoans = false;
    }

    public AbsDescriptor deserialization(InputStream inputStream) {
        try {
            //InputStream inputStream = new FileInputStream(new File(pathName));
            AbsDescriptor descriptor = deserializeFrom(inputStream);
            //isCopySucceeded = true;

            return descriptor;

            //System.out.println("name of first country is: " + descriptor.getAbsCustomers().getAbsCustomer().get(0).getName());
        } catch (JAXBException e) {
            //e.printStackTrace();
            return null;
        }
    }
    private static AbsDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (AbsDescriptor) unmarshaller.unmarshal(in);
    }

    public boolean getCheckYaz() {
        return checkYaz;
    }

    public void setCheckYaz(boolean checkYaz) {
        this.checkYaz = checkYaz;
    }

    public boolean getCheckDuplicateLoans() {
        return checkDuplicateLoans;
    }

    public void setCheckDuplicateLoans(boolean checkDuplicateLoans) {
        this.checkDuplicateLoans = checkDuplicateLoans;
    }
}