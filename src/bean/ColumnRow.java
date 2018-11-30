package bean;

import java.util.ArrayList;

public class ColumnRow {
	//行号
	private int rowNum;
	//每行的所有信息
	private ArrayList<ColumnData> cds=new ArrayList<ColumnData>();
	
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
}
