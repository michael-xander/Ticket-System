package view.dashboard.convener.views;


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
    }

    @Override
    public Collection<Query> getQueries() {
        return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
    }
}
