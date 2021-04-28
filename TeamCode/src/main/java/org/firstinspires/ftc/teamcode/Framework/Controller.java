package org.firstinspires.ftc.teamcode.Framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

class Controller extends LinearOpMode {
    
    boolean pressSwitch;
    boolean loop;
    
    static double LeftStickY;
    static double LeftStickX;
    static double RightStickX;
    
    static void controllerConfig(LinearOpMode opmode){
        LeftStickY = opmode.gamepad1.left_stick_y + opmode.gamepad2.left_stick_y;
        LeftStickX = opmode.gamepad1.left_stick_x + opmode.gamepad2.left_stick_x;
        RightStickX = opmode.gamepad1.right_stick_x + opmode.gamepad2.right_stick_x;
    }
    /*
    boolean hold(boolean press) {
        Thing = loop;
        Thing2 = pressSwitch;
        if(press && loop) {
            if(pressSwitch) {
                loop = false;
                pressSwitch = false;
            } else {
                loop = false;
                pressSwitch = true;
            }
        } else if(!press){
            loop = true;
        }
        this.pressSwitch = pressSwitch;
        this.loop = loop;
        return pressSwitch;
    }*/
    boolean hold(boolean press) {
        if(press && loop) {
            if(pressSwitch) {
                loop = false;
                pressSwitch = false;
            } else {
                loop = false;
                pressSwitch = true;
            }
        } else if(!press){
            loop = true;
        }
        this.pressSwitch = pressSwitch;
        this.loop = loop;
        return pressSwitch;
    }
    
    
    //Gamepad gamepad1, Gamepad gamepad2
    static void keyBind(LinearOpMode opmode, Controller Back) {
        //opmode.telemetry.clear();
        if(Back.hold(opmode.gamepad1.back)) {
            opmode.telemetry.addData("Move Forward: ", LeftStickY);
            opmode.telemetry.addData("Move Sideways: ", LeftStickX);
            opmode.telemetry.addData("Move Turn: ", RightStickX);
            //telemetry.update();
        }
        opmode.telemetry.update();
    }
    
    public void runOpMode() {
    
    }
}
