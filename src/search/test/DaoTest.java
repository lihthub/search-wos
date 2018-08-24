package search.test;

import search.dao.SsflDao;

public class DaoTest {

	public static void main(String[] args) {
		SsflDao ssflDao = new SsflDao();
		boolean isSuccess = ssflDao.createTable();
		System.out.println(isSuccess);
	}

}
