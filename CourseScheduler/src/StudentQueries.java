/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author akshaynataraja
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            
            addStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
        public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            
            getAllStudents = connection.prepareStatement("select studentid, firstname, lastname from app.student ");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
  
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
        return students;
        }
        
        public static String getStudentID(String lastName, String firstName) {
        connection = DBConnection.getConnection();
        String studentID = "";
        try
        {
            getAllStudents = connection.prepareStatement("select STUDENTID from APP.STUDENT where FIRSTNAME = '" + firstName + "' and LASTNAME = '" + lastName + "'");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                studentID = resultSet.getString("STUDENTID");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentID;
    }
        
        public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        String firstName = "";
        String lastName = "";
        try
        {
            getStudent = connection.prepareStatement("select * from app.student where studentid = '" + studentID + "'");
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
            {
                firstName = resultSet.getString("FIRSTNAME");
                lastName = resultSet.getString("LASTNAME");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return new StudentEntry(studentID,firstName,lastName);
    }
        
        
        public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        
        
        
        try
        {
            
            dropStudent = connection.prepareStatement("delete from app.student where studentid=?");
            dropStudent.setString(1, studentID);
            
            dropStudent.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        //Testing
//        for (CourseEntry course: courses) {
//            System.out.println(course.getCourseCode() + "\t" + course.getCourseDescription() + "\t" + course.getSemester() + "\t" + course.getSeats());
//        }
        
        
    }
        
      
    }