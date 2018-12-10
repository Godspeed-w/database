package bean;

import java.util.ArrayList;

public class ColumnRow {
	//行号
	private int rowNum;
	//行名
	private String rowName="";
	//每行的所有信息
	private ArrayList<ColumnData> cds=new ArrayList<ColumnData>();
	//备用数据1
	private int num1=0;
	//备用数据2
	private int num2=0;
	//备用数据3
	private int num3=0;
	
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public ArrayList<ColumnData> getCds() {
		return cds;
	}
	public void setCds(ArrayList<ColumnData> cds) {
		this.cds = cds;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
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
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
}
