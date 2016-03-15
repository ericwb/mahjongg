////////////////////////////////////////////////////////////////
// Authors: Eric Brown and Alla Model
// File: mah.java
// Description: Mahjongg game
////////////////////////////////////////////////////////////////
import java.applet.*;
import java.lang.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class mah extends Applet {
   Image img;
   Image tiles[];
   ImageFilter filter;
   Button uButton;
   Button newButton;
   Button hintButton;
   mahCanvas canvas;
   AudioClip sound,sound2;
   byte [] data;
   int highnum =0;
   Label highLabel;
   MediaTracker tracker;   

   int [] dist1 = {12,23,11,36,13,22,2,30,35,11,12,20,41,37,29,30,40,39,39,11,34,27,36,29,28,29,25,20,22,30,24,25,38,2,11,26,27,28,36,19,28,35,5,19,32,21,27,32,16,21,33,0,10,15,1,18,35,12,13,31,39,41,0,14,33,41,15,17,37,15,16,31,4,3,17,2,23,34,14,14,37,40,9,15,34,40,7,22,38,13,26,25,12,13,19,6,24,33,19,20,38,14,16,36,1,23,39,16,30,26,21,24,22,20,25,26,21,18,18,8,24,17,31,27,32,33,31,34,29,17,18,28,0,32,35,37,23,40,1,0,38,41,1,2};
   int [] dist2 = {18,20,13,16,21,20,17,35,15,18,19,18,14,13,35,11,12,16,19,9,12,24,36,27,38,29,11,28,34,29,14,30,15,5,40,23,39,25,34,37,26,31,36,22,32,7,27,33,37,29,40,3,1,21,0,22,33,2,24,30,40,41,39,23,32,41,0,26,31,1,25,2,4,1,21,0,22,33,2,24,30,40,39,23,32,41,0,26,31,1,25,2,6,40,23,39,25,34,37,26,31,36,22,32,8,27,33,37,29,40,10,12,24,36,27,38,29,11,28,34,29,14,30,15,17,14,13,35,11,12,16,19,19,17,35,15,17,18,41,13,16,20,21,20};
   int [] dist3 = {1,34,15,11,9,14,11,30,12,17,16,38,13,14,24,20,13,20,11,23,19,17,28,20,30,3,33,41,31,13,36,19,17,27,33,29,30,17,13,34,25,7,35,26,25,36,20,28,40,2,24,21,21,11,29,28,16,35,34,19,12,1,22,23,40,14,33,32,29,31,27,26,37,37,18,32,29,26,39,35,27,5,33,36,41,2,35,41,0,22,19,39,39,32,6,38,26,34,39,27,0,32,21,24,23,22,30,36,2,41,25,31,4,37,12,37,0,38,25,31,40,40,8,23,18,16,18,21,22,15,16,1,15,24,12,38,0,18,14,1,28,15,10,2};
   int [] dist4 = {27,40,16,38,30,40,37,37,34,18,26,13,22,20,33,39,7,2,29,17,11,38,0,32,29,19,33,1,18,8,41,2,33,24,24,14,35,30,35,20,27,25,18,28,9,24,15,10,0,15,20,33,0,16,21,27,36,36,28,19,11,3,37,39,1,15,13,31,1,13,21,23,17,23,14,24,31,32,4,22,32,34,5,25,19,34,18,12,36,26,12,22,13,21,32,36,28,6,29,25,25,29,22,15,26,30,35,41,16,20,12,17,14,35,19,30,31,41,2,11,1,17,38,39,40,31,23,39,2,41,0,28,38,12,23,26,11,21,16,14,37,34,40,27};

   int [] hint1 = {61,141,38,103,81,85,106,60,88,100,80,68,56,41,84,77,65,12,44,47,64,97,122,59,29,108,23,25,40,37,21,46,35,90,31,114,96,120,105,76,112,87,116,49,27,99,43,94,55,118,67,74,102,70,83,53,63,79,4,58,0,10,2,9,143,6,142,138,132,139,16,137,18,17,140,32,135,13,3,22,134,8,20,127,125,50,133,124,126,71,15,7,128,14,131,24,123,36,115,109,26,91,30,111,1,136,28,5,45,110,113,11,39,98,117,130,121,129,48,107,66,69,78,101,93,89,57,92,34,19,52,82,86,119,42,95,73,72,33,75,54,104,51,62};
   int [] hint2 = {61,138,65,85,60,81,47,106,68,88,44,103,64,84,41,100,59,80,38,97,56,77,31,122,29,120,27,118,25,116,23,114,21,112,49,108,46,105,43,102,40,99,37,96,35,94,70,90,67,87,63,83,58,79,55,76,53,74,1,141,5,143,4,142,0,137,3,140,2,139,10,132,8,135,7,134,6,133,9,11,124,136,18,131,13,126,14,127,15,128,16,129,17,130,12,125,32,123,30,121,28,119,26,117,24,115,22,113,20,111,19,110,50,109,48,107,45,104,42,101,40,98,36,95,34,93,33,92,71,91,69,89,66,86,62,82,57,78,54,75,52,73,51,72};
   int [] hint3 = {100,116,0,61,85,143,25,112,60,114,81,94,29,38,41,122,65,138,4,142,10,56,3,6,1,97,2,141,125,130,9,21,74,137,31,59,17,23,103,133,44,118,77,96,80,99,47,140,35,68,7,106,64,120,27,84,88,136,131,139,49,108,18,53,8,134,12,16,5,13,129,132,11,135,37,32,124,126,20,90,15,46,102,127,105,128,19,123,14,50,40,110,43,71,33,70,22,55,24,36,54,76,28,111,75,93,26,34,39,58,57,79,30,45,113,115,95,117,78,98,48,121,87,109,51,52,91,92,72,73,69,119,63,104,42,86,83,107,67,101,62,89,66,82};
   int [] hint4 = {97,61,0,143,2,138,1,142,3,132,6,7,8,141,5,124,38,106,112,139,81,85,88,56,21,122,23,94,35,74,53,108,41,100,25,114,90,133,70,137,27,120,105,37,87,110,40,55,126,134,11,92,44,47,43,131,127,123,65,103,60,136,59,84,58,96,77,80,13,109,64,68,67,76,15,63,125,116,99,83,14,26,130,22,111,121,129,107,36,113,91,12,42,9,54,93,62,140,4,115,19,72,73,71,10,135,79,102,118,128,20,119,46,49,16,29,24,98,39,50,89,104,17,31,33,34,30,117,18,101,52,48,78,82,57,95,28,86,32,51,66,69,75,45};
   int [] hint5 = {106,61,97,103,116,44,31,25,141,47,143,118,120,142,139,41,81,140,134,100,80,135,130,128,0,65,136,2,4,129,29,10,119,46,3,18,38,9,137,12,117,131,123,88,17,56,8,102,138,15,105,132,133,87,85,124,32,84,101,21,35,99,79,53,126,109,91,114,125,96,27,74,112,94,1,26,127,5,49,111,70,7,64,11,110,20,92,16,72,77,6,51,34,71,28,60,104,40,43,90,23,42,50,37,108,55,45,115,113,98,52,78,63,122,59,121,86,107,13,89,14,22,58,76,33,69,68,36,95,67,75,83,19,82,24,57,30,54,62,48,93,66,39,73};
   int [] hint6 = {141,125,130,44,29,47,4,17,61,9,10,16,143,18,68,100,38,65,31,56,49,112,32,41,122,77,85,108,88,64,118,81,142,80,111,23,121,37,140,107,25,135,136,27,128,0,94,117,74,93,120,97,139,129,119,103,114,102,96,46,30,55,1,2,138,5,132,6,3,7,14,13,28,67,124,24,84,110,134,60,116,127,133,76,40,126,137,39,113,99,21,83,92,73,90,35,79,95,89,87,72,43,70,51,106,69,59,50,12,58,11,20,48,34,131,57,8,75,71,105,19,104,91,78,109,22,101,86,53,82,52,115,98,45,15,66,26,63,123,42,54,62,33,36};
   int [] hint7 = {143,133,97,38,31,25,6,27,9,47,4,44,10,116,18,17,114,30,32,61,138,68,21,81,136,12,65,129,100,43,126,80,0,96,41,60,50,103,113,56,118,99,23,102,29,59,85,40,49,20,48,77,16,70,79,28,130,125,142,58,112,140,88,111,141,13,139,94,74,120,119,106,90,122,135,134,71,127,105,1,84,104,2,46,67,3,91,5,69,37,22,89,109,55,8,35,45,64,137,63,128,131,87,93,73,7,115,53,117,83,11,101,15,19,86,98,14,78,36,24,132,54,124,34,82,110,123,62,92,42,76,72,66,75,121,51,39,108,95,57,26,52,107,33};

   public void init() {
      tracker=new MediaTracker(this);

      sound=getAudioClip(getCodeBase(),"dohh.au");
      sound2=getAudioClip(getCodeBase(),"woohoo.au"); 

      tiles=new Image[42];
      cropImage("mahjongg");  

      resize(1024,600);
      setLayout(new BorderLayout());

      Panel p = new Panel();
      newButton = new Button("NEW");
      p.add(newButton);
      add("North",p);

      hintButton = new Button("HINT");
      p.add(hintButton);
      add("North",p);        

      uButton = new Button("UNDO");
      p.add(uButton);
      add("North",p);


      Choice c = new Choice();
      c.addItem("Distribution: 1");
      c.addItem("Distribution: 2");
      c.addItem("Distribution: 3");
      c.addItem("Distribution: 4");

      p.add(c);
      add("North",p);   

      Date time = new Date();
      long start = time.getTime();

      p = new Panel();


/*
      try {
         Socket server = new Socket("www.cs.purdue.edu",4321);
         InputStream in = server.getInputStream();
         OutputStream out = server.getOutputStream();

         PrintStream pout = new PrintStream(out);
         pout.println("gethighscore");

         DataInputStream din = new DataInputStream(in);
         String response=din.readLine();

         highnum = Integer.valueOf(response).intValue();  


         String highStr = "Highscore: " + response + " seconds";
         highLabel = new Label(highStr);
         p.add(highLabel);

         server.close();

      } catch (IOException e) {
      }
*/


      p.add(new Label("Tiles:"));
      Choice c2 = new Choice();
      c2.addItem("Mahjongg");
      c2.addItem("Foods"); 
      c2.addItem("Money");
      p.add(c2);
      add("South",p);



      p = new Panel();
      canvas=new mahCanvas(tiles,sound,sound2,start,highnum);

      p.add(canvas);
      add("Center",p);
   }
/////////////////////////////////////////////////////////////
// Function: cropImage
////////////////////////////////////////////////////////////
   public void cropImage(String s) {
      s = s.toLowerCase(); 
      img = getImage(getCodeBase(), s+".gif"); 
      tracker.addImage(img,0);
      try {
         tracker.waitForID(0);
      } catch (InterruptedException e) {
      }

      int cnt =0; 
      for (int i=0;i<=5;++i) {
         for (int j=0;j<=6;++j) {
            filter=new CropImageFilter(j*64,i*64,64,64);
            tiles[cnt]=createImage(new FilteredImageSource(img.getSource(),filter));
            tracker.addImage(tiles[cnt],1); 
            ++cnt;
         }

      }
      try {
         tracker.waitForID(1);
      } catch (InterruptedException e) {
      }
   }

//////////////////////////////////////////////////////////////
// Function: action
//////////////////////////////////////////////////////////////	
   public boolean action(Event e,Object arg) {
      if ((e.target == uButton) && (canvas.oldHigh1 != -1)) {
         canvas.table[canvas.oldPos[0]][canvas.oldPos[1]][canvas.oldPos[2]] = canvas.oldHigh1;
         canvas.table[canvas.oldPos[3]][canvas.oldPos[4]][canvas.oldPos[5]] = canvas.oldHigh2;
         canvas.open[canvas.indexes[canvas.oldPos[0]][canvas.oldPos[1]][canvas.oldPos[2]]] = 1;
         canvas.open[canvas.indexes[canvas.oldPos[3]][canvas.oldPos[4]][canvas.oldPos[5]]] = 1;

         canvas.tilesLeft += 2;

         // unhighlight tile
         canvas.flag=0;
         canvas.oldHigh1 = -1;
         canvas.oldHigh2 = -1;
         canvas.repaint();
         return true;
      } else if (e.target == newButton) {
         canvas.newGame();
         try {
            Socket server = new Socket("www.cs.purdue.edu",4321);
            InputStream in = server.getInputStream();
            OutputStream out = server.getOutputStream();

            PrintStream pout = new PrintStream(out);
            pout.println("gethighscore");

            DataInputStream din = new DataInputStream(in);
            String response=din.readLine();

            highnum = Integer.valueOf(response).intValue();  


            String highStr = "Highscore: " + response + " seconds";
            highLabel.setText(highStr);

            server.close();
         } catch (IOException e1) {
         }


         canvas.repaint();
         return true; 
      } else if (e.target == hintButton) {
         canvas.hint();
         canvas.repaint();
         return true;
      } else if (e.target instanceof Choice) {
         if (!(arg.toString().startsWith("Distribution:"))) {
            cropImage(arg.toString()); 
            canvas.tiles = tiles;   
            canvas.repaint(); 
            return true;
         } else {
            if (arg.toString().equals("Distribution: 1"))
               canvas.dist(dist1,hint1);
            else if (arg.toString().equals("Distribution: 2"))
               canvas.dist(dist2,hint2);
            else if (arg.toString().equals("Distribution: 3"))
               canvas.dist(dist3,hint3);
            else if (arg.toString().equals("Distribution: 4"))
               canvas.dist(dist4,hint4);
            try {
               Socket server = new Socket("www.cs.purdue.edu",4321);
               InputStream in = server.getInputStream();
               OutputStream out = server.getOutputStream();

               PrintStream pout = new PrintStream(out);
               pout.println("gethighscore");

               DataInputStream din = new DataInputStream(in);
               String response=din.readLine();

               highnum = Integer.valueOf(response).intValue();  


               String highStr = "Highscore: " + response + " seconds";
               highLabel.setText(highStr);

               server.close();
            } catch (IOException e1) {
            }


            canvas.repaint();
            return true;
         }

      }
      return false;
   }
}

//////////////////////////////////////////////////////////////////////
// class: mahCanvas
/////////////////////////////////////////////////////////////////////
class mahCanvas extends Canvas {
   static final int levels = 4;

   int i,j,num,flag,flagX,flagY,flagZ, oldHigh1, oldHigh2;
   int high;
   Image tiles[] = new Image[42];
   Image img;
   Image offScrImg;

   int tilex [] = new int[144];
   int tiley [] = new int[144];
   int tilez [] = new int[144];

   int [] open = new int [144];

   int table [][][] = new int [16][9][5];
   int indexes [][][] = new int [16][9][5];
   int xOffset [][][] = new int [16][9][5];
   int yOffset [][][] = new int [16][9][5];

   int [] tilevalue = {0,0,0,0,1,1,1,1,2,2,2,2,3,4,5,6,7,8,9,10,11,11,11,11,12,12,12,12,13,13,13,13,14,14,14,14,15,15,15,15,16,16,16,16,17,17,17,17,18,18,18,18,19,19,19,19,20,20,20,20,21,21,21,21,22,22,22,22,23,23,23,23,24,24,24,24,25,25,25,25,26,26,26,26,27,27,27,27,28,28,28,28,29,29,29,29,30,30,30,30,31,31,31,31,32,32,32,32,33,33,33,33,34,34,34,34,35,35,35,35,36,36,36,36,37,37,37,37,38,38,38,38,39,39,39,39,40,40,40,40,41,41,41,41};

   int ydim = 8;
   int xdim = 15;
   int oldPos [] = new int [6];
   int tilesLeft = 144;

   AudioClip sound,sound2;

   long start;
   long end;

   int highnum;

   int hintFlag[] = new int[6];

   boolean newFlag = true;

   int pnt = 0;
   int USflag = 0;



   int [] hint = new int [144];

   mahCanvas(Image tiles[],AudioClip sound,AudioClip sound2,long start,int highnum) {
      this.sound = sound;
      this.sound2 = sound2;      
      this.start = start;
      this.highnum = highnum;


      for (int c=0;c<42;++c) {
         this.tiles[c] = tiles[c];
      }
      flag =0;
      high = -1;
      oldHigh1 = -1;
      oldHigh2 = -1;

      resize(1024,550);

      for (int i = 0; i<16; i++) {
         for (int j=0; j<9; j++) {
            for (int k=0; k<5; k++) {
               table[i][j][k] = -2;
            }
         }
      }

      table[7][3][4] = -1;

      table[7][3][3] = -1;
      table[7][4][3] = -1;
      table[8][3][3] = -1;
      table[8][4][3] = -1;

      for (int a = 6; a < 10; a++) {
         for (int b = 2; b <6; b++) {
            table[a][b][2] = -1;
         }
      }

      for (int a = 5; a < 11; a++) {
         for (int b = 1; b <7; b++) {
            table[a][b][1] = -1;
         }
      }

      for (int a = 2; a<14; a++) {
         table[a][0][0] = -1;
         table[a][3][0] = -1;
         table[a][4][0] = -1;
         table[a][7][0] = -1;
      }
      for (int a = 3; a<13; a++) {
         table[a][2][0] = -1;
         table[a][5][0] = -1;
      }
      for (int a = 4; a<12; a++) {
         table[a][1][0] = -1;
         table[a][6][0] = -1;
      }
      table[1][3][0] = -1;
      table[14][3][0] = -1;
      table[15][3][0] = -1;

      randTile();


   }
///////////////////////////////////////////////////////////
// Function: randTile()
///////////////////////////////////////////////////////////   
   public void randTile() {

      if (newFlag) {
         // randomize tiles
         Random rand = new Random();
         int temp;

         for (int e=0;e<3; ++e) {
            for (int c = 0; c<144; c++) {
               int m = java.lang.Math.abs(rand.nextInt()%144);
               temp = tilevalue[c];
               tilevalue[c] = tilevalue[m];
               tilevalue[m] = temp;
            }
         } 
      }
      int tileno = 0;
      for (int a= 0; a<16; a++) {
         for (int b=0; b<9; b++) {
            for (int c=0; c<5; c++) {
               if (table[a][b][c] != -2) {

                  table[a][b][c] = tilevalue[tileno];
                  indexes[a][b][c] = tileno;

                  switch (tileno) {
                     case 0:
                     case 1:
                     case 4:
                     case 6:
                     case 9:
                     case 12:
                     case 17:
                     case 21:
                     case 23:
                     case 25:
                     case 27:
                     case 29:
                     case 31:
                     case 38:
                     case 41:
                     case 44:
                     case 47:
                     case 61:
                     case 97:
                     case 100:
                     case 103:
                     case 106:
                     case 112:
                     case 114:
                     case 116:
                     case 118:
                     case 120:
                     case 122:
                     case 125:
                     case 130:
                     case 133:
                     case 136:
                     case 138:
                     case 141:
                     case 143: open[tileno] = 1;

                  }

                  tilex[tileno] = a;
                  tiley[tileno] = b;
                  tilez[tileno++] = c;
               }
            }
         }
      }


      yOffset[1][3][0] = 1;
      yOffset[14][3][0] = 1; 
      yOffset[15][3][0] = 1;
      xOffset[7][3][4] =1;
      yOffset[7][3][4] =1;

   }


////////////////////////////////////////////////////////////
// Function: paint
////////////////////////////////////////////////////////////

   public void paint(Graphics g) {

      // clears the background
      g.setColor(Color.lightGray);
      g.fillRect(0,0,1024,550);

      int timespent = (int)((end-start)/1000);

      if (tilesLeft == 0) {
         String t = "Time: " + (timespent) + " seconds";
         g.setColor(Color.black);
         g.drawString(t,512,300);
         if (timespent < highnum) {
            try {

               Socket server = new Socket("www.cs.purdue.edu",4321);
               InputStream in = server.getInputStream();
               OutputStream out = server.getOutputStream();

               PrintStream pout = new PrintStream(out);
               String str = "updatehighscore " + timespent;
               pout.println(str);

            } catch (IOException e) {
            }

         }

      }

      int X,Y;

      String message = "Tiles Left: " + tilesLeft;
      g.setColor(Color.black);
      g.drawString(message,900,150);

      // loop thru the table of tile images
      for (int z=0;z<5;++z) {
         for (int y=0;y<8;++y) {
            for (int x=0;x<16;++x) {

               // display tile unless it's gone
               if (table[x][y][z] >= 0) {
                  X=64*x+(xOffset[x][y][z])*32;
                  Y=64*y+(yOffset[x][y][z])*32;

                  // if current tile is highlighted
                  if (flagX == x && flagY == y && flagZ == z && flag ==1) {
                     if (!(x == 1 && y == 3 && z == 0)) {
                        g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
                        g.setColor(Color.white);
                        g.drawRect(X+5*z,Y-5*z,63,63);
                        g.setColor(Color.black);
                        g.drawRect(X+1+5*z,Y+1-5*z,61,61);
                        g.setColor(Color.white);
                        g.drawRect(X+2+5*z,Y+2-5*z, 59, 59);

                        polygen(x,y,X,Y,z,g);
                     }
                  } else if ((hintFlag[0] == x && hintFlag[1] == y && hintFlag[2] == z)
                             || (hintFlag[3] == x && hintFlag[4] == y && hintFlag[5] == z)) {
                     g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
                     g.setColor(Color.white);
                     g.drawRect(X+5*z,Y-5*z,63,63);
                     g.drawRect(X+2+5*z,Y+2-5*z,59,59);
                     g.setColor(Color.black); 
                     g.drawRect(X+1+5*z,Y+1-5*z,61,61);
                     polygen(x,y,X,Y,z,g);
                  }

                  // tile is not highlighted
                  else {
                     if (!(x == 1 && y == 3 && z == 0)) {
                        g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
                        polygen(x,y,X,Y,z,g);
                     }
                  }
               }
            }
         }
      }
      int x=1;
      int y=3;
      int z=0;

      if (table[x][y][z] >= 0) {
         X=64*x+(xOffset[x][y][z])*32;
         Y=64*y+(yOffset[x][y][z])*32;

         // if current tile is highlighted
         if (flagX == x && flagY == y && flagZ == z && flag ==1) {
            g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
            g.setColor(Color.white);
            g.drawRect(X+5*z,Y-5*z,63,63);
            g.drawRect(X+2+5*z,Y+2-5*z,59,59);
            g.setColor(Color.black);
            g.drawRect(X+1+5*z,Y+1-5*z,61,61);

            polygen(x,y,X,Y,z,g);
         } else if ((hintFlag[0] == x && hintFlag[1] == y && hintFlag[2] == z)
                    || (hintFlag[3] == x && hintFlag[4] == y && hintFlag[5] == z)) {
            g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
            g.setColor(Color.white);
            g.drawRect(X+5*z,Y-5*z,63,63);
            g.drawRect(X+2+5*z,Y+2-5*z,59,59);
            g.setColor(Color.black);
            g.drawRect(X+1+5*z,Y+1-5*z,61,61);

            polygen(x,y,X,Y,z,g);
         }


         // tile is not highlighted
         else {
            g.drawImage(tiles[table[x][y][z]],X+5*z,Y-5*z,this);
            polygen(x,y,X,Y,z,g);
         }
      }

   }
/////////////////////////////////////////////////////////
// Function: polygen
/////////////////////////////////////////////////////////

   public void polygen(int x,int y,int X, int Y,int z, Graphics g) {

      if ((x == 0) || (table[x-1][y][z] < 0) || (x==2 && y==3 && z==0)) {
         int [] polyXs = {X-5+5*z,X-5+5*z,X+5*z,X+5*z};
         int [] polyYs = {Y+5-5*z,Y+68-5*z,Y+64-5*z,Y-5*z};
         Polygon poly = new Polygon(polyXs,polyYs,4);
         g.setColor(Color.darkGray);
         g.fillPolygon(poly);  
      }

      if ((y == ydim) || (table[x][y+1][z] < 0)) {
         int [] polyXs = {X+5*z,X-5+5*z,X+58+5*z,X+63+5*z};
         int [] polyYs = {Y+63-5*z,Y+68-5*z,Y+68-5*z,Y+63-5*z};
         Polygon poly = new Polygon(polyXs,polyYs,4);
         g.setColor(Color.darkGray);
         g.fillPolygon(poly);
      }
   }
////////////////////////////////////////////////////////////
// Method: dist()
////////////////////////////////////////////////////////////
   public void dist(int [] d, int [] h) {

      for (int i=0;i<144;++i) {
         hint[i] = h[i];
         tilevalue[i] = d[i];
         open[i] = 0;
      }
      for (int i=0;i<6;++i)
         hintFlag[i] = 0;

      newFlag = false;
      randTile();

      USflag=1;
      pnt = 0;
      flag=0;
      high=-1;
      oldHigh1 = -1;
      oldHigh2 = -1;
      tilesLeft = 144;
      Date time = new Date();
      start = time.getTime();    

   }   
////////////////////////////////////////////////////////////
// Function: newGame()
///////////////////////////////////////////////////////////

   public void newGame() {
      for (int i=0;i<144;++i)
         open[i] =0;
      for (int i=0;i<6;++i)
         hintFlag[i] = 0;
      newFlag = true;
      randTile();
      flag =0;
      high = -1;
      oldHigh1 = -1;
      oldHigh2 = -1;
      tilesLeft = 144;
      Date time = new Date();
      start = time.getTime();
      USflag = 0;
   }
////////////////////////////////////////////////////////////
// Method: hint()
////////////////////////////////////////////////////////////

   public void hint() {

      flag = 0;
      if (tilesLeft == 144-pnt && USflag==1) {
         hintFlag[0] = tilex[hint[pnt]];
         hintFlag[1] = tiley[hint[pnt]];
         hintFlag[2] = tilez[hint[pnt]];
         hintFlag[3] = tilex[hint[pnt+1]];
         hintFlag[4] = tiley[hint[pnt+1]];
         hintFlag[5] = tilez[hint[pnt+1]];

         pnt +=2;
      } else {

         USflag =0;

         //outer:
         for (int i=0;i<143;++i) {
            if (open[i] == -1 || open[i] == 0 && (!(checkOpen(tilex[i],tiley[i],tilez[i]))))
               continue;

            for (int j=i+1;j<144;++j) {
               if (tilevalue[j] == tilevalue[i] ||
                   (tilevalue[i] > 6 && tilevalue[i] < 11 && tilevalue[j] > 6 && tilevalue[j] < 11)
                   ||(tilevalue[i] >2 && tilevalue[i] < 7 && tilevalue[j] >2 && tilevalue[j] < 7)) {

                  if (open[j] == -1) continue;

                  if (open[j] == 1 || checkOpen(tilex[j],tiley[j],tilez[j])) {

                     hintFlag[0] = tilex[i];
                     hintFlag[1] = tiley[i];
                     hintFlag[2] = tilez[i];
                     hintFlag[3] = tilex[j];
                     hintFlag[4] = tiley[j];
                     hintFlag[5] = tilez[j];
                     i=145;
                     j=145;
                     //break outer;

                  }
               }
            }
         }
      }
   }    

////////////////////////////////////////////////////////////
// Function: update
////////////////////////////////////////////////////////////

   public void update(Graphics g) {
      if (offScrImg == null)
         offScrImg = createImage(1024,550);
      Graphics og = offScrImg.getGraphics();
      paint(og);
      g.drawImage(offScrImg,0,0,this);
      og.dispose();
   }

////////////////////////////////////////////////////////////
// Function: checkOpen
////////////////////////////////////////////////////////////

   public boolean checkOpen(int xcoor, int ycoor, int z) {

      if (xcoor > 15 || xcoor < 1 || ycoor < 0 || ycoor > 7 || z < 0 || z > 3)
         return false;

      if ((table[xcoor-1][ycoor][z] <0 || table[xcoor+1][ycoor][z]<0)
          && (table[xcoor][ycoor][z+1] <0) 
          && (!(z == 3 && table[7][3][4] >= 0))) {
         if ((z == 0 && xcoor==2 && ycoor==4 && table[1][3][0] >=0)
             || (z==0 && xcoor==13 && ycoor==4 && table[14][3][0] >=0))
            return false;
         if (table[xcoor][ycoor][z] > -1) {
            open[indexes[xcoor][ycoor][z]] = 1;
            return true;       
         }

      }
      open[indexes[xcoor][ycoor][z]] = 0;
      return false;
   }

////////////////////////////////////////////////////////////
// Function: mouseDown
////////////////////////////////////////////////////////////

   public boolean mouseDown(Event e, int x, int y) {

      int xcoor;
      int ycoor;

      for (int z=levels;z>=0;--z) {
         if (z == levels) {
            xcoor = (x-5*z-32)/64;
            ycoor = (y+5*z-32)/64;
            if (table[xcoor][ycoor][z] <0) continue;

         } else {
            xcoor = (x-5*z)/64;
            ycoor = (y+5*z)/64;
            if (z == 0 && (xcoor==1 || xcoor==14 || xcoor==15)) {
               ycoor = (y+5*z-32)/64;
               if (table[xcoor][ycoor][z] <0) continue;
            }
         }

         // if click on blank do nothing    
         if (table[xcoor][ycoor][z] <0) continue;

         // check if tile is open
         if (xcoor == 1 || xcoor == xdim || z == levels || checkOpen(xcoor,ycoor,z)) {

            if ((z == 0 && xcoor==2 && ycoor==4 && table[1][3][0] >=0)
                || (z==0 && xcoor==13 && ycoor==4 && table[14][3][0] >=0)) {

               sound.play();
               break;
            }

            // tile is highlighted already
            if (flag == 1) {

               // if not same tile
               if (!(flagX == (xcoor) && flagY == (ycoor) && flagZ == z)) {
                  // if tile has the same value, delete
                  if (table[xcoor][ycoor][z] == high) {

                     //System.out.println(indexes[xcoor][ycoor][z]);
                     //System.out.println(indexes[flagX][flagY][flagZ]);

                     table[xcoor][ycoor][z] = -1;
                     table[flagX][flagY][flagZ] = -1;
                     open[indexes[xcoor][ycoor][z]] = -1;
                     open[indexes[flagX][flagY][flagZ]] = -1;

                     // handle special cases (half-tiles)
                     if (z == levels || flagZ == levels) {
                        open[indexes[8][3][3]] = 1;
                        open[indexes[7][4][3]] = 1;
                        open[indexes[8][4][3]] = 1;
                     }

                     else if (xcoor == 1 || flagX == 1)
                        open[indexes[2][4][0]] = 1;
                     else if (xcoor == 14 || flagX == 14)
                        open[indexes[13][4][0]] = 1;

                     checkOpen(xcoor-1,ycoor,z);
                     checkOpen(xcoor+1,ycoor,z);
                     checkOpen(flagX-1,flagY,flagZ);
                     checkOpen(flagX+1,flagY,flagZ);
                     if (z > 0) {
                        checkOpen(xcoor,ycoor, z-1);
                        checkOpen(flagX,flagY,flagZ-1);
                     }

                     flag = 0;
                     oldPos[0] = xcoor;
                     oldPos[1] = ycoor;
                     oldPos[2] = z;
                     oldPos[3] = flagX;
                     oldPos[4] = flagY;
                     oldPos[5] = flagZ;
                     oldHigh1 = high;
                     oldHigh2 = high;
                     if (tilesLeft == 2) {
                        sound2.play();
                        Date time = new Date();
                        end = time.getTime();
                     }
                     tilesLeft -= 2;
                     break;
                  }

                  else if ((table[xcoor][ycoor][z] > 6) && (table[xcoor][ycoor][z] <11)) {
                     if ((high >6) && (high <11)) {

                        table[flagX][flagY][flagZ] = -1;
                        flag = 0;
                        oldPos[0] = xcoor;
                        oldPos[1] = ycoor;
                        oldPos[2] = z;
                        oldPos[3] = flagX;
                        oldPos[4] = flagY;
                        oldPos[5] = flagZ;
                        oldHigh1 = table[xcoor][ycoor][z];
                        oldHigh2 = high;
                        table[xcoor][ycoor][z] = -1;
                        open[indexes[xcoor][ycoor][z]] = -1;
                        open[indexes[flagX][flagY][flagZ]] = -1;

                        //System.out.println(indexes[xcoor][ycoor][z]);
                        //System.out.println(indexes[flagX][flagY][flagZ]);


                        if (z == levels || flagZ == levels) {
                           open[indexes[8][3][3]] = 1;
                           open[indexes[7][4][3]] = 1;
                           open[indexes[8][4][3]] = 1;
                        }

                        else if (xcoor == 1 || flagX == 1)
                           open[indexes[2][4][0]] = 1;
                        else if (xcoor == 14 || flagX == 14)
                           open[indexes[13][4][0]] = 1;

                        checkOpen(xcoor-1,ycoor,z);
                        checkOpen(xcoor+1,ycoor,z);
                        checkOpen(flagX-1,flagY,flagZ);
                        checkOpen(flagX+1,flagY,flagZ);
                        if (z > 0) {
                           checkOpen(xcoor,ycoor, z-1);
                           checkOpen(flagX,flagY,flagZ-1);
                        }
                        if (tilesLeft == 2) {
                           sound2.play();
                           Date time = new Date();
                           end = time.getTime();
                        }
                        tilesLeft -= 2;
                        break;  
                     }
                  }

                  else if ((table[xcoor][ycoor][z] > 2) && (table[xcoor][ycoor][z] <7)) {
                     if ((high > 2) && (high < 7)) {

                        table[flagX][flagY][flagZ] = -1;
                        flag = 0;
                        oldPos[0] = xcoor;
                        oldPos[1] = ycoor;
                        oldPos[2] = z;
                        oldPos[3] = flagX;
                        oldPos[4] = flagY;
                        oldPos[5] = flagZ;
                        oldHigh1 = table[xcoor][ycoor][z];
                        oldHigh2 = high;
                        table[xcoor][ycoor][z] = -1;
                        open[indexes[xcoor][ycoor][z]] = -1;
                        open[indexes[flagX][flagY][flagZ]] = -1;


                        //System.out.println(indexes[xcoor][ycoor][z]);
                        //System.out.println(indexes[flagX][flagY][flagZ]);


                        if (z == levels || flagZ == levels) {
                           open[indexes[8][3][3]] = 1;
                           open[indexes[7][4][3]] = 1;
                           open[indexes[8][4][3]] = 1;
                        }

                        else if (xcoor == 1 || flagX == 1)
                           open[indexes[2][4][0]] = 1;
                        else if (xcoor == 14 || flagX == 14)
                           open[indexes[13][4][0]] = 1;

                        checkOpen(xcoor-1,ycoor,z);
                        checkOpen(xcoor+1,ycoor,z);
                        checkOpen(flagX-1,flagY,flagZ);
                        checkOpen(flagX+1,flagY,flagZ);
                        if (z > 0) {
                           checkOpen(xcoor,ycoor, z-1);
                           checkOpen(flagX,flagY,flagZ-1);
                        }
                        if (tilesLeft == 2) {
                           sound2.play();
                           Date time = new Date();
                           end = time.getTime();
                        }
                        tilesLeft -= 2;
                        break;  
                     }
                  }
               }

               // if same tile, unhighlight
               else {
                  flag = 0;
                  break;
               }
            }

            // nothing is highlighted

            else {
               // if not blank 
               if (table[xcoor][ycoor][z] >= 0) {
                  flag = 1;
                  flagX = xcoor;
                  flagY = ycoor;
                  flagZ = z;
                  high = table[flagX][flagY][flagZ];
                  for (int i=0;i<6;++i) {
                     hintFlag[i] = 0;  
                  }
                  break;
               }

            }
         }
         sound.play();
         break;
      } // for
      repaint();
      return true;
   }
}
