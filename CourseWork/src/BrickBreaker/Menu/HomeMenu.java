/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your HighScore) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BrickBreaker.Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import BrickBreaker.Entity.HighScore;
import BrickBreaker.Entity.Leaderboard;
import BrickBreaker.Entity.Player;
import BrickBreaker.Entity.ScoreManager;
import BrickBreaker.Entity.ScoreViews;
import BrickBreaker.Entity.Wall;


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener, ActionListener {
	
	private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String MENU_TEXT = "Exit";
    private static final String HighScore_TEXT = "Score";
  

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle menuButton;
    private JButton InfoButton;
    //HighScore button
    
    private Rectangle HighScoreButton;
    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean HighScoreClicked;
    
    Image background;
    Image InfoIcon;
    private Wall wall;
    public Player player;
   
   
  


    public HomeMenu(GameFrame owner,Dimension area){
    	drawInfo();
    	

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        


        this.owner = owner;


       
        menuFace = new Rectangle(new Point(0,0),area);
        //Get background
        background = new ImageIcon("C:\\Users\\vungu\\eclipse-workspace\\CourseWork\\pictures\\bbicon.png").getImage();
        this.setPreferredSize(area);
        
        
     
       

        Dimension btnDim = new Dimension(area.width / 4, area.height / 8);
     
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        HighScoreButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);



    }


    public void paint(Graphics g){
        drawMenu((Graphics2D)g);  
        g.drawOval(400,70,50,50);
      
    }




	public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);
       
        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        
        drawText(g2d);
        drawButton(g2d);
      

        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
  
    }

    private void drawContainer(Graphics2D g2d){
    	
        Color prev = g2d.getColor();
    
      
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);
       

        g2d.setStroke(tmp);
        g2d.setColor(prev);
        //Draw background
        g2d.drawImage(background,0,0,null);
        
    
       
    }

    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 3);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width ) / 2;
        int y =(int) ((menuFace.height - startButton.height - HighScoreButton.height) * 0.8);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.19;
        
        // Draw HighScore Button
        HighScoreButton.setLocation(x,y);
        x = (int)(HighScoreButton.getWidth() - mTxtRect.getWidth()) / 5;
        y = (int)(HighScoreButton.getHeight() - mTxtRect.getHeight()) / 10;

        x += HighScoreButton.x;
        y += HighScoreButton.y + (HighScoreButton.height * 0.9);
        
        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(HighScoreButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(HighScore_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(HighScoreButton);
            g2d.drawString(HighScore_TEXT,x,y);
        }

        x = HighScoreButton.x;
        y = HighScoreButton.y;

        y *= 1.19;


        menuButton.setLocation(x,y);


        x = (int)(menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        if(menuClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT,x,y);
        }
  
       
  
    }
    public void drawInfo() {
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        InfoButton = new JButton("");
        InfoButton.setIcon(icon);
        InfoButton.setBounds(400, 70, 50, 50); 
        InfoButton.setBorder(null);
        InfoButton.setContentAreaFilled(false);
        InfoButton.addActionListener(this);
        InfoButton.setFocusable(false);
        InfoButton.setOpaque(true);

        add(InfoButton);

    }
 	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==InfoButton) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame,"_Use Space to start and pause the game\n"+"_Use A to move left and D to move right\n"+ "_Press ESC to enter or exit Pause Menu\n");
			}
		}
	


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
 

        }
        else if(menuButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        //HighScore button
        else if (HighScoreButton.contains(p)) {
        	  ScoreViews Sv = new ScoreViews();
              Sv.initialize();
        	
        }
   
        	
    
    }
    
        //HighScoreButton
   
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        //HighScoreButton
        else if(HighScoreButton.contains(p)){
            menuClicked = true;
            repaint(HighScoreButton.x,HighScoreButton.y,HighScoreButton.width+1,HighScoreButton.height+1);
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        //HighScoreButton
        else if(HighScoreClicked){
            HighScoreClicked = false;
            repaint(HighScoreButton.x,HighScoreButton.y,HighScoreButton.width+1,HighScoreButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || menuButton.contains(p) || HighScoreButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }

    	
		
	}

