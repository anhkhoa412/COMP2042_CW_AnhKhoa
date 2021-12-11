package com.brickbreaker.test;

import com.brickbreaker.model.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RubberBallTest {
    RubberBall rubberBall = new RubberBall(new Point());


    @Test
    void testBspeedX() {
        rubberBall.setXSpeed(4);
        assertEquals(4, rubberBall.getSpeedX());
    }
    @Test
    void testBspeedY(){
        rubberBall.setYSpeed(12);
        assertEquals(12, rubberBall.getSpeedY());
    }
    @Test
    void testBrespeedX(){
        rubberBall.setXSpeed(4);
        rubberBall.reverseX();
        assertEquals(-4, rubberBall.getSpeedX());
        rubberBall.reverseX();
        assertEquals(4, rubberBall.getSpeedX());
    }
    @Test
    void testBrespeedY(){
        rubberBall.setYSpeed(4);
        rubberBall.reverseY();
        assertEquals(-4, rubberBall.getSpeedY());
        rubberBall.reverseX();
        assertEquals(4, rubberBall.getSpeedY());
    }
}