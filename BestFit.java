import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BestFit {
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    public static void sendMsg(DataOutputStream dout, String msg) {
        try {
            byte[] message = msg.getBytes();
            message = Arrays.copyOf(message, message.length + 1);
            message[message.length - 1] = 10;
            dout.write(message);
            // System.out.println("SENT " + msg);
            dout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readMsg(DataInputStream din) {
        String message = "";
        try {
            message = din.readLine();

            while (din.available() > 0) {
                message += "\n" + din.readLine();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        // System.out.println("RCVD " + message);
        return message;
    }

    public BestFit(Socket s, DataInputStream din, DataOutputStream dout) {
        this.s = s;
        this.din = din;
        this.dout = dout;
    }

    public void run() throws IOException {
        sendMsg(this.dout, "REDY");

        // response
        String response = readMsg(this.din);

        // while REDY returns not none
        while (!response.contains("NONE")) {

            // Split the job
            String[] job = response.split("\\s");

            // if REDY is a job
            if (response.contains("JOBN")) {
                // GETS Capable servers based on submitted core, memory and disk
                sendMsg(this.dout, "GETS Capable " + job[4] + " " + job[5] + " " + job[6]);
                readMsg(this.din);
                sendMsg(this.dout, "OK");

                // String of all servers capable of running job
                String serverstring = readMsg(this.din);
                String[] serverlist = serverstring.split("\\s");
                ArrayList<Server> servers = new ArrayList<Server>();

                // serverType, serverID, state, curStartTime, core, mem, disk, wJobs, rJobs
                for (int i = 0; i < serverlist.length; i += 9) {
                    if ((serverlist.length - i) >= 9) {
                        String type = serverlist[i];
                        int id = Integer.parseInt(serverlist[i + 1]);
                        String state = serverlist[i + 2];
                        int cst = Integer.parseInt(serverlist[i + 3]);
                        int core = Integer.parseInt(serverlist[i + 4]);
                        int mem = Integer.parseInt(serverlist[i + 5]);
                        int disk = Integer.parseInt(serverlist[i + 6]);
                        int wJobs = Integer.parseInt(serverlist[i + 7]);
                        int rJobs = Integer.parseInt(serverlist[i + 8]);
                        servers.add(new Server(type, id, state, cst, core, mem, disk, wJobs, rJobs));
                    }
                }

                // Test the closeness value
                int closenessCore = servers.get(0).core - Integer.parseInt(job[4]);
                // Choose the first server as the best server for the initial run
                Server best_server = servers.get(0);

                // Traverse through all capable servers
                for (Server server : servers) {
                    // Obtain new fitness value
                    int fitness_value_core = server.core - Integer.parseInt(job[4]); 
                    // If the job's fitness value is less than 0, we select the first server, this
                    // is only a conditional
                    if (closenessCore < 0) {
                        closenessCore = fitness_value_core;
                        best_server = server;
                        // Find the job's closest server based on the fitness value.
                    } else if ((fitness_value_core < closenessCore) && server.wJobs < best_server.wJobs) {
                        closenessCore = fitness_value_core;
                        best_server = server;
                    }
                }



                // send OK
                sendMsg(this.dout, "OK");
                readMsg(this.din);

                // Schedule the job, accompanied by the id, type and server id
                sendMsg(this.dout, "SCHD " + job[2] + " " + best_server.serverType + " " + best_server.serverID);
                readMsg(this.din);
            }

            sendMsg(this.dout, "REDY");
            response = readMsg(this.din);
        }

    }

}
