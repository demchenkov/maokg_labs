package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationHorse implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholeHorse;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;

    private JFrame mainFrame;

    private float signX=1.0f;
    private float signY=1.0f;
    private float signZoom=1.0f;
    private float zoom=0.2f;
    private float xloc=-1.5499996f;
    private float yloc=-1.6999996f;
    private float zloc=0.0f;
    private int moveType=1;
    private int sideSign = 1;
    private int direction = 1;
    private Timer timer;

    int side = 0;

    public AnimationHorse(TransformGroup wholeHorse, Transform3D trans,JFrame frame){
        go = new Button("Go");
        this.wholeHorse=wholeHorse;
        this.translateTransform=trans;
        this.mainFrame=frame;

        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();
        rotateTransformX.rotX(3*Math.PI/2);
        translateTransform.mul(rotateTransformX);


        FirstMainClass.canvas.addKeyListener(this);
        timer = new Timer(100, this);

        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    private void initialHorseState(){
        xloc=-1.5499996f;
        yloc=-1.6999996f;
        zloc=0.0f;
        zoom=0.2f;
        moveType=1;
        signX=1.0f;
        signY=1.0f;
        if(timer.isRunning()){timer.stop();}
        go.setLabel("Go");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
        if (e.getSource()==go){
            if (!timer.isRunning()) {
                timer.start();
                go.setLabel("Stop");
            }
            else {
                timer.stop();
                go.setLabel("Go");
            }
        }
        else {
            Move(moveType);
            translateTransform.setScale(new Vector3d(zoom,zoom,zoom));
            translateTransform.setTranslation(new Vector3f(xloc,yloc,zloc));
            wholeHorse.setTransform(translateTransform);
        }
    }

    private void Move(int mType){
            if(side == 0) {
                rotateTransformX.rotX(Math.PI/16);
                translateTransform.mul(rotateTransformX);
                side = 1;
                sideSign = -1;
            }else if(side == 2){
                rotateTransformX.rotX(-Math.PI/16);
                translateTransform.mul(rotateTransformX);
                side = 1;
                sideSign = 1;
            } else{
                rotateTransformX.rotX(sideSign*Math.PI/16);
                translateTransform.mul(rotateTransformX);
                side = 2;
            }
            if(xloc <= -2.199999 && yloc <= -2.249999 && signX > 0){
                rotateTransformZ.rotZ(Math.PI/4);
                translateTransform.mul(rotateTransformZ);
                signX = -1;
            }
            else if(xloc >= -2.0499992 && yloc <= -2.4999988 && signX < 0 && signY != 0){
                rotateTransformZ.rotZ(Math.PI/4);
                translateTransform.mul(rotateTransformZ);
                signY = 0;
                signZoom = 0;
                signX = -2;
            }
            else if(xloc >= 1.6999993 && yloc <= -2.249999 && signX < 0 && signY == 0 && signZoom == 0){
                rotateTransformZ.rotZ(Math.PI/16);
                translateTransform.mul(rotateTransformZ);
                signY = -1;
                signZoom = -1;
                signX = -1;
            }
            else if(xloc >= 1.9499991 && yloc >= -2.349999 && signX < 0 && signY < 0 && signZoom < 0){
                rotateTransformZ.rotZ(Math.PI/4);
                translateTransform.mul(rotateTransformZ);
                signX = 1;
                signY = -0.73f;
            }
            else if(xloc <= 1.6499994 && yloc >= -2.0444992 && signX > 0 && signY < 0 && signZoom < 0){
                xloc = -1.5499996f;
                yloc=-1.6999996f;
                signX=1.0f;
                signY=1.0f;
                signZoom=1.0f;
                Transform3D vpTranslation = new Transform3D();
                Vector3f translationVector = new Vector3f(0.0F, -1.2F, 6F);
                vpTranslation.setTranslation(translationVector);
                translateTransform = vpTranslation;

                rotateTransformX.rotX(3*Math.PI/2);
                translateTransform.mul(rotateTransformX);
            }
                xloc = xloc - signX*.05f;
                yloc = yloc - signY*.05f;
                zoom= zoom + signZoom*0.018f;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }

}
