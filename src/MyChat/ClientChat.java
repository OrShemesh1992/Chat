package MyChat;
import java.net.*;
import java.io.*;
import java.util.*;
public class ClientChat extends javax.swing.JFrame 
{
	public String username;
	public String address = "localhost";
	public ArrayList<String> users = new ArrayList();
	public ArrayList<String> whoIsOnline;
	public int port = 2222;
	public Boolean isConnected = false;
	public Socket sock;
	public BufferedReader reader;
	public PrintWriter writer;
	/**
	 * Iterator of users
	 * @return
	 */
	public Iterator<String> iteretor() 
	{
		return  this.users.iterator();
	}

	/**
	 * founction Thread start The Server
	 */
	public void ListenThread() 
	{
		Thread startTheServer = new Thread(new IncomingReader());
		startTheServer.start();
	}
	public void writeUsers() 
	{
		whoIsOnline=new ArrayList<String>(users);
	}
	public void sendDisconnect() 
	{
		String bye = (username + ": :Disconnect");
		try
		{
			writer.println(bye); 
			writer.flush();
		} catch (Exception e) 
		{
			ta_chat.append("Could not send Disconnect message.\n");
		}
	}
	public void Disconnect() 
	{
		try 
		{
			ta_chat.append("Disconnected.\n");
			sock.close();
		} catch(Exception ex) {
			ta_chat.append("Failed to disconnect. \n");
		}
		isConnected = false;
		tf_username.setEditable(true);
	}  
	public ClientChat() 
	{
		initComponents();
	}
	public class IncomingReader implements Runnable
	{
		@Override
		public void run() 
		{
			String[] data;
			String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
			try 
			{
				while ((stream = reader.readLine()) != null) 
				{
					data = stream.split(":");
					if (data[2].equals(chat)) 
					{
						if(data[1].contains("@"))
						{
							String s[] = data[1].split(" ");
							String choosen = s[0].replace("@", "");
							int n = data[1].indexOf(" ");
							data[1] = data[1].substring(n, data[1].length());
							if(choosen.equals(username))
							{
								ta_chat.append(data[0] + ": " + data[1] + "\n");
							}
						}
						else
						{
							ta_chat.append(data[0] + ": " + data[1] + "\n");
							ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
						}
					} 
					else if (data[2].equals(connect))
					{
						ta_chat.removeAll();
						users.add(data[0]);

					} 
					else if (data[2].equals(disconnect)) 
					{
						ta_chat.append(data[0] + " is now offline.\n");
						users.remove(data[0]);
					} 
					else if (data[2].equals(done)) 
					{
						writeUsers();
						users.clear();
					}     
				}
			}catch(Exception ex) { }

		}
	}


	//gui


	private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {

	}
	private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {   
	}
	private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {
		if (isConnected == false) 
		{
			username = tf_username.getText();
			tf_username.setEditable(false);

			try 
			{
				sock = new Socket(address, port);
				InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(streamreader);
				writer = new PrintWriter(sock.getOutputStream());
				writer.println(username + ":has connected.:Connect");
				writer.flush(); 
				isConnected = true; 
			} 
			catch (Exception ex) 
			{
				ta_chat.append("Cannot Connect! Try Again. \n");
				tf_username.setEditable(true);
			}

			ListenThread();

		} else if (isConnected == true) 
		{
			ta_chat.append("You are already connected. \n");
		}
	}
	private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {
		sendDisconnect();
		Disconnect();
	}
	private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {
		if (isConnected == false) {
			ta_chat.append("you are not online");
			ta_chat.append("\n");

		}
			else {
				try {
					Iterator<String> It=this.whoIsOnline.iterator();
					ta_chat.append("Online Users:");
					ta_chat.append("\n");
					while(It.hasNext())
					{
						ta_chat.append(It.next()+" \n");
					}
				}
				catch (Exception ex) {
					ta_chat.append("you are not online");
					ta_chat.append("\n");

				}
			}
	}
	private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {
		String nothing = "";
		if ((tf_chat.getText()).equals(nothing)) {
			tf_chat.setText("");
			tf_chat.requestFocus();
		} else {
			try {
				writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
				writer.flush();
			} catch (Exception ex) {
				ta_chat.append("Message was not sent. \n");
			}
			tf_chat.setText("");
			tf_chat.requestFocus();
		}

		tf_chat.setText("");
		tf_chat.requestFocus();
	}
	public static void main(String args[]) 
	{
		java.awt.EventQueue.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				new ClientChat().setVisible(true);
			}
		});
	}
	@SuppressWarnings("unchecked")
	private void initComponents() {
		lb_address = new javax.swing.JLabel();
		tf_address = new javax.swing.JTextField();
		lb_username = new javax.swing.JLabel();
		tf_username = new javax.swing.JTextField();
		b_connect = new javax.swing.JButton();
		b_disconnect = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		ta_chat = new javax.swing.JTextArea();
		tf_chat = new javax.swing.JTextField();
		b_send = new javax.swing.JButton();
		lb_name = new javax.swing.JLabel();
		b_users = new javax.swing.JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat - Client's frame");
		setName("client"); // NOI18N
		setResizable(false);
		lb_address.setText("Address : ");
		tf_address.setText("localhost");
		tf_address.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tf_addressActionPerformed(evt);
			}
		});
		lb_username.setText("Username :");
		tf_username.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tf_usernameActionPerformed(evt);
			}
		});
		b_connect.setText("Connect");
		b_connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_connectActionPerformed(evt);
			}
		});
		b_disconnect.setText("Disconnect");
		b_disconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_disconnectActionPerformed(evt);
			}
		});
		b_users.setText("Online Users:");
		b_users.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_usersActionPerformed(evt);
			}
		});
		ta_chat.setColumns(20);
		ta_chat.setRows(5);
		jScrollPane1.setViewportView(ta_chat);

		b_send.setText("SEND");
		b_send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendActionPerformed(evt);
			}
		});
		lb_name.setText("Or&Yaara&Omer");
		lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
								.addComponent(jScrollPane1)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
												.addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
												.addComponent(tf_username))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup()
														.addComponent(b_connect)
														.addGap(2, 2, 2)
														.addComponent(b_disconnect)
														.addGap(0, 0, Short.MAX_VALUE))))))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lb_name)
						.addGap(201, 201, 201))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lb_address)
								.addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(b_users))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(tf_username)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(lb_username)
										.addComponent(b_connect)
										.addComponent(b_disconnect)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(tf_chat)
								.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(lb_name))
				);
		pack();
	}
	private javax.swing.JButton b_connect;
	private javax.swing.JButton b_disconnect;
	private javax.swing.JButton b_send;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_address;
	private javax.swing.JLabel lb_name;
	private javax.swing.JLabel lb_username;
	private javax.swing.JTextArea ta_chat;
	private javax.swing.JTextField tf_address;
	private javax.swing.JTextField tf_chat;
	private javax.swing.JTextField tf_username;
	private javax.swing.JButton b_users;
}
