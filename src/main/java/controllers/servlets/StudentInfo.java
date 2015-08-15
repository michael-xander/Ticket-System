package controllers.servlets;


import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Tree;


/**
 * Created by william on 15-08-2015.
 */

public class StudentInfo extends VerticalLayout {

    Tree tree;
    String [] courses;

    public StudentInfo(){
        buildLayout();
    }

    private void buildLayout(){
        tree = new Tree("Courses");
        //tree.set
        courses = new String [] {"CSC3003S,2015","SHARAPOVA",};
        for(int i = 0; i < courses.length; i++){
            tree.addItem(courses[i]);
            tree.setChildrenAllowed(courses[i], false);
        }

        addComponent(tree);
        setSpacing(true);
        setMargin(true);
    }

}
