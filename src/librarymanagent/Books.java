/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagent;

/**
 *
 * @author Navneet pc
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Books {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private String ID;	
        private String Name;
	private String author;
	private String publisher;	
	private String numberOfCopies;
        private String shelfNumber;
        private String a;
        String IssueDate=null,ReturnDate=null;
	private boolean available;

	public Books() {
	}

	public String getBookID() {
            return ID;
	}
        
        public String getBookName() {
            return Name;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}


	public String getNumberOfCopies() {
		return numberOfCopies;
	}
        
        public String getShelfNumber() {
		return shelfNumber;
	}
	
	public boolean getAvailable() {
            if(a.equals("1")) available = true;
            else available = false;
            
            return available;
	}
        
        public void setBookID(String b) {
            ID = b;
	}
        
        public void setBookName(String b) {
            Name = b;
	}

	public void setAuthor(String b) {
		author=b;
	}

	public void setPublisher(String b) {
		publisher=b;
	}


	public void setNumberOfCopies(String b) {
		numberOfCopies = b;
	}
	
	public void setAvailable(boolean b) {
		 available = b;
                 if(b)
                     a="1";
                 else
                     a="0";
	}
        
        public void setSelfNumber(String b){
            shelfNumber = b;

        }
     
    private void makeConnection() {
        try{
            Class.forName("com.mysql.jdbc.Dri0ver");
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
        
    public boolean addBook(){
        
        makeConnection(); 
         try{
            String sep = "','"; 
            //System.out.println(ID);
            String str1 = "Insert into books values ('"+ID+sep+Name+sep+author+sep+publisher+sep+a+sep+shelfNumber+sep+numberOfCopies+"')";
            stmt.executeUpdate(str1);
            //String str = "Select * from books";
//            rs = stmt.executeQuery(str);
//            while(rs.next()){
//                System.out.println(rs.getString(Name));
//            }
            return true;
        }catch(Exception e){
            System.out.println("query Not Found");
            return false;
        }
    }

    public boolean searchBook() {
        makeConnection();
        String str = null;
        if((!this.getBookID().isEmpty())&&(!this.getBookName().isEmpty())){
            str = "Select * from books where BookID = '"+ID +"' AND BookName='"+Name+"'";
        }
        else if(!this.getBookID().isEmpty()){
            str = "Select * from books where BookID = '"+ID+"' ";
            System.out.println(ID);
        }else if(!this.getBookName().isEmpty()){
            str = "Select * from books where BookName = '"+Name+"' ";
        }else{            
            return false;
        }
        try {        
                rs = stmt.executeQuery(str);            
                while(rs.next()){
                    this.setBookID(rs.getString("BookID"));
                    this.setBookName(rs.getString("BookName"));
                    this.setAuthor(rs.getString("Author"));
                    this.setPublisher(rs.getString("Publisher"));
                    String c = rs.getString("Available");
                    if(c.equals("1"))
                        this.setAvailable(true);
                    else
                        this.setAvailable(false);
                    this.setSelfNumber(rs.getString("ShelfNo"));
                    this.setNumberOfCopies(rs.getString("NumberOfCopies"));
                }
            
            System.out.println(this.getAuthor()+this.getAvailable()+this.getBookName()); 
            return true;
        } catch (SQLException ex) {
            System.out.println("Query is wrong");
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteBook() {
        makeConnection();
        String str = null;
        if((!this.getBookID().isEmpty())&&(!this.getBookName().isEmpty())){
            str = "DELETE from books where BookID = '"+ID +"'AND BookName='"+Name+"'";
        }
        else if(!this.getBookID().isEmpty()){
            str = "DELETE from books where BookID = '"+ID+"' ";
            System.out.println(ID);
        }else if(!this.getBookName().isEmpty()){
            str = "DELETE from books where BookName = '"+Name+"' ";
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

    private int findNumberOfCopies(){
        makeConnection();
        String str = "Select * from books where BookID = '"+ID+"'";
        int val=0;
        try {
            rs = stmt.executeQuery(str);
            while(rs.next()){
                String num = rs.getString("NumberOfCopies");
                Name = rs.getString("BookName");
                val = Integer.parseInt(num);
                author = rs.getString("Author");
                publisher = rs.getString("Publisher");
                shelfNumber = rs.getString("ShelfNo");
                a = rs.getString("Available");
            }
            return val;
        } catch (SQLException ex) {
            System.out.println("Query failed find number of copies");
            return -1;
            //Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void decreaseNumberOfCopies() {
        int val = findNumberOfCopies();
        //System.out.println("dec number of copies val = " + val);
        val--;
        setNumberOfCopies(Integer.toString(val));
        //System.out.println("dec number of copies number of copies = " + numberOfCopies);
        a = "1";
        if(val==0)
            a = "0";
        setAvailable();
        decBooks();
    }
    
    private void setAvailable(){
        makeConnection();
        String str = "Update "
                + "books "
                + "set Available = '" + a + "' where BookID = '" + ID + "'";
        try {
            stmt.executeUpdate(str);
        } catch (SQLException ex) {
            System.out.println("Query failed set Available ");
            //Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void decBooks(){
        makeConnection();
        String str = "Update "
                + "books "
                + "set NumberOfCopies = '" + numberOfCopies + "' where BookID = '" + ID + "'";
        try {
            stmt.executeUpdate(str);
        } catch (SQLException ex) {
            System.out.println("Query failed update of dec books ");
            //Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void addToIssueBooks(Members m) {
        makeConnection();
        String sep = "','";        
        dates();
        //System.out.println(IssueDate +"  "+ReturnDate );
        String str = "Insert into borrow values ('"+ ID + sep + m.getMemberID() + sep + IssueDate + sep +ReturnDate+"')";
        
            try {
                stmt.executeUpdate(str);
            } catch (SQLException ex) {
                System.out.println("Query failed add to issue books ");
//                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void dates(){
        //getting current date and time using Date class
       java.util.Date today = new java.util.Date(); 
       IssueDate = toddMMyy(today);
       Calendar cal = Calendar.getInstance();
       
       //adding 15 days to current date 
       cal.add(Calendar.DAY_OF_MONTH, 15); 
       java.util.Date tommrrow = cal.getTime(); 
       ReturnDate = toddMMyy(tommrrow);
        //System.out.println(IssueDate +"  "+ReturnDate );
    }
    
    private  String toddMMyy(java.util.Date day){ 
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy"); 
       String date = formatter.format(day);
       return date; 
   }

    public boolean changeBookquantity(boolean b,int val) {
        makeConnection();
        String str = null;
        
        int v = findNumberOfCopies();
        if(b){
            v += val;
        } else{
            v -= val;
            if(v<0)
                return false;
        }        
        numberOfCopies = Integer.toString(v);
        
        String str1 = "Update "
                + "books "
                + "set NumberOfCopies = '" + numberOfCopies+"'" ;
        if((!this.getBookID().isEmpty())&&(!this.getBookName().isEmpty())){
            str = "where BookID = '"+ID +"' AND BookName='"+Name+"'";            
        }
        else if(!this.getBookID().isEmpty()){
            str = "where BookID = '"+ID+"' ";
            System.out.println(ID);
        }else if(!this.getBookName().isEmpty()){
            str = "where BookName = '"+Name+"' ";
        }else{            
            return false;
        }        
        
        try {      
                str1 = str1 + str;
                stmt.executeUpdate(str1);
            return true;
        } catch (SQLException ex) {
            System.out.println("Query is wrong change books quantity");
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int GetNumberOfBooks() {
        int i=0;
        makeConnection();
        String str = "Select * from books";
            try {
                rs = stmt.executeQuery(str);
                while(rs.next()){
                    i++;
                }
            } catch (SQLException ex) {
                System.out.println("Get number of books query failed");
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
        return i;
    }

    public void getAllBooksInformation(String[][] b) {
        int i=0;
        makeConnection();
        String str = "Select * from books order by BookID asc";
            try {
                rs = stmt.executeQuery(str);
                while(rs.next()){
                    b[i][0] = rs.getString("BookID");
                    b[i][1] = rs.getString("BookName");
                    b[i][2] = rs.getString("Author");
                    b[i][3] = rs.getString("Publisher");
                    b[i][4] = rs.getString("Available");
                    b[i][5] = rs.getString("ShelfNo");
                    b[i][6] = rs.getString("NumberOfCopies");
                    i++;
                }
            } catch (SQLException ex) {
                System.out.println("Get number of books query failed");
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}