package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import view.dashboard.InfoWindow;

/**
 * A popup window that gives shows relevant information for a category
 * Created by Michael on 2015/08/30.
 */

public class CategoryInfoWindow extends InfoWindow
{
    private final Category category;

    public CategoryInfoWindow(final Category category)
    {
        this.category = category;
        setCaption("Category Information");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label title = new Label(category.getCategoryName());
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        Label description = new Label(category.getCategoryDescription());
        view.addComponent(description);

        if(category.getTemplateAnswer() != null && !category.getTemplateAnswer().isEmpty())
        {
            Label answerHeading = new Label("Template Answer:");
            Label templateAnswer = new Label(category.getTemplateAnswer(), ContentMode.HTML);
            view.addComponent(answerHeading);
            view.addComponent(templateAnswer);
        }

        view.addComponent(buildFooter());
        return view;
    }

    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button okay = new Button("Okay");
        okay.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        okay.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button edit = new Button("Edit Category");
        edit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
                UI.getCurrent().addWindow(new EditCategoryWindow(category));
            }
        });
        footer.addComponents(okay, edit);
        footer.setExpandRatio(okay, 1);
        footer.setComponentAlignment(okay, Alignment.TOP_RIGHT);
        return footer;

    }


}
