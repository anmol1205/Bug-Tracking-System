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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bug {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

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

    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("Select * from bug_management where email = ? ");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isBugExist(String name) {
        try {
            ps = con.prepareStatement("Select * from bug_management where email = ? ");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("Select * from bug_management where id = ? ");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void getBugValue(JTable table, String searchValue) {
        String sql = " select * from bug_management where concat(id,name,email) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {

                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void update(int id, String name, String date, String type, String email, String description, String status) {
        String sql = "UPDATE bug_management SET name = ?, date = ?, type = ?, email = ?, `desc` = ?, status = ? WHERE id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, type);
            ps.setString(4, email);
            ps.setString(5, description);
            ps.setString(6, status);
            ps.setInt(7, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Bug Data Updated Successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Records will be deleted permanently", "Bug Delete", JOptionPane.OK_CANCEL_OPTION);
        if (yesOrNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from bug_management where id = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Bug Data Deleted Successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Bug.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
    }
}
