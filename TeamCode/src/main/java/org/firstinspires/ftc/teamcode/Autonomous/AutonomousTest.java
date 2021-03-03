package org.firstinspires.ftc.teamcode.Autonomous;

import com.google.gson.JsonObject;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Define;

import java.io.FileWriter;

//@Disabled
@Autonomous(name="Run: AutonomousTest", group="Run")
public class AutonomousTest extends Define {
    
    private ElapsedTime	 currentPos = new ElapsedTime();
    private ElapsedTime	 detectionTime = new ElapsedTime();
    private ElapsedTime	 headingTime = new ElapsedTime();
    
    static final double	 COUNTS_PER_MOTOR_REV	= 1440 ;	// eg: TETRIX Motor Encoder
    static final double	 DRIVE_GEAR_REDUCTION	= 2.0 ;	 // This is < 1.0 if geared UP
    static final double	 WHEEL_DIAMETER_INCHES   = 4.0 ;	 // For figuring circumference
    static final double	 COUNTS_PER_INCH		 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    
    private String configFileName="AutonomousOrientation.txt";
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        runEncoders();
        
        target = heading();
        
        waitForStart();
        
        detectionTime.reset();
        
        //A
		/*
		movement(0,-1,0,1.5);
		
		movement(1,0,90,1.5);
		
		wobbleGoal();
		
		movement(-1,0,0,.55);
		
		//shooter(2000);
		
		movement(-1,0,0,.55);
		
		//Intake.setPower(1);
		
		movementPower(.25,.25,.25,.25);
		
		sleep(1000);
		
		movement(1,0,0,1);
		
		movementPower(0,0,0,0);
		
		//Intake.setPower(0);
		
		
		//B
		movement(0,-1,0,1.5);
		
		movement(0,1,0,.1);
		
		movement(1,0,0,2.2);
		
		movement(0,1,0,.5);
		
		wobbleGoal();
		
		movement(-1,0,0,.65);
		
		movement(0,1,0,.35);
		
		//shooter(2000);
		
		movement(-1,0,0,.55);
		
		//Intake.setPower(1);
		
		movementPower(.25,.25,.25,.25);
		
		sleep(1000);
		
		movement(1,0,0,1.15);
		
		movementPower(0,0,0,0);
		
		//Intake.setPower(0);
		*/
        //C
        movement(0,-1,0,1.5);
        
        movement(0,1,0,.1);
        
        movement(1,0,0,2.65);
        
        movement(0,-1,90,.25);
        
        //wobbleGoal();
        
        movement(0,1,90,.25);
        
        movement(1,0,90,0);
        
        movement(1,0,90,.3);
        
        movement(0,-1,0,1.4);
        
        //shooter(2000);
        
        movement(-1,0,0,.65);
        
        movement(0,1,0,.35);
        
        movement(-1,0,0,.55);
        
        //Intake.setPower(1);
        
        movementPower(.25,.25,.25,.25);
        
        sleep(1000);
        
        movement(1,0,0,1.15);
        
        movementPower(0,0,0,0);
        
        //Intake.setPower(0);
		/*
		try {
		OutputStreamWriter Orientation = new OutputStreamWriter(context.openFileOutput(configFileName, Context.MODE_PRIVATE));

		// write each configuration parameter as a string on its own line
		Orientation.write(heading());

		Orientation.close();
	  }
	  catch (IOException e) {
		telemetry.addData("Exception", "Configuration file write failed: ");
	  }*/
        double Orientation = heading();
        JsonObject Heading = new JsonObject();
        Heading.add("Orientation", Orientation);
        try(FileWriter AutonomousValues = new FileWriter("Heading.json")) {
        
        }
        /*
        Heading : [
            {
                "Orientation" :Orientation
            }
            ]*/
    
    }
    
    private void movement(double forward, double horizontal, double direction, double targetPos) {
        int FLTarget;
        int FRTarget;
        int BLTarget;
        int BRTarget;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            //compass(direction);
            
            // Determine new target position, and pass to motor controller
            FLTarget = (int)(FL.getCurrentPosition() + (int)-(forward + horizontal) * COUNTS_PER_INCH);
            FRTarget = (int)(FR.getCurrentPosition() + (int)-(forward - horizontal) * COUNTS_PER_INCH);
            BLTarget = (int)(BL.getCurrentPosition() + (int)-(forward + horizontal) * COUNTS_PER_INCH);
            BRTarget = (int)(BR.getCurrentPosition() + (int)-(forward - horizontal) * COUNTS_PER_INCH);
            
            FL.setTargetPosition(FLTarget);
            FR.setTargetPosition(FRTarget);
            BL.setTargetPosition(BLTarget);
            BR.setTargetPosition(BRTarget);
            
            runToPosition();
            
            // reset the timeout time and start motion.
            currentPos.reset();
            FL.setPower(1);
            FR.setPower(1);
            BL.setPower(1);
            BR.setPower(1);
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
            while ((opModeIsActive() && currentPos.seconds() < targetPos &&
                (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy())) && !isStopRequested()) {
					/*
					// Display it for the driver.
					telemetry.addData("Path1",  "Running to %7d :%7d",
					FLTarget,  FRTarget,
					BLTarget,  BRTarget);
					
					telemetry.addData("Path2",  "Running at %7d :%7d",
					FL.getCurrentPosition(), FR.getCurrentPosition(),
					BL.getCurrentPosition(), BR.getCurrentPosition());
					
					telemetry.addData("Turn: ", turn);
					telemetry.update();
					*/
            }
            
            // Stop all motion;
            movementPower(0, 0, 0, 0);
            
            // Turn off RUN_TO_POSITION
            runWithoutEncoders();
            
            compass(direction);
            sleep(500);   // optional pause after each move
        }
    }
    
    private void compass(double angle) {
        target = angle;
        turn = (target - heading()) * .02;
        headingTime.reset();
        while (((turn > 0.06 || turn < -0.06) && headingTime.seconds() < 2) && !isStopRequested()) {
            turn = (target - heading()) * .02;
            movementPower(-turn, turn, turn, -turn);
			/*
			telemetry.addData("Turn: ", turn);
			telemetry.update();*/
            //if (turn > 0.5 || turn < -0.5) break;
        }
        movementPower(0,0,0,0);
        target = heading();
    }
    
    private void shooter(int sleep) {
        shootPower(.705,-.705);
        Intake.setPower(1);
        sleep(sleep);
        Intake.setPower(0);
        shootPower(0,0);
    }
    
    private void wobbleGoal() {
        WobbleL.setPosition(0.1);
        WobbleA.setPower(.5);
        sleep(300);
        WobbleA.setPower(0);
        sleep(500);
        WobbleC.setPosition(1);
        sleep(500);
        WobbleA.setPower(-.5);
        sleep(300);
    }
}
