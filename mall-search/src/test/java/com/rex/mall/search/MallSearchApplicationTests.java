package com.rex.mall.search;


import com.alibaba.fastjson.JSON;
import com.rex.mall.search.config.MallElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallSearchApplicationTests {

	@Autowired
	private RestHighLevelClient client;

	/**
	 * 1) 方便检索
	 * {
	 *     skuId： 1
	 *     skuTitle: 华为
	 *     price: 998
	 *     saleCount:99
	 *     attrs : {
	 *         {尺寸:5寸},
	 *         {CPU:高通945},
	 *         {分辨率: 全高清}
	 *     }
	 * }
	 *
	 * 冗余:
	 *
	 * 2）
	 * sku索引 只保存有用信息{
	 *     skuId： 1,
	 *     spuId: 11,
	 *
	 * }
	 *
	 * att索引{
	 *     spuId:11,
	 *     attrs: {
	 *          {尺寸:5寸},
	 * 	        {CPU:高通945},
	 * 	 		{分辨率: 全高清}
	 *     }
	 * }
	 *
	 * 出现问题:
	 * 搜索 小米：粮食，手机，电器
	 * 10000个，4000个spu
	 * 分布查询：4000个spu对应的所有可能属性
	 * esClient： spuId:[4000个spuId] 4000*8 = 32000byte=32kb
	 * 32kb*10000 = 320mb;
	 *
	 *
	 * @throws IOException
	 */

	@Test
	public void searchData() throws IOException {
		// 1. 构建检索请求
		SearchRequest searchRequest = new SearchRequest();
		// 指定索引
		searchRequest.indices("bank");

		// 指定DSL
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		searchRequest.source(sourceBuilder);

		// 1.1 构造检索条件
		sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
		// aggregation
		TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);

		AvgAggregationBuilder balanceAgg = AggregationBuilders.avg("balanceAgg").field("balance");

		sourceBuilder.aggregation(ageAgg).aggregation(balanceAgg);

		System.out.println(sourceBuilder.toString());
		// 2. 执行检索
		SearchResponse search = client.search(searchRequest, MallElasticSearchConfig.COMMON_OPTIONS);

		// 3. 分析结果
		System.out.println(search.toString());

		// 3.1 获取所有查到的数据

		// 3.2 获取Aggregations
		Aggregations aggregations = search.getAggregations();
		Terms ageAgg1 = aggregations.get("ageAgg");
		for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
			String keyAsString = bucket.getKeyAsString();
			System.out.println("年龄: " + keyAsString + " 有几个人: " + bucket.getDocCount());
		}

		Avg balanceAgg1 = aggregations.get("balanceAgg");
		System.out.println("balance: " + balanceAgg1.getValueAsString());


	}

	@Test
	public void indexData() throws IOException {
		IndexRequest indexRequest = new IndexRequest("users");
		indexRequest.id("1");

		User user = new User();
		user.setUserName("rex");
		user.setAge(18);
		user.setGender("M");
		String jsonString = JSON.toJSONString(user);
		indexRequest.source(jsonString, XContentType.JSON); //保存内容

		IndexResponse index = client.index(indexRequest, MallElasticSearchConfig.COMMON_OPTIONS);
		System.out.println(index);

	}

	@Data
	class User {
		private String userName;
		private String gender;
		private Integer age;
	}

	@Test
	public void contextLoads() {
		System.out.println(client);
	}

}
