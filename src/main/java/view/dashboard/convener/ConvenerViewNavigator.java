package view.dashboard.convener;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComponentContainer;
import model.domain.user.User;
import view.TicketSystemNavigator;
import view.TicketSystemUI;

/**
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
        for(String courseID : user.getCourseIDs())
        {
            ViewProvider viewProvider = new ClassBasedViewProvider("dashboard", AdminQueryView.class)
            {
                @Override
                public View getView(final String viewName)
                {
                    return new AdminQueryView(courseID);
                }
            };

            addProvider(viewProvider);
        }
    }
}
