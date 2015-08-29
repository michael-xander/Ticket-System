package view.dashboard.student.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.student.windows.QueryInfoWindow;

import java.util.Set;

/**
 * A view that lists all the queries for a student from all their courses
 * Created by Michael on 2015/08/19.
 */
public class StudentQueryView extends VerticalLayout implements View {

    private final Table table;

    public StudentQueryView()
    {
        setSizeFull();
        setSpacing(true);

        addComponent(buildToolbar());

        table = buildTable();
        addQueriesToTable();

        addComponent(table);
        setExpandRatio(table, 1);
    }

    private Component buildToolbar()
    {
        HorizontalLayout header = new HorizontalLayout();
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label("Queries");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        header.addComponent(filter);

        return header;
    }

    private Component buildFilter()
    {
        final TextField filter = new TextField();
        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    private Table buildTable()
    {
        final Table table = new Table();
        table.setSizeFull();

        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

        table.addContainerProperty("Date", String.class, "(default)");
        table.addContainerProperty("Subject", String.class, "(default)");
        table.addContainerProperty("Sender", String.class, "(default)");
        table.addContainerProperty("Course", String.class, "(default)");
        table.addContainerProperty("Privacy", String.class, "(default)");
        table.addContainerProperty("Status", String.class, "(default)");

        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {

                int queryId = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryId);
                getUI().addWindow(new QueryInfoWindow(query));
            }
        });

        table.setImmediate(true);
        return table;
    }

    private void addQueriesToTable()
    {
        for(Query query : getCourseQueriesForUser())
        {
            addQueryToTable(query);
        }
    }

    private void addQueryToTable(Query query)
    {
        table.addItem(new Object[] {
                query.getDate().toString(),
                query.getSubject(),
                query.getSenderID(),
                query.getCourseID(),
                query.getPrivacy().toString(),
                query.getStatus().toString()
        }, query.getMessageID());
    }

    private Set<Query> getCourseQueriesForUser()
    {
        return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForUser(
                (String) VaadinSession.getCurrent().getAttribute("userID"), null
        );
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
