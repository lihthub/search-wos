package search.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SsflDao extends BaseDao {
	
	public boolean addSsfl(String name, String value) {
		try {
			if (!isExist(value)) {
				String sql = "insert into ssfl (name, value) values (?, ?)";
				Object[] params = { name, value };
				int i = this.executeUpdate(sql, params);
				if (i > 0) {
					return true;
				}
			}
		} finally {
			this.closeResource();
		}
		return false;
	}
	
	public Map<String, String> getSsflMap() {
		Map<String, String> ssflMap = new HashMap<String, String>();
		String sql = "select * from ssfl";
		Object[] params = {};
		ResultSet rs = this.executeQuery(sql, params);
		try {
			while (rs.next()) {
				ssflMap.put(rs.getString("value"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return ssflMap;
	}
	
	public boolean isExist(String value) {
		String sql = "select * from ssfl where value = ?";
		Object[] params = { value };
		ResultSet rs = this.executeQuery(sql, params);
		try {
			if (rs.next()) {
				return true; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return false;
	}

	public boolean createTable() {
		try {
			String creatSql = "CREATE TABLE IF NOT EXISTS `ssfl` ( `id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `value` VARCHAR(100) NOT NULL, `name` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`) ) CHARSET = utf8;";
			return super.createTable(creatSql);
		} finally {
			this.closeResource();
		}
	}
	
	public boolean isTableExist() {
		try {
			return super.isTableExist("ssfl");
		} finally {
			this.closeResource();
		}
	}
	
}
