package org.firstinspires.ftc.teamcode.Autonomous.Methods;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.lang.annotation.Target;
import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@Autonomous(name = "Movement", group = "Autonomous")
public class Movement extends Define {
    
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private static final String VUFORIA_KEY = "AUH3W7//////AAABmRPeTi20x0n1vDw0WNnEaB85+/2MLZ8wvrAecldHMZYx0zw8T/JNFB7k8UfpsZGqwPNVgsWRHKlPk29EFCNgAZo9e+aqmobPLwzHEr5dm1EdFPQizLMKES9UdOSIdNb/Sx2cO8oI5iURlnGtF267JIi+oqlYZawFr0ERoqA9lmlRZpE4ak83vkqYn+2iFHXJoBvxZCvOu2O6toN7tO5LhnItem0I4iFrQNw9378YiVyIP4I7nE5XtlYHKmhiBdyTXkGyvXTUUI/lzpQVxU0Z9ynL0c4t09v54i/qQ1racrZG1CrrVMLVT8m7+L8+0dUu3Zv7zLl/G3MpvnufDs2KoA5VRjta0gnmVJoUME2npfZW";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    
    String rings = "None";
    
    private double power;
    
    private ElapsedTime	 runtime = new ElapsedTime();
    private double detectionTime = 5;
    
    static final double	 COUNTS_PER_MOTOR_REV	= 1440 ;	// eg: TETRIX Motor Encoder
    static final double	 DRIVE_GEAR_REDUCTION	= 2.0 ;	 // This is < 1.0 if geared UP
    static final double	 WHEEL_DIAMETER_INCHES   = 4.0 ;	 // For figuring circumference
    static final double	 COUNTS_PER_INCH		 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    
    public void runOpMode() {
        
        initHardware();
        initVariable();
        initVuforia();
        initTfod();
        
        if (tfod != null) {
            tfod.activate();
        }
        
        waitForStart();
        
        while (rings == "None" || detectionTime <= 5) {
            if (tfod != null) {
                
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    int i = 0;
                }
            }
        }
        if(opModeIsActive()){
            if(rings == "Single") {
                telemetry.addData("Single", rings);
                telemetry.update();
                
                encoderDrive(0, 1, 0, 1, 1);
                
            } else if (rings == "Quad") {
                telemetry.addData("Quad", rings);
                telemetry.update();
                
                
                encoderDrive(0, -1, 0, 1, 1);
            }else if (rings == "None") {
                telemetry.addData("None", rings);
                telemetry.update();
                
                
                encoderDrive(1, 0, 0, 1, 1);
            }
        }
        
        if (tfod != null) {
            tfod.shutdown();
        }
        
    }
    
    public void encoderDrive(double forward, double horizontal, double rotation, double speed, double timeout) {
        
        int FLTarget;
        int FRTarget;
        int BLTarget;
        int BRTarget;
        
        target = heading();
        updateHeading();
        
        if(opModeIsActive()) {
            //while (power > 0.25 || power < -0.25)
            //{
            updateHeading();
            //Determine new target position, and pass to motor controller
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
                (FL.isBusy() && FR.isBusy()) && BL.isBusy() && BR.isBusy()) {
                // Display it for the driver.
                // * (power * .02)
                updateHeading();
                telemetry.addData("Path1",  "Running to %7d :%7d", FLTarget,  FRTarget, BLTarget,  BRTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                    FL.getCurrentPosition(),
                    FR.getCurrentPosition(),
                    BL.getCurrentPosition(),
                    BR.getCurrentPosition());
                telemetry.addData("Power: ", power);
                telemetry.addData("Target: ", target);
                telemetry.addData("Speed: ", Math.abs(speed) * (power * .02));
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
            
            target = heading();
            
            //  sleep(250);   // optional pause after each move
        }
    }
    public void servoMove(double position, double power, double sleep) {
    
    }
    
    private void initVuforia() {
        
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "RingCam");
        
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    
    private void initTfod() {
        int tfodMonitorViewId =
            hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new
            TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.4;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters,
            vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET,
            LABEL_FIRST_ELEMENT,
            LABEL_SECOND_ELEMENT);
    }
    
    public void updateHeading()
    {
        heading = heading();
        power = heading - target * .05;
    }
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
    
    public void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
}