

import javax.swing.*;

public class snakegame extends JFrame
{
    snakegame()
    {
        super("Snake Game"); // sets the title of frame
        add(new Board()); // here we are importing board class 
         pack(); // used to refresh frame page so that if any changes are made then it can reflect in runtime

        setSize(300,300); // sets the size of frame
        setLocationRelativeTo(null); // aligns the frame in centre position
        setResizable(false);
        setVisible(true); // sets the visibility of frame 
    }

    public static void main(String[] args)
     {
       snakegame sg=new snakegame();
    }
}