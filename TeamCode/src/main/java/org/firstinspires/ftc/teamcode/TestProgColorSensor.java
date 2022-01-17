package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "usethisdrivercontrolblue_imported")
public class TestProgColorSensor extends LinearOpMode {

    private DcMotor right_drive;
    private DcMotor arm_drive;
    private DcMotor left_drive;
    private Servo grab_servo;
    private CRServo carousel_servo;
    private ColorSensor color_sensor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        arm_drive = hardwareMap.get(DcMotor.class, "arm_drive");
        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        grab_servo = hardwareMap.get(Servo.class, "grab_servo");
        carousel_servo = hardwareMap.get(CRServo.class, "carousel_servo");
        color_sensor = hardwareMap.get(ColorSensor.class, "color_sensor");

        right_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        arm_drive.setDirection(DcMotorSimple.Direction.FORWARD);

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.
                left_drive.setPower(LeftDriveCubePower());
                right_drive.setPower(RightDriveCubePower());
                arm_drive.setPower(-gamepad1.right_stick_y);
                if (gamepad1.dpad_down) {
                    // set pos so it can pick block
                    arm_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm_drive.setTargetPosition(-200);
                }
                if (gamepad1.dpad_left) {
                    // height 1 level
                }
                if (gamepad1.dpad_right) {
                    arm_drive.setTargetPosition(-800);
                }
                if (gamepad1.dpad_up) {
                    // height 3 level
                    arm_drive.setTargetPosition(-1000);
                }
                if (gamepad1.right_bumper == true) {
                    grab_servo.setPosition(0);
                } else if (gamepad1.left_bumper == true) {
                    grab_servo.setPosition(0.5);
                }
                carousel_servo.setPower(gamepad1.left_trigger);

                // convert the RGB values to HSV values.
                // multiply by the SCALE_FACTOR.
                // then cast it back to int (SCALE_FACTOR is a double)
                Color.RGBToHSV((int) (color_sensor.red() * SCALE_FACTOR),
                        (int) (color_sensor.green() * SCALE_FACTOR),
                        (int) (color_sensor.blue() * SCALE_FACTOR),
                        hsvValues);

                String color_name;

                color_name = "Unknown";

                if ((hsvValues[0]>90) && (hsvValues[0]<150  )) {
                    color_name = "Green";
                } else if ((hsvValues[0]>210) && (hsvValues[0]<270)) {
                    color_name = "Blue";
                } else if ((hsvValues[0]>330    ) || (hsvValues[0]<30)) {
                    color_name = "Red";
                }

                telemetry.addData("Alpha", color_sensor.alpha());
                telemetry.addData("Red  ", color_sensor.red());
                telemetry.addData("Green", color_sensor.green());
                telemetry.addData("Blue ", color_sensor.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("Color", color_name);


                telemetry.addData("Left Pow", left_drive.getPower());
                telemetry.addData("Right Pow", right_drive.getPower());
                telemetry.addData("ARM Pow", arm_drive.getPower());
                telemetry.addData("Servo Pos", grab_servo.getPosition());
                telemetry.addData("Arm Pos", arm_drive.getCurrentPosition());
                telemetry.update();
                // Use left stick to drive and right stick to turn
            }
        }
    }

    /**
     * Controls left drive using natural log
     */
    // TODO: Enter the correct return type for function named LeftDrivePower
    private double LeftDrivePower() {
        return gamepad1.left_stick_y - gamepad1.left_stick_x > 0 ? Math.log((gamepad1.left_stick_y - gamepad1.left_stick_x) + 1) * 0.7 : -Math.log(-(gamepad1.left_stick_y - gamepad1.left_stick_x) + 1) * 0.7;
    }

    /**
     * Controls right drive using natural log
     */
    // TODO: Enter the correct return type for function named RightDrivePower
    private double RightDrivePower() {
        return gamepad1.left_stick_y + gamepad1.left_stick_x > 0 ? Math.log(gamepad1.left_stick_y + gamepad1.left_stick_x + 1) * 0.7 : -Math.log(-(gamepad1.left_stick_y + gamepad1.left_stick_x) + 1) * 0.7;
    }
    /**
     * Controls left drive using natural log
     */
    // TODO: Enter the correct return type for function named LeftDrivePower
    private double LeftDriveCubePower() {
        return Math.pow((gamepad1.left_stick_y - gamepad1.left_stick_x),3) * 0.7;
    }

    /**
     * Controls right drive using natural log
     */
    // TODO: Enter the correct return type for function named RightDrivePower
    private double RightDriveCubePower() {
        return Math.pow((gamepad1.left_stick_y + gamepad1.left_stick_x),3) * 0.7;
    }
    /**
     * Controls left drive using natural log
     */
    // TODO: Enter the correct return type for function named LeftDrivePowerpower5
    private double LeftDrivePowerpower5() {
        return gamepad1.left_stick_y - gamepad1.left_stick_x > 0 ? (gamepad1.left_stick_y - gamepad1.left_stick_x) * (gamepad1.left_stick_y + gamepad1.left_stick_x + 0.2) : -(gamepad1.left_stick_y - gamepad1.left_stick_x) * ((gamepad1.left_stick_y + gamepad1.left_stick_x) - 0.2);
    }

    /**
     * Controls right drive using natural log
     */
    // TODO: Enter the correct return type for function named RightDrivePowerpower5
    private double RightDrivePowerpower5() {
        return gamepad1.left_stick_y + gamepad1.left_stick_x > 0 ? (gamepad1.left_stick_y + gamepad1.left_stick_x) * (gamepad1.left_stick_y + gamepad1.left_stick_x + 0.2) : -(gamepad1.left_stick_y + gamepad1.left_stick_x) * ((gamepad1.left_stick_y + gamepad1.left_stick_x) - 0.2);
    }
}
