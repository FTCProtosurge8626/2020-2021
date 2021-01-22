package org.firstinspires.ftc.teamcode.TeleOp.Run;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Run: TeleOp", group = "TeleOp")
public class RunTeleOp extends Define {
    
    double power;
    double moveTarget;
    
    public void runOpMode() {
        
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
        
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
        
        initHardware();
        initVariable();
        
        target = heading();
        
        while(opModeIsActive())
        {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            if(colors.alpha > 0.05) {
                compass(true, false, false, false);
                telemetry.addLine().addData("alpha", "%.3f", colors.alpha);
                telemetry.addLine(Boolean.toString(colors.alpha > 0.05));
                telemetry.update();
            }
            compass(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_bumper);
        }
    }
    
    private void movement(double forward, double horizontal, double rotation, boolean cripple){
        
        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);
        
        double FLPower = cripple ? ((-forward + horizontal) - rotation)/2 : ((-forward + horizontal) - rotation);
        double FRPower = cripple ? ((forward + horizontal) - rotation)/2 : ((forward + horizontal) - rotation);
        double BLPower = cripple ? ((forward - horizontal) - rotation)/2 : ((forward - horizontal) - rotation);
        double BRPower = cripple ? ((-forward - horizontal) - rotation)/2 : ((-forward - horizontal) - rotation);
		
		/*if(rotation != 0) {
			moveTarget = heading;
			movementPower(FLPower, FRPower, BLPower, BRPower);
		}
		if (forward != 0 || horizontal != 0) {
			double movePower = (heading - moveTarget) * .02;
			//movementPower(movePower, -movePower, movePower, -movePower);
			movementPower(FLPower, FRPower, BLPower, BRPower);
		}*/
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
            //movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0 ||
                gamepad2.left_stick_y != 0 || gamepad2.left_stick_x != 0 || gamepad2.right_stick_x != 0) power = 0;
            movement(0, 0, power,  gamepad1.right_bumper);
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
