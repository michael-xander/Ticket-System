package views;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import data.access.DaoFactory;
import data.access.message.QueryDao;
import data.access.message.ReplyDao;
import model.domain.message.Message;
import model.domain.message.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by william on 15-08-2015.
 */
public class ReplyForm extends FormLayout {

    //components required
    TextArea replyContent = new TextArea("Message");
    Button sendButton = new Button("Send");
    DaoFactory daoFactory;
    Query query;

    public ReplyForm(DaoFactory daoFactory){
        buildLayout();
        this.daoFactory = daoFactory;
    }

    private void buildLayout(){
        setMargin(true);
        HorizontalLayout buttons = new HorizontalLayout(sendButton);
        buttons.setSpacing(true);
        sendButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                sendButton.setComponentError(null);
                Notification notification = new Notification("Successful Reply");
                notification.setDescription("Your reply was successfully sent");
                reply(query.getMessageID(), replyContent.getValue());
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(Page.getCurrent());
            }
        });
        addComponents(replyContent, buttons);
    }

    public void reply(int queryID,String message){
        QueryDao queryDao = daoFactory.getQueryDao();
        ReplyDao replyDao = daoFactory.getReplyDao();
        Query query = queryDao.getQuery(queryID);
        query.setStatus(Query.Status.REPLIED);
        queryDao.deleteQuery(queryID);
        queryDao.addQuery(query);
        Message reply = new Message();
        reply.setMessageID(queryID);
        reply.setText(message);
        reply.setSender("TEST");
        reply.setDate(LocalDate.now());
        replyDao.addReply(reply);
        replyContent.clear();

    }

    public void showQuery(int queryID){
        Query query = daoFactory.getQueryDao().getQuery(queryID);
        this.query=query;
    }

}
