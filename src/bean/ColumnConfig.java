package bean;

public class ColumnConfig {
	//����
	private String col_name;
	//�е���������
	private String type;
	//����ռ�����ֽڴ�С
	private int size;
	//���Ƿ��Ϊ��
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
