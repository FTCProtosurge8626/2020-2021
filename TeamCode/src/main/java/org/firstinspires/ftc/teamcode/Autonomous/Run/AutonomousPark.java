package org.firstinspires.ftc.teamcode.Autonomous.Run;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@Autonomous(name="Run: AutonomousPark", group="Run")
public class AutonomousPark extends Define {
    
    private ElapsedTime	 runtime = new ElapsedTime();
    
    static final double	 COUNTS_PER_MOTOR_REV	= 1440 ;	// eg: TETRIX Motor Encoder
    static final double	 DRIVE_GEAR_REDUCTION	= 2.0 ;	 // This is < 1.0 if geared UP
    static final double	 WHEEL_DIAMETER_INCHES   = 4.0 ;	 // For figuring circumference
    static final double	 COUNTS_PER_INCH		 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        
        target = heading();
        
        waitForStart();
        
        Movement(0,-25,0,1,1);
        
        Movement(150,0,0,1.5,1);
        
    }
    
    public void Movement(
        double forward, double horizontal, double rotation,
        double timeout, double speed) {
        int FLTarget;
        int FRTarget;
        int BLTarget;
        int BRTarget;
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            
            // Determine new target position, and pass to motor controller
            FLTarget = FL.getCurrentPosition() + (int)((forward + horizontal - rotation) * COUNTS_PER_INCH);
            FRTarget = FR.getCurrentPosition() + (int)(((forward - horizontal) + rotation) * COUNTS_PER_INCH);
            BLTarget = BL.getCurrentPosition() + (int)(((-forward - horizontal) - rotation) * COUNTS_PER_INCH);
            BRTarget = BR.getCurrentPosition() + (int)(((-forward + horizontal) + rotation) * COUNTS_PER_INCH);
            
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
            FL.setPower(Math.abs(speed));
            FR.setPower(Math.abs(speed));
            BL.setPower(Math.abs(speed));
            BR.setPower(Math.abs(speed));
            
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                (runtime.seconds() < timeout) &&
                (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy())) {
                
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d",
                    FLTarget,  FRTarget,
                    BLTarget,  BRTarget);
                
                telemetry.addData("Path2",  "Running at %7d :%7d",
                    FL.getCurrentPosition(), FR.getCurrentPosition(),
                    BL.getCurrentPosition(), BR.getCurrentPosition());
                telemetry.update();
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
