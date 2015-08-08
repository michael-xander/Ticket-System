package data.access;

import model.domain.user.Course;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * DaoFactoryUnitTest.java
 * A class that tests the methods of the DaoFactory class
 * Created by Michael on 2015/08/07.
 */
public class DaoFactoryUnitTest
{
    private Properties getDatabaseAccessProperties()
    {
        Properties props = new Properties();

        props.setProperty("db.url", "jdbc:mysql://localhost:3306/ticket_system");
        props.setProperty("db.user", "ticSysDev");
        props.setProperty("db.password", "ticSysDev458");

        return props;
    }

    private DaoFactory getDaoFactory()
    {
        Properties props = getDatabaseAccessProperties();

        DaoFactory daoFactory = new DaoFactory(props.getProperty("db.url"),
                props.getProperty("db.user"), props.getProperty("db.password"));

        return daoFactory;
    }

    @Test
    public void constructorTest()
    {
        Properties props = getDatabaseAccessProperties();

        DaoFactory daoFactory = new DaoFactory(props.getProperty("db.url"),
                props.getProperty("db.user"), props.getProperty("db.password"));

        daoFactory.startConnection();
        daoFactory.endConnection();
    }

    /**
     * A method that tests the functionality of the Login DAO isUser method
     */
    @Test
    public void loginDaoTest()
    {
        DaoFactory daoFactory = getDaoFactory();

        LoginDao loginDao = daoFactory.getLoginDao();

        assertFalse(loginDao.isUser("123", "123"));
        assertTrue(loginDao.isUser("KYYMIC001", "kyymic001"));
    }

    /**
     * A method that tests the getCourse method of the Course DAO
     */
    @Test
    public void courseDaoGetCourseTest()
    {
        DaoFactory daoFactory = getDaoFactory();
        CourseDao courseDao = daoFactory.getCourseDao();

        Course course = courseDao.getCourse("INF3011F 2017");
        assertNull(course);

        course = courseDao.getCourse("CSC3002F 2015");
        assertNotNull(course);

        assertTrue(course.getCourseID().equals("CSC3002F 2015"));

        assertNotNull(course.getDescription());

        assertFalse(course.getTestDates().isEmpty());
        assertFalse(course.getAssignmentDueDates().isEmpty());
    }

    /**
     * A method thae tests the getAllCourses method of the Course DAO
     */
    @Test
    public void courseDaoGetAllCoursesTest()
    {
        DaoFactory daoFactory = getDaoFactory();
        CourseDao courseDao = daoFactory.getCourseDao();

        List<Course> courses = courseDao.getAllCourses();
        assertNotNull(courses);

        Course tempCourse = new Course();
        tempCourse.setCourseCode("CSC3002F");
        tempCourse.setCourseYear(2015);

        assertTrue(courses.contains(tempCourse));

        tempCourse = courses.get(courses.indexOf(tempCourse));

        assertTrue(tempCourse.getCourseID().equals("CSC3002F 2015"));
        assertNotNull(tempCourse.getDescription());

        assertFalse(tempCourse.getTestDates().isEmpty());
        assertFalse(tempCourse.getAssignmentDueDates().isEmpty());
    }
}
