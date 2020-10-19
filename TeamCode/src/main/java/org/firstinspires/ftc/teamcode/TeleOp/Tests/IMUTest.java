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

    double lastHeading;
    double heading;
    double wheelHeading;

    double[] Orientation = {
            x,
            y,
            z,
            roll,
            pitch,
            yaw
    };

    protected void recomputeHeading() {
        org.firstinspires.ftc.robotcore.external.navigation.Orientation angles = Arsenal.IMU.getAngularOrientation();
        // This assumes the base of the REV hub is parallel to the ground plane.
        float heading_raw = angles.firstAngle;
        // This is the logic for detecting and correcting for the IMU discontinuity at +180degrees and -180degrees.
        if (lastHeading < -150.0 && heading_raw > 0.0) {
            // The previous raw IMU heading was negative and close to the discontinuity, and it is now positive. We have
            // gone through the discontinuity so we decrement the heading revolutions by 1 (we completed a negative
            // revolution). NOTE: the initial check protects from the case that the heading is near 0 and goes continuously
            // through 0, which is not the completion of a revolution.
            wheelHeading--;
        } else if (lastHeading > 150.0 && heading_raw < 0.0) {
            // The previous raw IMU heading was positive and close to the discontinuity, and it is now negative. We have
            // gone through the discontinuity so we increment the heading revolutions by 1 (we completed a positive
            // revolution). NOTE: the initial check protects from the case that the heading is near 0 and goes continuously
            // through 0, which is not the completion of a revolution.
            wheelHeading++;
        }
        heading = -((wheelHeading * 360.0) + heading_raw);
        lastHeading = heading_raw;
    }

}
