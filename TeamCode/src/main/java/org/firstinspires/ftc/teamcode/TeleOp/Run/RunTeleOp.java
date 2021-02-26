package org.firstinspires.ftc.teamcode.TeleOp.Run;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Run.RunAutonomous;
import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Run: TeleOp", group = "TeleOp")
public class RunTeleOp extends Define {
    
    double sign = 1;
    
    boolean clampSwitch = true;
    boolean lockSwitch = true;
    
    RunAutonomous Autonomous = new RunAutonomous();
    
    double moveTarget;
    double moveTurn;
    double moveHeading;
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        
        runWithoutEncoders();
        
        moveHeading = setHeading();
        
        waitForStart();
        
        while(opModeIsActive()) {
            
            if(ColorS.alpha() >= 400) {
                compass(true);
            }
            compass(gamepad1.dpad_up);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_button, gamepad1.left_stick_button);
            intake(gamepad1.right_trigger, gamepad1.left_trigger);
            shooter(gamepad2.right_bumper);
            wobbleArm(gamepad2.x);
            wobbleClamp(gamepad2.a);
            wobbleLock(gamepad2.dpad_left);
        }
    }
    
    private void movement(double forward, double horizontal, double rotation, boolean half, boolean inverse) {
        
        boolean movementSwitch = false;
        
        if(!inverse) {
            movementSwitch = false;
        }
        
        if(inverse && !movementSwitch && sign == -1) {
            sign = 1;
            movementSwitch = true;
        }
        
        if(inverse && !movementSwitch && sign == 1) {
            sign = -1;
            movementSwitch = true;
        }
        
        double FLPower = half ? (((forward - horizontal) + rotation)/2) * sign : ((forward - horizontal) + rotation) * sign;
        double FRPower = half ? (((forward + horizontal) - rotation)/2) * sign : ((forward + horizontal) - rotation) * sign;
        double BLPower = half ? (((forward - horizontal) - rotation)/2) * sign : ((forward - horizontal) - rotation) * sign;
        double BRPower = half ? (((forward + horizontal) + rotation)/2) * sign : ((forward + horizontal) + rotation) * sign;
		/*
		if (rotation != 0) {
		moveHeading = setHeading();
		moveTarget = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
		}
		
		if (forward != 0 || horizontal != 0)
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
		
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		*/
        //movementPower(-moveTurn, moveTurn, -moveTurn, moveTurn);
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    private void compass(boolean north) {
        if(north)
            turn = (setHeading() - target) * .02;
        while ((turn > 0.1 || turn < -0.1) && !isStopRequested())
        {
            turn = (setHeading() - target) * .02;
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0)
            {
                break;
            }
            movement(0, 0, turn,  gamepad1.right_bumper, gamepad1.left_stick_button);
        }
    }
    
    private void shooter(boolean out) {
        if(out) shootPower(.705,-.705);
        if(!out) shootPower(0,0);
    }
    
    private void intake(double in, double out) {
        Intake.setPower(in - out);
    }
    private void wobbleArm(boolean up) {
        
        ElapsedTime hold = new ElapsedTime();
        
        if(!up) {
            WobbleA.setPower(-.1);
            hold.reset();
        }
        
        if(up && hold.seconds() < 0.1) {
            WobbleL.setPosition(0.1);
            WobbleA.setPower(.5);
            sleep(300);
            WobbleA.setPower(0);
            sleep(500);
            WobbleC.setPosition(1);
            sleep(500);
            WobbleC.setPosition(0);
            sleep(500);
            WobbleA.setPower(-.5);
            sleep(300);
        }
    }
    
    private void wobbleClamp(boolean up) {
        
        if(!up) {
            clampSwitch = true;
        }
        
        if(up && clampSwitch && WobbleC.getPosition() == 0) {
            WobbleC.setPosition(1);
            clampSwitch = false;
        }
        
        if(up && clampSwitch && WobbleC.getPosition() == 1) {
            WobbleC.setPosition(0);
            clampSwitch = false;
        }
    }
    
    private void wobbleLock(boolean up) {
        
        if(!up) {
            lockSwitch = true;
        }
        
        if(up && lockSwitch && WobbleL.getPosition() < .11) {
            WobbleL.setPosition(1);
            lockSwitch = false;
        }
        
        if(up && lockSwitch && WobbleL.getPosition() == 1) {
            WobbleL.setPosition(.1);
            lockSwitch = false;
        }
    }
    
    public double setHeading() {
		/*if (Autonomous.orientation != 0)
			return heading() - Autonomous.orientation;*/
        return heading();
    }
}