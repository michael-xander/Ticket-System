package data.access;

import model.domain.user.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class CourseDaoImpl.java
 * A DAO class for the Course class
 * Created by Michael on 2015/08/08.
 */
public class CourseDaoImpl implements CourseDao
{
    private String dbUser;
    private String dbPassword;
    private String dbUrl;

    private Logger logger = Logger.getLogger(CourseDaoImpl.class.getName());

    public CourseDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * A method that returns the Course object from the database with given Course ID
     * @param courseID - ID of the Course to check for
     * @return Course object if exists in database of else null
     */
    @Override
    public Course getCourse(String courseID)
    {
        Course course = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Courses WHERE CourseID = ?");
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                course = readCourse(connection, resultSet);
            }
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return course;
    }

    /*
     * A method that reads in course from the database
     */
    private Course readCourse(Connection connection, ResultSet resultSet) throws SQLException
    {
        Course course = new Course();
        String[] tempArr = resultSet.getString(1).split(" ");
        course.setCourseCode(tempArr[0]);
        course.setCourseYear(Integer.parseInt(tempArr[1].trim()));
        course.setDescription(resultSet.getString(2));

        PreparedStatement tempPreparedStatement = connection.prepareStatement("SELECT * FROM Course_Assignment_Dates WHERE CourseID = ?");
        tempPreparedStatement.setString(1, course.getCourseID());
        ResultSet tempResultSet = tempPreparedStatement.executeQuery();

        while(tempResultSet.next())
        {
            String dateString = tempResultSet.getString(2);
            course.addAssignmentDueDate(LocalDate.parse(dateString));
        }

        tempPreparedStatement.clearParameters();
        tempPreparedStatement.close();

        tempPreparedStatement = connection.prepareStatement("SELECT * FROM Course_Test_Dates WHERE CourseID = ?");
        tempPreparedStatement.setString(1, course.getCourseID());
        tempResultSet = tempPreparedStatement.executeQuery();

        while(tempResultSet.next())
        {
            String dateString = tempResultSet.getString(2);
            course.addTestDate(LocalDate.parse(dateString));
        }
        tempPreparedStatement.close();
        return course;
    }

    /**
     * A method that returns all Courses in the database.
     * @return - List of all Courses
     */
    @Override
    public List<Course> getAllCourses()
    {
        ArrayList<Course> courses = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Courses");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Course course = readCourse(connection, resultSet);
                courses.add(course);
            }

        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return courses;
    }

    /**
     * A method that updates the values of the given Course in that database
     * @param course - Course with values to be updated
     */
    @Override
    public void updateCourse(Course course)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("UPDATE Courses SET Description = ? WHERE CourseID = ?");
            preparedStatement.setString(1, course.getDescription());
            preparedStatement.setString(2, course.getCourseID());
            preparedStatement.executeUpdate();

        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /**
     * A method that adds provided Course to the database
     * @param course - Course to be added
     */
    @Override
    public void addCourse(Course course) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("INSERT INTO Courses (CourseID, Description) VALUES (?,?)");
            preparedStatement.setString(1, course.getCourseID());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("INSERT INTO Course_Assignment_Dates (CourseID,  Assignment_Date) VALUES (?,?)");
            for(LocalDate date : course.getAssignmentDueDates())
            {
                preparedStatement.clearParameters();
                preparedStatement.setString(1, course.getCourseID());
                preparedStatement.setString(2, date.toString());
                preparedStatement.executeUpdate();
            }

            preparedStatement.clearParameters();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("INSERT INTO Course_Test_Dates (CourseID, Test_Date) VALUES (?,?)");

            for(LocalDate date : course.getTestDates())
            {
                preparedStatement.clearParameters();
                preparedStatement.setString(1, course.getCourseID());
                preparedStatement.setString(2, date.toString());
                preparedStatement.executeUpdate();
            }

            preparedStatement.clearParameters();
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /**
     * A method to delete a Course from the database
     * @param course - course to delete
     */
    @Override
    public void deleteCourse(Course course) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl,dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("DELETE FROM Courses WHERE CourseID = ?");
            preparedStatement.setString(1, course.getCourseID());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM Course_Test_Dates WHERE CourseID = ?");
            preparedStatement.setString(1, course.getCourseID());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("DELETE FROM Course_Assignment_Dates WHERE CourseID = ?");
            preparedStatement.setString(1, course.getCourseID());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
