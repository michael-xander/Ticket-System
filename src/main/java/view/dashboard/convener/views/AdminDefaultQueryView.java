package view.dashboard.convener.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.convener.windows.ConvenerQueryInfoWindow;
import view.dashboard.convener.windows.CreateQueryReplyWindow;

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
        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int queryID = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryID);
                getUI().addWindow(new ConvenerQueryInfoWindow(query));
            }
        });

        addComponent(buildToolbar("Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

    }

    @Override
    public Component buildToolbar(String toolbarHeader)
    {
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

        for(String courseID : super.getUser().getCourseIDs())
        {
            List<Query> tempList = TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
            queries.addAll(tempList);
        }
        return queries;
    }
}
