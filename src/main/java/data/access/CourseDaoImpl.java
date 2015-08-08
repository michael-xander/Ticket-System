package data.access;

import model.domain.user.Course;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
                course = new Course();
                String[] tempArr = courseID.split(" ");
                course.setCourseCode(tempArr[0]);
                course.setCourseYear(Integer.parseInt(tempArr[1].trim()));

                course.setDescription(resultSet.getString(2));
                preparedStatement.clearParameters();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement("SELECT * FROM Course_Test_Dates WHERE CourseID = ?");
                preparedStatement.setString(1, courseID);
                ResultSet tempResultSet = preparedStatement.executeQuery();

                while(tempResultSet.next())
                {
                    String dateString = tempResultSet.getString(2);
                    course.addTestDate(LocalDate.parse(dateString));
                }

                preparedStatement.clearParameters();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement("SELECT * FROM Course_Assignment_Dates WHERE CourseID = ?");
                preparedStatement.setString(1, courseID);

                tempResultSet = preparedStatement.executeQuery();

                while(tempResultSet.next())
                {
                    String dateString = tempResultSet.getString(2);
                    course.addTestDate(LocalDate.parse(dateString));
                }

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
                Course course = new Course();
                String[] tempArr = resultSet.getString(1).split(" ");
                course.setCourseCode(tempArr[0]);
                course.setCourseYear(Integer.parseInt(tempArr[1].trim()));

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

                courses.add(course);
                tempPreparedStatement.clearParameters();
                tempPreparedStatement.close();
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
