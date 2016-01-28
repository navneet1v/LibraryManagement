/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Navneet pc
 */
public class Members {
    
    private final int MIN_BOOK_ISSUED = 2;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String ID;
    private String Name;
    private String BooksIssued;
    private int Fine;
    private String to,bookdate;
    public void setMemberID(String x){
        ID = x;
    }
    
    public void setMemberName(String x){
        Name = x;
    }
    
    public void setBooksIssued(String x){
        BooksIssued = x;
    }
    
    public void setFine(int x){
        Fine  = x;
    }
    
    public String getMemberID(){
        return ID;
    }
    
    public String getMemberName(){
        return Name;
    }
    
    public String getBooksIssued(){
        return BooksIssued;
    }
    
    public int getFine(){
        return Fine;
    }
    
    private void makeConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){
            System.out.println("Class Not Found");
        }
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jlibrary","root","navneet");
        }catch(Exception e){
             System.out.println("connection db Not Found");
        }
         
        try{
             stmt = con.createStatement();
        }catch(Exception e){
             System.out.println("Stmt Not Found");
        }
    }   
    
    public boolean addMember(){
        makeConnection();
        this.setFine(0);
        this.setBooksIssued("0");
        try{
            String sep = "','"; 
            //System.out.println(ID);
            String str1 = "Insert into member values ('"+ID+sep+Name+sep+BooksIssued+"', "+Fine+" )";
            stmt.executeUpdate(str1);
//            stmt = con.createStatement();
//            String str = "Select * from member";
//            rs = stmt.executeQuery(str);
//            while(rs.next()){
//                System.out.println(rs.getString("Name"));
//            }
            return true;
        }catch(Exception e){
            System.out.println("query Not Found");
            return false;
        }
    }

    boolean deleteMember() {
        makeConnection();
        String str = null;
        if((!this.getMemberID().isEmpty())&&(!this.getMemberName().isEmpty())){
            str = "DELETE from member where MemberID = '"+ID +"' AND MemberName='"+Name+"'";
        }
        else if(!this.getMemberID().isEmpty()){
            str = "DELETE from member where MemberID = '"+ID+"' ";
            System.out.println(ID);
        }else if(!this.getMemberName().isEmpty()){
            str = "DELETE from member where MemberName = '"+Name+"' ";
            return true;
        }else{            
            return false;
        }
        try{
            stmt.executeUpdate(str);
            return true;
        }catch(Exception e){
            System.out.println("Query failed");
        }
        return false;
    }

    public boolean canTakeBook() {
        makeConnection();
        String str = "Select * from member where MemberID = '"+ID+"'";
        
        int val=0;
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                String num = rs.getString("BooksIssued");                               
                val = Integer.parseInt(num);
            }
            if(val<MIN_BOOK_ISSUED)
            {
                val++;
                BooksIssued = Integer.toString(val);
                changeBookIssued();
                return true;
            }
            else
                return false;
        } catch (SQLException ex) {
            System.out.println("Query failed can take books");
            return false;
            //Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void changeBookIssued(){
        makeConnection();
        String str = "Update "
                + "member "
                + "set BooksIssued = '"+BooksIssued +"' where MemberID = '"+ID+"'";
        try {
            stmt.executeUpdate(str);
        } catch (SQLException ex) {
            System.out.println("Query failed update ");
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean deleteRecord(Books b){        
        decBookIssued();
        boolean fine = chargeFine(b);
        deleteBorrow(b);
        return fine;
    }

    private void deleteBorrow(Books b) {
        makeConnection();
        String str = "Delete From borrow where BookID ='"+b.getBookID()+"' AND MemberID = '"+ID+"'";
        try {
            stmt.executeUpdate(str);
        } catch (SQLException ex) {
            System.out.println("Delete from borrow failed");
            //Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void decBookIssued() {
        makeConnection();
        String str = "Select * from member where MemberID='" + ID + "'";
        int val=0;
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                val = Integer.parseInt(rs.getString("BooksIssued"));
                Fine = rs.getInt("Fine");                
            }
            val--;
            BooksIssued = Integer.toString(val);
            changeBookIssued();
        } catch (SQLException ex) {
            System.out.println("Selection from change book Failed failed");
//            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean chargeFine(Books b) {
        getReturnDate(b);
//        if(bookdate.equals(to)){
//            return false;
//        }else{
            Fine += 0;
            String s = "Update member set Fine="+Fine+" where MemberID='"+ID+"'";
            try {
                stmt.executeUpdate(s);
            } catch (SQLException ex) {
                System.out.println("Fine is not updated");
//                Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        //}
    }

    private void getReturnDate(Books b) {
        java.util.Date today = new java.util.Date(); 
        to = toddMMyy(today);
        String str = "Select * from borrow where BookID=' "+b.getBookID()+"'AND MemberID='" + ID + "'";
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                bookdate = rs.getString("ReturnDate");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private  String toddMMyy(java.util.Date day){ 
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy"); 
       String date = formatter.format(day);
       return date; 
   }
    
    public int GetTotalNumberOfMembers(){
        int i=0;
        makeConnection();
        String str = "Select * from member";
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("Get Member query");
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public void GetMembers(String [][] mem){
        int i=0;
        makeConnection();
        String str = "Select * from member order by MemberID";
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                mem[i][0] = rs.getString(1);
                mem[i][1] = rs.getString(2);
                mem[i][2] = rs.getString(3);
                int f = rs.getInt("Fine");
                mem[i][3] = Integer.toString(f);
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("Get Member query");
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
