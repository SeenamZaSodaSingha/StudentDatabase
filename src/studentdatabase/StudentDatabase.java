/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SeenamZaSodaSingha
 */
public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        Student std;
        ArrayList<Student> list;

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(derbyClientDriver);
        String url = "jdbc:derby://localhost:1527/StudentDatabase";
        String user = "Register1";
        String passwd = "1234";

        Connection con = DriverManager.getConnection(url, user, passwd);
        Statement stmt = con.createStatement();
        
        //create student
        Student std1 = new Student(63050144, "Seenam", 3.60);
        Student std2 = new Student(63050102, "Kue", 3.65);
        Student std3 = new Student(63050600, "John", 4.20);
        
        //insert students
        insertStudentPreparedStatement(con, std1);
        insertStudentPreparedStatement(con, std2);
        insertStudentPreparedStatement(con, std3);
        
        //show all students in table
        System.out.println("------------------\nINSERT");
        printAllStudent(getAllStudent(con));
        
        //update GPA
        std3.setGPA(3.00);
        updateStudentGPAPreparedStatement(con, std3);
        
         //show all students in table
        System.out.println("------------------\nUPDATE GPA");
        printAllStudent(getAllStudent(con));
        
        //update name
        std3.setName("Stefan");
        updateStudentNamePreparedStatement(con, std3);
        
        //show all students in table
        System.out.println("------------------\nUPDATE NAME");
        printAllStudent(getAllStudent(con));
        
        //delete student
        deleteStudentPreparedStatement(con, std3);
        
        //show all students in table
        System.out.println("------------------\nDELETE");
        printAllStudent(getAllStudent(con)); 
    }
    
    public static void printAllStudent(ArrayList<Student> list){
        System.out.println("ID\t\tNname\tGPA");
        for(Student std : list){
            System.out.print(std.getId()+"\t");
            System.out.print(std.getName()+"\t");
            System.out.printf("%.2f\t\n", std.getGPA());
        }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from studentdata order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student std = new Student();
           std.setId(rs.getInt("id"));
           std.setName(rs.getString("name"));
           std.setGPA(rs.getDouble("gpa"));
           studentList.add(std);
       }
       rs.close();
       return studentList;
       
    }

    public static void insertStudentPreparedStatement(Connection con, Student std) throws SQLException {
        String sql = "insert into studentdata (id, name, gpa)"
                + " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, std.getId());
        ps.setString(2, std.getName());
        ps.setDouble(3, std.getGPA());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
    }

    public static void deleteStudentPreparedStatement(Connection con, Student std) throws SQLException {
        String sql = "delete from studentdata where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, std.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
    }

    public static void updateStudentGPAPreparedStatement(Connection con, Student std) throws SQLException {
        String sql = "update studentdata set gpa  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, std.getGPA());
        ps.setInt(2, std.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
    }

    public static void updateStudentNamePreparedStatement(Connection con, Student std) throws SQLException {
        String sql = "update studentdata set name  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, std.getName());
        ps.setInt(2, std.getId());
        int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
    }

    public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
        Student std = null;
        String sql = "select * from studentdata where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            std = new Student();
            std.setId(rs.getInt("id"));
            std.setName(rs.getString("name"));
            std.setGPA(rs.getDouble("gpa"));
        }
        return std;
    }
}
