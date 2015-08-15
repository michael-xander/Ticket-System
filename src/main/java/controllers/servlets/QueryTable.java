package controllers.servlets;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import model.domain.message.Query;

/**
 * Created by william on 15-08-2015.
 */

public class QueryTable extends VerticalLayout{

    Grid queryTable;

    public QueryTable(){
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
