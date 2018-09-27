package bean;

import java.util.ArrayList;

public class TableConfig {
	//表名
	private String tableName;
	//列表名数组
	public ArrayList<ColumnConfig> cfs = null;
	//主键
	private String primaryKey = null ;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ArrayList<ColumnConfig> getCfs() {
		return cfs;
	}
	public void setCfs(ArrayList<ColumnConfig> cfs) {
		this.cfs = cfs;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
}
