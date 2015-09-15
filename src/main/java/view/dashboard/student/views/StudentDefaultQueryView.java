package view.dashboard.student.views;


import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import model.domain.user.Role;
import view.TicketSystemUI;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A view that lists all the queries for a student from all their courses
 * Created by Michael on 2015/08/19.
 */
public class StudentDefaultQueryView extends StudentQueryTableView {

    public StudentDefaultQueryView()
    {
        super();
        addComponent(buildToolbar("Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

    }

    @Override
    public Component buildToolbar(String toolbarHeader) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(toolbarHeader);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        HorizontalLayout tools = new HorizontalLayout(filter);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        return header;
    }

    @Override
    public Collection<Query> getQueries() {
        ArrayList<Query> queries = new ArrayList<>();

        for(String courseID : getUser().getCourseIDs())
        {
            if(getUser().getRoleForCourse(courseID).equals(Role.STUDENT))
            {
                Collection<Query> temp = TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForUser(
                        getUser().getUserID(),
                        courseID
                );
                queries.addAll(temp);
            }
        }
        return queries;
    }
}
