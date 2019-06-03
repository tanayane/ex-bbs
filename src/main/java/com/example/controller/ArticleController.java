package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

/**
 * 掲示板を管理するコントローラ.
 * 
 * @author ayane.tanaka
 *
 */
@Controller
@RequestMapping("/")
public class ArticleController {
	
	@Autowired
	private ArticleRepository repository;
	
	/**
	 * 掲示板の記事一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return　記事一覧画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList=repository.findAll();
		model.addAttribute("ArticleList",articleList);
		return "bbs";
	}
}
