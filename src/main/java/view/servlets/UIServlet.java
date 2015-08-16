package view.servlets;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import view.MainNavigatorUI;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Michael on 2015/08/15.
 */
@WebServlet
        (
                name = "UIServlet",
                urlPatterns = "/*")
@VaadinServletConfiguration(
        ui = MainNavigatorUI.class,
        productionMode = false
)
public class UIServlet extends VaadinServlet {
}
