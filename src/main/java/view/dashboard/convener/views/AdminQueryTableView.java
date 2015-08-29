package view.dashboard.convener.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.dashboard.UserQueryTableView;

import java.util.Collection;

/**
 * Created by Michael on 2015/08/29.
 */
public abstract class AdminQueryTableView extends UserQueryTableView {

    public AdminQueryTableView()
    {
        setSizeFull();
        setSpacing(true);
        super.setTable(buildTable());

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {

            }
        });
    }

    public abstract Collection<Query> getQueries();

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

        return header;
    }
}
