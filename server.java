import java.io.*;
import java.net.*;

class UDPChatServer {
    public static DatagramSocket serversocket;
    public static DatagramPacket dp;
    public static BufferedReader dis;
    public static InetAddress ia;
    public static byte buf[] = new byte[1024];
    public static int cport = 789, sport = 790;

    // Defining colors
    public static final String CLEAR = "\033[H\033[2J"; // CLEAR_CONSOLE
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String RED_BOLD = "\033[1;31m"; // RED_BOLD
    public static final String GREEN_BOLD = "\033[1;32m"; // GREEN_BOLD
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW_BOLD
    public static final String ANSI_RESET = "\u001B[0m"; // ANSI_RESET

    public static void printMessage(String preS, String ANSI_COLOR, String message) {
        System.out.print(ANSI_COLOR + preS + ": " + ANSI_RESET + message);
    }

    public static void printText(String ANSI_COLOR, String message) {
        System.out.print(ANSI_COLOR + message + ANSI_RESET);
    }

    public static void main(String[] a) throws IOException {
        serversocket = new DatagramSocket(sport);
        dp = new DatagramPacket(buf, buf.length);
        dis = new BufferedReader(new InputStreamReader(System.in));
        ia = InetAddress.getLocalHost();
        System.out.println(CLEAR);
        System.out.println("Server is Running...");
        while (true) {
            serversocket.receive(dp);
            String str = new String(dp.getData(), 0, dp.getLength());
            if (str.equals("STOP")) {
                System.out.println("Terminated...");
                break;
            }
            System.out.println("Client: " + str);
            String str1 = new String(dis.readLine());
            buf = str1.getBytes();
            serversocket.send(new DatagramPacket(buf, str1.length(), ia, cport));
        }
    }
}