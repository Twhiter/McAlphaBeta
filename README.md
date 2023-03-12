Use this file to give us some information on your agent and your team:

Agent
=====

Idea
----

We persused the following idea:

our agent uses the [MCαβ](https://www.chessprogramming.org/MC%CE%B1%CE%B2), which is based on Monte carlo search \
tree and alpha beta. Whenever a leaf node is traversed in Monte carlo tree, it does a shallow alpha beta first depth \
search and considers the minmax value as the evaluation.


Implementation
--------------

Our agent was written as follows:

The monte carlo tree search in this agent can be described as following procedure:
1. Select phase, it is based on UCT strategy, unvisited node is considered prior.
2. Expand phase, when the current node is a leaf node, it adds all its children node, in this agent, there is expansion maximum limit.
3. Simulate(playout) phrase, the leaf node does a shallow alpha beta search, treats the minmax value as the evaluation.
4. Extent phase, when finishing playout, it fetches two promising (first and second-highest value for south, first and second lowest for north) successors from alpha beta, and add these two nodes to tree. Note that there is also maximum limit for extension.
5. Backpropgration phase, add the evaluation value to the path traversed and also the visit count increases by one.

The alpha beta search uses the store difference( south - north) as the evaluation function.


When iteration runs over, it chooses the successor with the highest average value.

Based on intuition that Monte carlo plays better than alpha beta in initial stage of the game and vice versa in the final stage.\
The sampling times, maximum depth for expansion, maximum depth for extension, maximum for alpha beta search varies as the game proceeds.
In early stage, more sampling and shallow search is adapted. In the final stage, the agent more relies on alpha beta search.

The program entry is the main method in MonteAgent.java

CAVEATS
-------

Note the that there might be complications if: \
Large game board is applied(time runs out, sorry not tested for that) \
The agent is tested on single core on i5-8300, not sure that the one in tournament is superior to i5-8300.


Team
====

List all students that participate in our group are:

- Xinyuan Tu (ky10pida)
-  my56nyra
- xo32gagy

Checklist
=========

Consider taking the time to appropriately check these points:

- [ ] Our agent runs without raising exceptions.
- [ ] Our agent manages to reliably beat a random agent.
- [ ] Our agent connects to localhost:2671 by default.
- [ ] Your program does not depend on non-free software.
- [ ] Any additional dependencies have been specified in the
  Dockerfile and we confirm that it builds.
- [ ] Max Musterman, Lieschen Müller and Hein Janmaat are not part
  of our team.
- [ ] We will not check this box.
- [ ] We have read all of the above.