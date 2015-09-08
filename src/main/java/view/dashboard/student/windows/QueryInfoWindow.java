package view.dashboard.student.windows;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Message;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.InfoWindow;

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
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        Label content = new Label(query.getText(), ContentMode.HTML);
        view.addComponent(content);

        Message reply = getQueryReply();

        if(reply != null)
        {
            Label replyHeading = new Label("Reply:");
            Label replyContent = new Label(reply.getText(), ContentMode.HTML);
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

}
