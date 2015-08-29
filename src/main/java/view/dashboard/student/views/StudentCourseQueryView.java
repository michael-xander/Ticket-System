package view.dashboard.student.views;


import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.student.windows.CreateQueryWindow;

import java.util.Collection;


/**
 * A view that shows the queries for a particular course for a student
 * Created by Michael on 2015/08/17.
 */
public class StudentCourseQueryView extends StudentQueryTableView
{
    private String courseID;

    public StudentCourseQueryView(String courseID)
    {
        super();
        this.courseID = courseID;

        addComponent(buildToolbar(this.courseID + " Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);
    }

    @Override
    public Component buildToolbar(String toolbarHeader) {
        HorizontalLayout header = new HorizontalLayout();
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(toolbarHeader);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        header.addComponent(filter);
        header.addComponent(buildCreateQuery());

        return header;
    }

    @Override
    public Collection<Query> getQueries() {
        return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForUser(
                (String) VaadinSession.getCurrent().getAttribute("userID"), courseID
        );
    }

    public Button buildCreateQuery()
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
}
