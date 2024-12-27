package com.mysite.nara.service;

import com.mysite.nara.entity.Answer;
import com.mysite.nara.entity.Question;
import com.mysite.nara.entity.User;
import com.mysite.nara.exception.DataNotFoundException;
import com.mysite.nara.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository aRepo;

    //답변 등록
    public void create(Question question, String content, User author) {
        Answer a = new Answer();
        a.setQuestion(question);
        a.setContent(content);
        a.setCreateDate(LocalDateTime.now());
        a.setAuthor(author);

        aRepo.save(a);
    }

    //답변 조회하기
    public Answer getAnswer(int id) {
        Optional<Answer> answer = aRepo.findById(id);
        if(answer.isPresent()) {
            return answer.get();
        }else{
            throw new DataNotFoundException("답변을 찾을 수 없음" + id);
        }
    }

    //답변 수정하기
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        aRepo.save(answer);
    }

    //답변 삭제하기
    public void delete(Answer answer) {
        aRepo.delete(answer);
    }

    //추천버튼
    public void vote(Answer answer, User user) {
        answer.getVoter().add(user);
        aRepo.save(answer);
    }




}
