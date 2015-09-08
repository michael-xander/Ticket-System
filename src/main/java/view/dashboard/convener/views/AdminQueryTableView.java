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

import java.time.LocalDate;
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
                int queryID = (Integer) itemClickEvent.getItemId();
                Query query = TicketSystemUI.getDaoFactory().getQueryDao().getQuery(queryID);
                getUI().addWindow(new ReplyQueryWindow(query));
            }
        });
    }

    public abstract Collection<Query> getQueries();

    @Override
    public Component buildToolbar(String toolbarHeader) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(toolbarHeader);
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        header.addComponent(filter);
        header.setExpandRatio(title, 1);

        return header;
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
                    getTable().removeItem(query.getMessageID());
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
