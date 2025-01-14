package com.example.demo.answer;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final UserService userService;
	
	@PostMapping("/create/{id}")
	// 현재 로그인한 사용자 정보를 알려주는 Principal 객체는 스프링 시큐리티가 제공하는 객체 입니다. 
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		Question question = questionService.getQuestion(id);
		SiteUser siteUser = userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		answerService.create(question, answerForm.getContent(), siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}