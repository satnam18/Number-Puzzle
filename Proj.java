import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.IOException.*;
import javax.swing.*;
/*
<applet code="Proj" width="1369" height="769"> </applet>
*/
public class Proj extends Applet implements KeyListener,Runnable,ActionListener
{
private AudioClip clip,but,win;
String s="";
Image background;
URL backgroundURL;
URL clipURL,butURL,winURL;
int[] a=new int[9];
int[] r=new int[9];
int x=550;//initial button Cordinates
int y=200;
int hints=4;
int moves=0;
boolean complete,sol_res;
Font f1=new Font("calibri",Font.BOLD,32);
Font f2=new Font("calibri",Font.BOLD,22);
Button[] b=new Button[9];// 9 buttons
Button b1,b2,b3,b4,b5,bb;
Label l1,l2,l3;
int zindex;
int mv_up=0;
int mv_lft=0;
Color cc=new Color(0,0,0);
public void init()
{
clipURL=this.getClass().getResource("audio.wav");
butURL=this.getClass().getResource("button-3.wav");
winURL=this.getClass().getResource("win.wav");
backgroundURL=this.getClass().getResource("zig.jpg");
background=getImage(backgroundURL);
clip=getAudioClip(clipURL);
but=getAudioClip(butURL);
win=getAudioClip(winURL);
repaint();
if(clip!=null)
clip.loop();
for(int i=0;i<9;i++)// storing the generated distinct random numbers
{
int rnd=(int)(9*Math.random());
if(r[rnd]==0)
{
r[rnd]=1;
a[i]=rnd;
}
else i--;
}
setLayout(null);
for(int i=0;i<9;i++)// creating Buttons and setting its Bounds
{
if(a[i]==0)
{
b[i]=new Button(" ");
zindex=i;
}
else
b[i]=new Button(""+a[i]);
b[i].setBounds(x,y,100,100);
if(x==750)
{
x=550;
y+=100;
}
else x+=100;
b[i].setFont(f1);
if(a[i]==0)
b[i].setBackground(Color.green);
else if(a[i]<=3)
b[i].setBackground(Color.red);
else if(a[i]<=6)
b[i].setBackground(Color.yellow);
else if(a[i]<=8)
b[i].setBackground(Color.cyan);
b[i].addActionListener(this);// registering each button with ActionListener
add(b[i]);
}

b1=new Button("RANDOMIZE");
b1.setForeground(Color.WHITE);
b1.setBounds(150,200,350,60);
b1.setFont(f1);
b1.setBackground(cc);
b1.addActionListener(this);
add(b1);

b2=new Button("SOLUTION EXISTS ??");
b2.setForeground(Color.WHITE);
b2.setBounds(150,280,350,60);
b2.setFont(f1);
b2.setBackground(cc);
b2.addActionListener(this);
add(b2);
b3=new Button("HINT");
b3.setForeground(Color.WHITE);
b3.setBounds(150,360,350,60);
b3.setFont(f1);
b3.setBackground(cc);
b3.addActionListener(this);
add(b3);
b4=new Button("QUIT");
b4.setForeground(Color.WHITE);
b4.setBounds(150,440,350,60);
b4.setFont(f1);
b4.setBackground(cc);
b4.addActionListener(this);
add(b4);
b5=new Button("PUZZLE   BOX");
b5.setBounds(550,140,300,60);
b5.setFont(f1);
b5.setBackground(Color.orange);
b5.addActionListener(this);
add(b5);

bb=new Button(" ");
bb.setBounds(0,0,0,0);
add(bb);
bb.addKeyListener(this);
bb.requestFocus();

l1=new Label(" ");
l1.setForeground(Color.WHITE);
l1.setBackground(cc);
l1.setFont(f2);
l1.setBounds(13,10,1337,40);
add(l1);
l2=new Label("MOVES : "+moves);
l2.setForeground(Color.WHITE);
l2.setBackground(new Color(0,0,0));
l2.setFont(f2);
l2.setBounds(150,550,250,50);
add(l2);
l3=new Label("HINTS REMAINING : " + hints);
l3.setForeground(Color.WHITE);
l3.setBackground(new Color(0,0,0));
l3.setFont(f2);
l3.setBounds(150,600,250,50);
add(l3);
}

public void destroy()
{
clip.stop();
this.setVisible(false);
}

void solvable()
{ 
int sum=0;
 mv_up=0;
 mv_lft=0;
int[] count=new int[9];

 while((zindex+1)%3!=0)
 {  set(zindex+1,true);
   mv_lft++;
      try{
   Thread.sleep(600);
   }
   catch(InterruptedException e)
   {}
   }
 while(zindex!=8)
 { set(zindex+3,true);
   mv_up++;
      try{
   Thread.sleep(600);
   }
   catch(InterruptedException e)
   {}
 }
 
 for(int i=0;i<7;i++)
 {  
   for(int j=i+1;j<8;j++)
   { if(a[i]>a[j])
   count[a[i]]++;
   }
   sum+=count[a[i]];
   }
    for(int i=0;i<9;i++)
{

if(a[i]==0)
b[i].setLabel(" ");
else
b[i].setLabel(""+a[i]+"-"+count[a[i]]);
}
if(sum%2==0)
showstatus("     SOLUTION EXISTS FOR THIS ARRANGEMENT OF NUMBERS SINCE SUM OF COUNT IS EVEN    ");
else
showstatus("  !! SOLUTION DOES NOT EXISTS FOR THIS ARRANGEMENT OF NUMBERS SINCE SUM OF COUNT IS ODD!!   ");
}
void resume()
{
 set(zindex,true);
 while(mv_up>0)
 { set(zindex-3,true);
      try{
   Thread.sleep(600);
   }
   catch(InterruptedException e)
   {}
   mv_up--;
 }
 while(mv_lft>0)
 {  set(zindex-1,true);
      try{
   Thread.sleep(600);
   }
   catch(InterruptedException e)
   {}
   mv_lft--;
   }
}
void showstatus(String msg)
{
l1.setText(msg);
}

void set(int pos,boolean stt)// for random()ize Button
{
but.play();
showstatus("                        Operation successful !!");
int pos1,pos2;
if(!stt)
{
pos1=(int)(9*Math.random());
pos2=(int)(9*Math.random());
}
else{
pos1=pos;
pos2=zindex;
zindex=pos1;
}
int tmp=a[pos1];
a[pos1]=a[pos2];
a[pos2]=tmp;

for(int i=0;i<9;i++)
{
if(a[i]==0)
b[i].setBackground(Color.green);
else if(a[i]<=3)
b[i].setBackground(Color.red);
else if(a[i]<=6)
b[i].setBackground(Color.yellow);
else if(a[i]<=8)
b[i].setBackground(Color.cyan);

if(a[i]==0)
{
b[i].setLabel(" ");
zindex=i;

}
else
b[i].setLabel(""+a[i]);
}
if(stt==false)
moves=0;
else if(!sol_res)
moves++;
l2.setText("MOVES:"+moves);
int flag=1;
for(int i=0;i<8;i++)
if(!(a[i]==i+1))
{flag=0;
}
if(flag==1)
{
win.play();
complete=true;
JOptionPane.showMessageDialog(null,"You won the game in  "+moves+"  moves","Congratulations!!",JOptionPane.INFORMATION_MESSAGE);
new Thread(this).start();
}
}

public void keyPressed(KeyEvent ke)
{
sol_res=false;
int k=ke.getKeyCode();
if((b2.getLabel()).equals("SOLUTION EXISTS ??"))
{
switch(k)
{
case KeyEvent.VK_UP:
if(zindex>=6)
{
showstatus("                            UP Move not Possible");
}
else
{
set(zindex+3,true);
}
break;
case KeyEvent.VK_DOWN:
if(zindex<=2)
{
showstatus("                            Down Move Not Possible");
}
else
{
set(zindex-3,true);
}
break;
case KeyEvent.VK_LEFT:
if((zindex+1)%3==0)
{
showstatus("                          Left Move Not Possible");
}
else
{
set(zindex+1,true);
}
break;
case KeyEvent.VK_RIGHT:
if(zindex%3==0)
{
showstatus("                         Right Move Not Possible");
}
else
{
set(zindex-1,true);
}
break;
}
}
}

public void keyTyped(KeyEvent ke)
{
}
public void keyReleased(KeyEvent ke)
{
}
public void actionPerformed(ActionEvent ae)
{
sol_res=false;
bb=new Button(" ");
bb.setBounds(0,0,0,0);
add(bb);
bb.addKeyListener(this);
bb.requestFocus();

int inx=-1;
s=ae.getActionCommand();
for(int i=0;i<9;i++)
{
if(s.equals(b[i].getLabel()))
inx=i;
}
if(s.equals("RANDOMIZE"))
{
b1.setVisible(false);
b2.setVisible(false);
b3.setVisible(false);
b4.setVisible(false);
complete=false;
if(b2.getLabel().equals("RESUME"))
b2.setLabel("SOLUTION EXISTS ??");
hints=4;
moves=0;
mv_lft=0;
mv_up=0;
new Thread(this).start();

}

else if(s.equals("SOLUTION EXISTS ??"))
{
sol_res=true;
b2.setLabel("RESUME");
solvable();

}
else if(s.equals("RESUME"))
{showstatus(" ");
b2.setLabel("SOLUTION EXISTS ??");
sol_res=true;
resume();
}
else if(s.equals("HINT")&&hints>=0)
{
if(hints==4)
showstatus("                             MOVE  1  TO  FIRST BOX  AND  MOVE  2  TO  THIRD BOX");
else if(hints==3)
showstatus(" MOVE  3  BELOW 2  ( KEEPING  2 FIXED IN  THE  THIRD  BOX )  AND  THEN  ARRANGE  THE  FIRST  ROW");
else if(hints==2)
showstatus("                              USE  ROTATIONS  TO  ARRANGE  THE  SECOND  ROW");
else if(hints==1)
showstatus("  CHECK  IF  SOLUTION  EXISTS....??    IF NOT RANDOMIZE..");
else if(hints==0)
showstatus("                             SORRY!!!....YOU  ARE  OUT  OF  HINTS....");
if(hints>0)
hints--;
l3.setText("HINTS REMAINING:"+hints);
}
else if(s.equals("QUIT"))
{
JOptionPane.showMessageDialog(null,"Game Left Incomplete !!  "+moves+"  Moves made","Terminating...",JOptionPane.INFORMATION_MESSAGE);
this.destroy();
}
else if((inx>=0)&&(b2.getLabel()).equals("SOLUTION EXISTS ??"))
{
int dif=zindex-inx;
if(!(dif==-1||dif==-3||dif==3||dif==1))
showstatus("                                   Move Not Possible!!");
else
{
if(inx<=5&&dif==3)set(inx,true);
else if(inx>=3&&dif==-3)set(inx,true);
else if((((inx+1)%3==0)||((inx-1)%3==0))&& dif==-1)set(inx,true);
else if(((inx%3==0)||((inx-1)%3==0))&& dif==1)set(inx,true);
else showstatus("                              Move Not Possible!!");
}
}
}
public void run()
{

if (complete||s.equals("RANDOMIZE"))
{
int tm=0;
while(tm++<=30)
{
set(0,false);
try
{
Thread.sleep(100);
}
catch(InterruptedException e)
{
}
}
b1.setVisible(true);
b2.setVisible(true);
b3.setVisible(true);
b4.setVisible(true);
}
}
public void paint(Graphics g)
{
g.drawImage(background,0,0,1370,670,this);
}}
