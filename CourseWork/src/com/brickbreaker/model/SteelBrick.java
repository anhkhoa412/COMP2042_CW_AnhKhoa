/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.brickbreaker.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import com.brickbreaker.controller.Brick;

/**
 * class for StealBrick object
 *
 */
public class SteelBrick extends Brick {

	/** name of the brick */
    private static final String NAME = "Steel Brick";
    /** color of the brick */
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    /** strength of the brick */
    private static final int STEEL_STRENGTH = 1;
    /** The chances to crack th� brick */
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    /** constructor of the steal brick */
    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
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

    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }
    @Override
    public Shape getBrick() {
        return brickFace;
    }

}
