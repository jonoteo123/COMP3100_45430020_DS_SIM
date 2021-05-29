import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    // We need a setter
    //
    public static List<String> defineJob(String response) {
        List<String> job = new ArrayList<String>(Arrays.asList(response.split("\\s+")));
        return job;
    }

    public static void main(String args[]) throws Exception {
        int count = 0;
        String response = readMsg(din);
        List<String> job = new ArrayList<String>();
        int previd = 0;
        int currentid = 0;
        int prevjobtime = 0;
        int currentjobtime = 0;

        while (!response.contains("NONE")) {
            job = defineJob(response);                
            currentid = Integer.parseInt(job.get(2));
            currentjobtime = Integer.parseInt(job.get(3));
            if (previd == currentid) {
                sendMsg(dout, "PSHJ");
                response = readMsg(din);
            } else {
                if (currentjobtime > prevjobtime) {
                    sendMsg(dout, "SCHD " + response.split("\\s+")[2] + " " + XML_LargestServer() + " " + count);
                }

            }
            prevjobtime = currentjobtime;
            previd = currentid;
            sendMsg(dout, "REDY");
            response = readMsg(din);
            // if (response.contains("JOBN") || response.contains("JOBP")) {


            //     // sendMsg(dout, "SCHD " + response.split("\\s+")[2] + " " + XML_LargestServer() + " " + count);
            //     // response = readMsg(din);

            //     // Check for errors, if error found, send to the next server
            //     // if (response.contains("ERR")) {
            //     //     count++;
            //     //     // Reset count to send to first server, otherwise will reach out of bounds and
            //     //     // try to send to servers that dont exist
            //     //     if (count == Integer.parseInt(XMLLimit())) {
            //     //         count = 0;
            //     //     }
            //     //     sendMsg(dout, "SCHD " + response.split("\\s+")[2] + XML_LargestServer() + " " + count);
            //     //     response = readMsg(din);
            //     // }
            // }
            // Ready for next job


        }
    }

    // function to place jobs into arraylists
    // REDY
    // READline
    // din.append

    // function to sort the arraylists
    // target the estimated run time.

}