package bean;

public class ColumnData {
	//����
	private String column;
	//��ǰ����
	private String data;
	//��ʼλ��
	private int begin;
	//����λ��
	private int end;
	//�к�
	private int row=0;
	//�����Ժ���
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
