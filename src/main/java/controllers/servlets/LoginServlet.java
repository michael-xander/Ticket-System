package controllers.servlets;

import data.access.DaoFactory;
import data.access.user.LoginDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet.java
 * A Servlet that handles logging into the TicketSystem
 * Created by Michael on 2015/08/07.
 */

@WebServlet(
        name="loginServlet",
        urlPatterns = {"/login"},
        loadOnStartup = 1
)
public class LoginServlet extends HttpServlet
{
    private DaoFactory daoFactory;
    private LoginDao loginDao;

    @Override
    public void init() throws ServletException
    {
        ServletContext c = this.getServletContext();
        String dbUrl = c.getInitParameter("dbUrl");
        String dbUserName = c.getInitParameter("dbUserName");
        String dbPassword = c.getInitParameter("dbPassword");

        daoFactory = new DaoFactory(dbUrl,dbUserName, dbPassword);
        loginDao = daoFactory.getLoginDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        if(request.getParameter("logout") != null)
        {
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("userID") != null)
        {
            response.sendRedirect("queries");
            return;
        }

        request.setAttribute("loginFailed", false);
        request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("userID") != null)
        {
            response.sendRedirect("queries");
            return;
        }

        String userID = request.getParameter("userID");
        String password = request.getParameter("password");

        if(userID == null || password == null ||
                !loginDao.isUser(userID,password))
        {
            request.setAttribute("loginFailed", true);
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                    .forward(request, response);
        }
        else
        {
            session.setAttribute("userID", userID);
            request.changeSessionId();
            response.sendRedirect("queries");
        }
    }
}
