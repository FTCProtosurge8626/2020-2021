package org.firstinspires.ftc.teamcode.TeleOp.New;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.New.RunMethods;

@TeleOp(name = "Run: TeleOpNew", group = "TeleOP")
public class TeleOpNew extends RunMethods {
    
    @Override
    public void runOpMode() {
        initHardware();
        Controller control = new Controller();
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            //Heading.start();
            Movement.move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, control.hold(gamepad1.left_stick_button));
            Heading.Reorient(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.dpad_up, control.hold(gamepad1.left_stick_button));
            //move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_button);
        }
    }
}