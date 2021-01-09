package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Compass", group = "TeleOP")
public class Compass extends Define {
    
    org.firstinspires.ftc.teamcode.TeleOp.Methods.Movement Movement = new Movement();
    
    double power;
    
    @Override
    public void runOpMode() {
        
        initHardware();
        initVariable();
        
    }
    
    public static void compass(boolean north, boolean south, boolean east, boolean west)
    {
        if (north) target = 0;
        if (south) target = 180;
        if (east) target =  -90;
        if (west) target = 90;
        if(north || south || east || west)
            heading = heading();
        power = (heading - target) * .02;
        while (power > 0.25 || power < -0.25)
        {
            heading = heading();
            power = (heading - target) * .02;
            Movement.movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            Movement.movement(0, 0, power);
            //if (north || south || east || west) break;
            telemetry.update();
        }
    }
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}