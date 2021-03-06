/*
 * Copyright 2013-2018 the original author.All rights reserved.
 * Kingstar(honeysoft@126.com)
 * The license,see the LICENSE file.
 */

package org.teasoft.honey.osql.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Transform ResultSet
 * @author Kingstar
 * @since  1.1
 */
public class TransformResultSet {

	public static StringBuffer toJson(ResultSet rs) throws SQLException {
		StringBuffer json = new StringBuffer("");
		ResultSetMetaData rmeta = rs.getMetaData();
		int columnCount = rmeta.getColumnCount();
		boolean ignoreNull = HoneyConfig.getHoneyConfig().isIgnoreNullInSelectJson();
		String temp = "";

		boolean dateWithMillisecond = HoneyConfig.getHoneyConfig().isDateWithMillisecondInSelectJson();
		boolean timeWithMillisecond = HoneyConfig.getHoneyConfig().isTimeWithMillisecondInSelectJson();
		boolean timestampWithMillisecond = HoneyConfig.getHoneyConfig().isTimestampWithMillisecondInSelectJson();

		while (rs.next()) {
			json.append(",{");
			for (int i = 1; i <= columnCount; i++) { //1..n
				if (rs.getString(i) == null && ignoreNull) {
					continue;
				}
				json.append("\"");
				json.append(rmeta.getColumnName(i));
				json.append("\":");

				if (rs.getString(i) != null) {

					if ("String".equals(HoneyUtil.getFieldType(rmeta.getColumnTypeName(i)))) {
						json.append("\"");
						//json.append(rs.getString(i));
						json.append(rs.getString(i).replace("\"", "\\\""));
						json.append("\"");
					} else if ("Date".equals(HoneyUtil.getFieldType(rmeta.getColumnTypeName(i)))) {
						if (dateWithMillisecond) {
							json.append(rs.getDate(i).getTime());
						} else {
							try {
								temp = rs.getString(i);
								Long.valueOf(temp); //test value
								json.append(temp);
							} catch (NumberFormatException e) {
								json.append("\"");
								json.append(temp.replace("\"", "\\\""));
								json.append("\"");
							}
						}
					} else if ("Time".equals(HoneyUtil.getFieldType(rmeta.getColumnTypeName(i)))) {
						if (timeWithMillisecond) {
							json.append(rs.getTime(i).getTime());
						} else {
							try {
								temp = rs.getString(i);
								Long.valueOf(temp); //test value
								json.append(temp);
							} catch (NumberFormatException e) {
								json.append("\"");
								json.append(temp.replace("\"", "\\\""));
								json.append("\"");
							}
						}
					} else if ("Timestamp".equals(HoneyUtil.getFieldType(rmeta.getColumnTypeName(i)))) {
						if (timestampWithMillisecond) {
							json.append(rs.getTimestamp(i).getTime());
						} else {
							try {
								temp = rs.getString(i);
								Long.valueOf(temp); //test value
								json.append(temp);
							} catch (NumberFormatException e) {
								json.append("\"");
								json.append(temp.replace("\"", "\\\""));
								json.append("\"");
							}
						}
					} else {
						json.append(rs.getString(i));
					}

				} else {// null
					json.append(rs.getString(i));
				}

				if (i != columnCount) json.append(",");
			}
			json.append("}");
		}
		if (json.length() > 0) {
			json.deleteCharAt(0);
		}
		json.insert(0, "[");
		json.append("]");

		return json;
	}

	public static List<String[]> toStringsList(ResultSet rs) throws SQLException {
		List<String[]> list = new ArrayList<String[]>();
		ResultSetMetaData rmeta = rs.getMetaData();
		int columnCount = rmeta.getColumnCount();
		boolean nullToEmptyString = HoneyConfig.getHoneyConfig().isNullToEmptyStringInReturnStringList();
		String str[] = null;
		while (rs.next()) {
			str = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				if (nullToEmptyString && rs.getString(i + 1) == null) {
					str[i] = "";
				} else {
					str[i] = rs.getString(i + 1);
				}
			}
			list.add(str);
		}
		return list;
	}

}
