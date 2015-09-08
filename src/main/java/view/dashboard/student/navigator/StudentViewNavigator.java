package view.dashboard.student.navigator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComponentContainer;
import model.domain.user.Role;
import model.domain.user.User;
import view.TicketSystemNavigator;
import view.TicketSystemUI;
import view.dashboard.convener.views.AdminCourseQueryView;
import view.dashboard.student.views.StudentCourseFaqView;
import view.dashboard.student.views.StudentCourseQueryView;
import view.dashboard.student.views.StudentDefaultQueryView;

/**
 * A class that handles navigation through the different views available to a student user
 * Created by Michael on 2015/08/18.
 */
public class StudentViewNavigator extends TicketSystemNavigator
{
    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String) VaadinSession.getCurrent().getAttribute("userID")
    );

    public StudentViewNavigator(ComponentContainer container) {
        super(container);
        initViews();
    }

    private void initViews()
    {
        addProvider(initDashboardViewProvider());
        for(String courseID : user.getCourseIDs())
        {
            ViewProvider viewProvider;
            if(user.getRoleForCourse(courseID).equals(Role.TA))
            {
                viewProvider = new ClassBasedViewProvider(courseID, AdminCourseQueryView.class)
                {
                    @Override
                    public View getView(final String viewName)
                    {
                        return new AdminCourseQueryView(courseID);
                    }
                };
                addProvider(viewProvider);
            }
            else
            {
                viewProvider = new ClassBasedViewProvider(courseID, StudentCourseQueryView.class)
                {
                    @Override
                    public View getView(final String viewName)
                    {
                        return new StudentCourseQueryView(viewName);
                    }
                };
                addProvider(viewProvider);
            }
            /*
            viewProvider = new ClassBasedViewProvider(courseID, StudentCourseQueryView.class)
            {
                @Override
                public View getView(final String viewName)
                {
                    return new StudentCourseQueryView(viewName);
                }
            };*/

            viewProvider = new ClassBasedViewProvider(courseID + " FAQs", StudentCourseFaqView.class)
            {
                @Override
                public View getView(final String viewName) {return new StudentCourseFaqView(courseID);}
            };

            addProvider(viewProvider);
        }
    }

    private ViewProvider initDashboardViewProvider()
    {
        ViewProvider viewProvider = new ClassBasedViewProvider("dashboard", StudentDefaultQueryView.class)
        {
          @Override
        public View getView(final String viewName)
          {
              return new StudentDefaultQueryView();
          }
        };

        return viewProvider;
    }
}
