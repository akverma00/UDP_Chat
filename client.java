import java.io.*; // Imported because we need the InputStream and OuputStream classes
import java.net.*; // Imported because the Socket class is needed

class UDPChatClient {
    public static DatagramSocket clientsocket;
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
        clientsocket = new DatagramSocket(cport);
        dp = new DatagramPacket(buf, buf.length);
        dis = new BufferedReader(new InputStreamReader(System.in));
        ia = InetAddress.getLocalHost();

        System.out.print(CLEAR);
        printText(GREEN, "Client is Running... ");
        printText(RED, "(Type 'STOP'to Quit) \n\n");
        while (true) {
            printMessage("Client", YELLOW_BOLD, "");
            String str = new String(dis.readLine());
            buf = str.getBytes();
            if (str.equals("STOP")) {
                printText(RED, "Terminated...");
                clientsocket.send(new DatagramPacket(buf, str.length(), ia, sport));
                break;
            }
            clientsocket.send(new DatagramPacket(buf, str.length(), ia, sport));
            clientsocket.receive(dp);
            String str2 = new String(dp.getData(), 0, dp.getLength());
            printMessage("Server", YELLOW_BOLD, str2 + "\n");
        }
    }
}