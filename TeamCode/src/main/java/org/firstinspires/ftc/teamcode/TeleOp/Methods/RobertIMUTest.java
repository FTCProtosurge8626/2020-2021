package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import	com.qualcomm.hardware.bosch.BNO055IMU;
import	com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import	com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import	com.qualcomm.robotcore.hardware.DcMotor;
import	org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import	org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import	org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import	org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@TeleOp(name	=	"RobertIMUTest",	group	=	"")
public	class	RobertIMUTest	extends	LinearOpMode	{
    private	BNO055IMU	IMU;
    private	DcMotor	FrontRight;
    private	DcMotor	FrontLeft;
    private	DcMotor	BackRight;
    private	DcMotor	BackLeft;
	/*private	DcMotor	Launch1;
	private	DcMotor	Launch2;
	private	DcMotor	Pusher;*/
    
    public Orientation	angles;
    public double target;
    public double heading;
    public boolean compass = false;
    
    @Override
    public	void runOpMode()	{
        BNO055IMU.Parameters imuParameters;
        
        IMU	=	hardwareMap.get(BNO055IMU.class,	"IMU");
        FrontRight	=	hardwareMap.dcMotor.get("FrontRight");
        FrontLeft	=	hardwareMap.dcMotor.get("FrontLeft");
        BackRight	=	hardwareMap.dcMotor.get("BackRight");
        BackLeft	=	hardwareMap.dcMotor.get("BackLeft");
		/*Launch1	=	hardwareMap.dcMotor.get("Launch1");
		Launch2	=	hardwareMap.dcMotor.get("Launch2");
		Pusher	=	hardwareMap.dcMotor.get("Pusher");*/
        
        imuParameters	=	new	BNO055IMU.Parameters();
        imuParameters.angleUnit	=	BNO055IMU.AngleUnit.DEGREES;
        imuParameters.loggingEnabled	=	true;
        IMU.initialize(imuParameters);
        angles	=	IMU.getAngularOrientation(AxesReference.INTRINSIC,	AxesOrder.ZYX,	AngleUnit.DEGREES);
        telemetry.update();
        waitForStart();
        if	(opModeIsActive())	{
            imuStats();
            while (opModeIsActive()) {
                Movement(gamepad1.left_stick_y,	gamepad1.left_stick_x,	gamepad1.right_stick_x);
                imuStats();
                Compass();
                while (gamepad1.right_bumper) {
                    Movement((gamepad1.left_stick_y*0.5),(gamepad1.left_stick_x*0.5),(gamepad1.right_stick_x*0.25));
                    imuStats();
                    Compass();
                }
            }
        }
    }
    
    //Gets	numbers	and	data	from	IMU	and	outputs	it
    public	void	imuStats()	{
        Orientation angles	=	IMU.getAngularOrientation(AxesReference.INTRINSIC,	AxesOrder.ZYX,	AngleUnit.DEGREES);
        heading = angles.firstAngle;
        telemetry.addData("Z",	angles.firstAngle);
		/*telemetry.addData("Y",	angles.secondAngle);
		telemetry.addData("X",	angles.thirdAngle);*/
        telemetry.update();
    }
    
    //Gets	numbers	and	inputs	from	the	gamepad	and	uses	it	to	drive	the	robot
    public	void	Movement(double	forward, double	horizontal,	double	rotation)	{
        FrontRight.setPower((-forward-horizontal)-rotation);
        FrontLeft.setPower((forward-horizontal)-rotation);
        BackRight.setPower((-forward+horizontal)-rotation);
        BackLeft.setPower((forward+horizontal)-rotation);
    }
    
    //Marcus' Compass setup but better.
    public void Compass() {
        if(gamepad1.dpad_up)
        {
            target = 0.0;
            while (heading >= target + 30 || heading <= target - 30)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 90));
                if(gamepad1.x) break; imuStats();
            }
            while (heading < target + 30 || heading > target - 30)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 30));
                if(gamepad1.x || (heading < target + 0.25 && heading > target - 0.25)) Movement(0.0, 0.0, 0.0); imuStats(); break;
            }
        }
        if(gamepad1.dpad_down)
        {
            target = 180.0;
            while (heading >= target + 30 || heading <= target - 30)
            {
                imuStats();
                Movement(0, 0, ((heading + target) / 90));
                if(gamepad1.x) break; imuStats();
            }
            while (heading < target + 30 || heading > target - 30)
            {
                imuStats();
                Movement(0, 0, ((heading + target) / 30));
                if(gamepad1.x || (heading < target + 0.25 && heading > target - 0.25)) Movement(0.0, 0.0, 0.0); imuStats(); break;
            }
        }
        if(gamepad1.dpad_right)
        {
            target = -90.0;
            while (heading >= target + 20 || heading <= target - 20)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 180.0));
                if(gamepad1.x) break;
            }
            while (heading < target + 20 || heading > target - 20)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 20));
                if(gamepad1.x) break;
            }
        }
        if(gamepad1.dpad_left)
        {
            target = 90.0;
            while (heading >= target + 20 || heading <= target - 20)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 180.0));
                if(gamepad1.x) break;
            }
            while (heading < target + 20 || heading > target - 20)
            {
                imuStats();
                Movement(0, 0, ((heading - target) / 20));
                if(gamepad1.x) break;
            }
        }
    }
		
	/*private	void Launch() {
		if	(gamepad1.right_trigger	> 0)	{
			Launch1.setPower(1);
			Launch2.setPower(1);
		}
		if	(gamepad1.right_trigger	==	2)	{
			Pusher.setPower(0.5);
		}
	}*/
}