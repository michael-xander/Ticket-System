package view.dashboard.student;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.TicketSystemUI;

import java.util.List;
import java.util.Set;


/**
 * Created by Michael on 2015/08/17.
 */
public class CourseQueryView extends VerticalLayout implements View
{
    private final Table table;
    private String courseID;

    public CourseQueryView(String courseID)
    {
        this.courseID = courseID;
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

        Label title = new Label(courseID + " Queries");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Button createQuery = buildCreateQuery();
        Component filter = buildFilter();
        header.addComponent(filter);
        header.addComponent(createQuery);

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

    public void addQueryToTable(Query query)
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
        if(courseID.isEmpty())
        {
            return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForUser(
                    (String) VaadinSession.getCurrent().getAttribute("userID"), null
            );
        }
        else
        {
            return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForUser(
                    (String) VaadinSession.getCurrent().getAttribute("userID"), courseID);
        }
    }

    private Button buildCreateQuery()
    {
        final Button createQuery = new Button("Create Query");

        createQuery.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().addWindow(new CreateQueryWindow(courseID));
            }
        });

        return createQuery;
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
