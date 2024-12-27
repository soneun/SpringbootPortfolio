package com.mysite.nara.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name = "tbl_list")

public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String listId;

    private String name;

    private String description;

    private int price;

    private Date date;

    @ManyToOne
    private User user;//유저 한명이 여러 비용 등록

    @CreationTimestamp
    private Date createAt;

    private String imageFileName;//이미지 파일명



    public ShoppingList() {
    }

    public ShoppingList(String listId, Long id, String name, String description, int price, Date date, User user) {
        this.listId = listId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.user = user;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", listId='" + listId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", user=" + user +
                '}';
    }

    public ShoppingList(String imageFileName, Date createAt) {
        this.imageFileName = imageFileName;
        this.createAt = createAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
