package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware.Define;

public class IMUTest extends Define {

    Define Arsenal = new Define();

    double x;
    double y;
    double z;
    double roll;
    double pitch;
    double yaw;

    double ticsPerInchForward;
    double ticsPerInchSideways;

    double lastHeading;
    double heading;
    double wheelHeading;
    double expectedHeading;
    double kp;

    double[] orientation = {
            x,
            y,
            z,
            roll,
            pitch,
            yaw
    };

    double leftStickY = gamepad1.left_stick_y;
    double leftStickX = gamepad1.left_stick_x;
    double rightStickY = gamepad1.right_stick_y;
    double rightStickX = gamepad1.right_stick_x;

    protected void recomputeHeading() {
        Orientation angles = Arsenal.IMU.getAngularOrientation();

        double heading_raw = angles.firstAngle;

        if (lastHeading < -150.0 && heading_raw > 0.0) {
            wheelHeading--;
        } else if (lastHeading > 150.0 && heading_raw < 0.0) {
            wheelHeading++;
        }
        heading = -((wheelHeading * 360.0) + heading_raw);
        lastHeading = heading_raw;
    }

    private void movement(double forward, double horizontal, double rotation) {

        double max = Math.abs(forward) + Math.abs(horizontal) + Math.abs(rotation);

        double FLPower = forward + horizontal + rotation;
        double FRPower = forward + horizontal + rotation;
        double BLPower = forward + horizontal + rotation;
        double BRPower = forward + horizontal + rotation;

        setPower(FLPower, FRPower, BLPower, BRPower);

    }

    public void setPower(double rightFrontPower, double rightBackPower, double leftFrontPower, double leftBackPower) {
        Arsenal.FL.setPower(rightFrontPower);
        Arsenal.FR.setPower(rightBackPower);
        Arsenal.BL.setPower(leftFrontPower);
        Arsenal.BR.setPower(leftBackPower);
    }

    private double forwardTics() {
        // NOTE:, to go forward all 4 drive motors are going forward.
        return Arsenal.FL.getCurrentPosition() + Arsenal.FR.getCurrentPosition() + Arsenal.BL.getCurrentPosition() + Arsenal.BR.getCurrentPosition();
    }

    private double sidewaysTics() {
        // NOTE:, to go sideways, the front right and left rear drive motors are reversed.
        return (Arsenal.BR.getCurrentPosition() + Arsenal.FL.getCurrentPosition()) - (Arsenal.FR.getCurrentPosition() + Arsenal.BL.getCurrentPosition());
    }

    final protected void move(double inches, double degrees, double maxPower,
                              double accelMin, double accelTics, double decelMin, double decelTics) {
        double sin = Math.sin(degrees / 180 * Math.PI);
        double cos = Math.cos(degrees / 180 * Math.PI);
        // get the forward and sideways components of the move. To give the greatest accuracy of the move we will use the one
        // that is the longest (greatest number of encoder tics) to decide when the move has completed.
        double forwardMaxSpeed = Math.abs(cos);
        double forwardInches = cos * inches;
        double forwardDirectionMult = (forwardInches > 0.0) ? 1.0 : -1.0;

        double sidewaysMaxSpeed = Math.abs(sin);
        double sidewaysInches = sin * inches;
        double sidewaysDirectionMult = (sidewaysInches > 0) ? 1.0 : -1.0;

        double target_tics = (forwardMaxSpeed >= sidewaysMaxSpeed) ?
                ticsPerInchForward * forwardInches * forwardDirectionMult :
                ticsPerInchSideways * sidewaysInches * sidewaysDirectionMult;
            double current_tics = (forwardMaxSpeed >= sidewaysMaxSpeed) ?
                    forwardTics() * forwardDirectionMult : sidewaysTics() * sidewaysDirectionMult;
            double speed_mult = powerAccelDecel(current_tics, target_tics, maxPower, accelMin, accelTics, decelMin, decelTics);
            recomputeHeading();
            double error = expectedHeading - heading;
            movement(forwardMaxSpeed * speed_mult * forwardDirectionMult,
                    sidewaysMaxSpeed * speed_mult * sidewaysDirectionMult,
                    kp * error);

            setStrafe(forwardMaxSpeed * speed_mult * forwardDirectionMult,
                    sidewaysMaxSpeed * speed_mult * sidewaysDirectionMult,
                    kp * error);

            setTurn(forwardMaxSpeed * speed_mult * forwardDirectionMult,
                    sidewaysMaxSpeed * speed_mult * sidewaysDirectionMult,
                    kp * error);
        }

    private double powerAccelDecel(double currentTics, double targetTics, double maxPower,
                                   double accelMin, double accelTics, double decelMin, double decelTics) {
        if (currentTics <= 0) {
            return accelMin;
        }
        if (currentTics >= targetTics) {
            return 0.0;
        }
        double mtrPower = maxPower;
        if (currentTics < accelTics) {
            double accelPower = accelMin + ((1 - accelMin) * (currentTics / accelTics));
            if (accelPower < mtrPower) {
                mtrPower = accelPower;
            }
        }
        if (currentTics > targetTics - decelTics) {
            double decelPower = decelMin + ((1 - decelMin) * ((targetTics - currentTics) / decelTics));
            if (decelPower < mtrPower) {
                mtrPower = decelPower;
            }
        }
        return mtrPower;
    }


}
