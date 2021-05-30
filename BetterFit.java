import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BetterFit {
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

    public BetterFit(Socket s, DataInputStream din, DataOutputStream dout) {
        this.s = s;
        this.din = din;
        this.dout = dout;
    }

    public void run() throws IOException {
        sendMsg(this.dout, "REDY");

        // Response from the server
        String response = readMsg(this.din);

        // Run the algorithm while the server still outputs jobs
        while (!response.contains("NONE")) {

            // Split the job into an array of strings for easy access
            String[] job = response.split("\\s");

            // if REDY is a job
            if (response.contains("JOBN")) {
                // GETS Capable servers based on submitted core, memory and disk of the job
                sendMsg(this.dout, "GETS Capable " + job[4] + " " + job[5] + " " + job[6]);
                readMsg(this.din);
                sendMsg(this.dout, "OK");

                // String of all servers capable of running job
                String serverstring = readMsg(this.din);

                // Split each server and insert each server into an array.
                String[] serverlist = serverstring.split("\\s");

                // Create a new arraylist which will hold our servers
                ArrayList<Server> servers = new ArrayList<Server>();

                // Populate our arraylist with each server object with metrics including:
                // serverType, serverID, state, curStartTime, core, mem, disk, waitJobs,
                // runningJobs
                for (int i = 0; i < serverlist.length; i += 9) {
                    if ((serverlist.length - i) >= 9) {
                        String type = serverlist[i];
                        int id = Integer.parseInt(serverlist[i + 1]);
                        String state = serverlist[i + 2];
                        int cst = Integer.parseInt(serverlist[i + 3]);
                        int core = Integer.parseInt(serverlist[i + 4]);
                        int mem = Integer.parseInt(serverlist[i + 5]);
                        int disk = Integer.parseInt(serverlist[i + 6]);
                        int waitJobs = Integer.parseInt(serverlist[i + 7]);
                        int runningJobs = Integer.parseInt(serverlist[i + 8]);
                        servers.add(new Server(type, id, state, cst, core, mem, disk, waitJobs, runningJobs));
                    }
                }

                // Test the closeness value
                int closenessCore = servers.get(0).core - Integer.parseInt(job[4]);

                // Choose the first server as the best server for the initial run
                Server best_server = servers.get(0);

                // Traverse through all capable servers
                for (Server server : servers) {
                    // Obtain new fitness value based on cores
                    int fitness_value_core = server.core - Integer.parseInt(job[4]);

                    // If the job's fitness value is less than 0, we select the first server, this
                    // is only a conditional, sometimes the testing will result in a value of less
                    // than one, say when a server that has 4 cores only but with no cores
                    // available, and the job requires 4 cores, we need to consider this situation
                    // and plan accordingly.
                    if (closenessCore < 0) {
                        closenessCore = fitness_value_core;
                        best_server = server;

                        // Find the job's closest server based on the fitness value. We must also make
                        // sure that there are no waiting jobs on the server queue.
                    } else if ((fitness_value_core < closenessCore) && server.waitJobs < best_server.waitJobs) {
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
