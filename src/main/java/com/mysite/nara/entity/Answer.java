package com.mysite.nara.entity;


import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_answer")

public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    //질문 엔티티 참조(외래키)
    @ManyToOne
    private Question question;

    //질문과 추천인가의 관계가 대 대 다 many to many 관계임
    @ManyToMany
    Set<User> voter;

    //외래키 설정
    @ManyToOne
    private User author;

    public Answer() {
    }

    public Answer(String content, int id, LocalDateTime createDate, LocalDateTime modifyDate, Question question, Set<User> voter, User author) {
        this.content = content;
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.question = question;
        this.voter = voter;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<User> getVoter() {
        return voter;
    }

    public void setVoter(Set<User> voter) {
        this.voter = voter;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", question=" + question +
                ", voter=" + voter +
                ", author=" + author +
                '}';
    }
}