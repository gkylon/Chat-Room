package Robot;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainRobot {
	
	private static final RobotService robotService = new QkyRobotServiceImpl();
	
	public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("与机器人聊天");
        JPanel panel = new JPanel(new GridLayout(3,1));
        JPanel questionPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel();
        JPanel answerPanel = new JPanel(new FlowLayout());
        JLabel question = new JLabel("问题");
        final JTextField enterQuestion = new JTextField(20);
        JLabel answer = new JLabel("机器人回答");
        final JTextArea enterAnswer = new JTextArea(3,25);
        JButton submit = new JButton("提交");
        
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        enterAnswer.setFont(new Font("宋体",Font.BOLD,15));
        enterQuestion.setFont(new Font("宋体",Font.BOLD,15));

        enterAnswer.setLineWrap(true);
        
        questionPanel.add(question);
        questionPanel.add(enterQuestion);

        answerPanel.add(answer);
        answerPanel.add(enterAnswer);
        buttonPanel.add(submit);
        panel.add(questionPanel);
        panel.add(answerPanel);
        panel.add(buttonPanel);
        frame.add(panel);
        
        submit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String answer = new String();
                String q = enterQuestion.getText();
                try {
                    answer = machine(q);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                enterAnswer.setText(answer);
        	}
        });
        
        enterQuestion.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
               
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10 || e.getKeyCode()==38) {
                    String answer = new String();
                    String q = enterQuestion.getText();
                    try {
                        answer = machine(q);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    enterAnswer.setText(answer);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                
            }
            
        });
        
	}
	
	private static String machine(String question) throws IOException {
        //接入机器人，输入问题
		String input = question;
		String answer;
		Response response = robotService.qa(input);
		if(response != null && response.getCode() == 0){
           answer=new String(response.getContent().getBytes(),"UTF-8");
        }
		else {
			answer=new String("你刚刚这句话我没听懂，可否再陈述一次~");
		}
        return answer;
    }
}
