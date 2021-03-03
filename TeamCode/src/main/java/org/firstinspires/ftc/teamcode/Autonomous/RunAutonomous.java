package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Hardware.Define;

import java.util.List;

@Autonomous(name="Run: Autonomous", group="Run")
public class RunAutonomous extends Define {
    
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private static final String VUFORIA_KEY = "AUH3W7//////AAABmRPeTi20x0n1vDw0WNnEaB85+/2MLZ8wvrAecldHMZYx0zw8T/JNFB7k8UfpsZGqwPNVgsWRHKlPk29EFCNgAZo9e+aqmobPLwzHEr5dm1EdFPQizLMKES9UdOSIdNb/Sx2cO8oI5iURlnGtF267JIi+oqlYZawFr0ERoqA9lmlRZpE4ak83vkqYn+2iFHXJoBvxZCvOu2O6toN7tO5LhnItem0I4iFrQNw9378YiVyIP4I7nE5XtlYHKmhiBdyTXkGyvXTUUI/lzpQVxU0Z9ynL0c4t09v54i/qQ1racrZG1CrrVMLVT8m7+L8+0dUu3Zv7zLl/G3MpvnufDs2KoA5VRjta0gnmVJoUME2npfZW";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    
    private String rings = "None";
    
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
        
        initVuforia();
        initTfod();
        
        target = heading();
        
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2, 1);
        }
        
        waitForStart();
        
        detectionTime.reset();
        
        while (rings == "None" && detectionTime.seconds() < 1.5 && !isStopRequested()) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                
                if (updatedRecognitions != null) {
                    int i = 0;
                    
                    for (Recognition recognition : updatedRecognitions) {
                        rings = recognition.getLabel();
                    }
                }
            }
        }
        if(opModeIsActive()){
            if(rings == "None") {
                
                telemetry.addData("Placement: ", "A");
                telemetry.update();
                
                //A
                movement(0,-1,0,1.25);
                
                movement(1,0,90,1.5);
                
                movement(-1,0,90,.75);
                
                movement(0,1,90,.5);
                
                movement(0,0,0,0);
                
            } else if (rings == "Single") {
                
                telemetry.addData("Placement: ", "B");
                telemetry.update();
                
                //B
                movement(0,-1,0,1.25);
                
                movement(0,1,0,.1);
                
                movement(1,0,0,2.4);
                
                movement(0,1,0,.25);
                
                movement(-1,0,0,.7);
                
            } else if (rings == "Quad") {
                
                telemetry.addData("Placement: ", "C");
                telemetry.update();
                
                //C
                movement(0,-1,0,1.25);
                
                movement(0,1,0,.1);
                
                movement(1,0,90,2.65);
                
                movement(1,0,90,.3);
                
                movement(0,-1,90,1.4);
                
                movement(0,0,0,0);
            }
        }
        
        if (tfod != null) {
            tfod.shutdown();
        }
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
        correctHeading();
        headingTime.reset();
        while (((turn > 0.06 || turn < -0.06) && headingTime.seconds() < 2) && !isStopRequested())
        {
            correctHeading();
            movementPower(-turn, turn, turn, -turn);
			/*
			telemetry.addData("Turn: ", turn);
			telemetry.update();*/
            //if (turn > 0.5 || turn < -0.5) break;
        }
        movementPower(0,0,0,0);
        target = heading();
    }
    
    public void correctHeading() {
        turn = (target - heading()) * .02;
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
    
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "RingCam");
        
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        
        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    
    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
	/*
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
	}*/
}
