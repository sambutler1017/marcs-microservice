package com.sql.service.parser;

import static com.globals.enums.InsiteSqlTags.INCLUDE;
import static com.globals.enums.InsiteSqlTags.NAME;
import static com.globals.enums.InsiteSqlTags.VALUE_ID;
import static com.globals.microservices.ServiceVariables.INSITE_ENV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.globals.enums.APIRequests;
import com.globals.enums.QueryStatements;
import com.globals.exceptions.InvalidParamValue;

/**
 * Used for reading and processing sql blocks of code. This file can only be
 * used on insitesql extensions and located in the resources folder.
 * 
 * @author Sam Butler
 * @since June 23, 2020
 */
public class SqlBuilder {
	private static String queryFile;
	private static APIRequests type;
	private static Map<String, String> params;
	private static boolean validNextLine = true;
	private static int lineNumber = 0;

	public String getQueryFile() {
		return queryFile;
	}

	public void setQueryFile(String file) {
		queryFile = file;
	}

	public APIRequests getType() {
		return type;
	}

	public void setType(APIRequests t) {
		type = t;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> values) {
		params = values;
	}

	/**
	 * Method called to get a sql block and returns a SqlClient object so that it
	 * can be executed and get the expected result from the query.
	 * 
	 * @param queryName - Name of the query that we want to execute
	 * @return a String
	 */
	public String getSql(String queryName) {
		String query = "";

		try {
			query = getQueryString(queryName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return query;
	}

	/**
	 * Method to get the query string from a the set file based on the given name
	 * that we are looking for.
	 * 
	 * @param name - Name of the query we are trying to recieve
	 * @return A String of the query with all the filled in values
	 * @throws IOException
	 * @throws InvalidParamValue
	 */
	private String getQueryString(String name) throws IOException, InvalidParamValue {
		String returnQuery = "";
		BufferedReader br = new BufferedReader(new FileReader(String.format("%s/%s.insitesql", INSITE_ENV, queryFile)));

		String line;
		while ((line = br.readLine()) != null) {
			lineNumber++;
			if (line.contains(String.format("@NAME(%s)", name)))
				break;
		}

		while ((line = br.readLine()) != null && !line.contains(NAME.text())) {
			lineNumber++;
			returnQuery += parseQueryLine(line);
		}

		br.close();
		return returnQuery.replaceAll("\\s{2,}", " ").trim();
	}

	/**
	 * Inserts any property values that have been set
	 * 
	 * @param line - Line of the query being studied
	 * @return A String of the updated line
	 * @throws InvalidParamValue
	 */
	private String parseQueryLine(String line) throws InvalidParamValue {
		String queryLine = "";
		QueryStatements annotation = QueryStatements.getEnumFromString(line);

		if (annotation.isQueryStatement())
			queryLine = insertCondition(line, annotation);
		else if (VALUE_ID.in(line))
			queryLine = insertPropertyValue(line);
		else if (INCLUDE.in(line))
			queryLine = insertInclude(line);
		else
			queryLine = line;

		return queryLine;
	}

	/**
	 * If there was a colon in the query line then it will update that line with
	 * it's associated property.
	 * 
	 * @param line - Line of the query being studied
	 * @return A String of the updated line with the new property value
	 * @throws InvalidParamValue
	 */
	private String insertPropertyValue(String line) throws InvalidParamValue {
		String replaceValue = getReplaceValue(line);
		if (validNextLine) {
			try {
				return line.replace(String.format(":%s:", replaceValue),
						String.format("'%s'", params.get(replaceValue)));
			} catch (Exception e) {
				throw new InvalidParamValue("Param value does not exist:", replaceValue, getQueryFile(), line.trim(),
						lineNumber);
			}
		} else {
			validNextLine = true;
			return "";
		}
	}

	/**
	 * Adds the query condition if the value exists in the params set, if it does
	 * not then the condition will be ignored.
	 * 
	 * @param line - line of the query being studied
	 * @return @return A String of the updated line with the new condition value
	 */
	private String insertCondition(String line, QueryStatements type) {
		if (!doesContainKey(line)) {
			validNextLine = false;
			return "";
		} else
			return line.replace(String.format("%s(:%s:)", type.annotation(), getReplaceValue(line)), type.text());
	}

	private String insertInclude(String line) {
		return null;
	}

	/**
	 * Gets the value between the colons that we want to replace in the query
	 * 
	 * @param line - line of the query being studied
	 * @return string of the replace value
	 */
	public String getReplaceValue(String line) {
		int startColonIndex = line.indexOf(VALUE_ID.text()) + 1;
		int lastColonIndex = line.lastIndexOf(VALUE_ID.text());

		return line.substring(startColonIndex, lastColonIndex);
	}

	/**
	 * If the value we are wanting to replace between the colons exists in the
	 * params then this method will return true, otherwise false.
	 * 
	 * @param line - line of the query being studied
	 * @return boolean if the key exists in the params
	 */
	public boolean doesContainKey(String line) {
		int startColonIndex = line.indexOf(VALUE_ID.text()) + 1;
		int lastColonIndex = line.lastIndexOf(VALUE_ID.text());

		return params.containsKey(line.substring(startColonIndex, lastColonIndex));
	}
}
