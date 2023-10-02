/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;


/**
 *
 * @author harsh_anmol
 */
public class MyConnection {
    
    private static final String username = "root";
    private static final String password = "Champion@1205";
    private static final String dataConn = "jdbc:mysql://127.0.0.1:3306/bug_management";

    public static Connection con =null;
    
    public static Connection getConnection(){
        
        Connection con =null;
        
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dataConn,username,password);   
        }
        catch(Exception ex){
        System.out.println(ex.getMessage());
        }
        return con;                  
}}
