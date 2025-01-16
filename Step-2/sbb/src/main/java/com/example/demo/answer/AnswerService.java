package com.example.demo.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public Answer create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		answerRepository.save(answer);
		return answer;
	}
	
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = answerRepository.findById(id);
		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		answerRepository.save(answer);
	}
	
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
}
