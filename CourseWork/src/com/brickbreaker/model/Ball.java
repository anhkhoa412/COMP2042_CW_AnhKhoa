package com.brickbreaker.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Abstract class for ball object
 *
 */
abstract public class Ball {

	/** ball's shape */
    private Shape ballFace;

    /** ball's position */
    private Point2D center;
    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    /** ball's color */
    private Color border;
    private Color inner;

    /** ball's speed */
    private int speedX;
    private int speedY;

    /** Constructor of ball 
     * Sets the center of the ball
     * Visualize the ball
     * */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }
    
    /** create ball */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /** handle movement of ball */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }
    /** set ball's speed */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }
    /** speed when ball go up */
    public void setXSpeed(int s){
        speedX = s;
    }
    /** speed when ball go up */
    public void setYSpeed(int s){
        speedY = s;
    }
    /** speed when ball go down*/
    public void reverseX(){
        speedX *= -1;
    }
    /** speed when ball go down */
    public void reverseY(){
        speedY *= -1;
    }
    /** get ball border color */
    public Color getBorderColor(){
        return border;
    }
    /** get ball inner color */
    public Color getInnerColor(){
        return inner;
    }
    /** get position of ball */
    public Point2D getPosition(){
        return center;
    }
    /** get shape of ball */
    public Shape getBallFace(){
        return ballFace;
    }

    /** handle the movement of ball */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /** handle the position of ball */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }


}
