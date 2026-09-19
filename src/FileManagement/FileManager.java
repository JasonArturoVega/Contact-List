package FileManagement;

import Logic.ContactManager;
import Model.Contact;

import java.io.*;
import java.util.HashMap;

public class FileManager {

    private final static String FILE_NAME = "Contacts.txt";

    public static void SaveContacts(HashMap<String, Contact> listOfContacts)
    {
        //Tries to write the file
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME)))
        {
            //Go for each contact in the list
            for (Contact contact: listOfContacts.values())
            {
                //Save contacts in lines
                String line = contact.getNumber() + "|" + contact.getContactName() + "|" + contact.getEmail();
                writer.write(line);
                writer.newLine();
            }
        }
        catch (IOException exception)
        {
            System.out.println("Error while saving contacts: " + exception.getMessage());
        }
    }

    public static HashMap<String, Contact> LoadContacts()
    {
        //Load file
        HashMap<String, Contact> newListOfContacts = new HashMap<>();
        File loadedFile = new File(FILE_NAME);

        if(!loadedFile.exists())
            return newListOfContacts;

        //Tries to read the file
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                //Split the line in parts
                String[] lineParts = line.split("\\|");

                if(lineParts.length < 3) continue;

                String contactNumber = lineParts[0];
                String contactName = lineParts[1];
                String contactEmail = lineParts[2];

                //Reconstruct contacts
                Contact loadedContact = new Contact(contactNumber, contactName, contactEmail);
                newListOfContacts.put(contactNumber, loadedContact);
            }
        }
        catch (IOException exception)
        {
            System.out.println("Error loading the file: " + exception.getMessage());
        }

        return newListOfContacts;
    }
}
