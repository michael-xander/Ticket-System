package view;

/**
 * TicketSystemUI.java
 * A class that contains the navigator between the different views of the system
 * Created by Michael on 2015/08/15.
 */

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import data.access.DaoFactory;
import view.login.LoginView;
import view.main.MainView;

@Theme("dashboard")
@Title("Query Ticket System")
public class TicketSystemUI extends UI
{

    private final DaoFactory daoFactory = new DaoFactory(
            VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl"),
            VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName"),
            VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword")
    );

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown
     */
    public void updateContent()
    {
        Object id = VaadinSession.getCurrent().getAttribute("userID");

        if(id != null)
        {
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo("dashboard");
        }
        else
        {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    /**
     * Nullifies the id attribute and updates the content of the page
     */
    public void userSignOut()
    {
        VaadinSession.getCurrent().setAttribute("userID", null);
        updateContent();
    }

    /**
     * return an instance for accessing the database
     */
    public static DaoFactory getDaoFactory()
    {
        return ((TicketSystemUI) getCurrent()).daoFactory;
    }
}
