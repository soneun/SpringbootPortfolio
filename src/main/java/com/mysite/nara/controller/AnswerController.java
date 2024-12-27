package com.mysite.nara.controller;

import com.mysite.nara.dto.AnswerForm;
import com.mysite.nara.entity.Answer;
import com.mysite.nara.entity.Question;
import com.mysite.nara.entity.User;
import com.mysite.nara.service.AnswerService;
import com.mysite.nara.service.QuestionService;
import com.mysite.nara.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private AnswerService aService;
    @Autowired
    private UserService uService;

    //답변 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable int id,
                               @Valid AnswerForm answerForm, BindingResult result,
                               Principal principal) {
        Question q = qService.getQuestion(id);
        User user = uService.getLoggedInUser();


        if (result.hasErrors()) {
            model.addAttribute("question",q);//다시 되돌아갈 때 질문객체 전달
            return "question_detail";
        }
        //답변을 저장하는 서비스
        aService.create(q,answerForm.getContent(), user);

        //답변을 저장 한 후 다시 질문 상세 페이지를 요청
        return "redirect:/question/detail/" + id;

    }
    //답변 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable int id, Principal principal,
                         AnswerForm answerForm) {
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerForm.setContent(a.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyAnswer(@Valid AnswerForm answerForm, BindingResult result, Principal principal, @PathVariable int id) {
        if(result.hasErrors()) {
            return "answer_form";
        }
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }
        aService.modify(a, answerForm.getContent());
        return "redirect:/question/detail/" + a.getQuestion().getId();
    }

    //답변 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Principal principal) {
        Answer a = aService.getAnswer(id);
        if(!a.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
        }
        aService.delete(a);
        return "redirect:/question/list";

    }

    //추천인
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable int id) {
        Answer answer = aService.getAnswer(id);
        User user = uService.getLoggedInUser();
        aService.vote(answer,user);
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }



}
