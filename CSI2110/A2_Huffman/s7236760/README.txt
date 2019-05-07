Name: NamChi Nguyen
Student number: 7236760

Part 3
What is the running time of lines 2-13 of the Algorithm Huffman(X) in terms of big-Oh as a function of the number d of distinct letters? 
Answer this question for the following 3 implementations of priority queue Q. Briefly justify in each case.

Note: The -> means that the operation is within a loop so its run-time would change

A. Priority queue Q implemented using a Sorted List.

2. Initialize a priority queue Q				// O(1)
3. For each character c in X do {				// O(d)
4. Create a single-node binary tree T storing c			// O(1) -> O(d)
5. Insert T with key f(c) into Q 				// O(d) -> O(d^2)
}
6. While Q.size() > 1 do {					// O(d) 
7. e1= Q.removeMin()						// O(1) -> O(d)
8. e2= Q.removeMin()						// O(1) -> O(d)
9. Create new binary tree newT with left subtree e1.tree and right subtree e2.tree // O(1) -> O(d)
10. newFreq= e1.freq+e2.freq					// O(1)	-> O(d)						
11. Insert newT with key newFreq into Q				// O(d) -> O(d^2)
}
12. e= Q.removeMin()						// O(1)
13. return e.tree						// O(1)

Calculation: 3O(1) + 7O(d) + 2O(d^2) = O(d^2)

The running time using a sorted list is O(d^2).


B. Priority queue Q implemented using a Unsorted List. 
	
2. Initialize a priority queue Q				// O(1)
3. For each character c in X do {				// O(d)
4. Create a single-node binary tree T storing c			// O(1) -> O(d)
5. Insert T with key f(c) into Q 				// O(1) -> O(d)
}
6. While Q.size() > 1 do {					// O(d) 
7. e1= Q.removeMin()						// O(d) -> O(d^2)
8. e2= Q.removeMin()						// O(d) -> O(d^2)
9. Create new binary tree newT with left subtree e1.tree and right subtree e2.tree // O(1) -> O(d)
10. newFreq= e1.freq+e2.freq					// O(1) -> O(d)
11. Insert newT with key newFreq into Q				// O(1) -> O(d)
}
12. e= Q.removeMin()						// O(d)
13. return e.tree						// O(1)

Calculation: 2O(1) + 8O(d) + 2O(d^2) = O(d^2)

The running time using a unsorted list is O(d^2).


C. Priority queue Q implemented using a Heap. 

2. Initialize a priority queue Q				// O(d)
3. For each character c in X do {				// O(d)
4. Create a single-node binary tree T storing c			// O(1) -> O(d)
5. Insert T with key f(c) into Q 				// O(log(d)) -> O(dlog(d)) 
}
6. While Q.size() > 1 do {					// O(d)
7. e1= Q.removeMin()						// O(log(d)) -> O(dlog(d)) 
8. e2= Q.removeMin()						// O(log(d)) -> O(dlog(d)) 
9. Create new binary tree newT with left subtree e1.tree and right subtree e2.tree // O(1) -> O(d)
10. newFreq= e1.freq+e2.freq					// O(1) -> O(d)
11. Insert newT with key newFreq into Q				// O(log(d)) -> O(dlog(d)) 
}
12. e= Q.removeMin()						// O(log(d))
13. return e.tree						// O(1)

Calculation: O(1) + 6O(d) + 4O(dlog(d)) + O(log(d)) = O(dlog(d))

The running time using a heap is O(dlog(d)).