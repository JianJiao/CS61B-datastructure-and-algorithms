import java.io.*;
public class Nuke2{
	public static void main(String[] args) throws IOException {
		Nuke2 n = new Nuke2();
		n.go();
	}

	public void go() throws IOException{
		String line;
		BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
		line = br.readLine();
		StringBuilder sb = new StringBuilder(line);
		sb.deleteCharAt(1);
		line = sb.toString();
		System.out.println(line);
	}
}
