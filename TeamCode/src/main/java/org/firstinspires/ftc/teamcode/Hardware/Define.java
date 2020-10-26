package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class Define extends LinearOpMode {

    //Motors
    public DcMotor FR = null;  //Front Right
    public DcMotor FL = null;  //Front Left
    public DcMotor BR = null;  //Back Right
    public DcMotor BL = null;  //Back Left
    /*
    public DcMotor ?;  //Front Right
    public DcMotor ?;  //Front Left
    public DcMotor ?;  //Back Right
    public DcMotor ?;  //Back Left

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
    public BNO055IMU IMU = null; // The IMU, generally on the hub controlling the motors


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

        // May want to use RUN_USING_ENCODERS if encoders are installed.
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FL to run without encoders
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BR to run without encoders
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FR to run without encoders
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BL to run without encoders

        /*							  Servos						  */

        /*							  CServos						  */

        /*							  Sensors						  */
        //IMU
        IMU = hardwareMap.get(BNO055IMU.class, "IMU");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        IMU.initialize(parameters);
    }
}