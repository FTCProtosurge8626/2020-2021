package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware.Define;
import org.firstinspires.ftc.teamcode.TeleOp.Methods.Compass;
import org.firstinspires.ftc.teamcode.TeleOp.Methods.Movement;

@TeleOp(name = "Run", group = "TeleOP")
public class Run extends Define {
    
    org.firstinspires.ftc.teamcode.TeleOp.Methods.Movement Movement = new Movement();
    org.firstinspires.ftc.teamcode.TeleOp.Methods.Compass Compass = new Compass();
    
    @Override
    public void runOpMode() {
        
        super.runOpMode();
        Movement.runOpMode();
        Compass.runOpMode();
        
        waitForStart();
        while(opModeIsActive()) {
            
            Movement.movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
			
			/*boolean flyWheel = gamepad2.right_bumper;
			boolean indegServo = gamepad2.a;
			boolean wobbleGrabber = gamepad2.y;
			boolean disengager = gamepad2.b;
			boolean ringScorer = gamepad2.x;*/
        
        }
    }
    
}
