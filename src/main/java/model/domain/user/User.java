package model.domain.user;

import model.domain.course.Course;

import java.util.HashMap;
import java.util.Set;

/**
 * User.java
 * A class that represents a User of the Ticket System
 * Created by Michael on 2015/08/07.
 */
public class User
{
    private String userID;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    private HashMap<String, Role> courseRoleHashMap = new HashMap<>();

    public Role getRoleForCourse(String  courseID)
    { return courseRoleHashMap.get(courseID);}

    public void setRoleForCourse(String courseID, Role role)
    { courseRoleHashMap.replace(courseID, role);}

    public Set<String> getCourseIDs()
    { return courseRoleHashMap.keySet();}

    public void addCourse(String courseID, Role role)
    { courseRoleHashMap.put(courseID, role);}

    public void setFirstName(String firstName)
    { this.firstName = firstName;}

    public String getFirstName()
    { return firstName;}

    public void setLastName(String lastName)
    { this.lastName = lastName;}

    public String getLastName()
    { return lastName;}

    public void setEmail(String email)
    { this.email = email;}

    public String getEmail()
    { return email;}

    public void setUserID(String userID)
    { this.userID = userID;}

    public String getUserID()
    {return userID;}

    public void setPassword(String password)
    { this.password = password;}

    public String getPassword()
    { return password;}


    @Override
    public boolean equals(Object object)
    {
        if((object == null) || !(object.getClass().equals(this.getClass())))
            return false;
        else
        {
            User user = (User) object;

            if((user.userID == null) || (this.userID == null) || !user.userID.equals(this.userID))
                return false;
            else
                return true;
        }
    }
}
