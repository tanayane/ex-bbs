package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticlePostForm;
import com.example.form.CommentPostForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

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
	
	@Autowired
	private CommentRepository commentrepo;
	
	@ModelAttribute
	public ArticlePostForm setupArticleForm() {
		return new ArticlePostForm();
	}
	@ModelAttribute
	public CommentPostForm setupCommentForm() {
		return new CommentPostForm();
	}
	
	/**
	 * 掲示板の記事一覧を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return　記事一覧画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList=repository.findAll();
		for(Article article:articleList) {
			article.setCommentList(commentrepo.findByArticleId(article.getId()));
		}
		model.addAttribute("articleList",articleList);
		return "bbs";
	}
	
	/**
	 * 記事を投稿する.
	 * 
	 * @param form 投稿内容
	 * @param result　エラーチェック
	 * @param model　リクエストスコープ
	 * @return　記事一覧画面
	 */
	@RequestMapping("articlepost")
	public String articlePost(@Validated ArticlePostForm form ,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Article article=new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		repository.insert(article);
		return index(model);
	}
}
