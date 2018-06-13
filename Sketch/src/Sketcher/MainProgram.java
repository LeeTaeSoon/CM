package Sketcher;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainProgram extends JFrame {
	BorderLayout mainLayout;
	CardLayout cardLayout;
	
	JPanel centerPanel;
	MyDrawing Sketcher;
	
	JPanel MenuPanel;
	JButton LoginBtn;
	JButton LogoutBtn;
	
	JPanel LoginPanel;
	
	public MainProgram() {
		
//		ButtonHandler btnHandler = new ButtonHandler();
//		cardLayout = new CardLayout();
//		mainLayout = new BorderLayout();
//		
//		centerPanel = new JPanel(cardLayout);
//		
//		Sketcher = new MyDrawing();
//		LoginPanel = new JPanel();
//		
//		//centerPanel.add("Login",LoginPanel);
//		centerPanel.add("Sketcher",Sketcher);
//			
//		MenuPanel = new JPanel(new FlowLayout());
//		LoginBtn = new JButton("로그인");
//		LogoutBtn = new JButton("로그아웃");
//		LoginBtn.addActionListener(btnHandler);
//		LogoutBtn.addActionListener(btnHandler);
//		MenuPanel.add(LoginBtn);
//		MenuPanel.add(LogoutBtn);
//		
//		setLayout(mainLayout);
//		add(centerPanel, BorderLayout.CENTER);
		Sketcher = new MyDrawing();
		add(Sketcher);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height - 50;
		int screenWidth = screenSize.width;
		setSize(screenWidth, screenHeight);
		setVisible(true);
	}
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object o=e.getSource();
			if(o instanceof JButton) {
				if(o == LoginBtn) {
					cardLayout.next(centerPanel);
				}
				else if( o == LogoutBtn) {
					cardLayout.first(centerPanel);
				}
			}
		}
		
	}
}
