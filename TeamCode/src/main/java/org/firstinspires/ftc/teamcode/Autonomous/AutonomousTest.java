package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Define;

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
    
    //private String configFileName="AutonomousOrientation.txt";
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        movementReverse();
        
        //runEncoders();
        
        runWithoutEncoders();
        
        target = heading();
        
        waitForStart();
        
        detectionTime.reset();
        
        //A
        //movement(1,0,0,5);
        while(ColorS.alpha() <= 1500 && !isStopRequested()){
            movementPower(-1,1,-1,1);
        }
        
        while(ColorS.alpha() <= 1500 && !isStopRequested()){
            movementPower(.45,.45,.45,.45);
        }
        movementPower(0,0,0,0);
        
        //movement(1,0,0,5);
        //movement(1,0,-90,1.25);
		/*
		movement(1,0,0,2);
		
		movement(0,1,0,1.175);
		
		//shooter(2000);
		
		//Intake.setPower(1);
		
		movement(-1,0,0,.75);
		
		movementPower(.25,.25,.25,.25);
		
		sleep(1000);
		
		//Intake.setPower(0);
		
		movement(1,0,-90,1.1);
		
		movement(1,0,0,.25);
		
		//wobbleGoal();
		
		//B
		
		movement(0,-1,0,1.25);
		
		movement(1,0,0,2);
		
		movement(0,1,0,1.175);
		
		//shooter(2000);
		
		//Intake.setPower(1);
		
		movement(-1,0,0,.75);
		
		movementPower(.25,.25,.25,.25);
		
		sleep(1000);
		
		//Intake.setPower(0);
		
		movement(1,0,0,1.25);
		
		//wobbleGoal();
		
		//C
		
		movement(0,-1,0,1.25);
		
		movement(1,0,0,2);
		
		movement(0,1,0,1.175);
		
		//shooter(2000);
		
		//Intake.setPower(1);
		
		movement(-1,0,0,.75);
		
		movementPower(.25,.25,.25,.25);
		
		sleep(1000);
		
		//Intake.setPower(0);
		
		movement(1,0,0,1.75);
		
		//wobbleGoal();
		
		movement(0,1,0,1.175);
		
		movement(-1,0,0,.6);
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
		/*double Orientation = heading();
		JsonObject Heading = new JsonObject();
		Heading.put("Orientation", Orientation);
		try(FileWriter AutonomousValues = new FileWriter("Heading.json")) {
			AutonomousValues.write(Heading.toString());
			AutonomousValues.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    
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
            FLTarget = (int)(FL.getCurrentPosition() + (int)-(-forward + horizontal) * COUNTS_PER_INCH);
            FRTarget = (int)(FR.getCurrentPosition() + (int)-(-forward - horizontal) * COUNTS_PER_INCH);
            BLTarget = (int)(BL.getCurrentPosition() + (int)-(-forward + horizontal) * COUNTS_PER_INCH);
            BRTarget = (int)(BR.getCurrentPosition() + (int)-(-forward - horizontal) * COUNTS_PER_INCH);
            
            FL.setTargetPosition(FLTarget);
            FR.setTargetPosition(FRTarget);
            BL.setTargetPosition(BLTarget);
            BR.setTargetPosition(BRTarget);
            
            runToPosition();
            
            // reset the timeout time and start motion.
            currentPos.reset();
            FL.setPower(.75);
            FR.setPower(.75);
            BL.setPower(.75);
            BR.setPower(.75);
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
					*/
                telemetry.addData("Target: ", target);
                telemetry.addData("Heading: ", heading());
                telemetry.addData("Turn: ", turn);
                telemetry.update();
                
            }
            
            // Stop all motion;
            movementPower(0, 0, 0, 0);
            
            // Turn off RUN_TO_POSITION
            runWithoutEncoders();
            
            compass(direction);
        }
    }
    
    private void compass(double angle) {
        turn = ((heading() - angle) * .02);
        headingTime.reset();
        while (((turn > .35 || turn < -.35) && headingTime.seconds() < 3) && !isStopRequested()) {
            turn = (heading() - angle) * .02;
            movementPower(-turn, turn, turn, -turn);
            
            telemetry.addData("Turn: ", turn);
            telemetry.addData("Heading: ", heading());
            telemetry.addData("Angle: ", angle);
            telemetry.update();
            //if (turn > 0.5 || turn < -0.5) break;
        }
        movementPower(0,0,0,0);
        target = heading();
        sleep(500);
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
        sleep(500);
        WobbleA.setPower(0);
        sleep(500);
        WobbleC.setPosition(1);
        sleep(500);
        WobbleA.setPower(-.5);
        sleep(500);
    }
}
