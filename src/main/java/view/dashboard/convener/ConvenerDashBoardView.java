package view.dashboard.convener;

import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import data.access.DaoFactory;
import model.domain.message.Message;
import model.domain.message.Query;
import model.domain.user.User;

import java.time.LocalDate;

/**
 * Created by Michael on 2015/08/15.
 */
public class ConvenerDashBoardView extends VerticalLayout implements View
{
    private DaoFactory daoFactory;
    private int queryID;
    private FormLayout replyFormLayout;
    private TextArea replyContent;
    private User convener;
    private Label userNameLabel;
    private Navigator navigator;

    public ConvenerDashBoardView()
    {
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUserName = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");
        daoFactory = new DaoFactory(dbUrl, dbUserName, dbPassword);
        setMargin(true);
        setSpacing(true);

        Button logoutButton = new Button("Log out");
        logoutButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        logoutButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo("login");
            }
        });

        addComponent(logoutButton);
        setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
        addComponent(buildUserLabels());

        replyFormLayout = buildReplyForm();
        HorizontalLayout horizontalLayout = new HorizontalLayout(buildQueryTable(), replyFormLayout);
        horizontalLayout.setSpacing(true);
        addComponent(horizontalLayout);
        replyFormLayout.setVisible(false);
    }

    private Component buildUserLabels()
    {
        userNameLabel = new Label();
        return userNameLabel;
    }

    private FormLayout buildReplyForm()
    {
        FormLayout replyForm = new FormLayout();
        replyForm.setSpacing(true);

        replyContent = new TextArea("Message");
        Button sendButton = new Button("Send");
        HorizontalLayout buttons = new HorizontalLayout(sendButton);
        buttons.setSpacing(true);

        sendButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                createReply();
                Notification notification = new Notification("Successful Reply");
                notification.setDescription("Your reply was successfully sent");
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(Page.getCurrent());
                replyFormLayout.setVisible(false);
            }
        });
        replyForm.addComponents(replyContent, buttons);
        return replyForm;
    }

    private VerticalLayout buildQueryTable() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Grid queryGrid = new Grid();
        queryGrid.addColumn("ID", String.class);
        queryGrid.addColumn("Query", String.class);
        queryGrid.addColumn("Status", String.class);
        queryGrid.addColumn("Date", String.class);
        queryGrid.setColumnOrder("ID", "Query", "Status", "Date");

        for (Query query : daoFactory.getQueryDao().getAllQueries())
        {
            queryGrid.addRow(query.getMessageID()+"", query.getText(), query.getStatus().toString(), query.getDate().toString());
        }

        queryGrid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                Property itemProperty = itemClickEvent.getItem().getItemProperty("ID");
                queryID = Integer.parseInt(itemProperty.getValue().toString());
                replyFormLayout.setVisible(true);
            }
        });
        verticalLayout.addComponent(queryGrid);
        verticalLayout.setMargin(true);
        return verticalLayout;
    }

    private void createReply()
    {
        Query query = daoFactory.getQueryDao().getQuery(queryID);
        query.setStatus(Query.Status.REPLIED);
        daoFactory.getQueryDao().updateQueryRole(query);

        Message reply = new Message();
        reply.setMessageID(queryID);
        reply.setText(replyContent.getValue());
        reply.setSender(convener.getUserID());
        reply.setDate(LocalDate.now());
        daoFactory.getReplyDao().addReply(reply);
        replyContent.clear();
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
        navigator = viewChangeEvent.getNavigator();

        Object tempObject = VaadinSession.getCurrent().getAttribute("userID");

        if(tempObject != null)
        {
            String userID = (String) tempObject;
            convener = daoFactory.getUserDao().getUser(userID);
            userNameLabel.setValue(convener.getFirstName() + " " + convener.getLastName());
        }
        else
        {
            navigator.navigateTo("login");
        }
    }
}
