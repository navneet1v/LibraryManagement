/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Navneet pc
 */
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GettingCurrentDate {
   public static void main(String[] args) {
       //getting current date and time using Date class
       Date today = new Date(); 
       System.out.println("Today is " + toddMMyy(today));
       Calendar cal = Calendar.getInstance();
       //adding one day to current date 
       cal.add(Calendar.DAY_OF_MONTH, 15); 
       Date tommrrow = cal.getTime(); 
       System.out.println("Tomorrow will be " + toddMMyy(tommrrow));
    }
   
    public static String toddMMyy(Date day){ 
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy"); 
       String date = formatter.format(day);
       return date; 
   }


}