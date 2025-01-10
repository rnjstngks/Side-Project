package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import com.example.demo.question.QuestionService;

import jakarta.transaction.Transactional;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;

//	@Test
//	void testJpa() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//		
//		Question q2	= new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);
//	}
	
//	@Test
//	void findAll() {
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size());
//		
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());
//	}
	
//	@Test
//	void findById() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		if (oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("sbb가 무엇인가요?", q.getSubject());
//		}
//	}
	
//	@Test
//	void findBySubject() {
//		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
//		assertEquals(1, q.getId());
//	}
	
//	@Test
//	void findBySubjectAndContent() {
//		Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//		assertEquals(1, q.getId());
//	}
	
//	@Test
//	void findBySubjectLike() {
//		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb가 무엇인가요?", q.getSubject());
//	}
	
//	@Test
//	void editQuestion() {
//		Optional<Question> oq = questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		questionRepository.save(q);
//	}
	
//	@Test
//	void deleteQuestion() {
//		assertEquals(2, questionRepository.count());
//		Optional<Question> oq = questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		questionRepository.delete(question);
//	}
	
//	@Test
//	void createAnswer() {
//		Optional<Question> oq = questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		
//		Answer answer = new Answer();
//		answer.setContent("네 자동으로 생성됩니다.");
//		answer.setQuestion(question);
//		answer.setCreateDate(LocalDateTime.now());
//		answerRepository.save(answer);
//	}
	
//	@Test
//	void findByIdAnswer() {
//		Optional<Answer> oa = answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer answer = oa.get();
//		assertEquals(2, answer.getQuestion().getId());
//	}
	
//	@Transactional
//	@Test
//	void findByIdQuestion() {
//		Optional<Question> oq = questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		
//		List<Answer> answerList = question.getAnswerList();
//		
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//	}
	
	@Test
	void insertQuestionTestData() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용 무";
			questionService.create(subject, content);
		}
	}
}
