/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GraphAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Avita
 */

public class DFS {
    public static boolean searchPath(int[][] maze , int x, int y , List<Integer>path){
        //maze matrix , starting coordinate , List to store the coordinates of all blocks in the path of target
        
        if(maze[x][y]==9){  //when target is found , add the coordinates to the list and return true 
            path.add(x);
            path.add(y);
            return true;
        }
        
        if(maze[x][y]==0){  // If@0 means we can go forward to serach the target 9
            maze[x][y]=2;    //Once reach any box , mark it visited by replacing it with 2
            
            int [] dx={1,0,-1,0};  // creating an array for providing moving direction ROW wise //Assume we@(0,0) , [1,0] means traverse down & [-1,0] traverse up 
            int [] dy={0,1,0,-1};  //creating an array for providing moving direction COLUMN wise //[0,1] means traverse right & [0,-1] traverse left 
            
            for(int d=0;d<dx.length;d++){ //traversing possible path in search of target
                int newX=x+dx[d];   // getting new index row wise , x+dx[i] will give next index row wise
                int newY=y+dy[d];   //getting new index column wise , y+dy[i] will give next next index column wise
                if(newX>=0 && newX<maze.length && newY>=0 && newY<maze[0].length && searchPath(maze, newX, newY, path)){
                    //Recursive function searchPath to find the target  
                    // at extreme corners,traversing further gives indexOutOfBounds as we try to access the index out of the matrix 
                    path.add(x);
                    path.add(y);
                    return true;
                }   
            }
         }
        return false;   //If target not found return false
    }
    
    public static void main(String [ ] args){
        
        DFS obj =new DFS();
        int [ ] [ ] maze={
            {0,0,1},
            {0,1,9},
            {0,0,0}
        };
        List<Integer>path=new ArrayList<Integer>();
        boolean reach=obj.searchPath(maze , 0, 0,path);
        for(int i=0;i<path.size();i++){
            System.out.print(path.get(i)+" ");
        }
        }
    
    }
    

