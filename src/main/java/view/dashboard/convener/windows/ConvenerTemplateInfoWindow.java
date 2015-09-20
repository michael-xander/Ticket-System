package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.answer.template.TemplateAnswer;
import view.dashboard.InfoWindow;

/**
 * A simple window to see the information for a template
 * Created by Michael on 2015/09/20.
 */
public class ConvenerTemplateInfoWindow extends InfoWindow {

    private final TemplateAnswer template;

    public ConvenerTemplateInfoWindow(final TemplateAnswer template)
    {
        this.template = template;
        setCaption("View Template");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    /**
     * Builds the content for the window
     * @return - the layout containing the elements of the window
     */
    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label description = new Label(template.getQuestion());
        description.setCaptionAsHtml(true);
        description.setCaption("<u>Description</u>");
        view.addComponent(description);

        Label content = new Label(template.getAnswer(), ContentMode.HTML);
        content.setCaptionAsHtml(true);
        content.setCaption("<u>Content</u>");
        view.addComponent(content);

        view.addComponent(buildFooter());
        return view ;
    }

    /**
     * Builds the elements in the footer of the window
     * @return - the layout containing the footer elements
     */
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

        Button edit = new Button("Edit Template");
        edit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        footer.addComponents(okay, edit);
        footer.setExpandRatio(okay, 1);
        footer.setComponentAlignment(okay, Alignment.TOP_RIGHT);
        return footer;
    }
}
