package search.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	public static final String SID = "sid";
	public static final String SSFL = "ssfl";
	public static final String PAGE = "page";
	public static final String PAGECOUNT = "page_count";
	
	private String pathname = "./res/setting.ini";
	private static PropertiesUtils propertiesUtils;
	private static Properties properties;
	
	private PropertiesUtils() {
		properties = new Properties();
	}
	
	/**
	 * 创建ConfigManager的实例
	 * 
	 * @return
	 */
	public static PropertiesUtils getInstance() {
		if (propertiesUtils == null) {
			propertiesUtils = new PropertiesUtils();
		}
		return propertiesUtils;
	}
	
	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		File file = new File(pathname);
		if (!file.exists()) {
			return null;
		}
		String value = null;
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			properties.load(is);
			value = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 修改属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pathname);
			properties.setProperty(key, value);
			properties.store(fos, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
