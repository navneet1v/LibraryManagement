/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagent;


import java.awt.*;
import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author Navneet pc
 */
public class ViewBooks extends JFrame {
    private JPanel topPanel;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button;
    
    public ViewBooks(){
        setTitle("Books in Library");
        setSize(400,200);
        setBackground(Color.GRAY);
        topPanel = new JPanel();
        button = new JButton();
        button.setText("Close");
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );
        getContentPane().add(button,BorderLayout.AFTER_LAST_LINE);
        
        String columnNames[] = {"Book ID","Book Name","Author","Publisher","Available","Shelf Number","Number Of Books"};
        
        Books bo = new Books();
        int numberOfBooks = bo.GetNumberOfBooks();
        String b[][] = new String[numberOfBooks][columnNames.length];
        bo.getAllBooksInformation(b);
        table = new JTable( b, columnNames );

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
//    {
//            // Create an instance of the test application
//            ViewBooks mainFrame = new ViewBooks();
//            mainFrame.setVisible( true );
//    }
}
