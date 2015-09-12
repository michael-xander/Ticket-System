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
    }

    public abstract Collection<Query> getQueries();


}
