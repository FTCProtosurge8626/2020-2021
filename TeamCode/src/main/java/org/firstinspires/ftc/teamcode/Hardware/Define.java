package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class Define extends LinearOpMode {
    
    //Motors
    public static DcMotor FL;  //Front Left
    public static DcMotor FR;  //Front Right
    public static DcMotor BL;  //Back Left
    public static DcMotor BR;  //Back Right
    
    
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
    
    public BNO055IMU IMU;	//	The	IMU, generally on the hub controlling the motors
    
    public double heading = heading();
    public double target = heading();
    public double power = (heading - target) * .02;
    
    public Orientation angles;
    
    protected void initVariable() {
        Orientation angles;
        angles = IMU.getAngularOrientation();
    }
    
    protected void initHardware() {
        
        //Sets motor names to be the same as Control Hub's configured names
        FL = hardwareMap.dcMotor.get("FrontLeft");	//FL = FrontLeftMotor
        FR = hardwareMap.dcMotor.get("FrontRight");	//FR = FrontRightMotor
        BL = hardwareMap.dcMotor.get("BackRight");	//BL = BackLeftMotor
        BR = hardwareMap.dcMotor.get("BackLeft");	//BR = BackRightMotor
        
        //LeftShooter = hardwareMap.dcMotor.get("LeftShooter");	//Shooter = BackRightMotor
        //RightShooter = hardwareMap.dcMotor.get("RightShooter");	//Shooter = BackRightMotor
        
        LockShooter = hardwareMap.servo.get("LockShooter");	//Shooter = BackRightMotor
        
        IMU = hardwareMap.get(BNO055IMU.class, "IMU");
        
        //Sets motor's direction
        FL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FL Direction
        FR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets FR Direction
        BL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BL Direction
        BR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BR Direction
        
        //LeftShooter.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BL Direction
        //RightShooter.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BR Direction
        
        //Turn on
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
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