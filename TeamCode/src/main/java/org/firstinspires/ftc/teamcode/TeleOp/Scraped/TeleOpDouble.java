package org.firstinspires.ftc.teamcode.TeleOp.Scraped;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Run: TeleOpDouble", group = "TeleOp")
public class TeleOpDouble extends TeleOpMethods {
    
    //TeleOpMovement Movement = new TeleOpMovement();
    boolean hold = false;
    
    public void runOpMode() {
        initTeleOp();
        
        waitForStart();
        
        moveHeading = setHeading();
        
        //controls();
        
        while(opModeIsActive()) {
            
            //controls();
            //Movement.movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_button, gamepad1.right_stick_button);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.left_stick_button, gamepad1.right_stick_button);
            //movement(gamepad1.left_stick_button, gamepad1.right_stick_button);
            compass(gamepad1.dpad_up);
			/*intake(gamepad1.left_trigger, gamepad1.y)
			shooter(gamepad2.right_bumper);
			wobbleArm(gamepad2.x);
			wobbleClamp(gamepad2.a);
			wobbleLock(gamepad2.dpad_left);
			mode = true;*/
        }
    }
}