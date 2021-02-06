package org.firstinspires.ftc.teamcode.Autonomous.Run;

import org.firstinspires.ftc.teamcode.Hardware.Define;
import java.lang.annotation.Target;
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

@Autonomous(name="Run: AutonomousIMU", group="Run")
public class AutonomousIMU extends Define {
    
    private ElapsedTime	 runtime = new ElapsedTime();
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        
        // wait for the start button to be pressed.
        waitForStart();
        
        // check for button state transitions.
        while (opModeIsActive())  {
            
            target = heading();
            
            waitForStart();
            
            //movement(20,false,1,1);
            
            //compass(0);
            
            //movement(-20,false,1,1);
            
            compass(90);
		
		
		/*
		movement(0,-20,0,1,1);
		
		movement(100,0,0,1,1.25);
		
		movement(0,15,0,1,1);
		
		compass(180);
		
		movement(15,0,0,1,1);*/
        }
    }
    
    private void movement(double distance, boolean strafe,
                          double speed, double timeout) {
        int FLTarget;
        int FRTarget;
        int BLTarget;
        int BRTarget;
        target = heading();
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            
            if (strafe) {
                // Determine new target position, and pass to motor controller
                FLTarget = (int)(FL.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
                FRTarget = (int)(FR.getCurrentPosition() + (int)(-distance) * COUNTS_PER_INCH);
                BLTarget = (int)(BL.getCurrentPosition() + (int)(-distance) * COUNTS_PER_INCH);
                BRTarget = (int)(BR.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
            } else {
                // Determine new target position, and pass to motor controller
                FLTarget = (int)(FL.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
                FRTarget = (int)(FR.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
                BLTarget = (int)(BL.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
                BRTarget = (int)(BR.getCurrentPosition() + (int)(distance) * COUNTS_PER_INCH);
            }
            
            FL.setTargetPosition(FLTarget);
            FR.setTargetPosition(FRTarget);
            BL.setTargetPosition(BLTarget);
            BR.setTargetPosition(BRTarget);
            
            runToPosition();
            
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
                
                telemetry.addLine().addData("Alpha: ", ColorS.alpha());
                telemetry.addLine().addData("Turn",turn);
                
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d",
                    FLTarget,  FRTarget,
                    BLTarget,  BRTarget);
                
                telemetry.addData("Path2",  "Running at %7d :%7d",
                    FL.getCurrentPosition(), FR.getCurrentPosition(),
                    BL.getCurrentPosition(), BR.getCurrentPosition());
                telemetry.update();
                
                if(ColorS.alpha() >= 0.05 && detection) {
                    FLTarget = 0;
                    FRTarget = 0;
                    BLTarget = 0;
                    BRTarget = 0;
                    
                    telemetry.addLine("[Color Seen]");
                    telemetry.addLine().addData("Alpha: ", ColorS.alpha());
                    telemetry.update();
                    
                    break;
                }
            }
            
            // Stop all motion;
            movementPower(0, 0, 0, 0);
            
            // Turn off RUN_TO_POSITION
            runEncoders();
            
            sleep(500);   // optional pause after each move
        }
    }
    
    private void compass(double angle) {
        target = angle;
        correctHeading();
        while (turn > 0.5 || turn < -0.5)
        {
            correctHeading();
            movementPower(-turn, turn, turn, -turn);
            //if (turn > 0.5 || turn < -0.5) break;
        }
        movementPower(0,0,0,0);
        target = heading();
    }
    
    public void correctHeading() {
        heading = heading();
        turn = (heading - target) * .02;
    }
    
    private void intakeUp(int sleep) {
        Intake.setPower(1);
        sleep(sleep);
        Intake.setPower(0);
    }
    
    private void intakeDown(int sleep) {
        Intake.setPower(-1);
        sleep(sleep);
        Intake.setPower(0);
    }
    
    private void armUp(int sleep) {
        WobbleA.setPower(1);
        sleep(sleep);
        WobbleA.setPower(0);
    }
    
    private void armDown(int sleep) {
        WobbleA.setPower(-1);
        sleep(sleep);
        WobbleA.setPower(0);
    }
    
    private void clampUp() {
        WobbleC.setPosition(1);
    }
    
    private void clampDown() {
        WobbleC.setPosition(0);
    }
    
    private void LockUp() {
        WobbleL.setPosition(1);
    }
    
    private void lockDown() {
        WobbleL.setPosition(0);
    }
    
    public void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    
    public void shootPower(double RightSPower, double LeftSPower) {
        LeftS.setPower(RightSPower);
        RightS.setPower(LeftSPower);
    }
    
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}