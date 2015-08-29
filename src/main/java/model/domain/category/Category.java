package model.domain.category;


import java.io.Serializable;

/**
 * Category.java
 * A class that represents a category in the Ticket System class
 * Created by marcelo on 08-08-2015.
 */
public class Category implements Serializable {

    private int categoryID;
    private String name;
    private String description;
    private String courseID;

    public int getCategoryID(){
        return categoryID;
    }

    public void setCategoryID(int categoryID){
        this.categoryID=categoryID;
    }

    public String getCategoryName(){
        return name;
    }

    public void setCategoryName(String name){
        this.name=name;
    }

    public String getCategoryDescription(){
        return description;
    }

    public void setCategoryDescription(String description){
        this.description=description;
    }

    public String getCourseID()
    { return courseID;}

    public void setCourse(String courseID)
    { this.courseID = courseID;}
}
