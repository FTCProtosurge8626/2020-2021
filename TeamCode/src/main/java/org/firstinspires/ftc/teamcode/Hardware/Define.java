package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class Define extends LinearOpMode {
    
    //Motors
    public DcMotor FL;  //Front Left
    public DcMotor FR;  //Front Right
    public DcMotor BL;  //Back Left
    public DcMotor BR;  //Back Right
    
    
    //public DcMotor LeftShooter;  //Front Left
    //public DcMotor RightShooter;  //Front Left
	/*public DcMotor ?;  //Front Right
	public DcMotor ?;  //Back Left
	public DcMotor ?;  //Back Right
	
	//Servos
	*/public Servo LockShooter;  //
	/*public Servo ?;  //
	public Servo ?;  //
	public Servo ?;  //
	
	//Control Servos
	public CServo ?;  //
	public CServo ?;  //
	public CServo ?;  //
	public CServo ?;  //
	*/
    //Sensors
    
    public NormalizedColorSensor colorSensor;
    public View relativeLayout;
    
    public BNO055IMU IMU;	//	The	IMU, generally on the hub controlling the motors
    
    public double heading;
    public double target;
    public double moveTarget;
    
    public Orientation angles;
    
    public static final double	 COUNTS_PER_MOTOR_REV	= 1440 ;	// eg: TETRIX Motor Encoder
    public static final double	 DRIVE_GEAR_REDUCTION	= 2.0 ;	 // This is < 1.0 if geared UP
    public static final double	 WHEEL_DIAMETER_INCHES   = 4.0 ;	 // For figuring circumference
    public static final double	 COUNTS_PER_INCH		 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    
    
    // values is a reference to the hsvValues array.
    public float[] hsvValues = new float[3];
    public final float values[] = hsvValues;
    
    // bPrevState and bCurrState keep track of the previous and current state of the button
    public boolean bPrevState = false;
    public boolean bCurrState = false;
    
    protected void initVariable() {
        
        heading = heading();
        
    }
    
    protected void initHardware() {
        
        //Sets motor names to be the same as Control Hub's configured names
        FL = hardwareMap.dcMotor.get("FrontLeft");	//FL = FrontLeftMotor
        FR = hardwareMap.dcMotor.get("FrontRight");	//FR = FrontRightMotor
        BL = hardwareMap.dcMotor.get("BackRight");	//BL = BackLeftMotor
        BR = hardwareMap.dcMotor.get("BackLeft");	//BR = BackRightMotor
        
        
        
        IMU = hardwareMap.get(BNO055IMU.class, "IMU");
        
        //Sets motor's direction
        FL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FL Direction
        FR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets FR Direction
        BL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BL Direction
        BR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BR Direction
        
        //LeftShooter.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BL Direction
        //RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BR Direction
        
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //Turn on
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        //LeftShooter = hardwareMap.dcMotor.get("LeftShooter");	//Shooter = BackRightMotor
        //RightShooter = hardwareMap.dcMotor.get("RightShooter");	//Shooter = BackRightMotor
        
        LockShooter = hardwareMap.servo.get("LockShooter");	//Shooter = BackRightMotor
        
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "csense");
        
        BNO055IMU.Parameters parameters;
        parameters = new BNO055IMU.Parameters();
        
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        IMU.initialize(parameters);
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