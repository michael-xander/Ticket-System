package view;

/**
 * MainNavigator.java
 * A class that contains the navigator between the different views of the system
 * Created by Michael on 2015/08/15.
 */

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import view.dashboard.convener.ConvenerDashBoardView;
import view.dashboard.student.StudentDashboardView;
import view.login.LoginView;

@Theme("valo")
public class MainNavigatorUI extends UI
{
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //initiate navigator that handles requests to different views given the view identifier
        Navigator navigator = new Navigator(this, this);
        navigator.addView("login", new LoginView());
        navigator.addView("student", new StudentDashboardView());
        navigator.addView("convener", new ConvenerDashBoardView());
        navigator.navigateTo("login");
    }
}
