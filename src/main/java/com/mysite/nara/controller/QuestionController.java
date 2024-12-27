package com.mysite.nara.controller;

import com.mysite.nara.dto.AnswerForm;
import com.mysite.nara.dto.QuestionForm;
import com.mysite.nara.entity.Question;
import com.mysite.nara.entity.User;
import com.mysite.nara.service.QuestionService;
import com.mysite.nara.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService qService;
    @Autowired
    private UserService userService;

    //질문 리스트 가져오기
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0")int page) {
        Page<Question> paging = this.qService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    //질문 상세페이지
    @RequestMapping("/detail/{id}")
    public String questionDetail(@PathVariable int id, Model model,
                                 AnswerForm answerForm){
        Question question = qService.getQuestion(id);
        model.addAttribute("question", question);
        //서비스로 질문 내용을 가져옴
        return "question_detail";
    }

    //질문 등록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String Create(QuestionForm questionForm){
        return "question_form";
    }

    //질문 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult result,
                                 Principal principal){
        User user = userService.getLoggedInUser();
        if(result.hasErrors()){
            return "question_form";
        }
        //질문 저장
        qService.create(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";
    }

    //질문 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String Modify(QuestionForm questionForm, @PathVariable int id, Principal principal){
            Question question = qService.getQuestion(id);
            if(!question.getAuthor().getEmail().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            questionForm.setSubject(question.getSubject());
            questionForm.setContent(question.getContent());
        return "questionEdit_form";

    }

    //질문 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult result,
                                 Principal principal, @PathVariable int id){
        if(result.hasErrors()){
            return "questionEdit_form";
        }
        Question question = qService.getQuestion(id);
        if(!question.getAuthor().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.qService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/" + id;
    }

    //질문 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Principal principal){
        Question question = qService.getQuestion(id);
        if(!question.getAuthor().getEmail().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        qService.delete(question);
        return "redirect:/question/list";
    }

    //추천 하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable int id){
        Question question = qService.getQuestion(id);
        User user = userService.getLoggedInUser();
        qService.vote(question, user);
        return "redirect:/question/detail/" + id;

    }



}
