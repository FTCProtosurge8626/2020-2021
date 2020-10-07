package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class Define extends OpMode{

    //Motors
    public DcMotor FR = null;  //Front Right
    public DcMotor FL = null;  //Front Left
    public DcMotor BR = null;  //Back Right
    public DcMotor BL = null;  //Back Left
    /*
    public DcMotor ? = null;  //Front Right
    public DcMotor ? = null;  //Front Left
    public DcMotor ? = null;  //Back Right
    public DcMotor ? = null;  //Back Left

    //Servos
    public Servo ? = null;  //
    public Servo ? = null;  //
    public Servo ? = null;  //
    public Servo ? = null;  //

    //Control Servos
    public CServo ? = null;  //
    public CServo ? = null;  //
    public CServo ? = null;  //
    public CServo ? = null;  //
    */

    @Override
    public void init() {

        /*                              Motors                          */

        //Sets motor names to be the same as Control Hub's configured names
        FR = hardwareMap.dcMotor.get("FrontRight");    //FR = FrontRightMotor
        FL = hardwareMap.dcMotor.get("FrontLeft");    //FL = FrontLeftMotor
        BR = hardwareMap.dcMotor.get("backLeft");    //BR = BackRightMotor
        BL = hardwareMap.dcMotor.get("backRight");    //BL = BackLeftMotor

        //Sets motor's direction
        FR.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets FR Direction
        FL.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets FL Direction
        BR.setDirection(DcMotorSimple.Direction.FORWARD);   //Sets BR Direction
        BL.setDirection(DcMotorSimple.Direction.REVERSE);   //Sets BL Direction


        // May want to use RUN_USING_ENCODERS if encoders are installed.
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);    //Sets FR to run without encoders
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);    //Sets FL to run without encoders
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);    //Sets BR to run without encoders
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);    //Sets BL to run without encoders

        //Sets motor's power to 0
        FR.setPower(0);    //FR power = 0
        FL.setPower(0);    //FL power = 0
        BR.setPower(0);    //BR power = 0
        BL.setPower(0);    //BL power = 0

        /*                              Servos                          */



        /*                              CServos                          */


    }

    @Override
    public void loop() {

    }
}
