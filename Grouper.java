import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Grouper {

    // We want to read three incoming jobs.
    // We need to skip jobs and save to arraylist.
    // PSHJ - requests to get the next job, skipping the current job without
    // scheduling it

    // We need some global variables
    int Group1EstTime;
    int Group2EstTime;
    Boolean group;
    ArrayList<String> Group1 = new ArrayList<String>();
    ArrayList<String> Group2 = new ArrayList<String>();

    // Set the average estimated time of both groups
    // We intake analyse each job
    public void SetGroupEstimatedTimes() {
        for (int i = 0; i < 2; i++) {
            String jobsplit = Group1.get(i).split("\\s+")[3];
            Group1EstTime += Integer.parseInt(jobsplit);
        }
        for (int i = 0; i < 2; i++) {
            String jobsplit = Group2.get(i).split("\\s+")[3];
            Group2EstTime += Integer.parseInt(jobsplit);
        }
        Group1EstTime = Group1EstTime / 3;
        Group2EstTime = Group2EstTime / 3;
    }

    // If group 1 is slower than group 2, use group 1, else use group 2
    public void fastestGroup() {
        if (Group1EstTime > Group2EstTime) {
            group = false;
        } else {
            group = true;
        }
    }

    public static void main(String args[]) throws Exception {
        int count = 0;
        String response = readMsg(din);

        while (!response.contains("NONE")) {
            if (response.contains("JOBN")) {
                for (int i = 0; i < 2; i++) {
                    Group
                }
                sendMsg(dout, "SCHD " + response.split("\\s+")[2] + " " + XML_LargestServer() + " " + count);
                response = readMsg(din);

                // Check for errors, if error found, send to the next server
                if (response.contains("ERR")) {
                    count++;
                    // Reset count to send to first server, otherwise will reach out of bounds and
                    // try to send to servers that dont exist
                    if (count == Integer.parseInt(XMLLimit())) {
                        count = 0;
                    }
                    sendMsg(dout, "SCHD " + response.split("\\s+")[2] + XML_LargestServer() + " " + count);
                    response = readMsg(din);
                }

            }
            // Ready for next job
            sendMsg(dout, "REDY");
            response = readMsg(din);
        }
    }

    // function to place jobs into arraylists
    // REDY
    // READline
    // din.append

    // function to sort the arraylists
    // target the estimated run time.

}