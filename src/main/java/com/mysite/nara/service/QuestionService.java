package com.mysite.nara.service;

import com.mysite.nara.entity.Question;
import com.mysite.nara.entity.User;
import com.mysite.nara.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qRepo;

    //질문들을 모두 가져오는 메서드(페이지 적용)
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
        return qRepo.findAll(pageable);
    }

    public Question getQuestion(int id) {
        Optional<Question> q = qRepo.findById(id);
        if(q.isPresent()) {
            return q.get();
        }else{
            //id로 질문을 못찾았을 경우에 못찾음 예외 발생
            throw new RuntimeException("질문을 찾지 못하였습니다");
        }
    }

    //질문 등록
    public void create(String subject, String content, User user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);

        qRepo.save(q);
    }


    //질문 수정
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
    }

    //질문 삭제
    public void delete(Question question) {
        qRepo.delete(question);
    }

    //추천인 저장
    public void vote(Question question, User user) {
        question.getVoter().add(user);
        qRepo.save(question);
    }


}
