package Ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ContactListPanel extends JScrollPane {

    public ContactListPanel()
    {
        //The titles on each column
        String[] columns = new String[]{"Number", "Name", "Email"};

        //Create table and model
        tableModel = new DefaultTableModel(columns, 0)
        {
            //The user can NOT edit the info on the table itself
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        //The table goes inside the scroll
        setViewportView(table);

        setBounds(40, 180, 700, 370);
    }

    private final JTable table;
    private final DefaultTableModel tableModel;

    public void addContactToPanel(String contactNumber, String contactName, String contactEmail)
    {
        tableModel.addRow(new Object[]{contactNumber, contactName, contactEmail});
    }

    public void removeContactFromPanel(int contactToRemoveIndex)
    {
        tableModel.removeRow(contactToRemoveIndex);
    }

    public void editContactInPanel(int editingRow, String numberEdited, String nameEdited, String emailEdited)
    {
        tableModel.setValueAt(numberEdited, editingRow, 0);
        tableModel.setValueAt(nameEdited, editingRow, 1);
        tableModel.setValueAt(emailEdited, editingRow, 2);
    }

    public JTable getTable() {return table;}
}
