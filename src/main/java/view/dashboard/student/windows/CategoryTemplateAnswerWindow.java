package view.dashboard.student.windows;

import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.PopupWindow;

/**
 * A popup window that shows the user the template answer for a category
 * Created by Michael on 2015/08/31.
 */
public class CategoryTemplateAnswerWindow extends PopupWindow {

    private final Category category;
    private final Query query;

    public CategoryTemplateAnswerWindow(final Category category, final Query query)
    {
        this.category = category;
        this.query = query;
        setCaption("Does this answer your query?");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label templateAnswer = new Label(category.getTemplateAnswer());
        view.addComponent(templateAnswer);

        view.addComponent(buildFooter());

        return view;
    }

    @Override
    public Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button yes = new Button("Yes");
        yes.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                query.setStatus(Query.Status.REPLIED);
                TicketSystemUI.getDaoFactory().getQueryDao().addQuery(query);
                Notification notification = new Notification("Query created and answered", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDescription("Your query has successfully been created and replied to.");
                notification.show(Page.getCurrent());

                close();
                UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
            }
        });

        Button no = new Button("No");
        no.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                TicketSystemUI.getDaoFactory().getQueryDao().addQuery(query);
                Notification notification = new Notification("Query created", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDescription("Your query has successfully been submitted.");
                notification.show(Page.getCurrent());
                
                close();
                UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
            }
        });
        no.addStyleName(ValoTheme.BUTTON_PRIMARY);

        footer.addComponents(yes, no);
        footer.setExpandRatio(yes, 1);
        footer.setComponentAlignment(yes, Alignment.TOP_RIGHT);

        return footer;
    }
}
