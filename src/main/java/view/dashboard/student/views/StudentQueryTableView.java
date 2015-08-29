package view.dashboard.student.views;

import com.vaadin.event.ItemClickEvent;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.UserQueryTableView;
import view.dashboard.student.windows.QueryInfoWindow;

/**
 * Created by Michael on 2015/08/29.
 */
public abstract class StudentQueryTableView extends UserQueryTableView
{
    public StudentQueryTableView()
    {
        setSizeFull();
        setSpacing(true);
        super.setTable(buildTable());

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int queryID = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryID);
                getUI().addWindow(new QueryInfoWindow(query));
            }
        });
    }
}
