package views;

import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import data.access.DaoFactory;
import data.access.message.QueryDao;
import model.domain.message.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by william on 15-08-2015.
 */

public class QueryTable extends VerticalLayout{

    Grid queryTable;
    DaoFactory daoFactory;
    ReplyForm replyForm;

    public QueryTable(DaoFactory daoFactory, ReplyForm replyForm){
        this.daoFactory= daoFactory;
        queryTable = new Grid();
        configureGrid();
        buildLayout();
        this.replyForm=replyForm;
    }

    private void configureGrid(){

        queryTable.addColumn("ID", String.class);
        queryTable.addColumn("Query", String.class);
        queryTable.addColumn("Status", String.class);
        queryTable.addColumn("Date", String.class);
        queryTable.setColumnOrder("ID","Query","Status","Date");

        List<Query> allQueries = daoFactory.getQueryDao().getAllQueries();
        for (Query query : allQueries){
            queryTable.addRow(query.getMessageID()+"",query.getText(), query.getStatus().name(), query.getDate().toString());
        }

        queryTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Property itemProperty = event.getItem().getItemProperty("ID");
                int queryID = Integer.parseInt(itemProperty.getValue().toString());
                replyForm.showQuery(queryID);
            }
        });

        addComponent(queryTable);
    }


    private void buildLayout(){
        setMargin(true);
    }
}
