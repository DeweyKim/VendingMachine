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
 * VendingMachineFrame�� VendingMachine�� �ϳ��� ���Ʊ� ������ VendingMachineŬ������ ���� ������ ����
 *
 * @param VendingMachineProgram ���Ǳ� �ùķ��̼� �г�
 * @param product				��ǰ�� ������ �������̹Ƿ� List�� ���
 * @param change				�Ž����� ��ȯ����� ����
 * @param money					����ڰ� �����ϴ� ��
 * @param MAXSIZE				���� ũ������ �� ��ǰ�� �ִ� ������ 15�� ����
 * @param MAXCOIN				���Ǳ� �ȿ� ����ִ� ������ ������ 50���� �ʱ�ȭ
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
	 * ������ ����
	 */
	public VendingMachineFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		VendingMachinePanel = new VendingMachinePanel();

		add(new TitlePanel(), BorderLayout.NORTH); 
		add(VendingMachinePanel, BorderLayout.CENTER);
		add(new ButtonPanel(), BorderLayout.SOUTH); 
		
		setSize(850,800);
		setResizable(false); // ����ڰ� �������� ũ�� ������ �� ������ ����
		setVisible(true);
	}
	/**
	 * TitlePanel ����
	 * ���Ǳ� �̸� GS24
	 */
	class TitlePanel extends JPanel {
		JLabel titleMsg = new JLabel("GS24");
		public TitlePanel() {
			titleMsg.setHorizontalAlignment(JLabel.CENTER); // ���̺��� ���ڿ��� �߾ӿ� ����
			setBackground(Color.WHITE);			
			add(titleMsg);
		}
	}
	
	/**
	 * ��ư�� ������ ����
	 * @param Buttons		��ǰ�� ����/�迭 names�� �̸����� ����
	 * @param payButtons	������ ����/�迭 pay�� �̸����� ����
	 * @param etcButtons	�迭 etc�� �̸����� ����
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
				Buttons[i] = new JButton(names[i]); // ��ư ������Ʈ ����
				add(Buttons[i]); // ��ư �ޱ�
				
				// ��� ��ư�� Action ������ ���
				Buttons[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// e.getActionCommand()�� Ŭ���� ��ư�� ���ڿ� ���� ����
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
	 * ���ǱⰡ �ùķ��̼��ϰ� ��� ������� �����ִ�  �г�. ��ġ�����ڸ� null�� ����
	 * @param boxes		5 ���� ������� ǥ���ϴ� ���̺� ������Ʈ �迭
	 * @param rest1		���� ������ ������ ǥ��
	 * @param rests		�ܷ��� ������ ��Ÿ���� �迭
	 * @param solds		�ȸ� ���� ������ ��Ÿ���� �迭
	 * @param pennies	�ܵ��� ������ ��Ÿ���� �迭
	 * @param coins 	�ܵ��� �ܷ��� ��Ÿ���� �迭
	 * 
	 * @param Label		�� ��ǰ�� �̹����� ���
	 */
	class VendingMachinePanel extends JPanel {
		BoxLabel [] boxes = new BoxLabel[5];
		JLabel rest1;
		JLabel [] rests = new JLabel[5];
		JLabel [] solds = new JLabel[5];
		JLabel [] pennies = new JLabel[4];
		JLabel [] coins = new JLabel[4];
		
		JLabel cokeImageLabel; //��ǰ�� ������ �� �̸� �����ִ� �̹��� ���̺�
		JLabel coffeeImageLabel;
		JLabel waterImageLabel; 
		JLabel sodaImageLabel; 
		JLabel milkImageLabel; 
		
		// BoxLabel[]�� ���� �ε���
		final int COKE = 0;
		final int COFFEE = 1;
		final int WATER = 2;
		final int SODA = 3;
		final int MILK = 4;
		
		//ȭ�鿡 �⺻������ ��µ� �迭��
		String [] ProductNames = {"Coke", "Coffee","Water","Soda","Milk"};
		String [] Rest = {"15","15","15","15","15"};
		String [] Sold = {"0","0","0","0","0"};
		String [] Price = {"800","700","500","800","600"};
		String [] Coin = {"50","50","50","50"};
		String [] Penny = {"10��","50��","100��","500��"};
		
		//��ǰ���� �̹��� ��ü
		ImageIcon cokeIcon = new ImageIcon("images/coke.jpg");
		ImageIcon coffeeIcon = new ImageIcon("images/coffee.jpg");
		ImageIcon waterIcon = new ImageIcon("images/water.jpg");
		ImageIcon sodaIcon = new ImageIcon("images/soda.jpg");
		ImageIcon milkIcon = new ImageIcon("images/milk.jpg");
		
		public VendingMachinePanel() {
			setLayout(null); // ������Ʈ�� ��ġ�� ������� ������ �� �ֵ��� null�� ����
			
			for(int i=0; i<boxes.length; i++) {
				boxes[i] = new BoxLabel(); // ������� ǥ���ϴ� ���̺� ������Ʈ ����
				boxes[i].setLocation(80+140*i, 10); // ���̺��� ��ġ ����
				boxes[i].setSize(50, 100); // ���̺��� ũ�� ����
				add(boxes[i]);
				
				JLabel text = new JLabel(ProductNames[i]); // ����� �ؿ� ����� ���ڿ�
				text.setLocation(80+140*i, 120);
				text.setSize(50, 30);
				add(text);
				
				rests[i] = new JLabel(Rest[i]); // �ܷ��� ���
				rests[i].setLocation(80+140*i, 150);
				rests[i].setSize(50, 30);
				add(rests[i]);
				
				solds[i] = new JLabel(Sold[i]); // �ȸ� ���� ���
				solds[i].setLocation(80+140*i, 180);
				solds[i].setSize(50, 30);
				add(solds[i]);
				
				JLabel price = new JLabel(Price[i]); // ������ ���
				price.setLocation(80+140*i, 210);
				price.setSize(50, 30);	
				add(price);
			}
			
			for(int i=0;i<coins.length;i++){
				pennies[i] = new JLabel(Penny[i]); // ������ ������ ���
				pennies[i].setLocation(80+140*i, 240);
				pennies[i].setSize(80, 30);
				add(pennies[i]);
				coins[i] = new JLabel(Coin[i]); // �� ������ �ܷ��� ���
				coins[i].setLocation(80+140*i, 270);
				coins[i].setSize(80, 30);
				add(coins[i]);
			}
			
			JLabel left = new JLabel("�ܷ�"); 
			left.setLocation(11, 150);
			left.setSize(50, 30);
			add(left);
			
			JLabel nums = new JLabel("�ȸ� ��");
			nums.setLocation(11, 180);
			nums.setSize(50, 30);
			add(nums);
			
			JLabel terms = new JLabel("����"); 
			terms.setLocation(11, 210);
			terms.setSize(50, 30);
			add(terms);
			
			JLabel change = new JLabel("�ܵ�"); 
			change.setLocation(11, 240);
			change.setSize(50, 30);
			add(change);
			
			rest1 = new JLabel("���� ����:"+money); 
			rest1.setLocation(400, 650);
			rest1.setSize(100, 30);
			add(rest1);
			
			/**
			 * �� ��ǰ�� �̹����� ����� �̹��� ���̺�
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
		 * ��ǻ� VendingMachine�� ����� �����ϴ� �Լ�
		 * @param cmd	��ǰ�� ������ ����
		 */
		public void operate(String cmd) { 
			if(cmd.equals("Coke")) { // ������ ��ư�� "Coke" �϶�
				if(boxes[COKE].isEmpty()) {	//�ܷ��� ������
					error("�ݶ� �����մϴ�."); // ���â�� ����Ѵ�.
					return;
				}
				else if(money<800){			//������ ���� �����ص�
					error("���� �����մϴ�."); // ���â�� ����Ѵ�.
				}
				else {
					money-=800;			//���� ����
					rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����
					boxes[COKE].consume(); 				// �ݶ� �ϳ� ���δ�.
					this.cokeImageLabel.setIcon(cokeIcon);		// ���������� �ݶ� ������ �Ǿ����Ƿ� �ݶ� �̹����� ����Ѵ�.
					//�ݶ��� �ܷ��� �ȸ� ����
					rests[0].setText(""+boxes[COKE].product.getnumOfStock());
					solds[0].setText(""+boxes[COKE].product.getnumOfPurchased());
					// �ݶ� �������� �˷��ִ� �޽��� â�� ����Ѵ�.
					JOptionPane.showMessageDialog(null, "��������, ��ſ� �Ϸ�~~", "�ݶ󳪿Խ��ϴ�.", 
							JOptionPane.INFORMATION_MESSAGE);
					// �ݶ� �̹����� �����.
					cokeImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Coffee")) { // ������ ��ư�� "Coffee" �̸�
				if(boxes[COFFEE].isEmpty()) {
					error("Ŀ�ǰ� �����մϴ�."); // ���â�� ����Ѵ�.
					return;
				}
				else if(money<700){
					error("���� �����մϴ�."); // ���â�� ����Ѵ�.
				}
				else {
					money-=700;
					rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
					boxes[COFFEE].consume(); // Ŀ�� ���� �ϳ� ���δ�.	
					// ���������� Ŀ�ǰ� ������ �Ǿ����Ƿ� Ŀ������ ����Ѵ�.
					this.coffeeImageLabel.setIcon(coffeeIcon);
					rests[1].setText(""+boxes[COFFEE].product.getnumOfStock());
					solds[1].setText(""+boxes[COFFEE].product.getnumOfPurchased());
					// Ŀ�ǰ� �������� �˷��ִ� �޽��� â�� ����Ѵ�.
					JOptionPane.showMessageDialog(null, "�߰ſ���, ��ſ� �Ϸ�~~", "Ŀ�ǳ��Խ��ϴ�.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// Ŀ���� �̹����� �����.
					coffeeImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Water")) { // ������ ��ư�� "Water" �̸�
				if(boxes[WATER].isEmpty()) {
					error("���� �����մϴ�. ä���ּ���."); // ���â�� ����Ѵ�.
					return;
				}
				else if(money<500){
					error("���� �����մϴ�."); // ���â�� ����Ѵ�.
				}
				else {
					money-=500;
					rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
					boxes[WATER].consume(); // ���� �ϳ� ���δ�.	
					// ���������� ���� ������ �Ǿ����Ƿ� ���� ����Ѵ�.
					this.waterImageLabel.setIcon(waterIcon);
					rests[2].setText(""+boxes[WATER].product.getnumOfStock());
					solds[2].setText(""+boxes[WATER].product.getnumOfPurchased());
					// ���� �������� �˷��ִ� �޽��� â�� ����Ѵ�.
					JOptionPane.showMessageDialog(null, "�ÿ��ؿ�, ��ſ� �Ϸ�~~", "�����Խ��ϴ�.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// �� �̹����� �����.
					waterImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Soda")) { // ������ ��ư�� "Water" �̸�
				if(boxes[SODA].isEmpty()) {
					error("���̴ٰ� �����մϴ�."); // ���â�� ����Ѵ�.
					return;
				}
				else if(money<800){
					error("���� �����մϴ�."); // ���â�� ����Ѵ�.
				}
				else {
					money-=800;
					rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
					boxes[SODA].consume(); // ���̴ٸ� �ϳ� ���δ�.	
					// ���������� ���̴ٰ� ������ �Ǿ����Ƿ� Ŀ������ ����Ѵ�.
					this.sodaImageLabel.setIcon(sodaIcon);
					rests[3].setText(""+boxes[SODA].product.getnumOfStock());
					solds[3].setText(""+boxes[SODA].product.getnumOfPurchased());
					// ���̴ٰ� �������� �˷��ִ� �޽��� â�� ����Ѵ�.
					JOptionPane.showMessageDialog(null, "��ŭ�ؿ�, ��ſ� �Ϸ�~~", "���̴� ���Խ��ϴ�.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// ���̴� �̹����� �����.
					sodaImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("Milk")) { // ������ ��ư�� "Water" �̸�
				if(boxes[MILK].isEmpty()) {
					error("������ �����մϴ�."); // ���â�� ����Ѵ�.
					return;
				}
				else if(money<600){
					error("���� �����մϴ�."); // ���â�� ����Ѵ�.
				}
				else {
					money-=600;
					rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
					boxes[MILK].consume(); // ������ �ϳ� ���δ�.
					// ���������� ������ ������ �Ǿ����Ƿ� ������ ����Ѵ�.
					this.milkImageLabel.setIcon(milkIcon);
					rests[4].setText(""+boxes[MILK].product.getnumOfStock());
					solds[4].setText(""+boxes[MILK].product.getnumOfPurchased());
					// ������ �������� �˷��ִ� �޽��� â�� ����Ѵ�.
					JOptionPane.showMessageDialog(null, "�ź��ؿ�, ��ſ� �Ϸ�~~", "�������Խ��ϴ�.", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// ������ �̹����� �����.
					milkImageLabel.setIcon(null);
				}
			}
			else if(cmd.equals("10")){
				money+=10;
				change.InsertCoin(0);
				coins[0].setText(""+change.insert[0]);
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
			}
			else if(cmd.equals("50")){
				money+=50;
				change.InsertCoin(1);
				coins[1].setText(""+change.insert[1]);
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
			}
			else if(cmd.equals("100")){
				money+=100;
				change.InsertCoin(2);
				coins[2].setText(""+change.insert[2]);
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
			}
			else if(cmd.equals("500")){
				money+=500;
				change.InsertCoin(3);
				coins[3].setText(""+change.insert[3]);
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
			}
			else if(cmd.equals("1000")){
				money+=1000;
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
			}
			else if(cmd.equals("Change")){
				change.Payback(money);
				//change.ShowPayback();
				money=0;
				rest1.setText("���� ����: "+ money);   // Inner class���� outer class�� �ʵ�� �޼ҵ忡 ���� ����!
				for(int i=0;i<coins.length;i++){
					coins[i].setText(""+change.insert[i]);
				}
			}
			else{ // // ������ ��ư�� "charge" �̸�
				boxes[COKE].reset(); // ���� �ʱ� ���¿� ���� ���� ä���.
				boxes[COFFEE].reset(); // Ŀ�Ǹ� �ʱ� ���¿� ���� ���� ä���.
				boxes[WATER].reset(); // ���� �ʱ� ���¿� ���� ���� ä���.
				boxes[MILK].reset(); // ������ �ʱ� ���¿� ���� ���� ä���.	
				boxes[SODA].reset(); // ũ���� �ʱ� ���¿� ���� ���� ä���.
				repaint(); // boxes[]�� ���̺� ������Ʈ�� ��ȭ�� �������Ƿ� �θ𿡰� �ٽ� �׸����� �Ѵ�.
				for(int i=0;i<boxes.length;i++){
					rests[i].setText(""+MAXSIZE);
				}
				for(int i=0;i<coins.length;i++){
					coins[i].setText(""+MAXCOIN);
				}
				return;
			}

			repaint(); // boxes[]�� ���̺� ������Ʈ�� ��ȭ�� �������Ƿ� �θ𿡰� �ٽ� �׸����� �Ѵ�.

		}
			
		public void error(String msg) { // ���â�� ����ϴ� �޼ҵ�
			JOptionPane.showMessageDialog(null, msg, "���Ǳ� ���", 
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	// ������� �����ϴ� ���̺� Ŭ����
	class BoxLabel extends JLabel {
		int currentSize; // ���� �뿡 ��� �ִ� ����� ��
		int purchased=0;
		Product product=new Product(MAXSIZE,purchased);
		public BoxLabel() {
			currentSize = MAXSIZE; // �ʱ⿡�� �뿡 ��ᰡ ����ä���� �ֵ��� ����	
			//Product product=new Product(MAXSIZE,purchased);
		}
		boolean consume() { // ��Ḧ �ϳ� �Һ��Ѵ�.
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
		
		void reset() { // ���� �ʱ� ������ ������ �����Ѵ�. 
			currentSize = MAXSIZE; 
		}				
	
		boolean isEmpty() { return currentSize == 0; }
		
		// �뿡 ���� �ִ� ����� ���� �����ֱ� ���� �ۼ���
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// LIGHT_GRAY ������ ���� �ִ� ���� ���� �ֱ����� �׸���.
			g.setColor(Color.LIGHT_GRAY);
			int y = this.getHeight() - (currentSize*this.getHeight() / MAXSIZE);
			g.fillRect(0, y, this.getWidth(), this.getHeight() - y);
			
			// GRAY ������ ���� �ܰ����� �׸���.			
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);			
		}
	}
	
	static public void main(String [] args) {
		new VendingMachineFrame();
	}
}
 
