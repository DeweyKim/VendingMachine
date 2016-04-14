package VDMachine;
/**
 * @author Dewey 
 * @date 2016. 4. 1.
 */

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
/**
 * VendingMachineFrame과 VendingMachine을 하나로 합쳤기 때문에 VendingMachine클래스는 따로 만들지 않음
 *
 * @param VendingMachineProgram 자판기 시뮬레이션 패널
 * @param product				제품의 갯수가 여러개이므로 List를 사용
 * @param change				거스름돈 반환기능을 포함
 * @param money					사용자가 투입하는 돈
 * @param MAXSIZE				통의 크기이자 각 제품의 최대 갯수를 15로 설정
 * @param MAXCOIN				자판기 안에 들어있는 동전의 갯수를 50으로 초기화
 *
 */
public class VendingMachineFrame extends JFrame {
	VendingMachinePanel VendingMachinePanel; 
	List<Product> product;		
	Change change=new Change();	
	int money=0;
	final int MAXSIZE = 15;
	final int MAXCOIN = 50;
	
	/**
	 * 프레임 설정
	 */
	public VendingMachineFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VendingMachinePanel = new VendingMachinePanel();

		add(new TitlePanel(), BorderLayout.NORTH); 
		add(VendingMachinePanel, BorderLayout.CENTER);
		add(new ButtonPanel(), BorderLayout.SOUTH); 
		
		setSize(850,800);
		setResizable(false); // 사용자가 프레임의 크기 조절할 수 없도록 설정
		setVisible(true);
	}
	/**
	 * TitlePanel 설정
	 * 자판기 이름 GS24
	 */
	class TitlePanel extends JPanel {
		JLabel titleMsg = new JLabel("GS24");
		public TitlePanel() {
			titleMsg.setHorizontalAlignment(JLabel.CENTER); // 레이블의 문자열을 중앙에 정렬
			setBackground(Color.WHITE);			
			add(titleMsg);
		}
	}
	
	/**
	 * 버튼의 종류를 설정
	 * @param Buttons		제품의 종류/배열 names를 이름으로 가짐
	 * @param payButtons	동전의 종류/배열 pay를 이름으로 가짐
	 * @param etcButtons	배열 etc를 이름으로 가짐
	 */
	class ButtonPanel extends JPanel {
		JButton [] Buttons = new JButton[5]; 
		JButton [] payButtons = new JButton[5];
		JButton [] etcButtons = new JButton[2];
		String [] names = {"Coke","Coffee","Water","Soda","Milk"}; 
		String [] pay = {"10","50","100","500","1000"};
		String [] etc = {"Charge","Change"};
		public ButtonPanel() {
			for(int i=0; i<Buttons.length; i++) { 
				Buttons[i] = new JButton(names[i]); // 버튼 컴포넌트 생성
				add(Buttons[i]); // 버튼 달기
				
				// 모든 버튼에 Action 리스너 등록
				Buttons[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// e.getActionCommand()는 클릭된 버튼의 문자열 정보 리턴
							VendingMachinePanel.operate(e.getActionCommand());				
						}
				});
			}
			for(int i=0; i<payButtons.length; i++) { 
				payButtons[i] = new JButton(pay[i]); 
				add(payButtons[i]); 
				
				payButtons[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							VendingMachinePanel.operate(e.getActionCommand());				
						}
				});
			}
			for(int i=0; i<etcButtons.length; i++) { 
				etcButtons[i] = new JButton(etc[i]);
				add(etcButtons[i]); 
				
				etcButtons[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							VendingMachinePanel.operate(e.getActionCommand());				
						}
				});
			}
		}
	}
	
	/**
	 * 자판기가 시뮬레이션하고 모든 재료통을 보여주는  패널. 배치관리자를 null로 설정
	 * @param boxes		5 개의 재료통을 표현하는 레이블 컴포넌트 배열
	 * @param rest1		투입 가격이 얼마인지 표현
	 * @param rests		잔량이 얼마인지 나타내는 배열
	 * @param solds		팔린 양이 얼마인지 나타내는 배열
	 * @param pennies	잔돈의 종류를 나타내는 배열
	 * @param coins 	잔돈의 잔량을 나타내는 배열
	 * 
	 * @param Label		각 제품의 이미지를 출력
	 */
	class VendingMachinePanel extends JPanel {
		BoxLabel [] boxes = new BoxLabel[5];
		JLabel rest1;
		JLabel [] rests = new JLabel[5];
		JLabel [] solds = new JLabel[5];
		JLabel [] pennies = new JLabel[4];
		JLabel [] coins = new JLabel[4];
		
		JLabel cokeImageLabel; //제품이 나왔을 때 이를 보여주는 이미지 레이블
		JLabel coffeeImageLabel;
		JLabel waterImageLabel; 
		JLabel sodaImageLabel; 
		JLabel milkImageLabel; 
		
		// BoxLabel[]에 대한 인덱스
		final int COKE = 0;
		final int COFFEE = 1;
		final int WATER = 2;
		final int SODA = 3;
		final int MILK = 4;
		
		//화면에 기본적으로 출력될 배열들
		String [] ProductNames = {"Coke", "Coffee","Water","Soda","Milk"};
		String [] Rest = {"15","15","15","15","15"};
		String [] Sold = {"0","0","0","0","0"};
		String [] Price = {"800","700","500","800","600"};
		String [] Coin = {"50","50","50","50"};
		String [] Penny = {"10원","50원","100원","500원"};
		
		//제품들의 이미지 객체
		ImageIcon cokeIcon = new ImageIcon("images/coke.jpg");
		ImageIcon coffeeIcon = new ImageIcon("images/coffee.jpg");
		ImageIcon waterIcon = new ImageIcon("images/water.jpg");
		ImageIcon sodaIcon = new ImageIcon("images/soda.jpg");
		ImageIcon milkIcon = new ImageIcon("images/milk.jpg");
		
		public VendingMachinePanel() {
			setLayout(null); // 컴포넌트의 위치를 마음대로 지정할 수 있도록 null로 설정
			
			for(int i=0; i<boxes.length; i++) {
				boxes[i] = new BoxLabel(); // 재료통을 표시하는 레이블 컴포넌트 생성
				boxes[i].setLocation(80+140*i, 10); // 레이블의 위치 설정
				boxes[i].setSize(50, 100); // 레이블의 크기 설정
				add(boxes[i]);
				
				JLabel text = new JLabel(ProductNames[i]); // 재료통 밑에 출력할 문자열
				text.setLocation(80+140*i, 120);
				text.setSize(50, 30);
				add(text);
				
				rests[i] = new JLabel(Rest[i]); // 잔량을 출력
				rests[i].setLocation(80+140*i, 150);
				rests[i].setSize(50, 30);
				add(rests[i]);
				
				solds[i] = new JLabel(Sold[i]); // 팔린 양을 출력
				solds[i].setLocation(80+140*i, 180);
				solds[i].setSize(50, 30);
				add(solds[i]);
				
				JLabel price = new JLabel(Price[i]); // 가격을 출력
				price.setLocation(80+140*i, 210);
				price.setSize(50, 30);	
				add(price);
			}
			
			for(int i=0;i<coins.length;i++){
				pennies[i] = new JLabel(Penny[i]); // 동전의 종류를 출력
				pennies[i].setLocation(80+140*i, 240);
				pennies[i].setSize(80, 30);
				add(pennies[i]);
				coins[i] = new JLabel(Coin[i]); // 각 동전의 잔량을 출력
				coins[i].setLocation(80+140*i, 270);
				coins[i].setSize(80, 30);
				add(coins[i]);
			}
			
			JLabel left = new JLabel("잔량"); 
			left.setLocation(11, 150);
			left.setSize(50, 30);
			add(left);
			
			JLabel nums = new JLabel("팔린 양");
			nums.setLocation(11, 180);
			nums.setSize(50, 30);
			add(nums);
			
			JLabel terms = new JLabel("가격"); 
			terms.setLocation(11, 210);
			terms.setSize(50, 30);
			add(terms);
			
			JLabel change = new JLabel("잔돈"); 
			change.setLocation(11, 240);
			change.setSize(50, 30);
			add(change);
			
			rest1 = new JLabel("투입 가격:"+money); 
			rest1.setLocation(400, 650);
			rest1.setSize(100, 30);
			add(rest1);
			
			/**
			 * 각 제품의 이미지를 출력할 이미지 레이블
			 */
			cokeImageLabel = new JLabel(); 	
			cokeImageLabel.setLocation(180, 350);
			cokeImageLabel.setSize(cokeIcon.getIconWidth(), cokeIcon.getIconHeight());
			add(cokeImageLabel);
			
			coffeeImageLabel = new JLabel(); 			
			coffeeImageLabel.setLocation(180, 350);
			coffeeImageLabel.setSize(coffeeIcon.getIconWidth(), coffeeIcon.getIconHeight());
			add(coffeeImageLabel);
			
			waterImageLabel = new JLabel(); 		
			waterImageLabel.setLocation(180, 350);
			waterImageLabel.setSize(waterIcon.getIconWidth(), waterIcon.getIconHeight());
			add(waterImageLabel);
			
			milkImageLabel = new JLabel(); 			
			milkImageLabel.setLocation(180, 350);
			milkImageLabel.setSize(milkIcon.getIconWidth(), milkIcon.getIconHeight());
			add(milkImageLabel);
			
			sodaImageLabel = new JLabel(); 			
			sodaImageLabel.setLocation(180, 350);
			sodaImageLabel.setSize(sodaIcon.getIconWidth(), sodaIcon.getIconHeight());
			add(sodaImageLabel);
		}
		
		/**
		 * 사실상 VendingMachine의 기능을 수행하는 함수
		 * @param cmd	제품의 종류를 구별
		 */
		public void operate(String cmd) { 
			if(cmd.equals("Coke")) { // 눌러진 버튼이 "Coke" 일때
				if(boxes[COKE].isEmpty()) {	//잔량이 없으면
					error("콜라가 부족합니다."); // 경고창을 출력한다.
					return;
				}
				else if(money<800){			//투입한 돈이 부족해도
					error("돈이 부족합니다."); // 경고창을 출력한다.
				}
				else {
					money-=800;			//돈을 차감
					rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능
					boxes[COKE].consume(); 				// 콜라를 하나 줄인다.
					this.cokeImageLabel.setIcon(cokeIcon);		// 성공적으로 콜라가 나오게 되었으므로 콜라 이미지를 출력한다.
					//콜라의 잔량과 팔린 양을
					rests[0].setText(""+boxes[COKE].product.getnumOfStock());
					solds[0].setText(""+boxes[COKE].product.getnumOfPurchased());
					// 콜라가 나왔음을 알려주는 메시지 창을 출력한다.
					JOptionPane.showMessageDialog(null, "차가워요, 즐거운 하루~~", "콜라나왔습니다.", 
							JOptionPane.INFORMATION_MESSAGE);
					// 콜라 이미지를 지운다.
					cokeImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Coffee")) { // 눌러진 버튼이 "Coffee" 이면
				if(boxes[COFFEE].isEmpty()) {
					error("커피가 부족합니다."); // 경고창을 출력한다.
					return;
				}
				else if(money<700){
					error("돈이 부족합니다."); // 경고창을 출력한다.
				}
				else {
					money-=700;
					rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
					boxes[COFFEE].consume(); // 커피 양을 하나 줄인다.	
					// 성공적으로 커피가 나오게 되었으므로 커피잔을 출력한다.
					this.coffeeImageLabel.setIcon(coffeeIcon);
					rests[1].setText(""+boxes[COFFEE].product.getnumOfStock());
					solds[1].setText(""+boxes[COFFEE].product.getnumOfPurchased());
					// 커피가 나왔음을 알려주는 메시지 창을 출력한다.
					JOptionPane.showMessageDialog(null, "뜨거워요, 즐거운 하루~~", "커피나왔습니다.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// 커피잔 이미지를 지운다.
					coffeeImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Water")) { // 눌러진 버튼이 "Water" 이면
				if(boxes[WATER].isEmpty()) {
					error("물이 부족합니다. 채워주세요."); // 경고창을 출력한다.
					return;
				}
				else if(money<500){
					error("돈이 부족합니다."); // 경고창을 출력한다.
				}
				else {
					money-=500;
					rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
					boxes[WATER].consume(); // 물을 하나 줄인다.	
					// 성공적으로 물이 나오게 되었으므로 물을 출력한다.
					this.waterImageLabel.setIcon(waterIcon);
					rests[2].setText(""+boxes[WATER].product.getnumOfStock());
					solds[2].setText(""+boxes[WATER].product.getnumOfPurchased());
					// 물이 나왔음을 알려주는 메시지 창을 출력한다.
					JOptionPane.showMessageDialog(null, "시원해요, 즐거운 하루~~", "물나왔습니다.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// 물 이미지를 지운다.
					waterImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Soda")) { // 눌러진 버튼이 "Water" 이면
				if(boxes[SODA].isEmpty()) {
					error("사이다가 부족합니다."); // 경고창을 출력한다.
					return;
				}
				else if(money<800){
					error("돈이 부족합니다."); // 경고창을 출력한다.
				}
				else {
					money-=800;
					rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
					boxes[SODA].consume(); // 사이다를 하나 줄인다.	
					// 성공적으로 사이다가 나오게 되었으므로 커피잔을 출력한다.
					this.sodaImageLabel.setIcon(sodaIcon);
					rests[3].setText(""+boxes[SODA].product.getnumOfStock());
					solds[3].setText(""+boxes[SODA].product.getnumOfPurchased());
					// 사이다가 나왔음을 알려주는 메시지 창을 출력한다.
					JOptionPane.showMessageDialog(null, "상큼해요, 즐거운 하루~~", "사이다 나왔습니다.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// 사이다 이미지를 지운다.
					sodaImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Milk")) { // 눌러진 버튼이 "Water" 이면
				if(boxes[MILK].isEmpty()) {
					error("우유가 부족합니다."); // 경고창을 출력한다.
					return;
				}
				else if(money<600){
					error("돈이 부족합니다."); // 경고창을 출력한다.
				}
				else {
					money-=600;
					rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
					boxes[MILK].consume(); // 우유를 하나 줄인다.
					// 성공적으로 우유가 나오게 되었으므로 우유를 출력한다.
					this.milkImageLabel.setIcon(milkIcon);
					rests[4].setText(""+boxes[MILK].product.getnumOfStock());
					solds[4].setText(""+boxes[MILK].product.getnumOfPurchased());
					// 우유가 나왔음을 알려주는 메시지 창을 출력한다.
					JOptionPane.showMessageDialog(null, "거북해요, 즐거운 하루~~", "우유나왔습니다.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// 우유잔 이미지를 지운다.
					milkImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("10")){
				money+=10;
				change.InsertCoin(0);
				coins[0].setText(""+change.insert[0]);
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
			}
			else if(cmd.equals("50")){
				money+=50;
				change.InsertCoin(1);
				coins[1].setText(""+change.insert[1]);
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
			}
			else if(cmd.equals("100")){
				money+=100;
				change.InsertCoin(2);
				coins[2].setText(""+change.insert[2]);
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
			}
			else if(cmd.equals("500")){
				money+=500;
				change.InsertCoin(3);
				coins[3].setText(""+change.insert[3]);
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
			}
			else if(cmd.equals("1000")){
				money+=1000;
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
			}
			else if(cmd.equals("Change")){
				change.Payback(money);
				//change.ShowPayback();
				money=0;
				rest1.setText("투입 가격: "+ money);   // Inner class에서 outer class의 필드와 메소드에 접근 가능!
				for(int i=0;i<coins.length;i++){
					coins[i].setText(""+change.insert[i]);
				}
			}
			else{ // // 눌러진 버튼이 "charge" 이면
				boxes[COKE].reset(); // 컵을 초기 상태와 같이 가득 채운다.
				boxes[COFFEE].reset(); // 커피를 초기 상태와 같이 가득 채운다.
				boxes[WATER].reset(); // 물을 초기 상태와 같이 가득 채운다.
				boxes[MILK].reset(); // 설탕을 초기 상태와 같이 가득 채운다.	
				boxes[SODA].reset(); // 크림을 초기 상태와 같이 가득 채운다.
				repaint(); // boxes[]의 레이블 컴포넌트에 변화가 생겼으므로 부모에게 다시 그리도록 한다.
				for(int i=0;i<boxes.length;i++){
					rests[i].setText(""+MAXSIZE);
				}
				for(int i=0;i<coins.length;i++){
					coins[i].setText(""+MAXCOIN);
				}
				return;
			}

			repaint(); // boxes[]의 레이블 컴포넌트에 변화가 생겼으므로 부모에게 다시 그리도록 한다.

		}
			
		public void error(String msg) { // 경고창을 출력하는 메소드
			JOptionPane.showMessageDialog(null, msg, "자판기 경고", 
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	// 재료통을 묘사하는 레이블 클래스
	class BoxLabel extends JLabel {
		int currentSize; // 현재 통에 들어 있는 재료의 양
		int purchased=0;
		Product product=new Product(MAXSIZE,purchased);
		public BoxLabel() {
			currentSize = MAXSIZE; // 초기에는 통에 재료가 가득채워져 있도록 설정	
			//Product product=new Product(MAXSIZE,purchased);
		}
		boolean consume() { // 재료를 하나 소비한다.
			if(currentSize > 0) {
				currentSize--;
				purchased++;
				product.setnumOfStock(currentSize);
				product.setnumOfPurchase(purchased);
				return true;
			}
			else
				return false;
		}
		
		void reset() { // 통을 초기 상태의 양으로 설정한다. 
			currentSize = MAXSIZE; 
		}				
	
		boolean isEmpty() { return currentSize == 0; }
		
		// 통에 남아 있는 재료의 양을 보여주기 위해 작성됨
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// LIGHT_GRAY 색으로 남아 있는 양을 보여 주기위해 그린다.
			g.setColor(Color.LIGHT_GRAY);
			int y = this.getHeight() - (currentSize*this.getHeight() / MAXSIZE);
			g.fillRect(0, y, this.getWidth(), this.getHeight() - y);
			
			// GRAY 색으로 통의 외곽선을 그린다.			
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);			
		}
	}
	
	static public void main(String [] args) {
		new VendingMachineFrame();
	}
}
 
