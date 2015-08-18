package view.main;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import view.dashboard.student.StudentDashboardMenu;
import view.dashboard.student.StudentViewNavigator;

/**
 * Main View is a simple HorizontalLayout that wraps the menu on the left
 * and creates a simple container for the navigator on the right
 * Created by Michael on 2015/08/18.
 */
public class MainView extends HorizontalLayout {

    public MainView()
    {
        setSizeFull();

        if(isStudent())
        {
            addComponent(new StudentDashboardMenu());

            VerticalLayout content = new VerticalLayout();
            content.setSizeFull();
            addComponent(content);
            setExpandRatio(content, 1.0f);

            new StudentViewNavigator(content);
        }
        else
        {

        }
    }

    /*
     * Checks whether the logged in user is student or convener
     * Returns true if student and false other wise
     */
    private boolean isStudent()
    {
        return true;
    }
}
