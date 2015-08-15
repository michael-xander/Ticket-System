package controllers.servlets;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Michael on 2015/08/15.
 */
@WebServlet
        (
                name = "UIServlet",
                urlPatterns = "/*")
@Theme("valo")
public class UIServlet extends VaadinServlet {
}
