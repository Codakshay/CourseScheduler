/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author akshaynataraja
 */
public class CourseQueries {
     private static Connection connection;
     private static ResultSet resultSet;
     private static PreparedStatement getAllCourses;
     private static PreparedStatement addCourse;
     private static PreparedStatement getAllCourseCodes;
     private static PreparedStatement getCourseSeats;
     private static PreparedStatement seatLower;
     private static PreparedStatement dropCourse;
     private static PreparedStatement raiseSeats;
     private static int seats;
     
     
     
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            
            getAllCourses = connection.prepareStatement("select semester, CourseCode, description, seats from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
                
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
        return courses;
    }
    
        public static void addCourse(CourseEntry course) //adminTab-->addCourseTab-->subbmitButton
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (Semester, CourseCode, Description, Seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setString(4, String.valueOf(course.getSeats()));
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        public static ArrayList<String> getAllCourseCodes(String semester)  
    {
         connection = DBConnection.getConnection();
         ArrayList<String> CourseCode = new ArrayList<String>();
         try
         {
             getAllCourseCodes = connection.prepareStatement("select CourseCode from  app.course where Semester = ?"); 
                 
                getAllCourseCodes.setString(1, semester);
                resultSet = getAllCourseCodes.executeQuery();
                

            
             while(resultSet.next())
            {
                CourseCode.add(resultSet.getString(1));
            }
        }
            catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
            return CourseCode;
         }
        
        
        
        
        public static int getCourseSeats(String semester, String courseCode) {

            try {
                connection = DBConnection.getConnection();
                getCourseSeats = connection.prepareStatement("Select Seats from app.course where Semester = ? and courseCode = ?");

                getCourseSeats.setString(1, semester);
                getCourseSeats.setString(2, courseCode);
                resultSet = getCourseSeats.executeQuery();

                while (resultSet.next()) {
                    seats = resultSet.getInt(1);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return seats;
        }
        
        
        
        public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            
            dropCourse = connection.prepareStatement("delete from app.course where semester = ? and courseCode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            
            dropCourse.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        
        
    }

        
