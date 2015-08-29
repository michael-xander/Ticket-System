package view.dashboard;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;

import java.util.Collection;

/**
 * A view that displays queries to a User
 * Created by Michael on 2015/08/29.
 */

public abstract class UserQueryTableView extends UserTableView {

    @Override
    public Table buildTable() {
        final Table table = new Table();
        table.setSizeFull();

        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

        table.addContainerProperty("Date", String.class, "(default)");
        table.addContainerProperty("Subject", String.class, "(default)");
        table.addContainerProperty("Sender", String.class, "(default)");
        table.addContainerProperty("Course", String.class, "(default)");
        table.addContainerProperty("Category", String.class, "(default)");
        table.addContainerProperty("Privacy", String.class, "(default)");
        table.addContainerProperty("Status", String.class, "(default)");

        table.setImmediate(true);

        return table;
    }

    @Override
    public Component buildFilter() {
        final TextField filter = new TextField();
        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    @Override
    public abstract Component buildToolbar(String toolbarHeader);

    public abstract Collection<Query> getQueries();

    public void addQueryToTable(Query query)
    {
        super.getTable().addItem(new Object[] {
                query.getDate().toString(),
                query.getSubject(),
                query.getSenderID(),
                query.getCourseID(),
                query.getCategoryName(),
                query.getPrivacy().toString(),
                query.getStatus().toString()
        }, query.getMessageID());
    }

    public void addQueriesToTable(Collection<Query> queries)
    {
        for(Query query : queries)
        {
            addQueryToTable(query);
        }
    }

}
