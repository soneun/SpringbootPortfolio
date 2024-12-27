package com.mysite.nara;

import com.mysite.nara.entity.Question;
import com.mysite.nara.entity.User;
import com.mysite.nara.service.QuestionService;
import com.mysite.nara.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InputTest {

    @Autowired
    private QuestionService qService;
    @Autowired
    private UserService uService;
//
//    @Test
//    public void test() {
//        String s = String.format("레고 문의드립니다");
//        String c = "나루토 커스텀 레고 갖고 계신분 계신가요? ";
//        User user = uService.getLoggedInUser();
//        qService.create(s,c);
//
//    }
}
