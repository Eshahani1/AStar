package com.astar;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

//By: Brandon Beckwith
class AStar {

    // NOTE: This is the only class you need to edit.
    //Feel free to add whatever methods you need here!

    /**
     * Runs A star on the given board
     * @param board The board to run A Star on
     * @param start The starting Point
     * @param end The ending Point
     * @return The spaces in order from the start Point to the end Point
     */

    // Make a method to calculate manhattan distance

    public static int calculateMDistance(Space a, Space b) {
        int Aabs = Math.abs(a.getX() - b.getX());
        int Babs = Math.abs(a.getY() - b.getY());
        int distance = Aabs + Babs;
        return distance;
    }

    //Make a method to reconstruct the path

    public static void reconstructpath(ArrayList<Space> path, Space endSpace){
        Space current = endSpace;
        while (current != null){
            path.add(0, current);
            current = current.getPrevious();
        }
    }

    public static ArrayList<Space> findPath(Board board, Point start, Point end) {

        //Setup an ArrayList to hold the path to  return to the GUI
        ArrayList<Space> path = new ArrayList<Space>();

        //Setup an Array list for open and closed sets

        PriorityQueue<Space> openSet = new PriorityQueue<>();
        HashSet<Space> closedSet = new HashSet<>();

        // TODO: Implement AStar

        //Make variable to hold starting and ending spaces

        Space startingSpace = board.getSpace(start.x,start.y);
        Space endingSpace = board.getSpace(end.x,end.y);

        //Set the start Space

        startingSpace.setDistFrom(0);
        startingSpace.setDistTo(calculateMDistance(startingSpace, endingSpace));
        openSet.add(startingSpace);

        //The actual algorithim now

        while(!openSet.isEmpty()){
            
            //find space with lowest score which will be first one in
            Space current = openSet.poll();

            //if the current is end break
            if(current == endingSpace){
                reconstructpath(path, endingSpace);
                break;
            }

            //Add current space to the closed set
            closedSet.add(current);

            //Now we need to star checking neigbors of current

            for(Space neighbors: board.getNeighbors(current)){
                //don't get neighbors that are in the current set or blocked off
                if(closedSet.contains(neighbors) || neighbors.getType() == SpaceType.BLOCK){
                    continue;
                }

                //Calculate G Score

                int gScore = current.getDistFrom() + 1;

                //add neigbors to open set

                if(!openSet.contains(neighbors)){
                    openSet.offer(neighbors);
                }else if(gScore >= neighbors.getDistFrom()){
                    continue;
                }

                //best path gets recorded 
                neighbors.setPrevious(current);
                neighbors.setDistFrom(gScore);
                neighbors.setDistFrom(calculateMDistance(neighbors, endingSpace));
            }
        }
        return path;
    }
}
