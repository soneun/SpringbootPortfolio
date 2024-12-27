package com.mysite.nara.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.Collection;


public class ShoppingListDTO {
    private Long id;

    private String listId;

    @NotBlank(message = "이름을 2자 이상 입력해 주세요")

    private String name;

    @NotBlank(message = "설명을 입력해 주세요")
    private String description;

    @Min(value = 10, message = "비용은 10이상으로 작성해 주세요")
    private int price;

    private Date date;

    private String dateString;

    private MultipartFile imageFile;

    //private String imageFileName;





    public ShoppingListDTO(Long id, String listId, String name, String description, int price, Date date, String dateString) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.dateString = dateString;
    }



    public ShoppingListDTO() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }


    @Override
    public String toString() {
        return "ShoppingListDTO{" +
                "id=" + id +
                ", listId='" + listId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", dateString='" + dateString + '\'' +
                '}';
    }

    public ShoppingListDTO(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

//    public ShoppingListDTO(String imageFileName) {
//        this.imageFileName = imageFileName;
//    }
//
//    public String getImageFileName() {
//        return imageFileName;
//    }
//
//    public void setImageFileName(String imageFileName) {
//        this.imageFileName = imageFileName;
//    }
}
