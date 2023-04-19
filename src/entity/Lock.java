package entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.Game;
import main.Main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;



public class Lock {
	private int x, y;
	private int width, height;
	private boolean unlock;
	private String password, password2;
	private int currTyping;
	private int limitTyping;
	
	private JPanel passwordPanel;
	private JLabel label;
	private JTextField input;
	private JButton cfBtn;
	
	private boolean lose;
	
	
	
	private boolean isShowInput;
	
	
	public Lock(int x, int y, int width, int height,String password, String password2, int limitTyping) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.password = password.toLowerCase().replaceAll("\\s+", "");
		this.password2 = password2.toLowerCase().replaceAll("\\s+", "");
		this.limitTyping = limitTyping;
		
		currTyping = 0;
		
		unlock = false;
		passwordPanel = new JPanel();
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setFocusable(true);
		//passwordPanel.setOpaque(false);
		
		//label
		label = new JLabel();
		label.setText("Enter password ("+currTyping+"/"+limitTyping+")"+" :");
		Font font = new Font("Arial", Font.PLAIN, (int)(11*Main.SCALE));
		label.setPreferredSize(new Dimension(width, (int)((height-10)/3)));
		label.setFont(font);
		
		//text-input
		input = new JTextField(password.length()+5);
		input.setBorder(null);
		input.setBackground(new Color(245, 243, 142, 255));
		input.setPreferredSize(new Dimension(width, (int)((height-10)/3)));
		//enter
		ActionListener inputHandle = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String res = input.getText().toLowerCase().replaceAll("\\s+", "");
		        if(res.isEmpty()) return;
		        if(res.equals(password)||res.equals(password2)) {
		        	unlock = true;
		        	isShowInput = false;
		        	input.setText("");
		        	//System.out.println(unlock+" "+isShowInput);
		        	System.out.println("Correct answer!!");
		        }
		        else{
		        	currTyping ++;
		        	input.setText("");
		        	unlock = false;
		        	System.out.println("Incorrect answer!!");
		        	if(currTyping>limitTyping) lose = true;
		        }
		    }
		};
		input.addActionListener(inputHandle);
		
		//btn
		cfBtn = new JButton("Confirm");
		cfBtn.setPreferredSize(new Dimension(width, (int)((height-10)/3)));
		cfBtn.setFocusPainted(false);
		cfBtn.setBackground(new Color(226, 180, 54, 255));
		cfBtn.setFont(font);
		cfBtn.addActionListener(inputHandle);
		
		Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		passwordPanel.setBorder(padding);
		
		passwordPanel.add(label, BorderLayout.NORTH);
		passwordPanel.add(input, BorderLayout.CENTER);
		passwordPanel.add(cfBtn, BorderLayout.SOUTH);
		passwordPanel.setBounds(0, 0, width, height);
		passwordPanel.setBackground(new Color(207, 137, 52, 255));
		
	}
	public void render(int xMapOffset) {
		if(isShowInput) {
			label.setText("Enter password ("+currTyping+"/"+limitTyping+")"+" :");
			passwordPanel.setBounds(x - xMapOffset, y, width, height);
			passwordPanel.setVisible(true);
		}
		else {
			passwordPanel.setVisible(false);
		}
		
		
	}
	public void reset() {
		input.setText("");
		unlock = false;
	}
	

	public JPanel getPasswordPanel() {
		return passwordPanel;
	}
	
	
	public boolean isLose() {
		return lose;
	}
	public void setShowInput(boolean isShowInput) {
		this.isShowInput = isShowInput;
	}
	
	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}
	
	
	
	
	
}
