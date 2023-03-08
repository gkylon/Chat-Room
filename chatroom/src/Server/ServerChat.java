package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;


public class ServerChat {
    //����һ��main�������ǳ������ڣ�
    public static void main(String[] args) throws Exception {
        ServerJframe serverJframe = new ServerJframe();
        serverJframe.init();
    }
}

class ServerJframe extends JFrame {
    //GUI�������
    JTextArea serverTa = new JTextArea();
    JPanel btnTool = new JPanel();
    JButton startBtn = new JButton("����");
    JButton stopBtn = new JButton("ֹͣ");
    //�˿�
    private static final int PORT = 8888;
    //ServerSocket
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    //Server��������
    private DataInputStream dataInputStream = null;

    // ����ͻ��˷���ʱ���ͻ��˶�������List��
    private ArrayList<ClientCoon> ccList = new ArrayList<ClientCoon>();

    // �����������ı�־ (��ʵServerSocket ss ��ʼ������ʱ��ζ�ŷ�����������)
    private boolean isStart = false;

    public void init() throws Exception {
        this.setTitle("�������˴���");
        this.add(serverTa, BorderLayout.CENTER);//��ʽ����
        btnTool.add(startBtn);
        btnTool.add(stopBtn);
        this.add(btnTool, BorderLayout.SOUTH);

        this.setBounds(0, 0, 500, 500);

        //�жϷ������Ƿ��Ѿ�����
        if (isStart) {
            System.out.println("�������Ѿ�������\n");
        } else {
            System.out.println("��������û������������������������\n");
        }
        //��ť���������������������ÿ�ʼλfalse
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (serverSocket == null) {
                        serverSocket = new ServerSocket(PORT);
                    }
                    isStart = true;
                    //startServer();
                    serverTa.append("�������Ѿ��������� \n");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        // ��ֹ��ť����ֹͣ���������ÿ�ʼλtrue
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                        isStart = false;
                    }
                    System.exit(0);
                    serverTa.append("��������������\n");
                    System.out.println("��������������\n");

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        /**
         * ���������ڹر�Ӧ��ֹͣ����������Ľ��Ĵ���
         */
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // serverTa.setEditable(false);
        this.setVisible(true);
        startServer();
    }

    /**
     * ��������������
     */
    public void startServer() throws Exception {
        try {
            try {
                serverSocket = new ServerSocket(PORT);
                isStart = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ���Խ��ܶ���ͻ��˵�����
            // ��ÿһ����Ϣʱ���������������նϣ����Խ���д��while�����У��жϷ�Ϊ���������ص��жϷ�
            while (isStart) {
                socket = serverSocket.accept();
                ccList.add(new ClientCoon(socket));
                System.out.println("\n" + "һ���ͻ������ӷ�����" + socket.getInetAddress() + "/" + socket.getPort());
                serverTa.append("\n" + "һ���ͻ������ӷ�����" + socket.getInetAddress() + "/" + socket.getPort());
            }
            //���������ܿͻ���һ�仰
            /*receiveStream();*/
        } catch (SocketException e) {
            System.out.println("�������ն��ˣ�����");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * ������ֹͣ����
     * */

    /**
     * �������������ݵķ������ͻ��˴���һ�仰��,�����ö���ͻ��˽���ͨ��
     * */
    /*public void receiveStream(){
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            serverTa.append(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /**
     * @author ������
     * @deprecated �ڲ������� ���� ������������ڷ������˵�һ�����Ӷ���
     */
    class ClientCoon implements Runnable {
        Socket socket = null;

        public ClientCoon(Socket socket) {
            this.socket = socket;
            /**
             * �߳����������
             * ��ʼ�������� ��ʼ��һ���߳� ���߳��з�װ�����Լ����������̵߳ĵ���
             */
            (new Thread(this)).start();
        }

        //���ܿͻ�����Ϣ�����߳�run����������
        @Override
        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                // Ϊ���÷������ܹ����ܵ�ÿ���ͻ��˵Ķ�仰
                while (isStart) {
                    //readUTF()��һ��������������һ���ִ�����ˣ�����ѭ����
                    String str = dataInputStream.readUTF();
                    System.out.println("\n" + socket.getInetAddress() + "|" + socket.getPort() + "˵" + str + "\n");
                    serverTa.append("\n" + socket.getInetAddress() + "|" + socket.getPort() + "˵" + str + "\n");
                    //��������ÿ���ͻ��˷��ͱ�Ŀͻ��˷�������Ϣ
                    // ����ccList������send����,�ڿͻ��������Ӧ���Ƕ��̵߳Ľ���
                    String strSend = "\n" + socket.getInetAddress() + "|" + socket.getPort() + "˵" + str + "\n";
                    Iterator<ClientCoon> iterator = ccList.iterator();
                    while (iterator.hasNext()) {
                        ClientCoon clientCoon = iterator.next();
                        clientCoon.send(strSend);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ��������ÿ�����Ӷ��������ݵķ���
        public void send(String str) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                dataOutputStream.writeUTF(str);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}



