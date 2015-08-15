package controllers.servlets;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import data.access.DaoFactory;
import model.domain.message.Query;

/**
 * Created by william on 15-08-2015.
 */

public class QueryTable extends VerticalLayout{

    Grid queryTable;
    private DaoFactory daoFactory;

    public QueryTable(){
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUser = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");

        daoFactory = new DaoFactory(dbUrl, dbUser, dbPassword);


        queryTable = new Grid();
        configureGrid();
        buildLayout();

    }

    private void configureGrid(){
        queryTable.addColumn("Query", String.class);
        queryTable.addColumn("Status", String.class);
        queryTable.setColumnOrder("Query", "Status");
                  

        queryTable.addRow("When is the re-test?", "Pending");
        addComponent(queryTable);

    }

    private void buildLayout(){
        setMargin(true);
    }
}
