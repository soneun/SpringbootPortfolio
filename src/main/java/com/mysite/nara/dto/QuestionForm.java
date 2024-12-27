package com.mysite.nara.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuestionForm {

    @NotBlank(message = "제목은 필수항목입니다")
    @Size(max = 200, message = "제목은 200자 이하입니다")
    private String subject;

    @NotBlank(message = "내용은 필수 항목입니다")

    private String content;

    public QuestionForm() {
    }

    public QuestionForm(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionForm{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
