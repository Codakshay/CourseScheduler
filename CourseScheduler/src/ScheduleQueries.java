/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author akshaynataraja
 */
import java.sql.Timestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement ssetStatus;
    private static ResultSet resultSet;
    
    
    public static void addScheduleEntry(ScheduleEntry schedule) {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, schedule.getSemester());
            addScheduleEntry.setString(2, schedule.getStudentID());
            addScheduleEntry.setString(3, schedule.getCourseCode());
            addScheduleEntry.setString(4, schedule.getStatus());
            addScheduleEntry.setTimestamp(5, schedule.getTimeStamp());
            
            addScheduleEntry.executeUpdate();          
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.schedule where semester = ? and studentID = ?");
            
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            
            resultSet = getScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                schedule.add(entry);
            }
            

        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedule;
        
    }
    
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            
            resultSet = getScheduledStudentCount.executeQuery();
            
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
                        
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
        return count;
        
    }
    
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.schedule where semester = ? and courseCode = ?");
            
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, courseCode);
            
            resultSet = getScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                schedule.add(entry);
            }
            

        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedule;
        
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>();
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select * from app.schedule where semester = '" + semester + "' and COURSECODE = '" + courseCode + "' and STATUS = 'w'");
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("studentid");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                
                students.add(new ScheduleEntry(semester,courseCode,studentID,"w",timestamp));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where studentID = '" + studentID + "' and semester = '" + semester + "' and courseCode = '" + courseCode + "'");
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester,String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where  semester = '" + semester + "' and courseCode = '" + courseCode + "'");
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String studentID, String status, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            
            updateScheduleEntry = connection.prepareStatement("update app.schedule set status=? where studentID=? and courseCode=?");
            updateScheduleEntry.setString(1, status); 
            updateScheduleEntry.setString(2, studentID);
            updateScheduleEntry.setString(3, courseCode);
            
            updateScheduleEntry.executeUpdate();
                        
           
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }   
    
    
    
    
}
