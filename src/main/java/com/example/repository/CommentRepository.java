package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;

/**
 * コメントテーブルを操作するリポジトリ.
 * 
 * @author ayane.tanaka
 *
 */
@Repository
@Transactional
public class CommentRepository {
	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * DBからのデータを格納する.
	 */
	private final static RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * 記事のidから対応するコメントのリストを検索.
	 * 
	 * @param articleId 検索する記事のid
	 * @return コメントのリスト
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "select id,name,content,article_id from comments where article_id= :id order by id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * コメントを投稿する.
	 * 
	 * @param comment 投稿したいコメント
	 */
	public void insert(Comment comment) {
		String sql = "insert into comments (name,content,article_id) values (:name,:content,:articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}
}
