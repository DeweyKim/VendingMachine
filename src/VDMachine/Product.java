package VDMachine;
/**
 * @author Dewey 
 * @date 2016. 4. 1.
 */

import javax.swing.JLabel;
/**
 * ���Ǳ� ��ǰ�� �ȸ� ������ �����ִ� ������ �����ϴ� Ŭ����
 * @param numOfPurchased	�ȸ� ����
 * @param numOfStock		�ܰ� ����
 */

public class Product {
	private int numOfPurchased;	
	private int numOfStock;		
	
	public Product(int stock,int sold){		//ó���� ���� �ִ� 15��
		this.numOfStock=stock;
		this.numOfPurchased=sold;
	}
	
	public void setnumOfStock(int currentSize){
		this.numOfStock=currentSize;
	}
	
	public void setnumOfPurchase(int purchased){
		this.numOfPurchased=purchased;
	}
	
	public int getnumOfStock() {
		return numOfStock;
	}
	
	public int getnumOfPurchased() {
		return numOfPurchased;
	}
}
