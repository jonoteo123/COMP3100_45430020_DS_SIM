# <strong>COMP3100 45430020 Distributed System Scheduler</strong>

## Scheduling Algorithms

Scheduling algorithms are essential for distributed systems. They allow us to allocate resources efficiently and prevent waste of resources. There are many different types of scheduling algorithms. Three well-knows algorithms include Best-Fit, Worst-Fit, and First-Fit. Each with their own distinct advantages and dissadvantages. These three algorithms have been explored throughout the semester of COMP3100, however these are not the only approaches to the scheduling of tasks.

- ### Best-Fit
  The Best-Fit algorithm sees the scheduler take in to account the incoming job's required core count. Depending on the number of cores that the job requires, the scheduler will allocate the job to the server which has the closest core count which can facilitate the job.

- ### Worst-Fit
  The Worst-Fit algorithm sees the scheduler allocate the incoming job to the largest server that can facilitate the job.

- ### First-Fit
  The First-Fit algorithm sees the scheduler analyse the list of servers and allocate the job to the first available server that is large enough to execute the job.

## Proposed Algorithm

Task 2 sees an opportunity to experiment and test the boundaries of scheduling algorithms. The chosen approach sees the consideration of min-maxing turnaround time and resource utilisation.

Rental Cost does not matter in my scenario. I believe that if a company's business model turns a net profit, then it doesnt need to consider the cost of running the server.

Consider, using first fit as a baseline model, we see that it will quickly schedule jobs to the first available server. However, this is not resource efficient. If a future incoming job requires 4 cores, however the only server which has 4 cores is currently executing a job which only required 2 cores, and in the mean time, a job that was running on a server with 2 cores has finished halfway through the 2 core job on the 4 core server. It may be better to schedule the 2 core job onto the 2 core server and have it wait until the first 2 core job has completed, while it left the 4 core server open for the incoming 4 core job.

In this way, instead of instantly scheduling the incoming job, we skip three incoming jobs, and with those three jobs, we sort them in an array via the estimated run time. We then skip another three incoming jobs and sort them in the same way. We now have two groups of jobs, sorted from lowest to highest estimated run time. We then average out the estimated run time of both groups and begin to schedule the group which has the smallest estimated run time.

From this, we gather a large enough sample size to analyse the jobs and schedule appropriately.
