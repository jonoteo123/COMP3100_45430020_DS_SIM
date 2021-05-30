public class Server {
    String serverType;
    int serverID;
    String state;
    int curStartTime;
    int core;
    int mem;
    int disk;
    int wJobs;
    int rJobs;

    // Constructors
    public Server(String serverType, int serverID, String state, int curStartTime, int core, int mem, int disk, int wJobs, int rJobs){
        this.serverType = serverType;
        this.serverID = serverID;
        this.state = state;
        this.curStartTime = curStartTime;
        this.core = core;
        this.mem = mem;
        this.disk = disk;
        this.wJobs = wJobs;
        this.rJobs = rJobs;
    }

    public Server(){};

}