package Bug;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Bug {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    // Get the maximum id from the table
    public int getMax() {    
        int id = 0;
        Statement st;   
        try {
            st = con.createStatement(); 
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM bug_management");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    
    
    public void insert(int id, String name, String date, String type, String email, String description, String status) {
        String sql = "INSERT INTO bug_management VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, type);
            ps.setString(5, email);
            ps.setString(6, description);
            ps.setString(7, status);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New bug added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
    
    public boolean isEmailExist(String email){
        try{
            ps=con.prepareStatement("Select * from student where email = ? ");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
    }catch(SQLException ex){
        Logger.getLogger(Bug.class.getName()).log(Level.SEVERE,null,ex);
        
    }
    return false;
    
    
}
}