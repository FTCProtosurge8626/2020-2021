package org.firstinspires.ftc.teamcode.TeleOp.New;

import org.firstinspires.ftc.teamcode.Hardware.New.HardwareNew;

class Controller extends HardwareNew {
    
    boolean pressSwitch;
    int loop;
    
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
