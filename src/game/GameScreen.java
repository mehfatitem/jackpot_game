package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameScreen extends Component {
	private static final long serialVersionUID = 1L;

	//game panel component//
	private JFrame frame = new JFrame();
	private JButton buttonStart = new JButton();
	private JButton buttonReset = new JButton();
	private JLabel lifeName = new JLabel();
	private JLabel lifeScore = new JLabel();
	private JLabel pointName = new JLabel();
	private JLabel pointScore = new JLabel();
	private ImagePanel imgp;
	private ImagePanel imgp1;
	private ImagePanel imgp2;

	//game algorithms values//
	private String[][] picMatrix = new String[10][10];
	private int ImlocationX = 100;
	private int ImlocationY = 100;
	private int life = 5;
	private int point = 0;
	private boolean confirm = false;
	
	//Sound object//
	private Sound snd = new Sound();
	
	//values for jackpot table//
	private final String gameIcon = "D:////gameIcon//gameIcon.jpg//";
	private final String winMusic = "D://///gameMusic//winMusic.wav//";
	private final String resetMusic = "D://///gameMusic//resetMusic.wav//";
	private final String jackPotPic = "D:////jackpotPic//";
	private final String voiceIcon = "D:////voiceIcon//";
	private final String[] path = { jackPotPic + "rihanna.jpg", jackPotPic + "angelina.jpg", jackPotPic + "shakira.jpg" };
	private final String[] voicePath = { voiceIcon + "voice.jpg", voiceIcon + "muteVoice.jpg" };
	private Dimension size = new Dimension(500, 530);

	public GameScreen() {
		render();
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				snd.playMusic("D://///gameMusic//playMusic.wav//", true);
				life--;
				lifeScore.setText(Integer.toString(life));
				resetComponent();
				render();
				displayMessage(life);
				resetMatrix(picMatrix, 3, 3);
				if (life == 0 && confirm == false) {
					snd.playMusic("D://///gameMusic//loseMusic.wav//", true);
					JOptionPane.showMessageDialog(null, "You lost !!!", "Game Over ", JOptionPane.PLAIN_MESSAGE);
					life = 5;
					point = 0;
					lifeScore.setText(Integer.toString(life));
				}
				confirm = false;
			}
		});
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				snd.playMusic(resetMusic, true);
				confirm = false;
				life = 5;
				point = 0;
				lifeScore.setText(Integer.toString(life));
				resetComponent();
				render();
				resetMatrix(picMatrix, 3, 3);
			}
		});
	}

	public void makeForm() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.RED);
		frame.getContentPane().setLayout(null);
		frame.setTitle("JACKPOT GAME");
		buttonStart.setBounds(100, 440, 90, 25);
		buttonStart.setText("PLAY");
		buttonReset.setBounds(300, 440, 90, 25);
		buttonReset.setText("RESET");
		lifeName.setBounds(20, 40, 90, 25);
		lifeName.setText("LIFE :");
		lifeScore.setBounds(60, 40, 90, 25);
		lifeScore.setText(Integer.toString(life));
		pointName.setBounds(20, 60, 90, 25);
		pointName.setText("SCORE :");
		pointScore.setBounds(70, 60, 90, 25);
		pointScore.setText(Integer.toString(point));

		frame.getContentPane().add(pointName);
		frame.getContentPane().add(pointScore);
		frame.getContentPane().add(lifeName);
		frame.getContentPane().add(lifeScore);
		frame.getContentPane().add(buttonStart);
		frame.getContentPane().add(buttonReset);
		frame.pack();
		Toolkit t = getToolkit();
		Image img = t.getImage(gameIcon);
		frame.setIconImage(img);
		frame.setSize(size);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void setVoiceIcon() {
		imgp1 = new ImagePanel(new ImageIcon(voicePath[0]).getImage());
		imgp1.setLocation(445, 5);
		frame.getContentPane().add(imgp1);

		imgp2 = new ImagePanel(new ImageIcon(voicePath[1]).getImage());
		imgp2.setLocation(445, 5);
		frame.getContentPane().add(imgp2);

		imgp1.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
				imgp1.show(false);
				imgp2.show(true);
			}
		});
		imgp2.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
				imgp2.show(false);
				imgp1.show(true);
			}
		});
	}

	public void makePictureMatrix() {
		Random rnd = new Random();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int temp = rnd.nextInt(3);
				picMatrix[i][j] = path[temp];
				imgp = new ImagePanel(new ImageIcon(picMatrix[i][j]).getImage());
				imgp.setLocation(ImlocationX, ImlocationY);
				frame.getContentPane().add(imgp);
				ImlocationX += 100;
			}
			ImlocationX = 100;
			ImlocationY += 100;
		}
	}

	public void resetMatrix(String[][] matrix, int x, int y) {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				picMatrix[i][j] = "";
			}
		}
	}

	public void resetComponent() {
		frame.getContentPane().removeAll();
		ImlocationX = 100;
		ImlocationY = 100;
	}

	public void displayMessage(int life) {
		if (picMatrix[1][0] == picMatrix[1][1] && picMatrix[1][1] == picMatrix[1][2]) {
			confirm = true;
			pointScore.setText(Integer.toString(calculateScore(life)));
			life = 5;
			point = 0;
			snd.playMusic(winMusic, true);
			JOptionPane.showMessageDialog(null, "You win.", "Game Over ", JOptionPane.PLAIN_MESSAGE);
			lifeScore.setText(Integer.toString(life));
			pointScore.setText(Integer.toString(calculateScore(life)));
		}
	}

	public int calculateScore(int life) {
		switch (life) {
		case 4:
			point += 150;
			break;
		case 3:
			point += 100;
			break;
		case 2:
			point += 75;
			break;
		case 1:
			point += 50;
			break;
		case 0:
			point += 25;
			break;
		default:
			System.out.println("Error life value !!!");
			break;
		}
		return point;
	}

	public void render() {
		this.setVoiceIcon();
		this.makePictureMatrix();
		this.makeForm();
	}


	public static void main(String[] args) {
		new GameScreen();
	}
}
