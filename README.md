# Warehouse Order Management and Distribution System
## Collabration: 
Zechuan(Zac) YANG         zacyang@seas.upenn.edu;   
Wei(Simon) ZHANG          wei14@seas.upenn.edu;  
Meiyuan(Claire) SHEN      myshen@seas.upenn.edu;   

## Proposal

### Project Idea:  
Our group aims to develop a warehouse order management system with a simple graphical user interface (GUI). The system will integrate warehouse storage management, product search, and shortest path finding for efficient order processing and delivery. When a user places an order, the system will locate the product in the warehouse, determine the shortest path for a vehicle to reach the product, and then deliver it to the user's location.  
  
### Reason for Choosing This Idea:  
We chose this idea because it presents an opportunity to apply various data structures and algorithms learned throughout the course. Additionally, it addresses a real-world problem in warehouse management and order processing, which can help improve the efficiency of logistics operations.  
  
### Features:  
#### Product search:   
Implement a trie data structure to efficiently search for product names and their corresponding storage locations.  
#### Warehouse storage management:   
Utilize Java Collections & Maps (e.g., ArrayLists, HashMaps) to manage and track product inventory within the warehouse.  
#### Shortest path finder:   
Apply graph theory and related algorithms (e.g., Dijkstra's or A* algorithm) to find the shortest path for a vehicle to reach the product storage location.  
#### GUI:   
Develop a user-friendly interface for placing orders, visualizing warehouse layout, and monitoring vehicle movement.  
Performance optimization:   
Experiment with different hashing strategies for HashMaps or incorporate sorted structures (TreeMaps/Sets) to enhance system performance.  
  
### Meaningful and Rigorous Use of Course Material:  
#### Graphs:   
Implement graph-based algorithms for the shortest path finder, which is directly related to the course's Graphs unit.  
#### Indexing:   
Use trie data structures for efficient product search, which is inspired by the course's indexing unit.  
#### Java Collections & Maps:   
Apply various Java Collections & Maps (e.g., ArrayLists, HashMaps, TreeMaps/Sets) throughout the project, demonstrating a well-rounded understanding of data structures.  
  
### Outstanding Questions:  
Are there any additional data structures or algorithms from the course that could further enhance the efficiency or functionality of our system?  
Given the scope of the project, what milestones and deadlines should we set to ensure timely completion?  
Do you have any suggestions for the GUI design or other aspects of the project that could improve user experience?  
  
### Java Class Structure:  
Product: WarehouseNum, StockQuantity  
Order: Product, Address(Zip Code)   
City: Address(Zip Code)    
Warehouse: Product, Address(Zip Code)  

File I/O  
  
### Presentation Procedure:  
#### Product search and Placing Order.  
For Searching, type in the first few letters of the name of Product and get recommendations <Tries>.  
Product associated with one/several IDs linked to Warehouse location <Hashmap>.  
Placing Order will require an input of zip code for delivery <Shortest Path in Graph of Cities, BFS in tree>.    
#### Distribution:   
Read a bunch of Orders from file <Hashmap, I/O>.  
Read start and end location <HashMap with List of tuple>.  
A path for distribution from each Warehouse. (One route per warehouse method) <Minimum Spanning Tree/ Shortest Path >.  
#### Locating a Distribution Chain.  
Find the minimum spanning tree for the graph.  
Make some of the warehouse  on diverging nodes as dispatch center (interchange cargos from several routes).  
  
    
## Outcome Presentation:
  
### Data Parser  
  <img width="1200" alt="Screenshot 2023-05-08 at 11 42 23 PM" src="https://user-images.githubusercontent.com/117859291/236988350-94799e61-e893-44d7-a4fa-05023e166d69.png">  
 The picture shows all the city we chosen as our database to follow through.        
 The dataprocessor is a sophisticated tool that has been developed to parse through a massive dataset of over 250,000 book records from Amazon and various large cities in America.  
    
### Product Search 
  
Search panel as followed: 
 <img width="600" alt="Screenshot 2023-05-09 at 12 54 56 AM" src="https://user-images.githubusercontent.com/45669402/237033226-3622e41a-fcda-4a93-8192-05a232321a80.png">
  
<img width="600" alt="Screenshot 2023-05-09 at 12 55 39 AM" src="https://user-images.githubusercontent.com/45669402/237033259-a998753f-6772-4bba-8940-863e52f79ac2.png">

The program is a search engine that allows users to look up books based on their title, author. Users can input any combination of these search criteria, and the program will return a list of matching books along with their corresponding information.  
For each book in the search results, the program will display its title, author name, and category and link for the book.   
The program will be able to search through a large database of books, ensuring that users have access to a wide range of options. It will also have a user-friendly interface that makes it easy for users to enter their search criteria and navigate through the search results.  


  
### Random Generate Orders
One such scenario is the ability to randomly generate online book orders and simulate the delivery process in real life.  
By generating a large number of these orders, the dataprocessor can provide valuable insights into how delivery paths are determined and what factors play a role in the decision-making process.     
  
### Path Programming
  <img width="600" alt="Screenshot 2023-05-09 at 12 05 59 AM" src="https://user-images.githubusercontent.com/117859291/236991311-fa556e26-d605-4e69-9cfe-ccf637038758.png"> <img width="600" alt="Screenshot 2023-05-09 at 12 08 51 AM" src="https://user-images.githubusercontent.com/117859291/236991689-d2bc4965-846d-4ff6-aca0-eb2555f7570e.png"> <img width="600" alt="Screenshot 2023-05-09 at 12 07 54 AM" src="https://user-images.githubusercontent.com/117859291/236991562-3c374bbb-22db-43ba-9daf-9009889ed2e1.png"> <img width="600" alt="Screenshot 2023-05-09 at 12 13 09 AM" src="https://user-images.githubusercontent.com/117859291/236992232-4a5663fc-d532-433a-9a32-eee4f295fb7a.png">.   
  he Method class is a critical component of the book delivery system, as it uses several advanced pathfinding algorithms to determine the most efficient distribution routes for book orders. By comparing different pathfinding methods, the Method class is able to generate insights into the best ways to optimize delivery routes and minimize delivery times.  
Using a variety of pathfinding algorithms, such as Dijkstra's algorithm or the A* algorithm, the Method class can calculate the most efficient path between delivery points. It considers a range of factors such as traffic patterns, distance, and delivery driver availability to determine the best possible route for each order.  

### Google Map API
  For display
  
### GUI 
  For display
