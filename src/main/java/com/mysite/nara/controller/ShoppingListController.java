package com.mysite.nara.controller;

import com.mysite.nara.dto.ShoppingFilterDTO;
import com.mysite.nara.dto.ShoppingListDTO;
import com.mysite.nara.entity.ShoppingList;
import com.mysite.nara.repository.ShoppingListRepository;
import com.mysite.nara.service.ShoppingListService;
import com.mysite.nara.util.DateTimeUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
public class ShoppingListController {

  @Autowired
  private ShoppingListService listService;
  @Autowired
  private ShoppingListRepository listRepo;

  //쇼핑 리스트 가져오기
  @GetMapping("/shoppinglists")
    public String showShoppingLists(Model model) {
        List<ShoppingListDTO> listDTO = listService.getAllShoppingLists();
        model.addAttribute("shoppingLists", listService.getAllShoppingLists());
        model.addAttribute("filter", new ShoppingFilterDTO());
        int total = listService.totalShoppingLists(listDTO);
        model.addAttribute("total", total);
        return "shopping_list";
    }

    //get 요청시 비용 입력을 위한 창을 보여주기
    @GetMapping("/createList")
    public String createList(Model model) {
        model.addAttribute("shoppingLists", new ShoppingListDTO());
        return "shopping_form";
    }

    //구매 목록 저장/ 수정
    @PostMapping("/saveOrUpdateshopping")
    public String saveOrUpdateShopping(@Valid @ModelAttribute("shoppingLists") ShoppingListDTO shoppingListDTO,
                                       BindingResult result) throws ParseException {
       if (shoppingListDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("shoppingLists", "imageFile", "이미지 파일을 넣어주세요"));
        }
        if(result.hasErrors()) {
            return "shopping_form";
        }
        //성공시 DB 저장
        MultipartFile image = shoppingListDTO.getImageFile();
        Date createDate = new Date();
        String storeFileName = createDate.getTime()+"_"+image.getOriginalFilename();
        //이미지를 public/images 폴더에 저장
        try {
            String uploadDir = "public/images/";//저장주소문자열
            Path uploadPath = Paths.get(uploadDir);//업로드주소 객체
            if(!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {

        }
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListDTO.getName());
        shoppingList.setDescription(shoppingListDTO.getDescription());
        shoppingList.setDate(shoppingListDTO.getDate());
        shoppingList.setPrice(shoppingListDTO.getPrice());
        shoppingList.setImageFileName(storeFileName);

        listRepo.save(shoppingList);




        //서비스의 메서드로 shoppingListDTO를 DB에 저장
        listService.saveShoppingDetails(shoppingListDTO);

        return "redirect:/shoppinglists";

    }

    //구매 목록 삭제
    @GetMapping("/deleteShopping")
    public String deleteShopping(@RequestParam("id") String listId) {

      try {
          ShoppingList shoppingList = listRepo.findByListId(listId).get();
          //이미지 파일 삭제하기
          String uploadDir = "public/images/";
          Path imagePath = Paths.get(uploadDir + shoppingList.getImageFileName());

          try {
              Files.delete(imagePath);

          }catch (Exception e){
              e.printStackTrace();
          }
          //제품 삭제
          //listRepo.delete(shoppingList);
          listService.deleteShoppingList(listId);

      }catch (Exception e) {
          e.printStackTrace();
      }
        return "redirect:/shoppinglists";
    }


    //수정할 페이지 보여주기
    @GetMapping("/updateShopping")
    public String updateShopping(@RequestParam("id") String listId, Model model) {

      try {
          ShoppingList shoppingList = listRepo.findByListId(listId).get();
          model.addAttribute("shoppingLists", listService.getListById((listId)));

          ShoppingListDTO shoppingListDTO = new ShoppingListDTO();
          shoppingListDTO.setName(shoppingList.getName());
          shoppingListDTO.setDescription(shoppingList.getDescription());
          shoppingListDTO.setDate(shoppingList.getDate());
          shoppingListDTO.setPrice(shoppingList.getPrice());


          model.addAttribute("shoppingListDTO", shoppingListDTO);

      }catch (Exception e) {
          e.printStackTrace();
      }

        return "shopping_form";
    }


}
