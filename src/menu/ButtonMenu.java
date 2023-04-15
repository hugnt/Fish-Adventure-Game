package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import main.Main;
import root.IOHandler;

public class ButtonMenu extends JButton{
	private final static Dimension BUTTON_SIZE = new Dimension((int)(150*Main.SCALE),(int)(50*Main.SCALE));
	private final static Color COLOR_BUTTON = new Color(41, 171, 226, 117);
	private final static Border BORDER_BUTTON =  BorderFactory.createLineBorder(Color.BLACK, 2);
	private final static Font FONT_BTN = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));
	public ButtonMenu(String btnName) {
		setText(btnName);
        setPreferredSize(BUTTON_SIZE);
        setBackground(COLOR_BUTTON);
        setBorder(BORDER_BUTTON);
        setFocusPainted(false);
        setFont(FONT_BTN);
        setOpaque(false);
	}
}
