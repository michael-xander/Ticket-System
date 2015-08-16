package view.servlets;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import view.MainNavigatorUI;

import javax.servlet.annotation.WebServlet;

/**
 * UIServlet.java
 * A servlet class that handles web requests to the system and redirects them to the Main UI
 * Created by Michael on 2015/08/15.
 */
@WebServlet
        (
                name = "UIServlet",
                urlPatterns = "/*")
//configuring the class that is to supply the UI for requests to this servlet
@VaadinServletConfiguration(
        ui = MainNavigatorUI.class,
        productionMode = false
)
public class UIServlet extends VaadinServlet {
}
