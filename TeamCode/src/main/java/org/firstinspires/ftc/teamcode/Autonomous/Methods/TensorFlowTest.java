package org.firstinspires.ftc.teamcode.Autonomous.Methods;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "Concept: TensorFlowTest", group = "Run")
public class TensorFlowTest extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private static final String VUFORIA_KEY = "AUH3W7//////AAABmRPeTi20x0n1vDw0WNnEaB85+/2MLZ8wvrAecldHMZYx0zw8T/JNFB7k8UfpsZGqwPNVgsWRHKlPk29EFCNgAZo9e+aqmobPLwzHEr5dm1EdFPQizLMKES9UdOSIdNb/Sx2cO8oI5iURlnGtF267JIi+oqlYZawFr0ERoqA9lmlRZpE4ak83vkqYn+2iFHXJoBvxZCvOu2O6toN7tO5LhnItem0I4iFrQNw9378YiVyIP4I7nE5XtlYHKmhiBdyTXkGyvXTUUI/lzpQVxU0Z9ynL0c4t09v54i/qQ1racrZG1CrrVMLVT8m7+L8+0dUu3Zv7zLl/G3MpvnufDs2KoA5VRjta0gnmVJoUME2npfZW";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    
    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();
        
        if (tfod != null) {
            tfod.activate();
            //tfod.setZoom(2.5,1.78);
        }
        
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        String rings = "None";
        
        if (opModeIsActive()) {
            while (opModeIsActive() && rings == "None") {
                if (tfod != null) {
                    
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        }
                        telemetry.update();
                    }
                }
            }
        }
        if(opModeIsActive()){
            if(rings == "Single") {
                telemetry.addData("Single", rings);
                telemetry.update();
            } else if (rings == "Quad") {
                telemetry.addData("Quad", rings);
                telemetry.update();
            }
        }
        
        if (tfod != null) {
            tfod.shutdown();
        }
    }
    
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "RingCam");
        
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        
        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    
    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId =
            hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new
            TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.4;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters,
            vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET,
            LABEL_FIRST_ELEMENT,
            LABEL_SECOND_ELEMENT);
    }
}
