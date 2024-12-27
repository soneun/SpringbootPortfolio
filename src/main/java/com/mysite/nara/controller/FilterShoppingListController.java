package com.mysite.nara.controller;

import com.mysite.nara.dto.ShoppingFilterDTO;
import com.mysite.nara.dto.ShoppingListDTO;
import com.mysite.nara.entity.ShoppingList;
import com.mysite.nara.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.ParseException;
import java.util.List;

@Controller
public class FilterShoppingListController {

    @Autowired
    private ShoppingListService listService;


    @GetMapping("/filtershoppinglists")
    public String filterShoppingLists(@ModelAttribute("filter") ShoppingFilterDTO filter,
                                      Model model) throws ParseException {
        System.out.println(filter);
        List<ShoppingListDTO> listDTO = listService.getFilterShoppingLists(filter);
        model.addAttribute("shoppingLists", listDTO);
        int total = listService.totalShoppingLists(listDTO);
        model.addAttribute("total", total);

        return "shopping_list";
    }

}
