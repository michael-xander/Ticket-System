package view.dashboard;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents;
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
        // adding the table filter
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                Filterable data = (Filterable) getTable().getContainerDataSource();
                data.removeAllContainerFilters();
                data.addContainerFilter(new Filter() {
                    @Override
                    public boolean passesFilter(Object o, Item item) throws UnsupportedOperationException {
                        if (textChangeEvent.getText() == null || textChangeEvent.getText().equals(""))
                            return true;

                        return filterByProperty("Subject", item, textChangeEvent.getText())
                                || filterByProperty("Course", item, textChangeEvent.getText())
                                || filterByProperty("Category", item, textChangeEvent.getText())
                                || filterByProperty("Privacy", item, textChangeEvent.getText())
                                || filterByProperty("Status", item, textChangeEvent.getText());

                    }

                    @Override
                    public boolean appliesToProperty(Object propertyId) {

                        if(propertyId.equals("Subject")
                                || propertyId.equals("Course")
                                || propertyId.equals("Category")
                                || propertyId.equals("Privacy")
                                || propertyId.equals("Status")) {
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

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
