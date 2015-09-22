package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import model.domain.answer.template.TemplateAnswer;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

/**
 * A simple window to create a template
 * Created by Michael on 2015/09/20.
 */
public class CreateTemplateWindow extends CreateWindow
{
    private TextField description;
    private RichTextArea richTextArea;

    public CreateTemplateWindow()
    {
        setCaption("Create Template");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    /**
     * Set the functionality of the save button
     */
    @Override
    public void setSaveButtonFunction() {
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    TemplateAnswer templateAnswer = new TemplateAnswer();
                    templateAnswer.setUser(getUser().getUserID());
                    templateAnswer.setQuestion(description.getValue().trim());
                    templateAnswer.setAnswer(richTextArea.getValue().trim());

                    TicketSystemUI.getDaoFactory().getTemplateAnswerDAO().addTemplateAnswer(templateAnswer);
                    Notification notification = new Notification("Template created", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setDescription("Your template has successfully been created");
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
     * Check whether provided input to window is valid
     * @return - input valid or not
     */
    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        description.setComponentError(null);
        richTextArea.setComponentError(null);

        if(description.getValue().trim().isEmpty())
        {
            isValid = false;
            description.setComponentError(new UserError("A template should have a description"));
        }

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A template should have content"));
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
        view.addComponent(description);

        richTextArea = new RichTextArea("Content");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }
}
