package MyChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class ServerChat extends javax.swing.JFrame 
{
	public ArrayList clientOutputStreams;
	public  ArrayList<String> users;
	PrintWriter writer;

	public Iterator<String> iteretor() 
    	{
		return  this.users.iterator();
		}
	public class ClientHandler implements Runnable	
	{
		BufferedReader reader;
		Socket sock;
		PrintWriter client;

		public ClientHandler(Socket clientSocket, PrintWriter user) 
		{
			client = user;
			try 
			{
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}
			catch (Exception ex) 
			{
				ta_chat.append("Unexpected error... \n");
			}
		}
		@Override
		public void run() 
		{
			String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
			String[] data;
			try 
			{
				while ((stream = reader.readLine()) != null) 
				{
					data = stream.split(":");

					ta_chat.append("Received: " + stream + "\n");
					data = stream.split(":");
					for (String token:data) 
					{
						ta_chat.append(token + "\n");
					}
					if (data[2].equals(connect)) 
					{
						tellEveryone((data[0] + ":" + data[1] + ":" + chat));
						userAdd(data[0]);
					} 
					else if (data[2].equals(disconnect)) 
					{
						tellEveryone((data[0] + ":has disconnected." + ":" + chat));

						userRemove(data[0]);
					} 
					else if (data[2].equals(chat)) 
					{
						tellEveryone(stream);
					} 
					else 
					{
						ta_chat.append("No Conditions were met. \n");
					}
				} 
			} 
			catch (Exception ex) 
			{
				ta_chat.append("Lost a connection. \n");
				ex.printStackTrace();
				clientOutputStreams.remove(client);
			} 
		}
	}

	public ServerChat() 
	{
		initComponents();
	}

	@SuppressWarnings("unchecked")

	private void initComponents() {
		jScrollPane1 = new javax.swing.JScrollPane();
		ta_chat = new javax.swing.JTextArea();
		b_start = new javax.swing.JButton();
		b_end = new javax.swing.JButton();
		b_users = new javax.swing.JButton();
		b_send = new javax.swing.JButton();
		b_clear = new javax.swing.JButton();
		b_sendText = new javax.swing.JTextField();
		lb_name = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		ta_chat.setColumns(20);
		ta_chat.setRows(5);
		jScrollPane1.setViewportView(ta_chat);

		b_send.setText("Send");
		b_send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendActionPerformed(evt);
			}
		});

		b_start.setText("Connect");
		b_start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_startActionPerformed(evt);
			}
		});

		b_users.setText("Show Online");
		b_users.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_usersActionPerformed(evt);
			}
		});

		b_end.setText("Disconnect");
		b_end.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_endActionPerformed(evt);
			}
		});

		b_sendText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendTextActionPerformed(evt);
			}
		});

		b_clear.setText("Clear");
		b_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_clearActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(b_sendText, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(b_end, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(b_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
										.addGap(84, 84, 84)
										.addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(b_end, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(19, 19, 19)
										.addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(b_sendText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				);

		pack();
	}
	private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {        
		String nothing = "";
		if ((b_sendText.getText()).equals(nothing)) {
			b_sendText.setText("");
			b_sendText.requestFocus();
		}
		else {
			try {
				tellEveryone("Server" + ":" + b_sendText.getText() + ":" + "Chat");
				writer.flush(); // flushes the buffer
			} catch (Exception ex) {
			}
			b_sendText.setText("");
			b_sendText.requestFocus();
		}

		b_sendText.setText("");
		b_sendText.requestFocus();

	}  
	private void b_sendTextActionPerformed(java.awt.event.ActionEvent evt) {                                             
	}  
	private void b_endActionPerformed(java.awt.event.ActionEvent evt) {
		try 
		{
			Thread.sleep(5000);
		} 
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}
		tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
		ta_chat.append("Server stopping... \n");
		ta_chat.setText("");
	}
	private void b_startActionPerformed(java.awt.event.ActionEvent evt) {
		Thread starter = new Thread(new ServerStart());
		starter.start();
		ta_chat.append("Server started...\n");
	}
	public void b_usersActionPerformed(java.awt.event.ActionEvent evt) {
		ta_chat.append("\n Online users : \n");
		Iterator<String> It = this.iteretor();
		while(It.hasNext()) {
			ta_chat.append(It.next());
			ta_chat.append("\n");
		}
	}

	private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {
		ta_chat.setText("");
	}
	public static void main(String args[]) 
	{
		java.awt.EventQueue.invokeLater(new Runnable() 
		{
			@Override
			public void run() {
				new ServerChat().setVisible(true);
			}
		});
	}
	public class ServerStart implements Runnable 
	{
		@Override
		public void run() 
		{
			clientOutputStreams = new ArrayList();
			users = new ArrayList();  
			try 
			{
				ServerSocket serverSock = new ServerSocket(2222);
				while (true) 
				{
					Socket clientSock = serverSock.accept();
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
					clientOutputStreams.add(writer);

					Thread listener = new Thread(new ClientHandler(clientSock, writer));
					listener.start();
					ta_chat.append("Got a connection. \n");
				}
			}
			catch (Exception ex)
			{
				ta_chat.append("Error making a connection. \n");
			}
		}
	}
	public void userAdd (String data) 
	{
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		ta_chat.append("Before " + name + " added. \n");
		users.add(name);
		ta_chat.append("After " + name + " added. \n");
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);
		for (String token:tempList) 
		{
			message = (token + add);
			tellEveryone(message);
		}
		tellEveryone(done);
	}  
	public void userRemove (String data) 
	{
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		users.remove(name);
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);
		for (String token:tempList) 
		{
			message = (token + add);
			tellEveryone(message);
		}
		tellEveryone(done);
	} 
	public void tellEveryone(String message) 
	{
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) 
		{
			try 
			{
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				ta_chat.append("Sending: " + message + "\n");
				writer.flush();
				ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

			} 
			catch (Exception ex) 
			{
				ta_chat.append("Error telling everyone. \n");
			}
		} 
	}
	private javax.swing.JButton b_send;
	private javax.swing.JTextField b_sendText;
	private javax.swing.JButton b_clear;
	private javax.swing.JButton b_end;
	private javax.swing.JButton b_start;
	private javax.swing.JButton b_users;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_name;
	private javax.swing.JTextArea ta_chat;
}
