package com.mysite.nara.service;

import com.mysite.nara.dto.ShoppingFilterDTO;
import com.mysite.nara.dto.ShoppingListDTO;
import com.mysite.nara.entity.ShoppingList;
import com.mysite.nara.entity.User;
import com.mysite.nara.repository.ShoppingListRepository;
import com.mysite.nara.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository listRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    //쇼핑리스트 가져오기
    public List<ShoppingListDTO> getAllShoppingLists() {
        User user = userService.getLoggedInUser();

        List<ShoppingList> list = listRepo.findByUserIdAndDateBetween(
                user.getId(),
                Date.valueOf(LocalDate.now().withDayOfMonth(1)),
                Date.valueOf(LocalDate.now()));

        List<ShoppingListDTO> listDTO = list.stream().map(this::mapToDTO).collect(Collectors.toList());

        return listDTO;


    }

 /*private ShoppingListDTO mapToDTO(ShoppingList list) {
      ShoppingListDTO dto = new ShoppingListDTO();
      dto.setId(list.getId());
      dto.setListId(list.getListId());
      dto.setPrice(list.getPrice());
      dto.setName(list.getName());
      dto.setDescription(list.getDescription());
      dto.setDate(list.getDate());
      return dto;

  }*/
    private ShoppingListDTO mapToDTO(ShoppingList list) {
        ShoppingListDTO listDTO = modelMapper.map(list, ShoppingListDTO.class);
        listDTO.setDateString(DateTimeUtil.convertDateToString(listDTO.getDate()));
        System.out.println("변환 후 : " + listDTO);
        return listDTO;
    }

    public ShoppingListDTO saveShoppingDetails(ShoppingListDTO shoppingListDTO) throws ParseException {
        //DTO -> Entity
        ShoppingList shoppingList = mapToEntity(shoppingListDTO);
        System.out.println(shoppingList);
        shoppingList.setUser(userService.getLoggedInUser());
        //DB에 저장
        shoppingList = listRepo.save(shoppingList);

        //Entity -> DTO
        return mapToDTO(shoppingList);
    }

    //DTO -> Entity
    private ShoppingList mapToEntity(ShoppingListDTO shoppingListDTO) throws ParseException {
        ShoppingList shoppingList = modelMapper.map(shoppingListDTO, ShoppingList.class);

        //listId 입력
        if (shoppingListDTO.getId() == null) {
            shoppingList.setListId(UUID.randomUUID().toString());
        }

        //date 입력("2024-12-17" -> sql Date
        shoppingList.setDate(DateTimeUtil.convertStringToDate(shoppingListDTO.getDateString()));
        return shoppingList;
    }

    //비용 id 로 삭제하기
    public void deleteShoppingList(String id) {
        ShoppingList shoppingList = listRepo.findByListId((id)).orElseThrow(
                ()-> new RuntimeException("해당 ID의 비용을 찾을 수 없습니다."));
        listRepo.delete(shoppingList);
    }

    //listId로 수정할 shoppinglist를 찾아 DTO로 리턴
    public ShoppingListDTO getListById(String id) {
        ShoppingList shoppingList = listRepo.findByListId((id)).orElseThrow(()->
                new RuntimeException("해당 ID의 아이템을 찾을 수 없습니다."));
        ShoppingListDTO shoppingListDTO = mapToDTO(shoppingList);
        //DTO 의 날짜입력 형식은 2023-6-17 형식이므로 날짜만 다시 변환
        shoppingListDTO.setDateString(DateTimeUtil.convertDateToInput(shoppingList.getDate()));
        return mapToDTO(shoppingList);
    }

    public List<ShoppingListDTO> getFilterShoppingLists(ShoppingFilterDTO shoppingFilterDTO) throws ParseException {
        String keyword = shoppingFilterDTO.getKeyword();
        String startDate = shoppingFilterDTO.getStartDate();
        String endDate = shoppingFilterDTO.getEndDate();

        //sql 날짜로 문자열 시작일과 종료일을 반환
        Date startDay = !startDate.isEmpty() ? DateTimeUtil.convertStringToDate(startDate) : new Date(0);
        Date endDay = !endDate.isEmpty() ? DateTimeUtil.convertStringToDate(endDate) : new Date(System.currentTimeMillis());
        User user = userService.getLoggedInUser();

        List<ShoppingList> list = listRepo.findByNameContainingAndDateBetweenAndUserId(keyword, startDay, endDay, user.getId());
        List<ShoppingListDTO> listDTO = list.stream().map(this::mapToDTO).collect(Collectors.toList());

        return listDTO;
    }

    //리스트의 총비용 계산
    public int totalShoppingLists(List<ShoppingListDTO> listDTO) {
        int sum = (int) listDTO.stream()
                .mapToLong(ShoppingListDTO::getPrice).sum();
        return sum;


    }


}
