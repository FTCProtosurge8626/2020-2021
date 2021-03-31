package org.firstinspires.ftc.teamcode.TeleOp.New;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.RunMethods;

@TeleOp(name = "Run: TeleOpNew", group = "TeleOP")
public class TeleOpNew extends RunMethods {
    
    @Override
    public void runOpMode() {
        initHardware();
        Controller control = new Controller();
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            Controller.controllerConfig(gamepad1,gamepad1);
            //Heading.start();
            Movement.move(Controller.LeftStickY, Controller.LeftStickX, Controller.RightStickX, control.hold(gamepad1.left_stick_button));
            Heading.Reorient(Controller.LeftStickY, Controller.LeftStickX, Controller.RightStickX, gamepad1.dpad_up, control.hold(gamepad1.left_stick_button));
            //move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_button);
        }
    }
}