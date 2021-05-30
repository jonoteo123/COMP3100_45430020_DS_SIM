public class Server {
    String serverType;
    int serverID;
    String state;
    int curStartTime;
    int core;
    int mem;
    int disk;
    int waitJobs;
    int runningJobs;

    // Constructors
    public Server(String serverType, int serverID, String state, int curStartTime, int core, int mem, int disk, int waitJobs, int runningJobs){
        this.serverType = serverType;
        this.serverID = serverID;
        this.state = state;
        this.curStartTime = curStartTime;
        this.core = core;
        this.mem = mem;
        this.disk = disk;
        this.waitJobs = waitJobs;
        this.runningJobs = runningJobs;
    }

    public Server(){};

}