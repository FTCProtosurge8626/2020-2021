package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "IMUTest", group = "TeleOp")
 public class IMUTest extends Define {

    double ticsPerInchForward;
    double ticsPerInchSideways;

    double lastHeading;
    double heading;
    double wheelHeading;
    double expectedHeading;
    double kp;

    @Override
    public void runOpMode(){

        super.runOpMode();

        double leftStickY = gamepad1.left_stick_y;
        double leftStickX = gamepad1.left_stick_x;
        double rightStickY = gamepad1.right_stick_y;
        double rightStickX = gamepad1.right_stick_x;

        boolean flyWheel = gamepad2.right_bumper;
        boolean indegServo = gamepad2.a;
        boolean wobbleGrabber = gamepad2.y;
        boolean disenganger = gamepad2.b;
        boolean ringScorer = gamepad2.x;

        waitForStart();

        while(opModeIsActive()) {
            movement(leftStickY, leftStickX, rightStickX);
            recomputeHeading();
        }
    }

    private void movement(double forward, double horizontal, double rotation) {

        //double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);

        double FLPower = (forward + horizontal + rotation);
        double FRPower = ((forward - horizontal) + rotation);
        double BLPower = ((forward - horizontal) - rotation);
        double BRPower = ((forward + horizontal) - rotation);

        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
        //setPower(FLPower, FRPower, BLPower, BRPower);
    }

    public void setPower(double rightFrontPower, double rightBackPower, double leftFrontPower, double leftBackPower) {
        FL.setPower(rightFrontPower);
        FR.setPower(rightBackPower);
        BL.setPower(leftFrontPower);
        BR.setPower(leftBackPower);
    }

    protected void recomputeHeading() {
        Orientation angles = IMU.getAngularOrientation();

        double heading_raw = angles.firstAngle;

        if (lastHeading < -150.0 && heading_raw > 0.0) {
            wheelHeading--;
        } else if (lastHeading > 150.0 && heading_raw < 0.0) {
            wheelHeading++;
        }
        heading = -((wheelHeading * 360.0) + heading_raw);
        lastHeading = heading_raw;
    }
}