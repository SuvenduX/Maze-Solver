
package GUI;

import GraphAlgorithm.DFS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class View extends JFrame implements ActionListener , MouseListener  //MouseListener is a interfeace , who has 5 methods which will
                                                                                                                            //helps to move mouse and click on any component
{
     /**
     * Maze values Interpretation
     * values 0 : not visited
     *              1:blocked wall
     *              2: visited blocks
     *              9: target block
     */
    private int[][] maze = 
        {   
                  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1,1 },
                  {1, 0, 1, 0, 1, 0, 1, 0, 0, 0,0,0,1 },
                  {1, 0, 1, 0, 0, 0, 1, 0, 1, 1,1,0,1 },
                  {1, 0, 0, 0, 1, 1, 1, 0, 0, 0,0,0,1 },
                  {1, 0, 1, 0, 0, 0, 0, 0, 1, 1,1,0,1 },
                  {1, 0, 1, 0, 1, 1, 1, 0, 1, 0,0,0,1 },
                  {1, 0, 1, 0, 1, 0, 0, 0, 1, 1,1,0,1 },
                  {1, 0, 1, 0, 1, 1, 1, 0, 1, 0,1,0,1 },
                  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,1,9,1 },
                  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    
    private int[] target ={8,11};  //creating an array to store the target coordinate
    private List<Integer> path =new ArrayList<Integer>(); //creating a list 'path' to store the path coordinates getting from DFS algorithm
    
    JButton submitButton;  //declaring the buttons which will be there in JFrame
    JButton clearButton;  
    JButton cancelButton;
    
    public View()       //View Constructor
    {
        this.setTitle("Maze Solver");  
        this.setSize(500, 520);   //size of the JFrame
        this.setLayout(null); //There are diff layout option in Java , but null bcz we r taking absolute layout for putting button,textarea,table anywhre
        this.setLocationRelativeTo(null); //The GUI will appear at middle of the screen,if we dont specify this then it will be left upper corner
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // GUI to close after clicking on cross sign at right upper corner
        
         submitButton =new JButton("submit");  // initializing objects of JButton class to create the button & naming the submit
         submitButton.addActionListener(this); // now the button will have some functionality so addActionListener will call the actionPerformed method and
                                                                              //'this' parameter will execute whatever code is written inside actionPerformed method
         submitButton.setBounds(100, 400, 80, 30);  // deciding the position & size of the button 
         
          clearButton =new JButton("clear");  
          clearButton.addActionListener(this);
          clearButton.setBounds(220, 400, 80, 30);
          
          cancelButton =new JButton("cancel");  
          cancelButton.addActionListener(this); 
          cancelButton.setBounds(340, 400, 80, 30);
          
          this.add(submitButton); // Finally adding the buttons in JFrame 
          this.add(clearButton);
          this.add(cancelButton);
          this.addMouseListener(this);
          
    }
    
    @Override  //As the paint function is already declared in JFrame /AL class,now it's overiding the function which is initialized here 
    public void paint (Graphics g) // This paint function belongs to JFrame class& It will add the mess graphics in JFrame.As 'View' is inheriting 
    {                                                    //JFrame so paint will be called automatically when JFrame starts execution 
    
           super.paint(g); //paint present inside Jframe & super refers to parent class(JFrame)which is inherited by child class(View).
                                    //calling paint fuction & initializing it
             for(int row=0;row<maze.length;row++){   //Traversing like a 2D array
               for(int col=0;col<maze[0].length;col++){
                       Color color;
                       switch (maze[row][col]){
                       case 1 : color=Color.black;break;  //blocked wall color
                       case 9 : color=Color.red;break;    //target color
                       default : color=Color.white;  //setting path color
                    }
                   g.setColor(color);       //setting color depending upon the matrix values 0,1,9 . 
                   g.fillRect(40*col, 40*row, 40, 40); // Defining the the size of the box by traversing the matrix[40*col->going X axis wise col=1,2,3.. & 40*row-going y axis wise]
                   g.setColor(Color.black);  //Defining box BORDER color  
                   g.drawRect(40*col, 40*row, 40, 40);  //creating box border with defined color in every 40*40  pixels & 
                                                                                                 //As we are traversing the matrix ,40*row &40*col is giving us the every box coordinates        
               }
            } 
           //Initially paint func was also called when JFrame was initialized but at that below code was not executed bcz path was empty at that moment
           //later when DFS.searchPath called from submit button , then the 'path' list got the address of path & now calling 'repaint' & this time below code will get executed
           //path is already received through DFS ,now Painting the path to target after submit button pressed 
           for(int p=0;p<path.size();p+=2){  //+=2 bcz we are taking coordinates in a pair[x,y], assume we at 0,0 | next coordinate 0,1 so we need loop from 2 next time
               int pathX=path.get(p); //
               int pathY=path.get(p+1);
               g.setColor(Color.green);  //setting color for path to target 
               g.fillRect(40*pathY, 40*pathX, 40, 40);
           }
    }
    
    @Override  //writing override is not a must ,But it's good to write. It justs let user know that we are Overriding the abstract method actionPerfomed which is present
                        // inside the parent  ActionListener interface.
    public void actionPerformed (ActionEvent e)
    {    
        if(e.getSource()==cancelButton){  //e.getSource captures which button is pressed by the user
            //JOptionpane class-creating a dialog box , showconfirmdialog method -asks a confirmation qustion & provides option to choose (optiontype:YES_NO_OPTION)
            int response=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit", "submit",  JOptionPane.YES_NO_OPTION); //"submit" is the heading of the dialog box
            if(response==0){        //  if we press YES then function returns =0 || pressing  NO returns 1 || CANCEL =2 
                System.exit(0);
                }
            }
        
        //'clear button' clears the path that is already visited (take back to the original color) 
        if(e.getSource()==clearButton){
            for(int row=0;row<maze.length;row++){
                for(int col=0;col<maze[0].length;col++){  //if the path is visited, all the 0 will be changed to 2(using DFS), now we need to restore the 0 & original color
                    if(maze[row][col]==2){   // Replacing 2's with 0's
                        maze[row][col]=0;
                    }
                }
            }
            path.clear();       // removing all the color & coordinates from the 'path' list
            this.repaint();     //Again repainting the path/blocks to original colors  
                                        //Now when we are calling repaint func,line 110-114 will not be executed bcz path.clear made the 'path' list empty
          } 
        
         //submit button gives us the path to target
        if(e.getSource()==submitButton){
            try{
                boolean result = DFS.searchPath(maze , 1,1,path); //this will return us true if path found , [1,1] bcz 0 starts here
                System.out.print(result);
                this.repaint();
                }
            catch(Exception excp){ //if any error is there in DFS algo , we are handling this using catch else entire GUI would be collapse
                JOptionPane.showMessageDialog(null, excp.toString()); //excp.toString will give exact error or we can write the error warning also
                }
            }
    }    
    
    /*Need to implement all methods of MouseListener interface but only "mouseClicked" method will help to move target anywhere by clicking on it */
    @Override
    public void mouseClicked(MouseEvent e)
    {  /*
           Once mouse is clicked on a component within JFrame , Event e to be implemented
           Range of horizontal  ROW(X axis) wise left coordinate: 0 &  right coordinate : last column/no of column(maze[0].length) * 40 bcz every box size is 40 
           Range of verticle COLUMN wise(Y axis) top coordinate : 0 & bottom cordinate : last row/no of rows(maze.length)*40
           getX(horizontal line) gives  coulmn wise cooridnate & getY(verticle line) gives  row wise coorinate
        */
      if((e.getX()>=0 && e.getX()<=maze[0].length*40)  &&  (e.getY()>=0   && e.getY()<=maze.length*40))    //Mouse click will only work if we click inside the frame area
         {
            int x=e.getY()/40;  //getY gives rowth(Y) coordinate where we clicked & Y/40 gives row no ,
            int y=e.getX()/40;  //getX gives columnth(X) coordinate & get(X)/40 gives us the column no  
           //(x,y) gives the coordinate of box location (like row:2 , column:3) from this we come to know in which box mouse is clicked
           
           if(maze[x][y]==1){ //If the mouse is clicked on blocked wall (1) then simply return 
               return ;
             }
           //If clicked on the movable area then implement below code
           Graphics g=getGraphics();  //Initializing the graphics
           g.setColor(Color.white);    //setting the red color of old target block to white  
           g.fillRect(40*target[1], 40*target[0], 40, 40); //filling the newly set color(white) to the old target block 
           g.setColor(Color.red);      //setting the color for newly selected target block to red
           g.fillRect(40*y, 40*x, 40, 40);    //fill the new target block to red
           maze[target[0]] [target[1]]=0;    //As we changed the target,so change the old target value i.e 9 to 0 (O bcz it means visitable blocks)
           maze[x][y]=9;        //set the value of new target block to 9 as 9 is programmed to be the target
           target[0]=x;     //Now updating the coordinate of new target block in target array (refer line 159&160)
           target[1]=y;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){  //Once mouse is kept pressed on something
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e){ //Once is mouse is released after pressing 
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e){  //Once mouse enters inside a component/area like JFrame
        
    }
    
    @Override
    public void mouseExited(MouseEvent e){  //Once mouse exit the area 
        
    }
    
    
    public static void main(String[]args)
    {
        View view =new View();
        view.setVisible(true); // making the GUI visible
     }
    
}

