package com.example.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * コメント投稿のためのフォーム.
 * 
 * @author ayane.tanaka
 *
 */
public class CommentPostForm {
	/**	  コメントID*/
	private Integer articleId;
	/**	  コメント者名*/
	@NotBlank(message="名前を入力してください")
	@Length(min=0,max=50,message="名前は50文字以内で入力してください")
	private String name;
	/**	  コメント内容*/
	@NotBlank(message="投稿内容を入力してください")
	private String content;
	
	@Override
	public String toString() {
		return "CommentPostForm [name=" + name + ", content=" + content + "]";
	}

	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer id) {
		this.articleId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
