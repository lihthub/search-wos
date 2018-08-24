package search.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import search.entity.Title;

public class TitleDao extends BaseDao {
	
	public boolean addTitle(Title title) {
		try {
			Title getTitle = getTitle(title.getSerialNo(), title.getContent(), title.getSsfl());
			if (getTitle == null || getTitle.getId() == null) {
				String sql = "insert into title (serialNo, content, page, ssfl) values (?, ?, ?, ?)";
				Object[] params = { title.getSerialNo(), title.getContent(), title.getPage(), title.getSsfl() };
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
	
	public int getTotalCount() {
		int totalCount = 0;
		String sql = "select count(*) from title";
		Object[] params = {};
		ResultSet rs = this.executeQuery(sql, params);
		try {
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return totalCount;
	}
	
	public List<Title> getTitles(int page, String ssfl) {
		List<Title> titles = new ArrayList<Title>();
		String sql = "select id, serialNo, content, page, ssfl from title where page = ? and ssfl = ? order by serialNo";
		Object[] params = { page, ssfl };
		ResultSet rs = this.executeQuery(sql, params);
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int serialNo = rs.getInt("serialNo");
				String content = rs.getString("content");
				int page2 = rs.getInt("page");
				String ssfl2 = rs.getString("ssfl");
				Title title = new Title();
				title.setId(id);
				title.setSerialNo(serialNo);
				title.setContent(content);
				title.setPage(page2);
				title.setSsfl(ssfl2);
				titles.add(title);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return titles;
	}
	
	public Title getTitle(int serialNo, String content, String ssfl) {
		Title title = new Title();
		String sql = "select * from title where serialNo = ? and ssfl = ?";
		Object[] params = { serialNo, ssfl };
		ResultSet rs = this.executeQuery(sql, params);
		try {
			if (rs.next()) {
				int id = rs.getInt("id");
				int serialNo2 = rs.getInt("serialNo");
				String content2 = rs.getString("content");
				int page = rs.getInt("page");
				String ssfl2 = rs.getString("ssfl");
				title.setId(id);
				title.setSerialNo(serialNo2);
				title.setContent(content2);
				title.setPage(page);
				title.setSsfl(ssfl2); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return title;
	}
	
	public int getPageCount(String ssfl) {
		int pageCount = 0;
		String sql = "select max(page) from title where ssfl = ?";
		Object[] params = { ssfl };
		ResultSet rs = this.executeQuery(sql, params);
		try {
			if (rs.next()) {
				pageCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResource();
		}
		return pageCount;
	}
	
	public boolean createTable() {
		try {
			String createSql = "CREATE TABLE IF NOT EXISTS `title` ( `id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `serialNo` INT UNSIGNED NOT NULL, `content` VARCHAR(500) NOT NULL, `page` INT UNSIGNED NOT NULL, `ssfl` VARCHAR(100) NOT NULL, PRIMARY KEY (`id`) ) CHARSET = utf8;";
			return super.createTable(createSql);
		} finally {
			this.closeResource();
		}
	}
	
	public boolean isTableExist() {
		try {
			return super.isTableExist("title");
		} finally {
			this.closeResource();
		}
	}
	
}
