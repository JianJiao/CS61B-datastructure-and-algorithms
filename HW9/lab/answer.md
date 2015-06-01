* create a graph where every two neighbor cells are connected
* randomly choose the root, and do a depth-first-search
* when doing the depth-first-search, randomly choose the neighbor to continue
* whenever you encounter a node you have visited, cut the edge which led you to that node
* repeat the above steps until there is no cycle

Why don't you just insert items into the tree(randomly choose an item in the tree to insert to) to form a tree that is a maze?
Because only neighbor cells can be connected.