package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Define;

public abstract class TeleOpMethods extends Define {
    
    int sign = 1;
    
    boolean movementSwitch;
    boolean intakeSwitch;
    boolean armSwitch;
    boolean clampSwitch;
    boolean lockSwitch;
    boolean modeSwitch;
    
    boolean mode;
    
    double moveTarget;
    double moveTurn;
    double moveHeading;
    
    public float[] Movement = new float[3];
    public Object[] WobbleGrabber = new Object[3];
    
    protected void controls(){
        Movement[0] = gamepad1.left_stick_y;
        Movement[1] = gamepad1.left_stick_x;
        Movement[2] = gamepad1.right_stick_x;
        
        Movement[0] = gamepad1.left_stick_y;
        Movement[1] = gamepad1.left_stick_x;
        Movement[2] = gamepad1.right_stick_x;
    }/*
	protected void movement(boolean inverse, boolean half) {
		//LeftStickY = 0;
		if(!inverse) {
			movementSwitch = true;
		}
		
		if(inverse && movementSwitch && sign == -1) {
			sign = 1;
			movementSwitch = false;
		}
		
		if(inverse && movementSwitch && sign == 1) {
			sign = -1;
			movementSwitch = false;
		}
		
		double FLPower = half ? (((Movement[0] - Movement[1]) + Movement[2])/2) * sign : ((Movement[0] - Movement[1]) + Movement[2]) * sign;
		double FRPower = half ? (((Movement[0] + Movement[1]) - Movement[2])/2) * sign : ((Movement[0] + Movement[1]) - Movement[2]) * sign;
		double BLPower = half ? (((Movement[0] - Movement[1]) - Movement[2])/2) * sign : ((Movement[0] - Movement[1]) - Movement[2]) * sign;
		double BRPower = half ? (((Movement[0] + Movement[1]) + Movement[2])/2) * sign : ((Movement[0] + Movement[1]) + Movement[2]) * sign;
		
		if (Movement[2] != 0) {
			moveTarget = setHeading();
		}
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
		
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		
		//movementPower(-moveTurn, moveTurn, moveTurn, -moveTurn);
		//movementPower(moveTurn, -moveTurn, -moveTurn, moveTurn);
		
		while ((moveTurn > .06 || moveTurn < -.06) && !isStopRequested())
		{
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
			if (gamepad1.right_stick_x != 0)
			{
				movementPower(FLPower, FRPower, BLPower, BRPower);
				moveTarget = setHeading();
				moveTurn = 0;
				break;
			}
			movementPower(moveTurn, -moveTurn, -moveTurn, moveTurn);
			movementPower(FLPower, FRPower, BLPower, BRPower);
		}
		
		movementPower(FLPower, FRPower, BLPower, BRPower);
	}*/
    
    protected void movement(double forward, double horizontal, double rotation, boolean inverse, boolean half) {
        //LeftStickY = 0;
		/*if(!inverse) {
			TeleOpDouble.hold = true;
		}
		
		if(inverse && hold) {
			if(sign == -1) sign = 1; TeleOpDouble.hold = true;
			if(sign == 1) sign = -1; TeleOpDouble.hold = true;
		}*/
        
        if(!inverse) {
            movementSwitch = true;
        }
        
        if(inverse && movementSwitch && sign == -1) {
            sign = 1;
            movementSwitch = false;
        }
        
        if(inverse && movementSwitch && sign == 1) {
            sign = -1;
            movementSwitch = false;
        }
        
        double FLPower = half ? (((-forward - horizontal) + rotation)/2) * sign : ((-forward - horizontal) + rotation) * sign;
        double FRPower = half ? (((-forward + horizontal) - rotation)/2) * sign : ((-forward + horizontal) - rotation) * sign;
        double BLPower = half ? (((-forward - horizontal) - rotation)/2) * sign : ((-forward - horizontal) - rotation) * sign;
        double BRPower = half ? (((-forward + horizontal) + rotation)/2) * sign : ((-forward + horizontal) + rotation) * sign;
		/*
		if (rotation != 0) {
			moveTarget = setHeading();
		}
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
		
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		
		//movementPower(-moveTurn, moveTurn, moveTurn, -moveTurn);
		//movementPower(moveTurn, -moveTurn, -moveTurn, moveTurn);
		
		while ((moveTurn > .35 || moveTurn < -.) && !isStopRequested())
		{
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
			if (gamepad1.right_stick_x != 0)
			{
				movementPower(FLPower, FRPower, BLPower, BRPower);
				moveTarget = setHeading();
				moveTurn = 0;
				break;
			}
			movementPower(moveTurn, -moveTurn, -moveTurn, moveTurn);
			movementPower(FLPower, FRPower, BLPower, BRPower);
		}*/
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    protected void compass(boolean north) {
        if(north) turn = (setHeading() - target) * .02;
        while ((turn > .35 || turn < -.35) && !isStopRequested())
        {
            turn = (setHeading() - target) * .02;
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x != 0)
            {
                turn = 0;
                break;
            }
            movement(0, 0, turn,  gamepad1.right_bumper, gamepad1.left_stick_button);
        }
    }
    
    protected void powerShot(boolean shoot) { //Uses encoder to align and shoot rings
        if(shoot) {
            movementPower(1,-1,1,-1);
            shootPower(.705,-.705);
            sleep(2000);
            movementPower(1,-1,1,-1);
            shootPower(0,0);
        }
    }
    
    protected void indexer(double push) {  //Pushes ring into shooter
        Indexer.setPower(push);
    }
    
    protected void shooter(boolean out) {
        if(out) shootPower(.705,-.705);
        if(!out) shootPower(0,0);
    }
    
    protected void intake(double in, boolean out) {
        
        if(!out) Intake.setPower(in);
        if(out) Intake.setPower(-1);
    }
    protected void wobbleArm(boolean up) {
        
        ElapsedTime hold = new ElapsedTime();
        
        if(!up) {
            WobbleA.setPower(-.1);
            hold.reset();
        }
        
        if(up && hold.seconds() < 0.1 && armSwitch) {
            WobbleA.setPower(.5);
            sleep(300);
            armSwitch = false;
        }
        
        if(up && hold.seconds() < 0.1 && !armSwitch) {
            WobbleA.setPower(-.5);
            sleep(300);
            armSwitch = true;
        }
    }
    
    protected void wobbleClamp(boolean up) {
        
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
    
    protected void wobbleLock(boolean up) {
        
        if(!up) {
            lockSwitch = true;
        }
        
        if(up && lockSwitch && WobbleL.getPosition() < .65) {
            WobbleL.setPosition(.6);
            lockSwitch = false;
        }
        
        if(up && lockSwitch && WobbleL.getPosition() > .4) {
            WobbleL.setPosition(.35);
            lockSwitch = false;
        }
    }
    
    public double setHeading() {
		/*if (Autonomous.orientation != 0)
			return heading() - Autonomous.orientation;*/
        return heading();
    }
}