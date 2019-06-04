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
import com.example.domain.Comment;
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
	private ArticleRepository artrepository;
	
	@Autowired
	private CommentRepository comrepository;
	
	/**
	 * 記事投稿フォームの準備.
	 * 
	 * @return 記事投稿フォーム
	 */
	@ModelAttribute
	public ArticlePostForm setupArticleForm() {
		return new ArticlePostForm();
	}
	
	/**
	 * コメント投稿フォームの準備.
	 * 
	 * @return コメント投稿フォーム
	 */
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
		List<Article> articleList=artrepository.findAll();
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
		artrepository.insert(article);
		return "redirect:/";
	}

	/**
	 * コメントを投稿する.
	 * 
	 * @param form 投稿内容
	 * @param result　エラーチェック
	 * @param model　リクエストスコープ
	 * @return　記事一覧画面
	 */
	@RequestMapping("commentpost")
	public String commentPost(@Validated CommentPostForm form ,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Comment comment=new Comment();
		comment.setName(form.getName());
		comment.setContent(form.getContent());
		comment.setArticleId(form.getArticleId());
		comrepository.insert(comment);
		return "redirect:/";
	}
	
	/**
	 * 指定されたidの記事とその記事へのコメントを削除する.
	 * 
	 * @param articleId 指定されたid
	 * @param model リクエストスコープ
	 * @return　記事一覧画面
	 */
	@RequestMapping("/articledelete")
	public String articleDelete(Integer articleId,Model model) {
		artrepository.deleteById(articleId);
		return "redirect:/";
	}
}
