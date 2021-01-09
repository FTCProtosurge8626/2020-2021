package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Hardware.Define;
import org.firstinspires.ftc.teamcode.TeleOp.Methods.Compass;
import org.firstinspires.ftc.teamcode.TeleOp.Methods.Movement;

@TeleOp(name = "Run: Run", group = "Run")
public class Run extends Define {
    
    
    @Override
    public void runOpMode() {
        
        initHardware();
        initVariable();
        
        waitForStart();
        while(opModeIsActive()) {
            
            //Movement.runOpMode();
            //Compass.runOpMode();
            
            Movement.movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            Compass.compass(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);
			
			/*boolean flyWheel = gamepad2.right_bumper;
			boolean indegServo = gamepad2.a;
			boolean wobbleGrabber = gamepad2.y;
			boolean disengager = gamepad2.b;
			boolean ringScorer = gamepad2.x;*/
        
        }
    }
    
}
