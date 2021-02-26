package org.firstinspires.ftc.teamcode.Hardware;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class Define extends LinearOpMode {
    
    //Movement Motors
    public DcMotor FL;  //Front Left Wheel
    public DcMotor FR;  //Front Right Wheel
    public DcMotor BL;  //Back Left Wheel
    public DcMotor BR;  //Back Right Wheel
    
    //Ring Shooter
    public DcMotor LeftS;  //Left Ring Shooter
    public DcMotor RightS;  //Right Ring Shooter
    
    //Robot Intake
    public DcMotor Intake;  //Intake System
    
    //Wobble Goal Grabber
    public DcMotor WobbleA;  //Wobble Goal Grabber Arm
    public Servo WobbleC;  //Wobble Goal Grabber Clamp
    public Servo WobbleL;  //Wobble Goal Grabber Lock
    
    //Encoder Variables
    public static final double	 COUNTS_PER_MOTOR_REV	= 1440 ;	// eg: TETRIX Motor Encoder
    public static final double	 DRIVE_GEAR_REDUCTION	= 2.0 ;	 // This is < 1.0 if geared UP
    public static final double	 WHEEL_DIAMETER_INCHES   = 4.0 ;	 // For figuring circumference
    public static final double	 COUNTS_PER_INCH		 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    
    //Rev IMU
    public BNO055IMU IMU;	//	The	IMU, generally on the hub controlling the motors
    
    //IMU Variables
    public double target;
    public double turn;
    
    public Orientation angles;
    
    public ColorSensor ColorS;	// Hardware Device Object
    
    //Camrea Variables
    public boolean detection = false;
    
    protected void initVariables() {
        target = 0;
        turn = 0;
        
        detection = false;
    }
    
    protected void initHardware() {
        
        //Sets motor names to be the same as Control Hub's configured names
        FL = hardwareMap.dcMotor.get("FrontLeft");	//FL = FrontLeftMotor
        FR = hardwareMap.dcMotor.get("FrontRight");	//FR = FrontRightMotor
        BL = hardwareMap.dcMotor.get("BackRight");	//BL = BackLeftMotor
        BR = hardwareMap.dcMotor.get("BackLeft");	//BR = BackRightMotor
        
        //Sets motor's direction
        FL.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets FrontLeftMotor's Direction
        FR.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FrontRightMotor's Direction
        BL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BackLeftMotor's Direction
        BR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BackRightMotor's Direction
        
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        LeftS = hardwareMap.dcMotor.get("LeftShooter");		//LS = LeftShooter
        RightS = hardwareMap.dcMotor.get("RightShooter");	//RS = RightShooter
        
        LeftS.setDirection(DcMotorSimple.Direction.FORWARD);	//Sets LeftShooter's Direction
        RightS.setDirection(DcMotorSimple.Direction.FORWARD);	//Sets RightShooter's Direction
        
        Intake = hardwareMap.dcMotor.get("Intake");		//Intake = Intake
        
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);	//Sets Intake's Direction
        
        WobbleA = hardwareMap.dcMotor.get("WobbleArm");	//WobbleA = WobbleArm
        WobbleC = hardwareMap.servo.get("WobbleClamp");	//WobbleC = WobbleClamp
        WobbleL = hardwareMap.servo.get("WobbleLock");	//WobbleL = WobbleLock
        
        WobbleA.setDirection(DcMotorSimple.Direction.FORWARD);	//Sets WobbleArm's Direction
        
        WobbleC.setPosition(0);
        WobbleL.setPosition(0);
        
        IMU = hardwareMap.get(BNO055IMU.class, "IMU"); //IMU = IMU
        
        //Initializes IMU
        BNO055IMU.Parameters parameters;
        parameters = new BNO055IMU.Parameters();
        
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        IMU.initialize(parameters);
        
        // get a reference to our ColorSensor object.
        ColorS = hardwareMap.get(ColorSensor.class, "LineDetector");
        
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};
        
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;
        
        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        
        // bLedOn represents the state of the LED.
        boolean bLedOn = true;
        
        // Set the LED in the beginning
        ColorS.enableLed(bLedOn);
        
        // convert the RGB values to HSV values.
        Color.RGBToHSV(ColorS.red() * 8, ColorS.green() * 8, ColorS.blue() * 8, hsvValues);
    }
    
    protected void runToPosition() {
        // Turn On RUN_TO_POSITION
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    
    protected void runEncoders() {
        //Sets motors to run with encoders
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);	//Resets FrontLeftMotor's encoder
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);	//Resets FrontRightMotor's encoder
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);	//Resets BackLeftMotor's encoder
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);	//Resets BackRightMotor's encoder
        
        //Turns on the wheels' encoders
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);	//Sets FrontLeftMotor to run with encoder
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);	//Sets FrontRightMotor to run with encoder
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);	//Sets BackLeftMotor to run with encoder
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);	//Sets BackRightMotor to run with encoder
    }
    
    protected void runWithoutEncoders() {
        
        //Turns on the wheels' encoders
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FrontLeftMotor to run with encoder
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FrontRightMotor to run with encoder
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BackLeftMotor to run with encoder
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BackRightMotor to run with encoder
        
    }
    
    protected void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    
    protected void shootPower(double RightSPower, double LeftSPower) {
        LeftS.setPower(RightSPower);
        RightS.setPower(LeftSPower);
    }
    
    protected double heading() {
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}