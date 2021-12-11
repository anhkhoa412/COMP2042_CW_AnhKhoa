package com.brickbreaker.test;

import com.brickbreaker.model.CementBrick;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {
    CementBrick stealBrick = new CementBrick(new Point(0,0), new Dimension(4,12));
    Rectangle brickFace = new Rectangle(new Point(0, 0), new Dimension(4, 12));
    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(4,12);
        assertEquals(brickFace, stealBrick.makeBrickFace(pos, size));
    }

    @Test
    void setImpact() {
        Point2D down = new Point2D.Double();
        down.setLocation(300.0, 435.0);
        int up = 30;
        assertTrue(stealBrick.setImpact(down, up));
    }

    @Test
    void getBrick() {
        assertEquals(brickFace, stealBrick.getBrick());
    }
}