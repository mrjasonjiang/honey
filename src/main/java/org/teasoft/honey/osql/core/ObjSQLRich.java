/*
 * Copyright 2013-2018 the original author.All rights reserved.
 * Kingstar(honeysoft@126.com)
 * The license,see the LICENSE file.
 */

package org.teasoft.honey.osql.core;

import java.util.List;

import org.teasoft.bee.osql.Condition;
import org.teasoft.bee.osql.FunctionType;
import org.teasoft.bee.osql.IncludeType;
import org.teasoft.bee.osql.ObjSQLException;
import org.teasoft.bee.osql.ObjToSQLRich;
import org.teasoft.bee.osql.OrderType;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.bee.osql.exception.BeeIllegalParameterException;

/**
 * @author Kingstar
 * @since  1.0
 */
public class ObjSQLRich extends ObjSQL implements SuidRich {

	private ObjToSQLRich objToSQLRich = BeeFactory.getHoneyFactory().getObjToSQLRich();
//	private BeeSql beeSql = BeeFactory.getHoneyFactory().getBeeSql();

	@Override
	public <T> List<T> select(T entity, int size) {
		if (entity == null) return null;
		if(size<=0) throw new BeeIllegalParameterException("Parameter 'size' need great than 0!");
		String sql = objToSQLRich.toSelectSQL(entity, size);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> List<T> select(T entity, int start, int size) {
		if (entity == null) return null;
		if(size<=0) throw new BeeIllegalParameterException("Parameter 'size' need great than 0!");
		if(start<0) throw new BeeIllegalParameterException("Parameter 'start' need great equal 0!");
		String sql = objToSQLRich.toSelectSQL(entity, start, size);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> List<T> select(T entity, String selectField) {//sqlLib.selectSomeField
		if (entity == null) return null;
		List<T> list = null;
		try {
			String sql = objToSQLRich.toSelectSQL(entity, selectField);
			list = getBeeSql().selectSomeField(sql, entity);
		} catch (ObjSQLException e) {
			throw e;
		}

		return list;
	}

	@Override
	public <T> List<T> select(T entity, String selectFields, int start, int size) {
		if (entity == null) return null;
		if(size<=0) throw new BeeIllegalParameterException("Parameter 'size' need great than 0!");
		if(start<0) throw new BeeIllegalParameterException("Parameter 'start' need great equal 0!");
		
		List<T> list = null;
		String sql = objToSQLRich.toSelectSQL(entity, selectFields,start,size);
		list = getBeeSql().selectSomeField(sql, entity);

		return list;
	}

	@Override
	public <T> List<T> selectOrderBy(T entity, String orderFields) {
		if (entity == null) return null;
		List<T> list = null;
		try {
			String sql = objToSQLRich.toSelectOrderBySQL(entity, orderFields);
			Logger.logSQL("selectOrderBy SQL: ", sql);
			list = getBeeSql().select(sql, entity);
		} catch (ObjSQLException e) {
			throw e;
		}

		return list;
	}

	@Override
	public <T> List<T> selectOrderBy(T entity, String orderFields, OrderType[] orderTypes) {
		if (entity == null) return null;
		List<T> list = null;
		try {
			String sql = objToSQLRich.toSelectOrderBySQL(entity, orderFields, orderTypes);
			Logger.logSQL("selectOrderBy SQL: ", sql);
			list = getBeeSql().select(sql, entity);
		} catch (ObjSQLException e) {
			throw e;
		}

		return list;
	}

	@Override
	public <T> int[] insert(T entity[]) {
		if (entity == null) return null;
		int len = entity.length;
		String insertSql[] = new String[len];
		insertSql = objToSQLRich.toInsertSQL(entity);

		return getBeeSql().batch(insertSql);
	}

	@Override
	public <T> int[] insert(T entity[], String excludeFields) {
		if (entity == null) return null;
		int len = entity.length;
		String insertSql[] = new String[len];
		insertSql = objToSQLRich.toInsertSQL(entity, excludeFields);

		return getBeeSql().batch(insertSql);
	}

	@Override
	public <T> int[] insert(T entity[], int batchSize) {
		if (entity == null) return null;
		int len = entity.length;
		String insertSql[] = new String[len];
		insertSql = objToSQLRich.toInsertSQL(entity);

		return getBeeSql().batch(insertSql, batchSize);
	}

	@Override
	public <T> int[] insert(T entity[], int batchSize, String excludeFields) {
		if (entity == null) return null;
		int len = entity.length;
		String insertSql[] = new String[len];
		insertSql = objToSQLRich.toInsertSQL(entity, excludeFields);

		return getBeeSql().batch(insertSql, batchSize);
	}

	@Override
	public <T> int update(T entity, String updateFields) {
		if (entity == null) return -1;
		int r = 0;
//		try {
			String sql = objToSQLRich.toUpdateSQL(entity, updateFields);
			Logger.logSQL("update SQL(updateFields) :", sql);
			r = getBeeSql().modify(sql);
//		} catch (ObjSQLException e) {
//			throw e;
//		}

		return r;
	}

	@Override
	public <T> T selectOne(T entity) {
		if (entity == null) return null;
		List<T> list = select(entity);
		if (list == null || list.size() != 1) return null;

		return list.get(0);
	}

	@Override
	public <T> String selectWithFun(T entity, FunctionType functionType, String fieldForFun) {
		if (entity == null) return null;
		String s = null;
		try {
			String sql = objToSQLRich.toSelectFunSQL(entity, functionType, fieldForFun);
			s=getBeeSql().selectFun(sql);
		} catch (ObjSQLException e) {
			throw e;
		}

		return s;
	}

	@Override
	public <T> int update(T entity, String updateFields, IncludeType includeType) {
		if (entity == null) return -1;
		int r = 0;
//		try {
			String sql = objToSQLRich.toUpdateSQL(entity, updateFields, includeType);
			Logger.logSQL("update SQL(updateFields) :", sql);
			r = getBeeSql().modify(sql);
//		} catch (ObjSQLException e) {
//			throw e;
//		}

		return r;
	}

	@Override
	public <T> List<T> select(T entity, IncludeType includeType) {
		if (entity == null) return null;
		String sql = objToSQLRich.toSelectSQL(entity, includeType);
		Logger.logSQL("select SQL: ", sql);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> int update(T entity, IncludeType includeType) {
		if (entity == null) return -1;
		String sql = objToSQLRich.toUpdateSQL(entity, includeType);
		Logger.logSQL("update SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	public <T> int insert(T entity, IncludeType includeType) {
		if (entity == null) return -1;
		String sql = objToSQLRich.toInsertSQL(entity, includeType);
		Logger.logSQL("insert SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	public <T> int delete(T entity, IncludeType includeType) {
		if (entity == null) return -1;
		String sql = objToSQLRich.toDeleteSQL(entity, includeType);
		Logger.logSQL("delete SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	public <T> List<String[]> selectString(T entity) {

		if (entity == null) return null;

		List<String[]> list = null;
		String sql = objToSQLRich.toSelectSQL(entity);

		Logger.logSQL("List<String[]> select SQL: ", sql);
		list = getBeeSql().select(sql);

		return list;
	}

	@Override
	public <T> List<String[]> selectString(T entity, String selectFields) {

		if (entity == null) return null;

		List<String[]> list = null;
		try {
			String sql = objToSQLRich.toSelectSQL(entity, selectFields);
			list = getBeeSql().select(sql);
		} catch (ObjSQLException e) {
			throw e;
		}

		return list;
	}

	@Override
	public <T> String selectJson(T entity) {
		
		if (entity == null) return null;
		
		String sql = objToSQLRich.toSelectSQL(entity);
		Logger.logSQL("selectJson SQL: ", sql);
		
		return getBeeSql().selectJson(sql);
	}

	@Override
	public <T> String selectJson(T entity, IncludeType includeType) {
		if (entity == null) return null;
		
		String sql = objToSQLRich.toSelectSQL(entity, includeType);
		Logger.logSQL("selectJson SQL: ", sql);
		
		return getBeeSql().selectJson(sql);
	}

	@Override
	public <T> List<T> selectById(T entity, Integer id) {
		if (entity == null) return null;
		String sql = objToSQLRich.toSelectByIdSQL(entity, id);
		Logger.logSQL("selectById SQL: ", sql);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> List<T> selectById(T entity, Long id) {
		if (entity == null) return null;
		String sql = objToSQLRich.toSelectByIdSQL(entity, id);
		Logger.logSQL("selectById SQL: ", sql);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> List<T> selectById(T entity, String ids) {
		if (entity == null) return null;
		String sql = objToSQLRich.toSelectByIdSQL(entity, ids);
		Logger.logSQL("selectById SQL: ", sql);
		return getBeeSql().select(sql, entity);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public int deleteById(Class c, Integer id) {
		if (c == null) return 0;
		String sql = objToSQLRich.toDeleteByIdSQL(c, id);
		Logger.logSQL("deleteById SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public int deleteById(Class c, Long id) {
		if (c == null) return 0;
		String sql = objToSQLRich.toDeleteByIdSQL(c, id);
		Logger.logSQL("deleteById SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public int deleteById(Class c, String ids) {
		if (c == null) return 0;
		String sql = objToSQLRich.toDeleteByIdSQL(c, ids);
		Logger.logSQL("deleteById SQL: ", sql);
		return getBeeSql().modify(sql);
	}

	@Override
	@Deprecated
	public <T> List<T> select(T entity, IncludeType includeType, Condition condition) {
		if (entity == null) return null;
		String sql = objToSQLRich.toSelectSQL(entity, includeType,condition);
		Logger.logSQL("select SQL: ", sql);
		return getBeeSql().select(sql, entity);
	}

	@Override
	public <T> String selectJson(T entity, IncludeType includeType, Condition condition) {
		if (entity == null) return null;
		
		String sql = objToSQLRich.toSelectSQL(entity, includeType,condition);
		Logger.logSQL("selectJson SQL: ", sql);
		
		return getBeeSql().selectJson(sql);
	}

	@Override
	public <T> int updateBy(T entity, String whereFields) {
		if (entity == null) return -1;
		int r = 0;
//		try {
			String sql = objToSQLRich.toUpdateBySQL(entity, whereFields);  //updateBy
			Logger.logSQL("update SQL(whereFields) :", sql);
			r = getBeeSql().modify(sql);
//		} catch (ObjSQLException e) {
//			throw e;
//		}

		return r;
	}

	@Override
	public <T> int updateBy(T entity, String whereFields, IncludeType includeType) {
		if (entity == null) return -1;
		int r = 0;
		String sql = objToSQLRich.toUpdateBySQL(entity, whereFields, includeType);//updateBy
		Logger.logSQL("update SQL(whereFields) :", sql);
		r = getBeeSql().modify(sql);

		return r;
	}
	
	//v1.7.2
	@Override
	public <T> int updateBy(T entity, String whereFields, Condition condition) {
		if (entity == null) return -1;
		int r = 0;
		String sql = objToSQLRich.toUpdateBySQL(entity, whereFields, condition);//updateBy
		Logger.logSQL("update SQL(whereFields) :", sql);
		r = getBeeSql().modify(sql);

		return r;
	}

	//v1.7.2
	@Override
	public <T> int update(T entity, String updateFields, Condition condition) {
		if (entity == null) return -1;
		int r = 0;
		String sql = objToSQLRich.toUpdateSQL(entity, updateFields, condition);
		Logger.logSQL("update SQL(updateFields) :", sql);
		r = getBeeSql().modify(sql);
		
		return r;
	}
	

}
