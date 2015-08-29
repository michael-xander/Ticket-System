package view.dashboard.convener.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Message;
import model.domain.message.Query;
import view.TicketSystemUI;

import java.time.LocalDate;
import java.util.List;

/**
 * A view for the course convener to observe queries
 * Created by Michael on 2015/08/18.
 */
public class AdminCourseQueryView extends VerticalLayout implements View
{

    private String courseID;
    private final Table table;

    public AdminCourseQueryView(String courseID)
    {
        this.courseID = courseID;
        setSizeFull();
        setSpacing(true);

        addComponent(buildToolbar());

        table = buildTable();
        addComponent(table);
        setExpandRatio(table, 1);
    }

    private Component buildToolbar()
    {
        HorizontalLayout header = new HorizontalLayout();
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(courseID + " Queries");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        header.addComponent(filter);

        return header;
    }

    private Component buildFilter()
    {
        final TextField filter = new TextField();
        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    private Table buildTable()
    {
        final Table table = new Table();
        table.setSizeFull();

        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);
        table.addContainerProperty("Date", String.class, "(default)");
        table.addContainerProperty("Subject", String.class, "(default)");
        table.addContainerProperty("Sender", String.class, "(default)");
        table.addContainerProperty("Course", String.class, "(default)");
        table.addContainerProperty("Privacy", String.class, "(default)");
        table.addContainerProperty("Status", String.class, "(default)");

        for(Query query : getAllQueriesForCourse())
        {
            table.addItem(new Object[] {
                    query.getDate().toString(),
                    query.getSubject(),
                    query.getSenderID(),
                    query.getCourseID(),
                    query.getPrivacy().toString(),
                    query.getStatus().toString()
            }, query.getMessageID());

        }

        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int queryId = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryId);
                getUI().addWindow(new ReplyQueryWindow(query));
            }
        });

        table.setImmediate(true);
        return table;
    }

    private List<Query> getAllQueriesForCourse()
    {
        return TicketSystemUI.getDaoFactory().getQueryDao().getAllQueriesForCourse(courseID, Query.Status.PENDING);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    /*
     * A window to create a reply to a query
     */
    private class ReplyQueryWindow extends Window
    {
        private final Query query;
        private RichTextArea richTextArea;

        ReplyQueryWindow(final Query query)
        {
            this.query = query;
            setCaption("Create Reply");
            setModal(true);
            setClosable(false);
            setResizable(false);
            setWidth("40%");
            setContent(buildContent());
        }

        private Component buildContent()
        {
            VerticalLayout view = new VerticalLayout();
            view.setMargin(true);
            view.setSpacing(true);

            Label title = new Label(query.getSubject());
            title.addStyleName(ValoTheme.LABEL_H3);
            title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            view.addComponent(title);

            Label content = new Label(query.getText());
            view.addComponent(content);

            richTextArea = new RichTextArea("Reply");
            richTextArea.setWidth("100%");
            view.addComponent(richTextArea);

            view.addComponent(buildFooter());
            return view;
        }

        private Component buildFooter()
        {
            HorizontalLayout footer = new HorizontalLayout();
            footer.setSpacing(true);
            footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
            footer.setWidth(100.0f, Unit.PERCENTAGE);

            Button cancel = new Button("Cancel");
            cancel.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    close();
                }
            });
            cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

            Button reply = new Button("Reply");
            reply.addStyleName(ValoTheme.BUTTON_PRIMARY);
            reply.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    query.setStatus(Query.Status.REPLIED);
                    TicketSystemUI.getDaoFactory().getQueryDao().updateQueryRole(query);

                    Message reply = new Message();
                    reply.setMessageID(query.getMessageID());
                    reply.setText(richTextArea.getValue());
                    reply.setSender((String) VaadinSession.getCurrent().getAttribute("userID"));
                    reply.setDate(LocalDate.now());

                    TicketSystemUI.getDaoFactory().getReplyDao().addReply(reply);

                    Notification notification = new Notification("Reply sent", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDescription("Your reply has successfully been submitted.");
                    notification.show(Page.getCurrent());
                    table.removeItem(query.getMessageID());
                    close();
                }
            });

            reply.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);
            footer.addComponents(cancel, reply);
            footer.setExpandRatio(cancel, 1);
            footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
            return footer;
        }
    }
}
