package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import org.firstinspires.ftc.teamcode.Hardware.Define;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Run: Movement", group = "Run")
public class Movement extends Define {
    
    double power;
    
    @Override
    public void runOpMode() {
        
        initHardware();
        initVariable();
		
		/*while(opModeIsActive()){
			movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
			telemetry.addData("Heading: ", heading);
			telemetry.addData("Move Target: ", moveTarget);
			telemetry.addData("Power: ", power);
			telemetry.update();
		}*/
    }
    
    public static void movement(double forward, double horizontal, double rotation){
        
        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);
        
        double FLPower = (forward - horizontal + rotation);
        double FRPower = ((forward + horizontal) - rotation);
        double BLPower = ((-forward + horizontal) + rotation);
        double BRPower = ((-forward - horizontal) - rotation);
		
		/*if (forward == 0 || horizontal == 0)
		{
			moveTarget = heading();
		}
		heading = heading();
		while (forward != 0 || horizontal != 0 && moveTarget != heading)
		{
			heading = heading();
			telemetry.addData("Heading: ", heading);
			telemetry.addData("Move Target: ", moveTarget);
			telemetry.addData("Power: ", power);
			telemetry.update();
			power = (heading - moveTarget);
			movementPower(FLPower, FRPower, BLPower, BRPower);
			movementPower(power, -power, power, -power);
			if(gamepad1.x) break;
		}*/
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
        
    }
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}
