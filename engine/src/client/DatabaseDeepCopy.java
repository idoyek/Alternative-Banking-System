package client;

import java.io.*;

public class DatabaseDeepCopy {
    public static final String FILE_NAME = "database.dat";

    public void writePersonsToFile(Database database) throws IOException {
        //Construct an ArrayList of persons:
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {
            out.writeObject(database);
            out.flush();
        }
    }

    public Database readPersonsFromFile() throws IOException, ClassNotFoundException {
        // Read the array list  from the file
        Database databaseFromFile;
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(FILE_NAME))) {
            // we know that we read array list of Persons
            databaseFromFile = (Database) in.readObject();
        }
        System.out.println(databaseFromFile.getClientsMap());
        System.out.println(databaseFromFile.getLoansMap());
        return databaseFromFile;
    }

}
