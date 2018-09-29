package bean;

import java.util.ArrayList;

public class TableConfig {
	//����
	private String tableName;
	//�б�������
	public ArrayList<ColumnConfig> cfs = new ArrayList<ColumnConfig>();
	//����
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
		System.out.println("����toString");
		return "TableConfig [tableName=" + tableName + ", cfs=" + cfs + ", primaryKey=" + primaryKey
				+ ", getTableName()=" + getTableName() + ", getCfs()=" + getCfs() + ", getPrimaryKey()="
				+ getPrimaryKey() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
