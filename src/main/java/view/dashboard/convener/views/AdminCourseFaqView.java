package view.dashboard.convener.views;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import view.TicketSystemUI;
import view.dashboard.convener.windows.CreateFaqWindow;

import java.util.Collection;

/**
 * A view that shows the queries for a particular course for a student
 * Created by Michael on 2015/08/17.
 */
public class AdminCourseFaqView extends AdminFaqTableView
{
    private String courseID;

    public AdminCourseFaqView(String courseID)
    {
        super();
        this.courseID = courseID;

        addComponent(buildToolbar(this.courseID + " Faqs"));

        super.addFaqsToTable(getFaqs());

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
        Component createFaq = buildCreateFaq();
        HorizontalLayout tools = new HorizontalLayout(filter, createFaq);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    @Override
    public Collection<Faq> getFaqs() {
        return TicketSystemUI.getDaoFactory().getFaqDao().getAllFaqsForCourse(courseID);
    }

    public Button buildCreateFaq()
    {
        final Button createFaq= new Button("Create Faq");

        createFaq.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().addWindow(new CreateFaqWindow(courseID));
            }
        });

        return createFaq;
    }
}
