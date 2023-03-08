package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import Robot.MainRobot;

public class ClientChat {
    //����һ��main�������ǳ������ڣ�
    public static void main(String[] args) {
        ClientJframe clientJframe = new ClientJframe();
        clientJframe.init();
    }
}

class ClientJframe extends JFrame {
    //GUI����
    //�����¼��ʾ��
    private JTextArea ta = new JTextArea(10, 20);
    //�����¼������
    private JTextField tf = new JTextField(20);
    //���������������ť
    private JButton robot =new JButton("�����");
    //�˿�
    // ��̬���������˿ں�
    private static final String CONNSTR = "127.0.0.1";
    // ��̬�����������˿ں�
    private static final int CONNPORT = 8888;
    private Socket socket = null;

    //Client��������
    private DataOutputStream dataOutputStream = null;

    //�ͻ��������Ϸ������жϷ���
    private boolean isConn = false;

    /**
     * �޲εĹ��췽�� throws HeadlessException
     */
    public ClientJframe() throws HeadlessException {
        super();
    }

    public void init() {
        this.setTitle("�ͻ��˴���");
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        this.add(robot,BorderLayout.NORTH);
        this.setBounds(300, 300, 400, 400);


        // ��Ӽ�����ʹ�س���������������(�ж����ݺϷ���)��
        // ��ݔ�뵽����򣬻���
        tf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String strSend = tf.getText();
                // ȥ���ո��жϳ����Ƿ�Ϊ��
                if (strSend.trim().length() == 0) {
                    return;
                }
                //�ͻ�����ϢstrSend���͵���������
                send(strSend);
                tf.setText("");
                //ta.append(strSend + "\n");

            }
        });

        robot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    MainRobot.main(null);
                }
                catch(Exception x){
                    x.printStackTrace();
                }

            }
        });

        //�ر��¼�
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ta.setEditable(false);//�������򲻿�������
        tf.requestFocus();//���۽�

        try {
            socket = new Socket(CONNSTR, CONNPORT);
            isConn = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // �������߳�
        new Thread(new Receive()).start();

        this.setVisible(true);
    }

    /**
     * �ͻ��˷�����Ϣ���������ϵķ���
     */
    public void send(String str) {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class Receive implements Runnable {
        @Override
        public void run() {
            try {
                while (isConn) {
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    String str = dataInputStream.readUTF();
                    //ͨѶ��Ϣ
                    ta.append(str);
                }
            } catch (SocketException e) {
                System.out.println("������������ֹ�ˣ�");
                ta.append("������������ֹ�ˣ�");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}