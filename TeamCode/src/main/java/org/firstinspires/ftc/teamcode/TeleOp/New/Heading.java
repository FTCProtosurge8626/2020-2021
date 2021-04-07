package org.firstinspires.ftc.teamcode.TeleOp.New;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware.RunMethods;

class Heading extends RunMethods {
    
    static double target;
    static double turn;
    
    static void Reorient(double forward, double sideways, double rotation, boolean north, boolean inverse) {
        Controller control = new Controller();
        if(north) turn = (heading() - target) * .02;
        while (turn > .35 || turn < -.35) {
            turn = (heading() - target) * .02;
            if (Controller.LeftStickY != 0 || Controller.LeftStickX != 0 || Controller.RightStickX != 0) {
                turn = 0;
                break;
            }
            Movement.move(0, 0, turn, inverse);
        }
    }
    
    static double heading() {
        Orientation angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}