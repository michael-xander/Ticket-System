package view.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import model.domain.user.User;
import view.TicketSystemUI;

/**
 * A popup window abstract class
 * Created by Michael on 2015/08/30.
 */
public abstract class PopupWindow extends Window{

    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String) VaadinSession.getCurrent().getAttribute("userID")
    );

    public User getUser(){return user;}

    public abstract Component buildContent();

    public abstract Component buildFooter();
}
