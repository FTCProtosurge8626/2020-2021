package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Run: TeleOpSingle", group = "TeleOp")
public class TeleOpSingle extends TeleOpMethods {
    
    public void runOpMode() {
        
        initTeleOp();
        
        waitForStart();
        
        moveHeading = setHeading();
        
        while(opModeIsActive()) {
            //movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_button, gamepad1.right_stick_button);
            compass(gamepad1.dpad_up);
			/*intake(gamepad1.left_trigger, gamepad1.y);
			shooter(gamepad1.right_bumper);
			//powerShot(gamepad1.x);
			//indexer(gamepad1.right_trigger);
			wobbleArm(gamepad1.left_bumper);
			wobbleClamp(gamepad1.a);
			wobbleLock(gamepad1.dpad_left);*/
        }
    }
}