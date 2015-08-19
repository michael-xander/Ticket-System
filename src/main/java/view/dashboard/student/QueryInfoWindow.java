package view.dashboard.student;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Message;
import model.domain.message.Query;
import view.TicketSystemUI;

/**
 * A simple window to see the information of a query
 * Created by Michael on 2015/08/18.
 */
public class QueryInfoWindow extends Window
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

        Message reply = getQueryReply();

        if(reply != null)
        {
            Label replyHeading = new Label("Reply:");
            Label replyContent = new Label(reply.getText());
            view.addComponent(replyHeading);
            view.addComponent(replyContent);
        }

        view.addComponent(buildFooter());
        return view;
    }

    private Message getQueryReply()
    {
        Message reply = TicketSystemUI.getDaoFactory().getReplyDao().getReply(query.getMessageID());
        return reply;
    }

    private Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Okay");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);

        footer.addComponent(ok);
        footer.setExpandRatio(ok, 1);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
}
