package controllers.servlets;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import data.access.DaoFactory;
import data.access.message.QueryDao;
import data.access.message.ReplyDao;
import model.domain.message.Message;
import model.domain.message.Query;
import views.QueryTable;
import views.ReplyForm;
import views.StudentInfo;

import java.time.LocalDate;

/**
 * Created by marcelo on 15-08-2015.
 */

public class ReplyView extends HorizontalLayout implements View  {

    private DaoFactory daoFactory;
    QueryTable queryTable;
    public ReplyView(){
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUserName = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");
        setSizeFull();
        daoFactory = new DaoFactory(dbUrl, dbUserName, dbPassword);
        ReplyForm replyForm = new ReplyForm(daoFactory);
        queryTable = new QueryTable(daoFactory, replyForm);
        addComponents(queryTable, replyForm);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        Page.getCurrent().setTitle("All Queries");
    }

    private Component buildFields()
    {

        return null;
    }


}
