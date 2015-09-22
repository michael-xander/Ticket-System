package view.dashboard.student.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Message;
import model.domain.message.Query;
import model.domain.message.Reply;
import model.domain.user.Role;
import model.domain.user.User;
import view.TicketSystemUI;
import view.dashboard.InfoWindow;
import view.dashboard.convener.windows.ConvenerQueryReplyWindow;
import view.dashboard.convener.windows.CreateQueryReplyWindow;

import java.util.Collection;

/**
 * A simple window to see the information of a query
 * Created by Michael on 2015/08/18.
 */
public class QueryInfoWindow extends InfoWindow
{
    private final Query query;

    public QueryInfoWindow(final Query query)
    {
        this.query = query;
        setCaption("View Query");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    @Override
    public Component buildContent()
    {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label title = new Label(query.getSubject());
        title.setCaptionAsHtml(true);
        title.setCaption("<u>Query</u>");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        Label content = new Label(query.getText(), ContentMode.HTML);
        view.addComponent(content);

        Collection<Reply> replies = getQueryReplies();

        if(!replies.isEmpty())
        {
            Label conversationLabel = new Label("<u>Conversation</u>", ContentMode.HTML);
            view.addComponent(conversationLabel);
        }

        for(Reply reply : replies)
        {
            User user;
            if(reply.getSenderID().equals(getUser().getUserID()))
            {
                user = getUser();
            }
            else
            {
                user = TicketSystemUI.getDaoFactory().getUserDao().getUser(reply.getSenderID());
            }

            Label replyLabel = new Label(reply.getText(), ContentMode.HTML);
            replyLabel.setCaption(user.getFirstName() + " " + user.getLastName() + ":");
            view.addComponent(replyLabel);
        }


        view.addComponent(buildFooter());
        return view;
    }

    /**
     * Builds the okay and add message buttons at the bottom of the query info window
     * @return the footer
     */
    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button okay = new Button("Okay");
        okay.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        okay.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button addMessage = new Button("Add Message");
        addMessage.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addMessage.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
                if(getUser().getRoleForCourse(query.getCourseID()).equals(Role.TA))
                {
                    UI.getCurrent().addWindow(new ConvenerQueryReplyWindow(query));
                }
                else
                {
                    UI.getCurrent().addWindow(new CreateQueryReplyWindow(query));

                }
            }
        });
        footer.addComponents(okay, addMessage);
        footer.setExpandRatio(okay, 1);
        footer.setComponentAlignment(okay, Alignment.TOP_RIGHT);
        return footer;
    }

    private Collection<Reply> getQueryReplies()
    {
        return TicketSystemUI.getDaoFactory().getReplyDao().getAllRepliesForQueryID(query.getMessageID());
    }

}
