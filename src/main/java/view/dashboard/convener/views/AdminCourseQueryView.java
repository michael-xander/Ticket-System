package view.dashboard.convener.views;


import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.TicketSystemUI;

import java.util.Collection;

/**
 * A view for the course convener to observe queries for a single course
 * Created by Michael on 2015/08/18.
 */
public class AdminCourseQueryView extends AdminQueryTableView
{
    private String courseID;

    public AdminCourseQueryView(String courseID)
    {
        super();
        this.courseID = courseID;

        addComponent(buildToolbar(courseID + " Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

        super.getTable().setMultiSelect(true);
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
        Component createReply = buildCreateReply();
        HorizontalLayout tools = new HorizontalLayout(filter, createReply);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        return header;
    }

    public Button buildCreateReply()
    {
        final Button createReply = new Button("Create Reply");
        return createReply;
    }

    @Override
    public Collection<Query> getQueries() {
        return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
    }
}
