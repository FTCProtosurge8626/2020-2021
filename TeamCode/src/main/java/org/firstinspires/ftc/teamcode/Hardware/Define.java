package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class Define extends LinearOpMode {
    
    //Motors
    public DcMotor FL;  //Front Left
    public DcMotor FR;  //Front Right
    public DcMotor BL;  //Back Left
    public DcMotor BR;  //Back Right
    /*
    public DcMotor ?;  //Front Left
    public DcMotor ?;  //Front Right
    public DcMotor ?;  //Back Left
    public DcMotor ?;  //Back Right

    //Servos
    public Servo ?;  //
    public Servo ?;  //
    public Servo ?;  //
    public Servo ?;  //

    //Control Servos
    public CServo ?;  //
    public CServo ?;  //
    public CServo ?;  //
    public CServo ?;  //
    */
    //Sensors
    public	BNO055IMU	imu;	//	The	IMU,	generally	on	the	hub	controlling	the	motors
    public	Orientation	angles;
    
    public	double	target	=	angles.firstAngle;
    public	double	heading;
    
    
    @Override
    public void runOpMode() {
        
        /*							  Motors						  */
        
        //Sets motor names to be the same as Control Hub's configured names
        FL = hardwareMap.dcMotor.get("FrontLeft");	//FL = FrontLeftMotor
        FR = hardwareMap.dcMotor.get("FrontRight");	//FR = FrontRightMotor
        BL = hardwareMap.dcMotor.get("BackRight");	//BL = BackLeftMotor
        BR = hardwareMap.dcMotor.get("BackLeft");	//BR = BackRightMotor
    
        //Sets motor's direction
        FL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FL Direction
        FR.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FR Direction
        BL.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BL Direction
        BR.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BR Direction
    
        // Sets motors to use Encoders
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FL to run without encoders
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BR to run without encoders
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FR to run without encoders
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BL to run without encoders
        
        /*							  Servos						  */
        
        /*							  CServos						  */
        
        /*							  Sensors						  */
        //IMU
        imu = hardwareMap.get(BNO055IMU.class, "IMU");
    
        BNO055IMU.Parameters	imuParameters	=	new	BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu.initialize(imuParameters);
        
        while(opModeIsActive())
        {
            heading = target;
        }
    }
    
    public void setPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
}