package view.dashboard.student.navigator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComponentContainer;
import model.domain.user.User;
import view.TicketSystemNavigator;
import view.TicketSystemUI;
import view.dashboard.student.views.CourseQueryView;
import view.dashboard.student.views.QueryView;

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
            ViewProvider viewProvider = new ClassBasedViewProvider(courseID, CourseQueryView.class)
            {
                @Override
                public View getView(final String viewName)
                {
                    return new CourseQueryView(viewName);
                }
            };
            addProvider(viewProvider);
        }
    }

    private ViewProvider initDashboardViewProvider()
    {
        ViewProvider viewProvider = new ClassBasedViewProvider("dashboard", QueryView.class)
        {
          @Override
        public View getView(final String viewName)
          {
              return new QueryView();
          }
        };

        return viewProvider;
    }
}