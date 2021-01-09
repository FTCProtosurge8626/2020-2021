package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Run: TeleOP_Copy", group = "TeleOP")
public class RunTeleOp_Copy extends Define {
    
    @Override
    public void runOpMode() {
        
        initialize();
        
        while(opModeIsActive())
        {
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", target);
            telemetry.addData("Power: ", power);
            telemetry.update();
            compass(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
    
    private void movement(double forward, double horizontal, double rotation){
        
        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);
        
        double FLPower = (forward - horizontal - rotation);
        double FRPower = ((forward + horizontal) + rotation);
        double BLPower = ((-forward + horizontal) - rotation);
        double BRPower = ((-forward - horizontal) + rotation);
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    public void compass(boolean north, boolean south, boolean east, boolean west)
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
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            movement(0, 0, power);
            //if (north || south || east || west) break;
            telemetry.update();
        }
    }
    
    public void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}
