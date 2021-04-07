package org.firstinspires.ftc.teamcode.TeleOp.New;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.RunMethods;

@TeleOp(name = "Run: TeleOpNew", group = "TeleOP")
public class TeleOpNew extends RunMethods {
    
    @Override
    public void runOpMode() {
        initHardware();
        Controller Control = new Controller();
        Dance Dance = new Dance();
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            Controller.controllerConfig(gamepad1,gamepad1);
            Movement.move(Controller.LeftStickY, Controller.LeftStickX, Controller.RightStickX, Control.hold(gamepad1.left_stick_button));
            Heading.Reorient(Controller.LeftStickY, Controller.LeftStickX, Controller.RightStickX, gamepad1.dpad_up, Control.hold(gamepad1.left_stick_button));
            Dance.Random();
        }
    }
}