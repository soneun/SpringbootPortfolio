package com.mysite.nara.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnswerForm {

    @NotBlank (message = "내용은 필수입니다")
    private String content;

    public AnswerForm(String content) {
        this.content = content;
    }

    public AnswerForm() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AnswerForm{" +
                "content='" + content + '\'' +
                '}';
    }
}
