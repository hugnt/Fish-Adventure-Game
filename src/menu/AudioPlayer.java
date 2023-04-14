package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import root.IOHandler;

public class AudioPlayer extends JPanel implements Runnable{
	private Clip audio;
	private Thread audioThread;
	private JSlider audioSlider;
	
	public AudioPlayer(String auioUrl) {
		audio = IOHandler.getAudio(auioUrl);
		audio.loop(Clip.LOOP_CONTINUOUSLY);
		
//		setSize(Menu.BUTTON_SIZE);
		setLayout(new BorderLayout());
		//setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel lbVolumn= new JLabel("Volumn");
		lbVolumn.setPreferredSize(new Dimension((int)(Menu.BUTTON_SIZE.width), (int)(Menu.BUTTON_SIZE.height/2.5)));
		lbVolumn.setBorder(new EmptyBorder(0, 5, 0, 0));
		lbVolumn.setFont(IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(Menu.BUTTON_SIZE.height/4)));
		lbVolumn.setOpaque(false);

		//lbVolumn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		audioSlider = new JSlider();
		audioSlider.setOrientation(JSlider.HORIZONTAL);
		audioSlider.setMinimum(0);
		audioSlider.setMaximum(100);
		audioSlider.setValue(50);
		audioSlider.setMajorTickSpacing(20);
		audioSlider.setMinorTickSpacing(10);
		audioSlider.setPaintTicks(true);
		audioSlider.setPaintLabels(true);
		audioSlider.setOpaque(false);
		audioSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = (float)audioSlider.getValue() / 100;
                FloatControl gainControl = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);//dB = 20 * log10(volume).
                gainControl.setValue(dB);
            }
        });
		add(lbVolumn, BorderLayout.NORTH);
		add(audioSlider, BorderLayout.CENTER);
		setOpaque(false);
		
		audioThread = new Thread(this);
		audioThread.start();
	}
	@Override
	public void run() {
		audio.start();
	}
	
	public void stop() {
		audio.stop();
        audio.close();
		audioThread.interrupt();
	}

}
