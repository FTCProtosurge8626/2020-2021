package org.firstinspires.ftc.teamcode.TeleOp.New;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.HardwareNew;

class Controller extends HardwareNew {
    
    boolean pressSwitch;
    int loop;
    
    static double LeftStickY;
    static double LeftStickX;
    static double RightStickX;
    
    static void controllerConfig(Gamepad gamepad1, Gamepad gamepad2){
        LeftStickY = gamepad1.left_stick_y + gamepad2.left_stick_y;
        LeftStickX = gamepad1.left_stick_x + gamepad2.left_stick_x;
        RightStickX = gamepad1.right_stick_x + gamepad2.right_stick_x;
    }
    
    public boolean hold(boolean press) {
        if(!press){
            loop = 0;
        }
        if(press && loop < 1) {
            if(pressSwitch) {
                loop = 1;
                pressSwitch = false;
                return true;
            }
            
            if(!pressSwitch) {
                loop = 1;
                pressSwitch = true;
                return false;
            }
        }
        return !pressSwitch;
    }
    
    public void runOpMode() {
    
    }
}
