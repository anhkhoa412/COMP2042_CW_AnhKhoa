package com.brickbreaker.model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.brickbreaker.controller.Brick;
import com.brickbreaker.controller.Brick.Crack;
/** 
 * Class of object cement-brick 
 * */

public class CementBrick extends Brick {

	/** name of the brick */
    private static final String NAME = "Cement Brick";
    /** color of the brick */
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    /** strength of the brick */
    private static final int CEMENT_STRENGTH = 2;

    /** crack class */
    private Crack crack;
    private Shape brickFace;

    /** constructor of cement brick */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }
    /**Method from parent abstract class
	 * shape of object
	 * Collision of object
	 */

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
