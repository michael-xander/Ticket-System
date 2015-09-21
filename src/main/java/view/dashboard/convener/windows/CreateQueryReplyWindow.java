package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import model.domain.message.Reply;
import model.domain.user.Role;
import model.domain.user.User;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A window through which a user can reply to a query
 * Created by Michael on 2015/09/12.
 */
public class CreateQueryReplyWindow extends CreateWindow {

    private final Query query;
    private RichTextArea richTextArea;

    public CreateQueryReplyWindow(final Query query)
    {
        this.query = query;

        setCaption("Create Reply");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    @Override
    public void setSaveButtonFunction() {
        getSaveButton().setCaption("Reply");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if (inputIsValid()) {

                    if(getUser().getRoleForCourse(query.getCourseID()).equals(Role.STUDENT))
                    {
                        query.setStatus(Query.Status.PENDING);
                    }
                    else
                    {
                        query.setStatus(Query.Status.REPLIED);
                    }

                    TicketSystemUI.getDaoFactory().getQueryDao().updateQueryRole(query);

                    Reply reply = new Reply();
                    reply.setText(richTextArea.getValue().trim());
                    reply.setSender((String) VaadinSession.getCurrent().getAttribute("userID"));
                    reply.setDate(LocalDate.now());

                    Set<Integer> queryIds = new HashSet<>();
                    queryIds.add(query.getMessageID());
                    reply.setQueryIds(queryIds);
                    TicketSystemUI.getDaoFactory().getReplyDao().addReply(reply);

                    Notification notification = new Notification("Reply sent", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDescription("Your reply has successfully been submitted.");
                    notification.setDelayMsec(2500);
                    notification.show(Page.getCurrent());
                    UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
                    close();
                } else {
                    getSaveButton().setComponentError(new UserError("Input provided is invalid"));
                }
            }
        });

    }

    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        richTextArea.setComponentError(null);

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A reply must be provided"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
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

        richTextArea = new RichTextArea("Reply");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());
        return view;
    }

    private Collection<Reply> getQueryReplies()
    {
        return TicketSystemUI.getDaoFactory().getReplyDao().getAllRepliesForQueryID(query.getMessageID());
    }
}
