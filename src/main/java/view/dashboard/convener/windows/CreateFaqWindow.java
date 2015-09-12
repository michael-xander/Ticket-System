package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import model.domain.faq.Faq;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;
import java.time.LocalDate;

/**
 * A simple window displayed to create a faq
 * Created by Marcelo on 2015/08/31.
 */

public class CreateFaqWindow extends CreateWindow
{
    private final String courseID;

    private TextField question;
    private RichTextArea richTextArea;

    public CreateFaqWindow(final String courseID)
    {
        this.courseID = courseID;
        setCaption("Create Faq for " + courseID);
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    @Override
    public Component buildContent()
    {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        question = new TextField("Question");
        question.setWidth("100%");
        view.addComponent(question);

        richTextArea = new RichTextArea("Answer");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }

    @Override
    public void setSaveButtonFunction() {
            getSaveButton().addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    getSaveButton().setComponentError(null);
                    if(inputIsValid())
                    {
                        Faq faq = new Faq();
                        faq.setQuestion(question.getValue());
                        faq.setAnswer(richTextArea.getValue());
                        faq.setDate(LocalDate.now());
                        faq.setCourseID(courseID);

                        TicketSystemUI.getDaoFactory().getFaqDao().addFaq(faq);
                        Notification notification = new Notification("Faq created", Notification.Type.HUMANIZED_MESSAGE);
                        notification.setDelayMsec(2500);
                        notification.setDescription("Your faq has successfully been submitted.");
                        notification.show(Page.getCurrent());

                        close();
                        UI.getCurrent().getNavigator().navigateTo(faq.getCourseID() + " FAQs");
                    }
                    else
                    {
                        getSaveButton().setComponentError(new UserError("Input provided is invalid"));
                    }
                }
            });
    }

    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        question.setComponentError(null);
        richTextArea.setComponentError(null);

        if(question.getValue().isEmpty())
        {
            isValid = false;
            question.setComponentError(new UserError("A question has to be specified for an FAQ"));
        }

        if(richTextArea.getValue().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("An answer has to be specified for an FAQ"));
        }
        return isValid;
    }

}
