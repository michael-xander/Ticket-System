package view.dashboard;

import com.vaadin.data.Item;
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

    public UserTableView()
    {
        setSizeFull();
        addStyleName("transactions");
    }

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

    public boolean filterByProperty(final String prop, final Item item, final String text)
    {
        if(item == null || item.getItemProperty(prop) == null
                || item.getItemProperty(prop).getValue() == null) {
            return false;
        }

        String val = item.getItemProperty(prop).getValue().toString().trim().toLowerCase();

        if(val.contains(text.toLowerCase().trim())) {
            return true;
        }
        return false;
    }

    public abstract Table buildTable();

    public abstract Component buildFilter();

    public abstract Component buildToolbar(String toolbarHeader);

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
