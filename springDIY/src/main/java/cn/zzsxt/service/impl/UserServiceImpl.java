package cn.zzsxt.service.impl;

import cn.zzsxt.dao.UserDao;
import cn.zzsxt.service.UserService;
/**
 * 面向接口编程(避免面向实现类的编程)
 * 违背了"开闭原则"
 *   对扩展要"开"
 *   对修改要"闭"
 */
public class UserServiceImpl implements UserService {
//	private UserDao userDao = new UserJdbcDaoImpl();
//	private UserDao userDao = new UserMybatisDaoImpl();
	private UserDao userDao;
	
	//回调setter方法
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void add() {
		userDao.add();
	}
	
	

}
