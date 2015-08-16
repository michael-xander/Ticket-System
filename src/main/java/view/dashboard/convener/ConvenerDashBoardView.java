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
    //factory object to get access to the data access objects
    private DaoFactory daoFactory;

    //the ID of the query currently in focus
    private int queryID;

    //the reply form
    private FormLayout replyFormLayout;

    //the components of the reply form
    private TextArea replyContent;
    private User convener;

    //label to show the user's name
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

        //initiate and add a log out button that takes user back to login page once pressed
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

        //set the reply form invisible till user clicks a particular query
        replyFormLayout.setVisible(false);
    }

    /*
     * A method that generates the labels for the user to add to the view
     */
    private Component buildUserLabels()
    {
        userNameLabel = new Label();
        return userNameLabel;
    }

    /*
     * A method that generates the reply form for the reply form
     */
    private FormLayout buildReplyForm()
    {
        FormLayout replyForm = new FormLayout();
        replyForm.setSpacing(true);

        replyContent = new TextArea("Message");
        Button sendButton = new Button("Send");
        HorizontalLayout buttons = new HorizontalLayout(sendButton);
        buttons.setSpacing(true);

        //on pressing the send button, a reply is created and the user notified of it
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

    /*
     * A method that generates the query table
     */
    private VerticalLayout buildQueryTable() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Grid queryGrid = new Grid();

        //specifying the columns and their order for the course
        queryGrid.addColumn("ID", String.class);
        queryGrid.addColumn("Query", String.class);
        queryGrid.addColumn("Status", String.class);
        queryGrid.addColumn("Date", String.class);
        queryGrid.setColumnOrder("ID", "Query", "Status", "Date");

        //add each query to the table
        for (Query query : daoFactory.getQueryDao().getAllQueries())
        {
            queryGrid.addRow(query.getMessageID()+"", query.getText(), query.getStatus().toString(), query.getDate().toString());
        }

        //when an item is clicked on the table, make the reply form visible to the user
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

    /*
     * A method that creates and inputs a reply to the data base by getting the content from the fields
     */
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

        //empty the text field for the input
        replyContent.clear();
    }

    /*
     * A method thats called when the navigator navigates to this page
     */
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
        navigator = viewChangeEvent.getNavigator();

        //read the user ID attribute to create an instance of the convener
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
