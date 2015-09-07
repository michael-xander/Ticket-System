package view.dashboard;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import java.util.Collection;

/**
 * A view that displays faqs to a User
 * Created by Marcelo on 30/08/2015.
 */
public abstract class UserFaqTableView extends UserTableView {

    @Override
    public Table buildTable() {
        final Table table = new Table();
        table.setSizeFull();

        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

        table.addContainerProperty("Frequently Asked Questions", String.class, "(default)");

        table.setImmediate(true);

        return table;
    }

    @Override
    public Component buildFilter() {
        final TextField filter = new TextField();
        filter.setInputPrompt("Filter");
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    @Override
    public abstract Component buildToolbar(String toolbarHeader);

    public abstract Collection<Faq> getFaqs();

    public void addFaqToTable(Faq faq)
    {
        super.getTable().addItem(new Object[] {
                faq.getQuestion()
        }, faq.getFaqID());
    }

    public void addFaqsToTable(Collection<Faq> faqs)
    {
        for(Faq faq : faqs)
        {
            addFaqToTable(faq);
        }
    }

}
