package model.domain.answer.template;

import java.io.Serializable;

/**
 * A class that represents a template answer from which a convener can pick from
 * Created by Michael on 2015/09/20.
 */
public class TemplateAnswer implements Serializable{

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String question;
    private String answer;

}
