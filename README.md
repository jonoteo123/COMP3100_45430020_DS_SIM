# <strong>COMP3100 45430020 Distributed System Scheduler</strong>

## Scheduling Algorithms
Scheduling algorithms are essential for distributed systems. They allow us to allocate resources efficiently and prevent waste of resources. There are many different types of scheduling algorithms. Three well-knows algorithms include Best-Fit, Worst-Fit, and First-Fit. Each with their own distinct advantages and dissadvantages. These three algorithms have been explored throughout the semester of COMP3100, however these are not the only approaches to the scheduling of tasks.


- ### Best-Fit
    The Best-Fit algorithm sees the scheduler take in to account the incoming job's required core count. Depending on the number of cores that the job requires, the scheduler will allocate the job to the server which has the closest core count which can facilitate the job.

    #### Advantages
    - 
    #### Disadvantages
    - 


- ### Worst-Fit
    The Worst-Fit algorithm sees the scheduler allocate the incoming job to the largest server that can facilitate the job. 

    #### Advantages
    - 
    #### Disadvantages
    - 


- ### First-Fit
    The First-Fit algorithm sees the scheduler analyse the list of servers and allocate the job to the first available server that is large enough to execute the job.

    #### Advantages
    - 
    #### Disadvantages
    - 


## Proposed Algorithm
Task 2 sees an opportunity to experiment and test the boundaries of scheduling algorithms. The chosen approach sees the consideration of 