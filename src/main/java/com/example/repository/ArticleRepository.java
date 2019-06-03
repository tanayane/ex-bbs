package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;

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
	
	/**
	 * DBからの記事情報を格納する.
	 */
	private final static RowMapper<Article> ARTICLE_ROW_MAPPER=(rs,i)->{
		Article article=new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	/**
	 * 記事を全件検索する.
	 * 
	 * @return 記事のリスト
	 */
	public List<Article> findAll(){
		String sql="select id,name,content from articles order by id desc;";
		List<Article> articleList= template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
}
