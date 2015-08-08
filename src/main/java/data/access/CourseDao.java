package data.access;

import model.domain.user.Course;

import java.util.List;

/**
 * CourseDao.java
 * An interface for the Course
 * Created by Michael on 2015/08/08.
 */
public interface CourseDao {

    public Course getCourse(String courseID);

    public List<Course> getAllCourses();

    public void updateCourse(Course course);

    public void addCourse(Course course);

    public void deleteCourse(Course course);
}
