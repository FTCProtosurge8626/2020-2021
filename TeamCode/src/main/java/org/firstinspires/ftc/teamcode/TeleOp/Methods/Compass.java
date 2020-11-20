package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Compass", group = "TeleOP")
public class Compass extends Define {
    
    @Override
    public void runOpMode() {
        
        heading = 0;
        
        boolean north = gamepad1.dpad_up;
        boolean south = gamepad1.dpad_down;
        boolean east = gamepad1.dpad_right;
        boolean west = gamepad1.dpad_left;
        
        waitForStart();
        while (opModeIsActive()) {
            
            compass(north, south, east, west);
            
        }
    }
    
    private void compass(boolean north, boolean south, boolean east, boolean west)
    {
        if (north)
        {
            target = 90;
            double power = heading - target * .05;
            setPower(power, -power, power, -power);
        }
    }
}