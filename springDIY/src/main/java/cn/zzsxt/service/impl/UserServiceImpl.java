package cn.zzsxt.service.impl;

import cn.zzsxt.dao.UserDao;
import cn.zzsxt.service.UserService;
/**
 * ����ӿڱ��(��������ʵ����ı��)
 * Υ����"����ԭ��"
 *   ����չҪ"��"
 *   ���޸�Ҫ"��"
 */
public class UserServiceImpl implements UserService {
//	private UserDao userDao = new UserJdbcDaoImpl();
//	private UserDao userDao = new UserMybatisDaoImpl();
	private UserDao userDao;
	
	//�ص�setter����
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void add() {
		userDao.add();
	}
	
	

}
