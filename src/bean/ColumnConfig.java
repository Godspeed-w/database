package bean;

public class ColumnConfig {
	//列名
	private String col_name;
	//列的数据类型
	private String type;
	//列所占数据字节大小
	private int size;
	//列是否可为空
	private boolean isNull = false;
	

	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	
}
