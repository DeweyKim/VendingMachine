package VDMachine;
/**
 * @author Dewey 
 * @date 2016. 4. 1.
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * 거스름돈을 돌려주는 클래스 
 * @param insert[4]	[0]부터 [3]까지 각각 10원,50원,100원,500원의 잔고를 가리킴
 * @param minus[4] [0]부터 [3]까지 고객에게 돌려줄 동전의 갯수를 나타냄
 */
public class Change extends JFrame {
	public int[] insert = new int[4];
	public int[] minus = new int[4]; 

	VendingMachineFrame vendingmachineframe;

	/**
	 * 처음에 자판기에 들어있는 동전은 각 50개씩
	 */
	public Change() { 
		this.insert[0] = 50;
		this.insert[1] = 50;
		this.insert[2] = 50;
		this.insert[3] = 50;
	}
	/**
	 * 동전의 갯수를 최소화하기 위함
	 */
	public void Payback(int money) { 
		if (500 * insert[3] + 100 * insert[2] + 50 * insert[1] + 10 * insert[0] >= money) {	//남아있는 동전의 잔고로 잔돈을 돌려줄 수 있을 때
			minus[3] = money / 500; 
			if (insert[3] >= minus[3])	//현재 있는 500원의 수가 돌려줄 500원의 갯수보다 많으면
				money = money % 500;	//500원을 최대한 돌려주고 100원단위로 넘어감	
			else {						//그렇지 않으면
				money = money - 500 * insert[3];	//일단 있는 500원을 다 주고 나머지 돈은 100원 단위에서 처리하게 함
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
			insert[0] = insert[0] - minus[0]; // 고객에게 돌려준 갯수만큼을 뺀 현재 자판기에 남아있는 동전의 갯수
			insert[1] = insert[1] - minus[1];
			insert[2] = insert[2] - minus[2];
			insert[3] = insert[3] - minus[3];
			ShowPayback();		//거슬러줌
		}
		else{		//현재 있는 동전으로 돈을 거슬러 줄 수 없을 때
			JOptionPane.showMessageDialog(null,
					"동전이 부족해서 현재 거스름돈을 돌려드릴 수 없습니다. 문의:010-xxxx-xxxx","거스름돈 나왔습니다.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	/**
	 * VendingMachineFrame에서 동전버튼을 누르면 이 함수가 실행됨
	 * @param i VendingMachineFrame에서 각 동전별로 지정된 번호를 가리킴
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
	 * 고객에게 얼마를 돌려줄지 출력
	 */
	public void ShowPayback() {
		JOptionPane.showMessageDialog(null,
				"500원:" + minus[3] + "\n" + "100원:" + minus[2] + "\n" + "50원:" + minus[1] + "\n" + "10원:" + minus[0],
				"거스름돈 나왔습니다.", JOptionPane.INFORMATION_MESSAGE);
	}
}
