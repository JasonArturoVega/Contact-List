package Main;

import FileManagement.FileManager;
import Logic.ContactManager;
import Model.Contact;
import Ui.ContactListPanel;
import Ui.EnterContactPanel;
import Ui.MainWindow;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class MainExecution {

    static void main(String[] args) {

        ContactManager contactManager = new ContactManager();

        MainWindow window = new MainWindow(800, 600);
        ContactListPanel listPanel = new ContactListPanel();
        EnterContactPanel enterPanel = new EnterContactPanel(listPanel, contactManager);

        window.add(listPanel);
        window.add(enterPanel);
        window.setVisible(true);

        //Close window and save
        window.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                FileManager.SaveContacts(contactManager.getContactList());
            }
        });

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Load contacts if possible
        HashMap<String, Contact> loadedContacts = FileManager.LoadContacts();

        for(Contact contact: loadedContacts.values())
        {
            contactManager.addContact(contact.getNumber(), contact.getContactName(), contact.getEmail());
            listPanel.addContactToPanel(contact.getNumber(), contact.getContactName(), contact.getEmail());
        }
    }
}
