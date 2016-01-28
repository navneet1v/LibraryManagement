/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagent;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Navneet pc
 */
public class ViewMembers extends JFrame {
    
    private JPanel topPanel;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button;
    public ViewMembers()
    {
        setTitle( "Members Of Library" );
        setSize( 400, 200 );
        setBackground( Color.gray );

        // Create a panel to hold all other components
        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        button = new JButton();
        button.setText("Close");
        button.setSize(40, 50);
        getContentPane().add( topPanel );

        // Create columns names
        String columnNames[] = { "Member ID", "Member Name", "Books Issued","Fine" };
        Members m = new Members();
        int numberOfMembers = m.GetTotalNumberOfMembers();
        // Get some data
        String mem[][] = new String[numberOfMembers][4];
        m.GetMembers(mem);

        // Create a new table instance
        table = new JTable( mem, columnNames );

        // Add the table to a scrolling pane
        scrollPane = new JScrollPane( table );
        topPanel.add( scrollPane, BorderLayout.CENTER );
        getContentPane().add(button,BorderLayout.AFTER_LAST_LINE);
        // Adding the Action Listener
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
    } 
    
    private void buttonActionPerformed(java.awt.event.ActionEvent evt){
        this.setVisible(false);
    }
    
//    public static void main( String args[] )
//	{
//		// Create an instance of the test application
//		ViewMembers mainFrame = new ViewMembers();
//		mainFrame.setVisible( true );
//                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
