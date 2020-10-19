package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.Define;
import org.firstinspires.ftc.teamcode.TeleOp.Tests.IMUTest;

import java.lang.reflect.Array;

@TeleOp(name="Movement", group="TeleOp")
abstract public class Movement extends IMUTest {

    Define Arsenal = new Define();
    abstract public void init();

    @Override
    public void loop() {

        movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

    }

    private void movement(double forward, double horizontal, double rotation) {

        double FLPower = forward + horizontal + rotation;
        double FRPower = forward + horizontal + rotation;
        double BLPower = forward + horizontal + rotation;
        double BRPower = forward + horizontal + rotation;

        double[] wheelPowers = {
                Math.abs(FLPower),
                Math.abs(FRPower),
                Math.abs(BLPower),
                Math.abs(BRPower)
        };

        double totalPower = wheelPowers[3];
        if (totalPower > 1) {
            FLPower /= totalPower;
            FRPower /= totalPower;
            BLPower /= totalPower;
            BRPower /= totalPower;
        }

        Arsenal.FL.setPower(FLPower);
        Arsenal.FR.setPower(FRPower);
        Arsenal.BL.setPower(BLPower);
        Arsenal.BR.setPower(BRPower);

    }
}
