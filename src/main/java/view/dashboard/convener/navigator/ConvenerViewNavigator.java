package view.dashboard.convener.navigator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComponentContainer;
import model.domain.user.User;
import view.TicketSystemNavigator;
import view.TicketSystemUI;
import view.dashboard.convener.views.AdminCourseFaqView;
import view.dashboard.convener.views.AdminCourseQueryView;
import view.dashboard.convener.views.AdminDefaultQueryView;
import view.dashboard.convener.views.AdminFaqView;

/**
 * A navigator for the different views available to the course convener
 * Created by Michael on 2015/08/18.
 */
public class ConvenerViewNavigator extends TicketSystemNavigator {

    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String) VaadinSession.getCurrent().getAttribute("userID")
    );

    public ConvenerViewNavigator(ComponentContainer container) {
        super(container);
        initViews();
    }

    private void initViews()
    {
        addProvider(initDashboardViewProvider());

        for(String courseID : user.getCourseIDs())
        {
            ViewProvider viewProvider = new ClassBasedViewProvider(courseID, AdminCourseQueryView.class)
            {
                @Override
                public View getView(final String viewName)
                {
                    return new AdminCourseQueryView(courseID);
                }
            };

            addProvider(viewProvider);

            /*
            viewProvider = new ClassBasedViewProvider(courseID + " Categories", null)
            {
                @Override
                public View getView(final String viewName) { return null;}
            };

            addProvider(viewProvider);
            */
            viewProvider = new ClassBasedViewProvider(courseID + " FAQs", AdminFaqView.class)
            {
                @Override
                public View getView(final String viewName) {return new AdminCourseFaqView(courseID);}
            };

            addProvider(viewProvider);

        }
    }

    private ViewProvider initDashboardViewProvider()
    {
        ViewProvider viewProvider = new ClassBasedViewProvider("dashboard", AdminDefaultQueryView.class)
        {
            @Override
            public View getView(final String viewName)
            {
                return new AdminDefaultQueryView();
            }
        };

        return viewProvider;
    }
}
