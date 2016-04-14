package VDMachine;
/**
 * @author Dewey 
 * @date 2016. 4. 1.
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * �Ž������� �����ִ� Ŭ���� 
 * @param insert[4]	[0]���� [3]���� ���� 10��,50��,100��,500���� �ܰ� ����Ŵ
 * @param minus[4] [0]���� [3]���� ������ ������ ������ ������ ��Ÿ��
 */
public class Change extends JFrame {
	public int[] insert = new int[4];
	public int[] minus = new int[4]; 

	VendingMachineFrame vendingmachineframe;

	/**
	 * ó���� ���Ǳ⿡ ����ִ� ������ �� 50����
	 */
	public Change() { 
		this.insert[0] = 50;
		this.insert[1] = 50;
		this.insert[2] = 50;
		this.insert[3] = 50;
	}
	/**
	 * ������ ������ �ּ�ȭ�ϱ� ����
	 */
	public void Payback(int money) { 
		if (500 * insert[3] + 100 * insert[2] + 50 * insert[1] + 10 * insert[0] >= money) {	//�����ִ� ������ �ܰ�� �ܵ��� ������ �� ���� ��
			minus[3] = money / 500; 
			if (insert[3] >= minus[3])	//���� �ִ� 500���� ���� ������ 500���� �������� ������
				money = money % 500;	//500���� �ִ��� �����ְ� 100�������� �Ѿ	
			else {						//�׷��� ������
				money = money - 500 * insert[3];	//�ϴ� �ִ� 500���� �� �ְ� ������ ���� 100�� �������� ó���ϰ� ��
				minus[3] = insert[3];
			}
			minus[2] = money / 100;
			if (insert[2] >= minus[2])
				money = money % 100;
			else {
				money = money - 100 * insert[2];
				minus[2] = insert[2];
			}
			minus[1] = money / 50;
			if (insert[1] >= minus[1])
				money = money % 50;
			else {
				money = money - 50 * insert[1];
				minus[1] = insert[1];
			}

			minus[0] = money / 10;
			if (insert[0] >= minus[0])
				money = money % 10;
			else {
				money = money - 10 * insert[0];
				minus[0] = insert[0];
			}
			insert[0] = insert[0] - minus[0]; // ������ ������ ������ŭ�� �� ���� ���Ǳ⿡ �����ִ� ������ ����
			insert[1] = insert[1] - minus[1];
			insert[2] = insert[2] - minus[2];
			insert[3] = insert[3] - minus[3];
			ShowPayback();		//�Ž�����
		}
		else{		//���� �ִ� �������� ���� �Ž��� �� �� ���� ��
			JOptionPane.showMessageDialog(null,
					"������ �����ؼ� ���� �Ž������� �����帱 �� �����ϴ�. ����:010-xxxx-xxxx","�Ž����� ���Խ��ϴ�.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	/**
	 * VendingMachineFrame���� ������ư�� ������ �� �Լ��� �����
	 * @param i VendingMachineFrame���� �� �������� ������ ��ȣ�� ����Ŵ
	 */
	public void InsertCoin(int i) {
		if (i == 0)
			insert[0]++;
		else if (i == 1)
			insert[1]++;
		else if (i == 2)
			insert[2]++;
		else
			insert[3]++;
	}
	/**
	 * ������ �󸶸� �������� ���
	 */
	public void ShowPayback() {
		JOptionPane.showMessageDialog(null,
				"500��:" + minus[3] + "\n" + "100��:" + minus[2] + "\n" + "50��:" + minus[1] + "\n" + "10��:" + minus[0],
				"�Ž����� ���Խ��ϴ�.", JOptionPane.INFORMATION_MESSAGE);
	}
}
