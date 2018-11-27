package bean;

public class ColumnData {
	//列名
	private String cloumn;
	//当前数据
	private String data;
	//开始位置
	private int begin;
	//结束位置
	private int end;
	//行号
	private int row;
	
	public String getCloumn() {
		return cloumn;
	}
	public void setCloumn(String cloumn) {
		this.cloumn = cloumn;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
}
