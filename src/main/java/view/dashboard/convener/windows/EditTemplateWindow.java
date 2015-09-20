package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import model.domain.answer.template.TemplateAnswer;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

/**
 * A window to edit the contents of a template
 * Created by Michael on 2015/09/20.
 */
public class EditTemplateWindow extends CreateWindow
{
    private final TemplateAnswer template;
    private TextField description;
    private RichTextArea richTextArea;

    public EditTemplateWindow(final TemplateAnswer template)
    {
        this.template = template;
        setCaption("Edit Template");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    /**
     * Set the funtionality of the save button
     */
    @Override
    public void setSaveButtonFunction() {
        getSaveButton().setCaption("Update");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    template.setQuestion(description.getValue().trim());
                    template.setAnswer(richTextArea.getValue().trim());
                    TicketSystemUI.getDaoFactory().getTemplateAnswerDAO().updateTemplateAnswer(template);

                    Notification notification = new Notification("Template updated", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setDescription("Your template has been updated.");
                    notification.show(Page.getCurrent());

                    close();
                    UI.getCurrent().getNavigator().navigateTo("templates");
                }
                else
                {
                    getSaveButton().setComponentError(new UserError("Input provided is invalid"));
                }
            }
        });
    }

    /**
     * Check that input in the fields is valid
     * @return - valid or not valid input
     */
    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        description.setComponentError(null);
        richTextArea.setComponentError(null);

        if(description.getValue().trim().isEmpty())
        {
            isValid = false;
            description.setComponentError(new UserError("A template must have a description"));
        }

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A template must have content"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        description = new TextField("Description");
        description.setWidth("100%");
        description.setValue(template.getQuestion());
        view.addComponent(description);

        richTextArea = new RichTextArea("Content");
        richTextArea.setWidth("100%");
        richTextArea.setValue(template.getAnswer());
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }
}
