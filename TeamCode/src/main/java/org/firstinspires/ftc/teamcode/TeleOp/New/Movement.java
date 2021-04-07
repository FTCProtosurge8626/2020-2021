package org.firstinspires.ftc.teamcode.TeleOp.New;

import org.firstinspires.ftc.teamcode.Hardware.RunMethods;

public class Movement extends RunMethods {
    
    static double moveTarget;
    static double moveTurn;
    
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
    
    static void moveReorient(double forward, double sideways, double rotation, boolean inverse) {
        
        if(rotation != 0)
            moveTarget = Heading.heading();
        
        moveTurn = (Heading.heading() - moveTarget) * .1;
        
        move(forward, sideways, moveTurn + rotation, inverse);
        
    }
}
