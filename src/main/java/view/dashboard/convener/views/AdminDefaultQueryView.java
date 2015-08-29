package view.dashboard.convener.views;

import model.domain.message.Query;
import view.TicketSystemUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A view for all the queries the different courses for a Convener
 * Created by Michael on 2015/08/29.
 */
public class AdminDefaultQueryView extends AdminQueryTableView
{
    public AdminDefaultQueryView()
    {
        super();
        addComponent(buildToolbar("Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

    }

    @Override
    public Collection<Query> getQueries() {
        ArrayList<Query> queries = new ArrayList<>();

        for(String courseID : super.getUser().getCourseIDs())
        {
            List<Query> tempList = TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
            queries.addAll(tempList);
        }
        return queries;
    }
}
