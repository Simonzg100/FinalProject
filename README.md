# Warehouse Order Management and Distribution System
Collabration: 
Zechuan(Zac) YANG         zacyang@seas.upenn.edu; 
Wei(Simon) ZHANG          wei14@seas.upenn.edu;
Meiyuan(Claire) SHEN      myshen@seas.upenn.edu

Project Idea:
Our group aims to develop a warehouse order management system with a simple graphical user interface (GUI). The system will integrate warehouse storage management, product search, and shortest path finding for efficient order processing and delivery. When a user places an order, the system will locate the product in the warehouse, determine the shortest path for a vehicle to reach the product, and then deliver it to the user's location.
Reason for Choosing This Idea:
We chose this idea because it presents an opportunity to apply various data structures and algorithms learned throughout the course. Additionally, it addresses a real-world problem in warehouse management and order processing, which can help improve the efficiency of logistics operations.
Intended Features:
Product search: 
Implement a trie data structure to efficiently search for product names and their corresponding storage locations.
Warehouse storage management: 
Utilize Java Collections & Maps (e.g., ArrayLists, HashMaps) to manage and track product inventory within the warehouse.
Shortest path finder: 
Apply graph theory and related algorithms (e.g., Dijkstra's or A* algorithm) to find the shortest path for a vehicle to reach the product storage location.
GUI: 
Develop a user-friendly interface for placing orders, visualizing warehouse layout, and monitoring vehicle movement.
Performance optimization: 
Experiment with different hashing strategies for HashMaps or incorporate sorted structures (TreeMaps/Sets) to enhance system performance.

Meaningful and Rigorous Use of Course Material:
Graphs: 
Implement graph-based algorithms for the shortest path finder, which is directly related to the course's Graphs unit.
Indexing: 
Use trie data structures for efficient product search, which is inspired by the course's indexing unit.
Java Collections & Maps: 
Apply various Java Collections & Maps (e.g., ArrayLists, HashMaps, TreeMaps/Sets) throughout the project, demonstrating a well-rounded understanding of data structures.

Outstanding Questions:
Are there any additional data structures or algorithms from the course that could further enhance the efficiency or functionality of our system?
Given the scope of the project, what milestones and deadlines should we set to ensure timely completion?
Do you have any suggestions for the GUI design or other aspects of the project that could improve user experience?

Java Class Structure:
Product: WarehouseNum, StockQuantity
Order: Product, Address(Zip Code)
City: Address(Zip Code) 
Warehouse: Product, Address(Zip Code)
File I/O

Presentation Procedure:
Product search and Placing Order
For Searching, type in the first few letters of the name of Product and get recommendations <Tries>
Product associated with one/several IDs linked to Warehouse location <Hashmap>
Placing Order will require an input of zip code for delivery <Shortest Path in Graph of Cities, BFS in tree>
Distribution:
Read a bunch of Orders from file <Hashmap, I/O>
Read start and end location <HashMap with List of tuple>
A path for distribution from each Warehouse. (One route per warehouse method) <Minimum Spanning Tree/ Shortest Path >
Locating a Distribution Chain
Find the minimum spanning tree for the graph
Make some of the warehouse  on diverging nodes as dispatch center (interchange cargos from several routes)
