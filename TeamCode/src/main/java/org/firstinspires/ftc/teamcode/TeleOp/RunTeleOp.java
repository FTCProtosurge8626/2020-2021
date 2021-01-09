package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeleOp.Methods.Compass;
import org.firstinspires.ftc.teamcode.TeleOp.Methods.Movement;
@TeleOp(name = "Run: TeleOP", group = "TeleOP")
public class RunTeleOp extends LinearOpMode {
    
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
    
    public BNO055IMU IMU;	//	The	IMU, generally on the hub controlling the motors
    
    public double heading;
    public double target;
    public double power;
    
    public Orientation angles;
    
    
    public void runOpMode() {
        
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
        
        target = heading();
        
        while(opModeIsActive())
        {Movement.movement(1,1,1);
            telemetry.addData("Heading: ", heading);
            telemetry.addData("Target: ", target);
            telemetry.addData("Power: ", power);
            telemetry.update();
            compass(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
    
    private void movement(double forward, double horizontal, double rotation){
        
        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);
        
        double FLPower = (forward - horizontal - rotation);
        double FRPower = ((forward + horizontal) + rotation);
        double BLPower = ((-forward + horizontal) - rotation);
        double BRPower = ((-forward - horizontal) + rotation);
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    public void compass(boolean north, boolean south, boolean east, boolean west)
    {
        if (north) target = 0;
        if (south) target = 180;
        if (east) target =  -90;
        if (west) target = 90;
        if(north || south || east || west)
            heading = heading();
        power = (heading - target) * .02;
        while (power > 0.25 || power < -0.25)
        {
            heading = heading();
            power = (heading - target) * .02;
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            movement(0, 0, power);
            //if (north || south || east || west) break;
            telemetry.update();
        }
    }
    
    public void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    public double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}