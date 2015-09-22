package data.access;

import data.access.course.CourseDao;
import model.domain.course.Course;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 * A class to test the functionality of the course DAO
 * Created by Michael on 2015/08/08.
 */
public class CourseDaoUnitTest {
    /**
     * A method that tests teh getCourse method of the Course DAO
     */
    @Test
    public void courseGetCourseTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

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
     * A method to test the getAllCourses method of the Course DAO
     */
    @Test
    public void courseDaoGetAllCoursesTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

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
