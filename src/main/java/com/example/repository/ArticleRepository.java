package com.example.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * 記事テーブルを操作するリポジトリ.
 * 
 * @author ayane.tanaka
 *
 */
@Repository
@Transactional
public class ArticleRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
//	/**
//	 * DBからの記事情報を格納する.
//	 */
//	private final static RowMapper<Article> ARTICLE_ROW_MAPPER=(rs,i)->{
//		Article article=new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//	};
	
	/**
	 * 記事を全件検索する.
	 * 
	 * @return 記事のリスト
	 */
	public List<Article> findAll(){
		String sql="select a.id ,a.name,a.content,c.id as com_id,c.name as com_name,c.content as com_content "
				+ "from articles a left outer join comments c on(a.id=c.article_id) order by a.id desc ,c.id desc";
			
			ResultSetExtractor<List<Article>> resultSetExtractor=(ResultSet rs)->{
				List<Article> artList =new ArrayList<>();
				Article article=null;
				List<Comment> commentList = null;
				int flagId=0;
			
				while(rs.next()) {
					if(flagId != rs.getInt("id")) {	    //別の記事になったとき
						article=new Article();
						article.setId(rs.getInt("id"));
						article.setName(rs.getString("name"));
						article.setContent(rs.getString("content"));
						commentList=new ArrayList<>();
						article.setCommentList(commentList);
						artList.add(article);
						flagId=article.getId();
					}
					
					if(rs.getInt("com_id")!=0) {		//コメントが1件もない時
						Comment comment=new Comment();		  		    
						comment.setId(rs.getInt("com_id"));
						comment.setName(rs.getString("com_name"));
						comment.setContent(rs.getString("com_content"));
						comment.setArticleId(rs.getInt("id"));
						commentList.add(comment);
					}
				}
				return artList;
			};		
		List<Article> articleList=template.query(sql, resultSetExtractor);
		return articleList;
	}
	
	
	/**
	 * 記事を投稿する.
	 * 
	 * @param article 投稿したい記事
	 */
	public void insert(Article article) {
		String sql="insert into articles (name,content) values (:name,:content)";
		SqlParameterSource param=new BeanPropertySqlParameterSource(article);
		template.update(sql, param);	
	}
	
	/**
	 * 記事とその記事についたコメントを削除する.
	 * 
	 * @param articleId 削除する記事のid
	 */
	public void deleteById(Integer articleId) {
		String sql="WITH deleted AS (DELETE FROM articles WHERE id = :articleId RETURNING id) "
				+ "DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
}
