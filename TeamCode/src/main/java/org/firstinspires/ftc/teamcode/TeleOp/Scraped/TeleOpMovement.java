package org.firstinspires.ftc.teamcode.TeleOp.Scraped;

import org.firstinspires.ftc.teamcode.Hardware.Scraped.Define;

//@TeleOp(name = "Run: TeleOpMovement", group = "TeleOp")
public class TeleOpMovement extends Define {
    
    int sign = 1;
    
    double moveTarget;
    double moveTurn;
    double moveHeading;
    
    boolean movementSwitch;
    
    public void runOpMode(){
    }
    
    public void movement(double forward, double horizontal, double rotation, boolean inverse, boolean half) {
        
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
        
        double FLPower = half ? (((forward - horizontal) + rotation)/2) * sign : ((forward - horizontal) + rotation) * sign;
        double FRPower = half ? (((forward + horizontal) - rotation)/2) * sign : ((forward + horizontal) - rotation) * sign;
        double BLPower = half ? (((forward - horizontal) - rotation)/2) * sign : ((forward - horizontal) - rotation) * sign;
        double BRPower = half ? (((forward + horizontal) + rotation)/2) * sign : ((forward + horizontal) + rotation) * sign;
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
		
		while ((moveTurn > .06 || moveTurn < -.06) && !isStopRequested())
		{
		telemetry.addLine().addData("Heading: ", setHeading());
		telemetry.addLine().addData("MoveTurn: ", moveTurn);
		telemetry.addLine().addData("MoveTarget: ", moveTarget);
		telemetry.update();
		moveHeading = setHeading();
		moveTurn = (moveHeading - moveTarget) * .02;
			moveTurn = (setHeading() - moveTarget) * .02;
			if (gamepad1.right_stick_x != 0)
			{
			moveTarget = setHeading();
				//moveTurn = 0;
				break;
			}
			movement(0, 0, moveTurn,  gamepad1.right_bumper, gamepad1.left_stick_button);
		}*/
        
        movementPower(FLPower, FRPower, BLPower, BRPower);
    }
    
    public double setHeading() {
		/*if (Autonomous.orientation != 0)
			return heading() - Autonomous.orientation;*/
        return heading();
    }
}