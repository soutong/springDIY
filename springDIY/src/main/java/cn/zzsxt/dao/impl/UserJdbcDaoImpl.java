package cn.zzsxt.dao.impl;

import cn.zzsxt.dao.UserDao;

public class UserJdbcDaoImpl implements UserDao {

	public void add() {
		System.out.println("利用jdbc执行了用户新增....");
	}

}
