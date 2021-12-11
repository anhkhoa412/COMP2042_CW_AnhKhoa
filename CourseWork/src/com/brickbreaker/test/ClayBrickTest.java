package com.brickbreaker.test;

import com.brickbreaker.model.CementBrick;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {
    CementBrick clayBrick = new CementBrick(new Point(0,0), new Dimension(4,12));
    Rectangle brickFace = new Rectangle(new Point(0, 0), new Dimension(4, 12));
    @Test
    void makeBrickFace() {
        Point pos = new Point(0,0);
        Dimension size = new Dimension(4,12);
        assertEquals(brickFace, clayBrick.makeBrickFace(pos, size));
    }

    @Test
    void getBrick() {
        assertEquals(brickFace, clayBrick.getBrick());
    }
}