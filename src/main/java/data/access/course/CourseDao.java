package data.access.course;

import model.domain.course.Course;

import java.util.List;

/**
 * CourseDao.java
 * An interface for the Course DAO
 * Created by Michael on 2015/08/08.
 */
public interface CourseDao {

    Course getCourse(String courseID);

    List<Course> getAllCourses();

    void updateCourse(Course course);

    void addCourse(Course course);

    void deleteCourse(Course course);
}
