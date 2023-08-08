// in this class we will perform all the operations inside the frame i.e from colouring the background to final stage of console

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;


import java.awt.*;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener  // JPanel is just like div in css
{
    private int dots;
    private Image apple;  // Image is a class of AWT package
    private Image dot;
    private Image head;
    private final int All_Dots=900;
    private final int Dot_Size=10;
    private final int x[]=new int[All_Dots];
    private final int y[]=new int[All_Dots];

    private final int RANDOM_POSITION=29;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection= false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;

    private boolean inGame=true;

    private Timer timer;


    Board()
    {
      addKeyListener(new TAdapter());

      setBackground(Color.BLACK);
      setPreferredSize(new Dimension(300,300));
      setFocusable(true);

      loadImages(); 
      startGame();
    }
    
    public void loadImages() // method to load images from system
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/apple.png")); // used to pick images from system resources
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head=i3.getImage();
    }

    public void startGame() // method to start the game
    {
      dots=3;

      for(int i=0;i<dots;i++)
      {
         y[i]=50; // pos of dot from y axis 
         x[i]=50 - i*Dot_Size; // position of dot from x axis
      }
      locateAppleOnConsole();

       timer=new Timer(140, this); // Timer
      timer.start();
    }

    public void locateAppleOnConsole() // this method will will random apples on console
    {
       int r=(int)(Math.random() * RANDOM_POSITION); // Math.random() will guve in any random float value
       apple_x=r*Dot_Size;
       
       r=(int)(Math.random() * RANDOM_POSITION);
       apple_y=r*Dot_Size;
    
    }

    // to show these above images on the frame we have to paint on it for this purpose we will use this method
    // paintComponent

    public void paintComponent(Graphics g)  // method of graphic class, here g is a object of graphic class and
                                           //  it must be passed
    {
      super.paintComponent(g);
      draw(g);
    }

    public void draw(Graphics g) // this method is drwaing image on the frame
    {
      if(inGame)
      {
          g.drawImage(apple, apple_x, apple_y,this); // this will draw random apples on screen
        for(int i=0;i<dots;i++)
        {
            if(i==0)
            {
                g.drawImage(head, x[i], y[i], this);
            }
            else
            {
                 g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync(); // by this game will be initialised from default
      }

      else
      {
        gameOver(g);
      }
       
    }

    public void gameOver(Graphics g)
    {
      String msg="GAME OVER!!";
      Font font=new Font("SAN_SARIF", Font.BOLD, 14);
      FontMetrics metrices=getFontMetrics(font);

      g.setColor(Color.WHITE);
      g.setFont(font);
      g.drawString(msg,(300-metrices.stringWidth(msg)) /2,300/2);
    }
     
    public void move()
    {
      for(int i=dots;i>0;i--)
      {
        x[i]=x[i-1]; // head ke peeche wala part move krega
        y[i]=y[i-1];
      }

      if(leftDirection) x[0]=x[0]-Dot_Size;
      if(rightDirection) x[0]=x[0]+Dot_Size;
      if(upDirection) y[0]=y[0]-Dot_Size;
      if(downDirection) y[0]=y[0]+Dot_Size;

      //x[0]+= Dot_Size;  // head move krega yha pr
      //y[0]+=Dot_Size; 
    }

    public void checkApple() // this method will 
    {
      if((x[0]==apple_x) && (y[0]==apple_y))
      {
        dots++; // this will increase size of snake 

        locateAppleOnConsole(); // this will locate random apple on console
      }

    }

    public void checkCollision()
    { 
       for(int i=dots;i>0;i--)
       {
        if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
        {
           inGame=false;
        }
       }

       if(y[0]>=500) inGame=false;
       if(x[0]>=500) inGame=false;
       if(y[0]<0) inGame=false;
       if(x[0]<0) inGame=false;

       if(!inGame)
       {
        timer.stop();
       }

    }

    public void actionPerformed(ActionEvent ae)
    {
      if(inGame==true)
      {
           checkApple();

      checkCollision();

      move();
      }
      

      repaint(); // to reflect changes made in movement
    }

    public class TAdapter extends KeyAdapter
    {
      public void keyPressed(KeyEvent e)
      {
        int key=e.getKeyCode();

        if(key==KeyEvent.VK_LEFT &&(!rightDirection))
        {
          leftDirection=true;
          upDirection=false;
          downDirection=false;
         
        }
        if(key==KeyEvent.VK_RIGHT && (!leftDirection))
        {
          rightDirection=true;
          upDirection=false;
          downDirection=false;
        }
        if(key==KeyEvent.VK_UP && (!downDirection))
        {
          rightDirection=false;
          upDirection=true;
          leftDirection=false;
        }
        if(key==KeyEvent.VK_DOWN && (!upDirection))
        {
          rightDirection=false;
          downDirection=true;
          leftDirection=false;
        }
      }
    }   
    
}
 