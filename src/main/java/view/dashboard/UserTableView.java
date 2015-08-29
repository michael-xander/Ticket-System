package view.dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import model.domain.user.User;
import view.TicketSystemUI;

/**
 * A view for a user that contains a Table
 * Created by Michael on 2015/08/29.
 */
public abstract class UserTableView extends VerticalLayout implements View{

    private Table table;

    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String) VaadinSession.getCurrent().getAttribute("userID")
    );

    public User getUser()
    {
        return user;
    }

    public Table getTable()
    {
        return table;
    }

    public void setTable(Table table)
    {
        this.table = table;
    }

    public abstract Table buildTable();

    public abstract Component buildFilter();

    public abstract Component buildToolbar(String toolbarHeader);

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
