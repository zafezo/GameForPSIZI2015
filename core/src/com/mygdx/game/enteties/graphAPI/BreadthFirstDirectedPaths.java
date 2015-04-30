package com.mygdx.game.enteties.graphAPI;

import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path
    private Digraph G;
    private LinkedList<Integer> path ;
    /**
     * Computes the shortest path from <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */
    public BreadthFirstDirectedPaths(Digraph G) {
    	 this.G = G;
    	clearDate();       
    }
    private void clearDate(){
    	 marked = new boolean[G.V()];
         distTo = new int[G.V()];
         edgeTo = new int[G.V()];
         path = new LinkedList<Integer>();
         for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
    }

    public void setCurrentPoint(int s){
    	bfs(G, s);
    }
    
    // BFS from single source
    private void bfs(Digraph G, int s) {
    	clearDate();
        Queue<Integer> q = new Queue<Integer>();
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
        System.out.println("bfs: " + s);
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

  

    /**
     * Is there a directed path from the source <tt>s</tt> (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a directed path, <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source <tt>s</tt>
     * (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
    }
    
    public LinkedList<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
       path.clear();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x]){
            path.push(x);          
        }
        //System.out.println(x);   
        //path.push(x);
        return path;
    } 
  

  
}