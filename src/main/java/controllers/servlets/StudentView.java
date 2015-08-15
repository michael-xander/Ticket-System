package controllers.servlets;

/**
 * Created by william on 15-08-2015.
 */

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;

public class StudentView extends HorizontalLayout implements View {

   public StudentView(){
        addComponents(new StudentInfo(), new QueryTable(), new QueryForm());
    }

    public void enter(ViewChangeListener.ViewChangeEvent event){

    }
}
