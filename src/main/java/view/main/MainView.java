package view.main;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import model.domain.user.Role;
import model.domain.user.User;
import view.TicketSystemUI;
import view.dashboard.convener.menu.ConvenerDashboardMenu;
import view.dashboard.convener.navigator.ConvenerViewNavigator;
import view.dashboard.student.menu.StudentDashboardMenu;
import view.dashboard.student.navigator.StudentViewNavigator;

/**
 * Main View is a simple HorizontalLayout that wraps the menu on the left
 * and creates a simple container for the navigator on the right
 * Created by Michael on 2015/08/18.
 */
public class MainView extends HorizontalLayout {

    public MainView()
    {
        setSizeFull();
        addStyleName("mainview");

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
            addComponent(new ConvenerDashboardMenu());

            VerticalLayout content = new VerticalLayout();
            content.setSizeFull();
            addComponent(content);
            setExpandRatio(content, 1.0f);

            new ConvenerViewNavigator(content);
        }
    }

    /*
     * Checks whether the logged in user is student or convener
     * Returns true if student and false other wise
     */
    private boolean isStudent()
    {
        User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
                (String) VaadinSession.getCurrent().getAttribute("userID")
        );

        if(user.getCourseIDs().size() == 1)
        {
            for(String courseID : user.getCourseIDs())
            {
                if(user.getRoleForCourse(courseID) == Role.CONVENER)
                    return false;
            }
            return true;
        }
        else
        {
            return true;
        }
    }
}
