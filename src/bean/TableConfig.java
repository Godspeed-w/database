package bean;

import java.util.ArrayList;

public class TableConfig {
	//表名
	private String tableName;
	//列表名数组
	public ArrayList<ColumnConfig> cfs = new ArrayList<ColumnConfig>();
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
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	@Override
	public String toString() {
		System.out.println("进入toString");
		return "TableConfig [tableName=" + tableName + ", cfs=" + cfs + ", primaryKey=" + primaryKey
				+ ", getTableName()=" + getTableName() + ", getCfs()=" + getCfs() + ", getPrimaryKey()="
				+ getPrimaryKey() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
