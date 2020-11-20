package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import org.firstinspires.ftc.teamcode.Hardware.Define;

public class Movement extends Define {
    
    @Override
    public void runOpMode() {
        
        waitForStart();
        while (opModeIsActive()) {
            
            double leftStickY = gamepad1.left_stick_y;
            double leftStickX = gamepad1.left_stick_x;
            double rightStickY = gamepad1.right_stick_y;
            double rightStickX = gamepad1.right_stick_x;
            
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            
        }
    }
    
    public void movement(double forward, double horizontal, double rotation){
        
        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);
        
        double FLPower = (forward - horizontal + rotation);
        double FRPower = ((forward + horizontal) - rotation);
        double BLPower = ((-forward + horizontal) + rotation);
        double BRPower = ((-forward - horizontal) - rotation);
        
        setPower(FLPower, FRPower, BLPower, BRPower);
    }
}
