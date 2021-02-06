package org.firstinspires.ftc.teamcode.TeleOp.Run;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import org.firstinspires.ftc.teamcode.Hardware.Define;

@TeleOp(name = "Run: TeleOp", group = "TeleOp")
public class RunTeleOp extends Define {
    
    double sign = 1;
    
    public void runOpMode() {
        
        initHardware();
        initVariables();
        
        runWithoutEncoders();
        
        target = heading();
        
        waitForStart();
        
        while(opModeIsActive()) {
			/*
			if(gamepad1.atRest()){
			if(ColorS.alpha() > 0.05) {
				compass(true);
				telemetry.addLine().addData("alpha", ColorS.alpha());
				telemetry.addLine(Boolean.toString(ColorS.alpha() > 0.05));
				telemetry.update();
			}
			telemetry.addLine().addData("alpha", ColorS.alpha());
			telemetry.addLine(Boolean.toString(ColorS.alpha() > 0.05));
			telemetry.update();
			}*/
            
            compass(gamepad1.dpad_up);
            movement(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_button, gamepad1.left_stick_button);
			/*
			shooter(gamepad1.left_trigger, gamepad1.right_trigger);
			intake(gamepad1.right_bumper, gamepad1.left_bumper);
			wobbleArm(gamepad1.a, gamepad1.b);
			wobbleClamp(gamepad1.y);
			wobbleLock(gamepad1.x);
			*/
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
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    private void compass(boolean north) {
        if (north) target = 0;
        if(north)
            turn = (heading() - target) * .02;
        while ((turn > 0.25 || turn < -0.25) && !isStopRequested())
        {
            turn = (heading() - target) * .02;
            
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0)
            {
                turn = 0;
                break;
            }
            movement(0, 0, turn,  gamepad1.right_bumper, gamepad1.left_stick_button);
        }
    }
    
    private void shooter(double in, double out) {
        shootPower(in - out,in - out);
    }
    
    private void intake(boolean in, boolean out) {
        if(in) Intake.setPower(1);
        if(out) Intake.setPower(-1);
        if(!(in || out)) Intake.setPower(0);
    }
    
    private void wobbleArm(boolean up, boolean down) {
        if(up)WobbleA.setPower(.5);
        if(down)WobbleA.setPower(-.5);
        if(!(up || down))WobbleA.setPower(0);
    }
    
    private void wobbleClamp(boolean up) {
        
        boolean clampSwitch = false;
        
        if(!up) {
            clampSwitch = false;
        }
        
        if(up && !clampSwitch && WobbleC.getPosition() == 0) {
            WobbleC.setPosition(1);
            clampSwitch = true;
        }
        
        if(up && !clampSwitch && WobbleC.getPosition() == 1) {
            WobbleC.setPosition(0);
            clampSwitch = true;
        }
    }
    
    private void wobbleLock(boolean up) {
        
        boolean lockSwitch = false;
        
        if(!up) {
            lockSwitch = false;
        }
        
        if(up && !lockSwitch && WobbleL.getPosition() == 0) {
            WobbleL.setPosition(1);
            lockSwitch = true;
        }
        
        if(up && !lockSwitch && WobbleL.getPosition() == 1) {
            WobbleL.setPosition(0);
            lockSwitch = true;
        }
    }
    
    protected void movementPower(double FLPower, double FRPower, double BLPower, double BRPower) {
        FL.setPower(FLPower);
        FR.setPower(FRPower);
        BL.setPower(BLPower);
        BR.setPower(BRPower);
    }
    
    protected void shootPower(double RightSPower, double LeftSPower) {
        LeftS.setPower(RightSPower);
        RightS.setPower(LeftSPower);
    }
    
    protected double heading() {
        
        angles = IMU.getAngularOrientation();
        return angles.firstAngle;
    }
}