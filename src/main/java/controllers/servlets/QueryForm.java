package controllers.servlets;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import data.access.DaoFactory;
import model.domain.message.Query;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by william on 15-08-2015.
 */
public class QueryForm extends FormLayout {

    //components required
    ComboBox privacy;
    ComboBox category;

    TextField subject = new TextField("Subject");
    TextArea queryContent = new TextArea("Message");

    Button submitButton = new Button("Submit");
    Button clearButton = new Button("Clear");

    LocalDate currentDate;

    ArrayList<String> privacyList =new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();

    private DaoFactory daoFactory;

    public QueryForm(){
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUser = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");

        daoFactory = new DaoFactory(dbUrl, dbUser, dbPassword);

        fillForm();
        buildLayout();
    }

    private void fillForm(){
        /* initialize the boxes */
        privacyList.add("General"); privacyList.add("Public"); privacyList.add("Private");
        categoryList.add("Test"); categoryList.add("Exam"); categoryList.add("Mark Query");
        privacy = new ComboBox("Privacy", privacyList);
        category = new ComboBox("Category", categoryList);

        clearButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                clearForm();
            }
        });

        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //check if all fields are all
                if (privacy.getValue() == null || category.getValue() == null || subject.getValue() == null || queryContent.getValue() == null) {
                    // can't create query.
                    Notification.show("Enter all information", Notification.Type.WARNING_MESSAGE);

                } else {
                    //add query to database
                    submitQuery();
                }


            }

        });
    }

    private void buildLayout(){
        setMargin(true);

        HorizontalLayout boxes = new HorizontalLayout(privacy, category);
        boxes.setSpacing(true);
        HorizontalLayout buttons = new HorizontalLayout(submitButton, clearButton);
        buttons.setSpacing(true);

        addComponents(boxes, subject, queryContent, buttons);
    }

    private void setQueryPrivacy(Query query){
        String privacySet = (String) privacy.getValue();
        switch (privacySet){
            case "General" :
                query.setPrivacy(Query.Privacy.GENERAL);
                break;
            case "Public" :
                query.setPrivacy(Query.Privacy.PUBLIC);
                break;
            case "Private" :
                query.setPrivacy(Query.Privacy.PRIVATE);

        }
    }

    private void submitQuery(){
        //submit query to database

        Query query = new Query();

        //query.setCategoryID();
        setQueryPrivacy(query);
        query.setSubject(subject.getValue());
        query.setText(queryContent.getValue());

        query.setDate(currentDate.now());

        //query.setCourseID();
        //query.setSender();
        
        // add query to database
        daoFactory.getQueryDao().addQuery(query);
        query.setStatus(Query.Status.PENDING);
        Notification.show("Query submitted", Notification.Type.TRAY_NOTIFICATION);

    }

    private void clearForm(){
        queryContent.clear();
        subject.clear();
        privacy.clear();
        category.clear();
    }
}
