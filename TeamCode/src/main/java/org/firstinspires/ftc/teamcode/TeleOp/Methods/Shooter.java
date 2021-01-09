package org.firstinspires.ftc.teamcode.TeleOp.Methods;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.concurrent.locks.Lock;
import org.firstinspires.ftc.teamcode.Hardware.Define;


@TeleOp(name = "Run: Shooter", group = "Run")
public class Shooter extends Define {
    
    @Override
    public void runOpMode() {
        
        super.initialize();
        
        waitForStart();
        
        while(opModeIsActive())
        {
            
            shooter(gamepad1.left_trigger, gamepad1.right_trigger, gamepad1.x);
            
        }
    }
    
    private void shooter(double in, double out, boolean lock) {
        
        //LeftShooter.setPower(in);
        //RightShooter.setPower(-out);
        
        double lockMode = lock ? -0.7 : -0.5;
        
        LockShooter.setPosition(lockMode);
        
    }
    
}
