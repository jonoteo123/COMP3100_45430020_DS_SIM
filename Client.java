import java.io.*;
import java.net.*;
import java.util.Arrays;


class Client {

    public static void sendMsg(DataOutputStream dout, String msg) {
        try {
            byte[] message = msg.getBytes();
            message = Arrays.copyOf(message, message.length + 1);
            message[message.length - 1] = 10;
            dout.write(message);
            dout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reads the incoming messaged passed into it.
    public static String readMsg(DataInputStream din) {
        String message = "";
        try {
            // Despite readLine being deprecated, we still use it as it is the best way to read an incoming stream of characters.
            message = din.readLine();
            while(din.available() > 0) {
                message += "\n" + din.readLine();
            }
        // We need to catch any exceptions that might occur when the code is run
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    // Performs the initialisaton of the socket.
    public static void performHandshake(DataInputStream din, DataOutputStream dout) {
        sendMsg(dout, "HELO");
        readMsg(din);
        sendMsg(dout, "AUTH " + System.getProperty("user.name"));
        readMsg(din);
    }

    public static void main(String args[]) throws Exception {
        Socket s = new Socket("localhost", 50000);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        // Start handshake
        performHandshake(din, dout);

        // Run the BestFit algorithm
        BetterFit fit = new BetterFit(s, din, dout);
        fit.run();

        // Quit
        sendMsg(dout, "QUIT");
        readMsg(din);

        din.close();
        dout.close();
        s.close();
    }

}