package org.firstinspires.ftc.teamcode.Hardware.New;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class RunMethods extends HardwareNew {
    
    public static void movePower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    
    public static void moveForward() {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    
    public static void moveBackward() {
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    
    public static void runToPosition() {
        // Turn On RUN_TO_POSITION
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    
    public static void runEncoders() {
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
    
    public static void runWithoutEncoders() {
        
        //Turns on the wheels' encoders
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FrontLeftMotor to run with encoder
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets FrontRightMotor to run with encoder
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BackLeftMotor to run with encoder
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);	//Sets BackRightMotor to run with encoder
        
    }
    
    public static void setPositions() {
    
    }
    
    public void runOpMode() {
    
    }
}
