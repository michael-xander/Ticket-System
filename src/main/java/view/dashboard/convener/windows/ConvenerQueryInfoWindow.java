package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import model.domain.message.Reply;
import model.domain.user.User;
import view.TicketSystemUI;
import view.dashboard.InfoWindow;

import java.util.Collection;

/**
 * A query info window for an Convener
 * Created by Michael on 2015/09/15.
 */
public class ConvenerQueryInfoWindow  extends InfoWindow{

    private final Query query;
    private Label category;
    public ConvenerQueryInfoWindow(final Query query)
    {
        this.query = query;
        setCaption("View Query");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("50%");
        setContent(buildContent());
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        if(query.isForwarded())
        {
            Label forwarded = new Label("This query has been marked as forwarded");
            forwarded.addStyleName(ValoTheme.LABEL_H3);
            forwarded.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            view.addComponent(forwarded);
        }

        Label title = new Label(query.getSubject());
        title.setCaptionAsHtml(true);
        title.setCaption("<u>Query</u>");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        category = new Label(
                "<u>Query Category</u>: " + query.getCategoryName(),
                ContentMode.HTML
        );
        view.addComponent(category);

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
     * Builds the okay, forward and add message buttons at the bottom of the query info window
     * @return the footer
     */
    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Okay");
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                close();
                UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
            }
        });
        ok.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button changeCategory = new Button("Update Category");
        changeCategory.setDescription("Update the category of this query");
        changeCategory.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new UpdateQueryCategoryWindow(query, ConvenerQueryInfoWindow.this));
            }
        });

        Button forward = new Button("Forward Query");
        forward.setDescription("Queries will be forwarded to the TA but will still appear as pending till dealt with");
        forward.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                query.setForwarded(true);
                TicketSystemUI.getDaoFactory().getQueryDao().updateQueryForwardStatus(query);
                Notification notification = new Notification("Query forwarded", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDelayMsec(2500);
                notification.setDescription("The query has been marked as forwarded to the TA");
                notification.show(Page.getCurrent());

                close();
                UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
            }
        });

        Button addMessage = new Button("Add Message");
        addMessage.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addMessage.setDescription("Respond to this query or add a message to it");
        addMessage.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
                UI.getCurrent().addWindow(new ConvenerQueryReplyWindow(query));
            }
        });
        footer.addComponents(ok, changeCategory,forward, addMessage);
        footer.setExpandRatio(ok, 1);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public void updateQueryCategoryLabel()
    {
        category.setContentMode(ContentMode.HTML);
        category.setValue(
                "<u>Query Category</u>: " + query.getCategoryName()
        );
    }

    private Collection<Reply> getQueryReplies()
    {
        return TicketSystemUI.getDaoFactory().getReplyDao().getAllRepliesForQueryID(query.getMessageID());
    }
}
