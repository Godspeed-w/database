package bean;

public class ColumnData {
	//列名
	private String column;
	//当前数据
	private String data;
	//开始位置
	private int begin;
	//结束位置
	private int end;
	//行号
	private int row=0;
	//留作以后用
	private int num1=0;
	private int num2=0;
	
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String cloumn) {
		this.column = cloumn;
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
