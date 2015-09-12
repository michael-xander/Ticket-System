package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.message.Query;
import view.dashboard.CreateWindow;

import java.util.Collection;

/**
 * A window that enables a user to reply to multiple queries
 * Created by Michael on 2015/09/12.
 */
public class CreateMultiQueryReplyWindow extends CreateWindow {
    private final Collection<Query> queries;
    private RichTextArea richTextArea;

    public CreateMultiQueryReplyWindow(final Collection<Query> queries)
    {
        this.queries = queries;
        setCaption("Create Reply");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("50%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    @Override
    public void setSaveButtonFunction()
    {
        getSaveButton().setCaption("Reply");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    Notification notification = new Notification("Reply sent", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDescription("Your reply has successfully been submitted.");
                    notification.setDelayMsec(2500);
                    notification.show(Page.getCurrent());
                    close();
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
        richTextArea.setComponentError(null);

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A reply must be provided"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        for(Query query : queries)
        {
            VerticalLayout tempView = new VerticalLayout();
            tempView.setSpacing(true);

            Label author = new Label("Query by: " + query.getSenderID());
            author.addStyleName(ValoTheme.LABEL_H2);
            author.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            tempView.addComponent(author);

            Label title = new Label("Subject: " + query.getSubject());
            title.addStyleName(ValoTheme.LABEL_H3);
            title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            tempView.addComponent(title);

            Label content = new Label(query.getText());
            tempView.addComponent(content);

            view.addComponent(tempView);
        }

        richTextArea = new RichTextArea("Reply");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());
        return view;
    }
}
