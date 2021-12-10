package com.brickbreaker.model;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

import com.brickbreaker.controller.Brick;


/**
 * class for ClayBrick object
 *
 */
public class ClayBrick extends Brick {

	/** name of the brick */
    private static final String NAME = "Clay Brick";
    /** color of the brick */
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    /** strength of the brick */
    private static final int CLAY_STRENGTH = 1;

 /** Constructor of claybrick */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }
    /**Method from parent abstract class
	 * shape of object
	 */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
