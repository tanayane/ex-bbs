package com.example.form;

/**
 * 記事投稿機能のフォーム.
 * 
 * @author ayane.tanaka
 *
 */
public class ArticlePostForm {

	private String name;
	private String content;
	
	@Override
	public String toString() {
		return "ArticlePostForm [name=" + name + ", content=" + content + "]";
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
