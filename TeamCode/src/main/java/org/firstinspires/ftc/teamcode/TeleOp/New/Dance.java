package org.firstinspires.ftc.teamcode.TeleOp.New;

import org.firstinspires.ftc.teamcode.Hardware.RunMethods;

public class Dance extends RunMethods {
    
    void Random() {
        switch(getRandomInt(4)) {
            case 0:
                stepForward(500,.5);
                break;
            case 1:
                stepBackward(500,.5);
                break;
            case 2:
                stepRight(500,.5);
                break;
            case 3:
                stepLeft(500,.5);
                break;
            default:
                break;
        }
    }
    
    int getRandomInt(int max) {
        return (int)Math.floor(Math.random() * max);
    }
    
    void stepForward(int time, double speed) {
        movePower(speed,speed,speed,speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
        movePower(-speed,-speed,-speed,-speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
    }
    
    void stepBackward(int time, double speed) {
        movePower(-speed,-speed,-speed,-speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
        movePower(speed,speed,speed,speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
    }
    
    void stepRight(int time, double speed) {
        movePower(speed,-speed,-speed,speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
        movePower(-speed,speed,speed,-speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
    }
    
    void stepLeft(int time, double speed) {
        movePower(-speed,speed,speed,-speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
        movePower(speed,-speed,-speed,speed);
        sleep(time);
        movePower(0,0,0,0);
        sleep(500);
    }
    
}