package org.firstinspires.ftc.teamcode.TeleOp.New;

import org.firstinspires.ftc.teamcode.Hardware.New.RunMethods;

public class Movement extends RunMethods {
    
    static void move(double forward, double sideways, double rotation, boolean inverse) {
        
        if(!inverse) {
            moveForward();
            rotation = -rotation;
        } else {
            moveBackward();
        }
        
        double FLPower = forward - sideways + rotation;
        double FRPower = forward + sideways - rotation;
        double BLPower = forward + sideways + rotation;
        double BRPower = forward - sideways - rotation;
        
        movePower(FLPower, FRPower, BLPower, BRPower);
    }
}
