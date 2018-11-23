import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
class TestToChat {

	static ServerSocket ss;
	static Socket s;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ss = new ServerSocket(2222,8000,InetAddress.getByName("127.0.0.1"));
		s = new Socket("127.0.0.1",2222);
	}

	@Test
	void testClientOpen() throws IOException {
		s.isClosed();	
	}
	@Test
	void SocktClientIp() throws IOException {
		boolean a=(s.getInetAddress().equals("127.0.0.1"));
		if(a) {
			fail("Eror");
		}
	}
	void SocktClientPort() throws IOException {
		boolean a=(s.getInetAddress().equals(2222));
		if(a) {
			fail("Eror");
		}
	}
	@Test
	void testServerStart() throws IOException {
		if(ss.getLocalPort() != 2222) {
			fail("You Got Worng with the server socket");
		}
		if (!ss.getInetAddress().toString().equals("/127.0.0.1")) {
			fail("You Got Worng with the localhost");

		}
	}
	@Test
	void testisClosed(){
		if(ss.isClosed()) {
			fail("Your Server Socket closed before he need to");
		}
	}
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		ss.close();
		s.close();
	}
}
