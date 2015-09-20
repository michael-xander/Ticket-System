package view.dashboard.convener.views;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.answer.template.TemplateAnswer;
import view.TicketSystemUI;
import view.dashboard.UserTableView;
import view.dashboard.convener.windows.CreateTemplateWindow;

import java.util.Collection;

/**
 * The table view for a convener's template answers
 * Created by Michael on 2015/09/20.
 */
public class AdminTemplatesTableView extends UserTableView{

    public AdminTemplatesTableView()
    {
        super.setTable(buildTable());
        addComponent(buildToolbar("Templates"));
        addTemplatesToTable(getTemplates());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {

            }
        });
    }

    /**
     * Build the table of templates
     * @return the template table
     */
    @Override
    public Table buildTable() {
        final Table table = new Table();
        table.setSizeFull();
        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);

        table.setSelectable(true);

        table.addContainerProperty("Description", String.class, "(default)");
        table.addContainerProperty("Template", String.class, "(default)");

        table.setImmediate(true);

        return table;
    }

    /**
     * Build the filter for the templates table
     * @return the filter component
     */
    @Override
    public Component buildFilter() {
        final TextField filter = new TextField();

        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                Container.Filterable data = (Container.Filterable) getTable().getContainerDataSource();
                data.removeAllContainerFilters();
                data.addContainerFilter(new Container.Filter() {
                    @Override
                    public boolean passesFilter(Object o, Item item) throws UnsupportedOperationException {
                        if (textChangeEvent.getText() == null || textChangeEvent.getText().equals(""))
                            return true;

                        return filterByProperty("Description", item, textChangeEvent.getText())
                                || filterByProperty("Template", item, textChangeEvent.getText());
                    }

                    @Override
                    public boolean appliesToProperty(Object propertyId) {
                        if (propertyId.equals("Description") || propertyId.equals("Template"))
                            return true;
                        return false;
                    }
                });
            }
        });
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    /**
     * Builds the toolbar
     * @param toolbarHeader - the title for the toolbar
     * @return the built toolbar
     */
    @Override
    public Component buildToolbar(String toolbarHeader) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(toolbarHeader);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        Component createTemplate = buildCreateTemplate();
        HorizontalLayout tools = new HorizontalLayout(filter, createTemplate);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        return header;
    }

    /**
     * Build the create template button
     * @return - the built create template button
     */
    public Button buildCreateTemplate()
    {
        final Button createTemplate = new Button("Create Template");

        createTemplate.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new CreateTemplateWindow());
            }
        });

        return createTemplate;
    }

    /**
     * Gets templates from the database for the convener
     * @return collection of templates
     */
    public Collection<TemplateAnswer> getTemplates()
    {
        return TicketSystemUI.getDaoFactory().getTemplateAnswerDAO().getTemplateAnswersForUser(getUser().getUserID());
    }

    /**
     * Add template to the table
     * @param templateAnswer - template to be added to the table
     */
    public void addTemplateToTable(TemplateAnswer templateAnswer)
    {
        super.getTable().addItem(new Object[] {
                templateAnswer.getQuestion(),
                templateAnswer.getAnswer()
        }, templateAnswer.getID());
    }

    /**
     * Add template collection to the table
     * @param templates - templates to be added to the table
     */
    public void addTemplatesToTable(Collection<TemplateAnswer> templates)
    {
        for(TemplateAnswer templateAnswer : templates)
        {
            addTemplateToTable(templateAnswer);
        }
    }
}
