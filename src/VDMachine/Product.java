package VDMachine;
/**
 * @author Dewey 
 * @date 2016. 4. 1.
 */

import javax.swing.JLabel;
/**
 * 자판기 상품의 팔린 갯수와 남아있는 갯수를 저장하는 클래스
 * @param numOfPurchased	팔린 갯수
 * @param numOfStock		잔고 갯수
 */

public class Product {
	private int numOfPurchased;	
	private int numOfStock;		
	
	public Product(int stock,int sold){		//처음의 양은 최대 15개
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
