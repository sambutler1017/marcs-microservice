/**
 * 
 */
package com;

import static com.globals.enums.APIRequests.GET;

import java.util.HashMap;
import java.util.Map;

import com.sql.service.parser.SqlBuilder;

public class TestMain {

	public static void main(String[] args) throws Exception {
		SqlBuilder test = new SqlBuilder();
		Map<String, String> params = new HashMap<>();
		params.put("testid", "1");

		test.setParams(params);
		test.setQueryFile("managers");
		test.setType(GET);
	}
}
