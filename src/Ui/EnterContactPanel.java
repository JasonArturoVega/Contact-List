package Ui;

import Logic.ContactManager;

import javax.swing.*;
import java.awt.*;

public class EnterContactPanel extends JPanel {

    public EnterContactPanel(ContactListPanel contactListPanel, ContactManager contactManager)
    {
        //Save other classes
        this.contactListPanel = contactListPanel;
        this.contactManager = contactManager;

        //Create panel
        setBounds(40, 10, 700, 165);
        setLayout(null);

        //Font for the texts
        textFont = new Font("Arial", Font.BOLD, 15);

        //Place text
        infoNumber.setFont(textFont);
        infoNumber.setBounds(10, 10, 170, 30);
        infoNumber.setFocusable(false);
        infoNumber.setText("Enter Number");

        numberContactText.setText("");
        numberContactText.setFont(textFont);
        numberContactText.setBounds(200, 10, 200, 30);

        infoName.setFont(textFont);
        infoName.setBounds(10, 50, 170, 30);
        infoName.setFocusable(false);
        infoName.setText("Enter Name");

        nameContactText.setText("");
        nameContactText.setFont(textFont);
        nameContactText.setBounds(200, 50, 280, 30);

        infoEmail.setFont(textFont);
        infoEmail.setBounds(10, 90, 170, 30);
        infoEmail.setFocusable(false);
        infoEmail.setText("Enter Email");

        emailContactText.setText("");
        emailContactText.setFont(textFont);
        emailContactText.setBounds(200, 90, 280, 30);

        //Place button
        confirmContactButton.setFont(textFont);
        confirmContactButton.setBounds(510, 10, 170, 30);
        confirmContactButton.setBackground(new Color(128, 255, 135));
        confirmContactButton.setText("Add Contact");
        confirmContactButton.addActionListener(e -> ClickToAddContact());

        editContactButton.setFont(textFont);
        editContactButton.setBounds(510, 50, 170, 30);
        editContactButton.setBackground(new Color(247, 255, 128));
        editContactButton.setText("Edit Contact");
        editContactButton.addActionListener(e -> ClickToEdit());

        eraseContactButton.setFont(textFont);
        eraseContactButton.setBounds(510, 90, 170, 30);
        eraseContactButton.setBackground(new Color(255, 128, 128));
        eraseContactButton.setText("Delete Contact");
        eraseContactButton.addActionListener(e -> ClickToRemoveContact());

        enterNameSearchText.setFont(textFont);
        enterNameSearchText.setText("");
        enterNameSearchText.setBounds(200, 130, 280, 30);

        searchButton.setFont(textFont);
        searchButton.setBounds(510, 130, 170, 30);
        searchButton.setBackground(new Color(139, 190, 255));
        searchButton.setText("Search name");
        searchButton.addActionListener(e -> ClickToSearchByName());

        //Add Ui to the panel
        add(numberContactText);
        add(nameContactText);
        add(emailContactText);

        add(infoNumber);
        add(infoName);
        add(infoEmail);

        add(confirmContactButton);
        add(editContactButton);
        add(eraseContactButton);

        add(enterNameSearchText);
        add(searchButton);
    }

    private ContactListPanel contactListPanel;
    private ContactManager contactManager;

    private JButton confirmContactButton = new JButton();
    private JButton editContactButton = new JButton();
    private JButton eraseContactButton = new JButton();

    private TextField infoNumber = new TextField();
    private TextField numberContactText = new TextField();

    private TextField infoName = new TextField();
    private TextField nameContactText = new TextField();

    private TextField infoEmail = new TextField();
    private TextField emailContactText = new TextField();

    private JButton searchButton = new JButton();
    private TextField enterNameSearchText = new TextField();

    private Font textFont;
    private int editingRow = -1;

    public void ClickToAddContact()
    {
        //Save info from the texts
        String numberFromText = numberContactText.getText();
        String nameFromText = nameContactText.getText();
        String emailFromText = emailContactText.getText();

        //You need name and number at minimum
        if(numberFromText.isEmpty())
            return;
        if(nameFromText.isEmpty())
            return;

        //Adding a new contact
        if(editingRow == -1)
        {
            //Check for a duplicate number in the contact list
            boolean contactAdded = contactManager.addContact(numberFromText, nameFromText, emailFromText);

            //Not modify the table if the number is already added
            if(!contactAdded)
            {
                JOptionPane.showMessageDialog(this, "This number already exists");
                return;
            }


            contactListPanel.addContactToPanel(numberFromText, nameFromText, emailFromText);
        }

        //Editing a contact
        else
        {
            contactManager.editContact(numberFromText, nameFromText, emailFromText);
            contactListPanel.editContactInPanel(editingRow, numberFromText, nameFromText, emailFromText);

            //Stop editing
            editingRow = -1;
            confirmContactButton.setText("Add Contact");
            numberContactText.setEditable(true);
        }

        //Cleaning texts
        numberContactText.setText("");
        nameContactText.setText("");
        emailContactText.setText("");
    }

    public void ClickToRemoveContact()
    {
        JTable table = contactListPanel.getTable();
        int selectedRow = table.getSelectedRow();

        //Remove ONLY if one is selected
        if(selectedRow == -1)
            return;

        //Get selected contact
        String contactSelected = table.getValueAt(selectedRow, 0).toString();

        //Erase
        contactManager.removeContact(contactSelected);
        contactListPanel.removeContactFromPanel(selectedRow);
    }

    public void ClickToEdit()
    {
        JTable table = contactListPanel.getTable();
        int selectedRow = table.getSelectedRow();

        //Edit ONLY if one is selected
        if(selectedRow == -1)
            return;

        //Save editing row
        editingRow = selectedRow;

        //Get data from the contact and put it on screen
        numberContactText.setText(table.getValueAt(selectedRow, 0).toString());
        nameContactText.setText(table.getValueAt(selectedRow, 1).toString());
        emailContactText.setText(table.getValueAt(selectedRow, 2).toString());

        //You can NOT edit the number
        numberContactText.setEditable(false);

        //Change button to edit
        confirmContactButton.setText("Save changes");
    }

    public void ClickToSearchByName()
    {
        //Only search if have a valid text
        String nameToSearch = enterNameSearchText.getText();

        if(nameToSearch.isEmpty())
            return;

        String numberFound = contactManager.findNumberByContactName(nameToSearch);

        if(numberFound == null)
        {
            JOptionPane.showMessageDialog(this,"Contact not found");
            return;
        }

        //Search in the table for the correct contact
        JTable table = contactListPanel.getTable();

        for(int i = 0; i < table.getRowCount(); i++)
        {
            //Get the number in the row that is searching
            String numberInRow = table.getValueAt(i, 0).toString();

            if(numberInRow.equals(numberFound))
            {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i,0,true));
                return;
            }
        }
    }
}
