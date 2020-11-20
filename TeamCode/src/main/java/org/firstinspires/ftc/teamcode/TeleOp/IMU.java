package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "IMU", group = "TeleOP")
public class IMU extends Define {
    
    // The IMU sensor object
    BNO055IMU imu;
    
    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;
    
    @Override
    public void runOpMode() {
        
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
    
        imu = hardwareMap.get(BNO055IMU.class, "IMU");
        imu.initialize(parameters);
        
        waitForStart();
        
        while(opModeIsActive()) {
    
            double heading = angles.firstAngle;
    
            double forward = 0;
            double turn = heading * 0.02;
    
            driveHeading(gamepad1.left_stick_y, 10, 5);
            driveHeading(gamepad1.left_stick_y, -45, 3);
            
            telemetry.addData("Status", "Running");
            telemetry.addData("Angles", angles.toString());
            telemetry.addData("Angle 1", angles.firstAngle);
            telemetry.update();
        }
    }
    
    public void driveHeading(double forward, double target, double timeout) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        
        while(opModeIsActive()) {
            if(timer.seconds() > timeout) break; {
                double heading = getHeading();
                double turn = (heading-target) * 0.02;
                FL.setPower(forward + turn);
                FR.setPower(forward - turn);
                BL.setPower(forward + turn);
                BR.setPower(forward - turn);
            }
        }
    }
    
    double getHeading() {
        angles = imu.getAngularOrientation();
        return angles.firstAngle;
    }
    
}
