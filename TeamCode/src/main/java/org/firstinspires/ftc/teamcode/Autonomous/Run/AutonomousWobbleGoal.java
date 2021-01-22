package org.firstinspires.ftc.teamcode.Autonomous.Run;

import org.firstinspires.ftc.teamcode.Hardware.Define;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import java.util.List;

@Autonomous(name="Run: AutonomousWobbleGoal", group="Run")
public class AutonomousWobbleGoal extends Define {
    
    double turn;
    boolean detection = false;
    
    private ElapsedTime	 runtime = new ElapsedTime();
    
    public void runOpMode() {
        
        initHardware();
        initVariable();
        
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
        
        
        // wait for the start button to be pressed.
        waitForStart();
        
        // check for button state transitions.
        if (opModeIsActive())  {
            
            target = heading();
            
            waitForStart();
            
            Movement(0,-20,0,1,1);
            
            Movement(100,0,0,1,1.25);
            
            detection = true;
        }
    }
    
    public void Movement(
        double forward, double horizontal, double rotation,
        double speed, double timeout) {
        int FLTarget;
        int FRTarget;
        int BLTarget;
        int BRTarget;
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            
            heading = heading();
            turn = (heading - target) * .02;
            
            // Determine new target position, and pass to motor controller
			/*
			FLTarget = (int)(FL.getCurrentPosition() + (int)((forward + horizontal - rotation) * COUNTS_PER_INCH) * - turn);
			FRTarget = (int)(FR.getCurrentPosition() + (int)(((forward - horizontal) + rotation) * COUNTS_PER_INCH) * + turn);
			BLTarget = (int)(BL.getCurrentPosition() + (int)(((-forward - horizontal) - rotation) * COUNTS_PER_INCH) * - turn);
			BRTarget = (int)(BR.getCurrentPosition() + (int)(((-forward + horizontal) + rotation) * COUNTS_PER_INCH) * + turn);
			*/
            FLTarget = (int)(FL.getCurrentPosition() + (int)((forward + horizontal) - rotation) * COUNTS_PER_INCH);
            FRTarget = (int)(FR.getCurrentPosition() + (int)((-forward + horizontal) - rotation) * COUNTS_PER_INCH);
            BLTarget = (int)(BL.getCurrentPosition() + (int)((-forward - horizontal) - rotation) * COUNTS_PER_INCH);
            BRTarget = (int)(BR.getCurrentPosition() + (int)((forward - horizontal) - rotation) * COUNTS_PER_INCH);
            
            FL.setTargetPosition(FLTarget);
            FR.setTargetPosition(FRTarget);
            BL.setTargetPosition(BLTarget);
            BR.setTargetPosition(BRTarget);
            
            // Turn On RUN_TO_POSITION
            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            // reset the timeout time and start motion.
            runtime.reset();
            FL.setPower(speed);
            FR.setPower(speed);
            BL.setPower(speed);
            BR.setPower(speed);
			/*
			FL.setPower(-power);
			FR.setPower(power);
			BL.setPower(-power);
			BR.setPower(power);
			*/
            
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                (runtime.seconds() < timeout) &&
                (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy())) {
                
                NormalizedRGBA colors = colorSensor.getNormalizedColors();
                
                telemetry.addLine().addData("alpha", "%.3f", colors.alpha);
                
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d",
                    FLTarget,  FRTarget,
                    BLTarget,  BRTarget);
                
                telemetry.addData("Path2",  "Running at %7d :%7d",
                    FL.getCurrentPosition(), FR.getCurrentPosition(),
                    BL.getCurrentPosition(), BR.getCurrentPosition());
                telemetry.update();
                
                heading = heading();
                turn = (heading - target) * .02;
                
                if(colors.alpha >= 0.05 && detection) {
                    FLTarget = 0;
                    FRTarget = 0;
                    BLTarget = 0;
                    BRTarget = 0;
                    
                    telemetry.addLine("color seen");
                    telemetry.addLine().addData("alpha", "%.3f", colors.alpha);
                    telemetry.update();
                    
                    break;
                }
				
				/*
				FL.setPower(turn);
				FR.setPower(-turn);
				BL.setPower(turn);
				BR.setPower(-turn);*/
            }
            
            // Stop all motion;
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
            
            // Turn off RUN_TO_POSITION
            FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            
            sleep((int)timeout*1000);   // optional pause after each move
        }
    }
    
    protected void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    protected double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}
