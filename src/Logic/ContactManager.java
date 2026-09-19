package Logic;

import Model.Contact;

import java.util.HashMap;

public class ContactManager {

    //A HashMap is an array that have a key and a value
    private HashMap<String, Contact> contactList = new HashMap<>();

    public Boolean addContact(String contactNumber, String contactName, String contactEmail)
    {
        //You can NOT add an empty number
        if(contactNumber == null || contactNumber.isBlank())
            return false;

        //You can NOT add a contact that already exists
        if(contactList.containsKey(contactNumber))
            return false;

        //Create new contact
        Contact newContact = new Contact(contactNumber, contactName, contactEmail);

        //Add contact to the list
        contactList.put(contactNumber, newContact);
        return true;
    }

    public void removeContact(String contactNumber)
    {
        contactList.remove(contactNumber);
    }

    public void editContact(String contactNumber, String contactName, String contactEmail)
    {
        Contact contactToEdit = contactList.get(contactNumber);

        if(contactToEdit == null)
            return;

        contactToEdit.setName(contactName);
        contactToEdit.setEmail(contactEmail);
    }

    public HashMap<String, Contact> getContactList() {return contactList;}

    public String findNumberByContactName(String nameToSearch)
    {
        //Transform text to lower case and remove the empty spaces
        String searchName = nameToSearch.trim().toLowerCase();

        //Search in all contact list
        for(Contact contact : contactList.values())
        {
            //Return the contact number if it finds the name
            if(contact.getContactName().toLowerCase().contains(searchName))
                return contact.getNumber();
        }

        return null;
    }
}
