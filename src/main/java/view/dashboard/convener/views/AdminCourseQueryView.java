package view.dashboard.convener.views;


import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import model.domain.user.Role;
import view.TicketSystemUI;
import view.dashboard.convener.windows.ConvenerQueryInfoWindow;
import view.dashboard.convener.windows.CreateMultiQueryReplyWindow;
import view.dashboard.student.windows.QueryInfoWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A view for the course convener to observe queries for a single course
 * Created by Michael on 2015/08/18.
 */
public class AdminCourseQueryView extends AdminQueryTableView
{
    private final String courseID;
    private Button createReply;

    public AdminCourseQueryView(final String courseID)
    {
        super();
        this.courseID = courseID;

        addComponent(buildToolbar(courseID + " Queries"));

        super.addQueriesToTable(getQueries());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

        super.getTable().setMultiSelect(true);
        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int queryID = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryID);

                if(getUser().getRoleForCourse(courseID).equals(Role.TA))
                {
                    getUI().addWindow(new QueryInfoWindow(query));
                }
                else
                {
                    getUI().addWindow(new ConvenerQueryInfoWindow(query));
                }
            }
        });

        super.getTable().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (getTable().getValue() instanceof Set) {
                    Set<Object> val = (Set<Object>) getTable().getValue();
                    createReply.setEnabled(val.size() > 0);
                }
            }
        });
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
        createReply = buildCreateReply();
        HorizontalLayout tools = new HorizontalLayout(filter, createReply);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        return header;
    }

    public Button buildCreateReply()
    {
        final Button createReply = new Button("Create Reply");
        createReply.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Set<Integer> itemIds = (Set<Integer>) getTable().getValue();

                Set<Query> queries = new HashSet<Query>();
                for(Integer itemId: itemIds)
                {
                    Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(itemId);
                    queries.add(query);
                }

                getUI().addWindow(new CreateMultiQueryReplyWindow(queries));
            }
        });
        createReply.setEnabled(false);
        return createReply;
    }

    @Override
    public Collection<Query> getQueries() {

        Collection<Query> allQueries = TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
        if(getUser().getRoleForCourse(courseID).equals(Role.TA))
        {
            ArrayList<Query> taQueries = new ArrayList<>();

            for(Query query : allQueries)
            {
                if(query.isForwarded() || query.getPrivacy().equals(Query.Privacy.PUBLIC) || query.getPrivacy().equals(Query.Privacy.ADMINISTRATOR))
                {
                    taQueries.add(query);
                }
            }
            return taQueries;
        }
        else
        {
            return allQueries;
        }
    }
}
