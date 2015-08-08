package controllers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * QueriesServlet.java
 * A servlet thats a controller for users accessing queries
 * Created by Michael on 2015/08/08.
 */

@WebServlet(
        name = "queriesServlet",
        urlPatterns = "/queries"
)
public class QueriesServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.getWriter().println("Hello World");
    }
}
