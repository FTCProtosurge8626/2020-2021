package org.firstinspires.ftc.teamcode.Hardware.New;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class HardwareNew extends LinearOpMode {
    
    public static String breakpoint = "Not Hit";
    
    public static DcMotor FL, FR, BL, BR;
    public static BNO055IMU IMU;
    
    public void initHardware() {
        config();
        setDirections();
        imuSetup();
    }
    
    public void config() {
        FL = hardwareMap.dcMotor.get("FrontLeft");
        FR = hardwareMap.dcMotor.get("FrontRight");
        BL = hardwareMap.dcMotor.get("BackLeft");
        BR = hardwareMap.dcMotor.get("BackRight");
        
        IMU = hardwareMap.get(BNO055IMU.class, "IMU"); //IMU = IMU
    }
    
    void imuSetup(){
        BNO055IMU.Parameters parameters;
        parameters = new BNO055IMU.Parameters();
        
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        
        IMU.initialize(parameters);
    }
    
    public void setDirections() {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}