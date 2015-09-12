package view.dashboard.convener.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Message;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.UserQueryTableView;
import view.dashboard.convener.windows.CreateQueryReplyWindow;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Michael on 2015/08/29.
 */
public abstract class AdminQueryTableView extends UserQueryTableView {

    public AdminQueryTableView()
    {
        super.setTable(buildTable());

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int queryID = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryID);
                getUI().addWindow(new CreateQueryReplyWindow(query));
            }
        });
    }

    public abstract Collection<Query> getQueries();

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

}
