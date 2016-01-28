/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagent;

import javax.swing.JFrame;

/**
 *
 * @author Navneet pc
 */
public class LibraryManagent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Library l = new Library();
        l.setVisible(true);
        l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        l.setLocation(100, 250);
    }
    
}
