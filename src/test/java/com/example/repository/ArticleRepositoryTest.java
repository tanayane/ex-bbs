package com.example.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Article;

/**
 * ArticleRepository をテストする.
 * 
 * @author ayane.tanaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

	@Autowired
	ArticleRepository repository;
	
	
	/**
	 * findAll()をテストする.
	 * 
	 * TC1:findAllの個数を確かめる
	 * 
	 */
	@Test
	public void testfindAll() {
		List<Article> articleList=repository.findAll();
		assertThat("TC1:結果数が一致しません",articleList.size(),is(2));
		//fail("Not yet implemented");
	}

}
