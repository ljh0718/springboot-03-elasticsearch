package com.atguigu.elasticsearch;

import com.atguigu.elasticsearch.bean.Article;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticsearchApplicationTests {

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
        //1.给Eszhong中索引(保存)一个文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息好消息");
        article.setAuthor("Mr.Z");
        article.setContent("Hi");

        //构建一个索引功能
        Index index = new Index.Builder(article)
                .index("atguigu")
                .type("news")
                .build();

        try {
            //执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search(){

        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"Hi\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        //构建搜索功能
        Search search = new Search.Builder(json)
                .addIndex("atguigu")
                .addType("news")
                .build();

        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
