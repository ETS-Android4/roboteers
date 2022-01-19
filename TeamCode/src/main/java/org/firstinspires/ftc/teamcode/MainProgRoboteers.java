
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "0 Roboteers main program")
public class MainProgRoboteers extends LinearOpMode {

    private DcMotor right_drive;
    private DcMotor arm_drive;
    private DcMotor left_drive;
    private Servo grab_servo;
    private DcMotor carousel_drive;
    private DigitalChannel ms_up;
    private DigitalChannel ms_dn;
    private final double dir_scaling= 0.5;
    private double pow_dif;

    //private double gamepadlsy;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        initRobot();
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.

                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.

                if (gamepad1.a) {
                    arm_drive.setTargetPosition(310);
                    arm_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //MotorIsBusy(left_drive,right_drive);
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                    arm_drive.setPower(0.5);
                    while (arm_drive.isBusy()) {
                        // nothing to do here . ..
                    }
                    arm_drive.setPower(0);
                } else if (gamepad1.b) {
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                    arm_drive.setTargetPosition(620);
                    arm_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //MotorIsBusy(left_drive,right_drive);
                    arm_drive.setPower(0.5);
                    while (arm_drive.isBusy()) {
                        // nothing to do here . ..
                    }
                    arm_drive.setPower(0);
                } else if (gamepad1.y) {
                    left_drive.setPower(0);
                    right_drive.setPower(0);
                    arm_drive.setTargetPosition(900);
                    arm_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //MotorIsBusy(left_drive,right_drive);
                    arm_drive.setPower(0.5);
                    while (arm_drive.isBusy()) {
                        // nothing to do here . ..
                    }
                    arm_drive.setPower(0);

                    //left_drive.setTargetPosition(left_drive.getCurrentPosition() + 200);
                    //right_drive.setTargetPosition(left_drive.getCurrentPosition() + 200);
                    //MotorIsBusy(left_drive,right_drive)
                }
                pow_dif = dir_scaling * gamepad1.left_stick_x;
                // Front right turn
                /*if (-gamepad1.left_stick_y>0 && gamepad1.left_stick_x>0) {
                    left_drive.setPower(-gamepad1.left_stick_y + pow_dif);
                    right_drive.setPower(-gamepad1.left_stick_y - pow_dif);
                } // Front left turn
                else if (-gamepad1.left_stick_y>0&&gamepad1.left_stick_x<0){
                    left_drive.setPower(-gamepad1.left_stick_y+pow_dif);
                    right_drive.setPower(-gamepad1.left_stick_y-pow_dif);
                } else if (-gamepad1.left_stick_y>0&&gamepad1.left_stick_x<0){
                    left_drive.setPower(-gamepad1.left_stick_y+pow_dif);
                    right_drive.setPower(-gamepad1.left_stick_y-pow_dif);
                }*/
                if (-gamepad1.right_stick_y>0){
                    left_drive.setPower((-gamepad1.left_stick_y+pow_dif));
                    right_drive.setPower((-gamepad1.left_stick_y-pow_dif));}
                    if(left_drive.getPower()<1){left_drive.setPower(1); }
                    if(right_drive.getPower()<1){right_drive.setPower(1); }
                    if(left_drive.getPower()>0){left_drive.setPower(0); }
                    if(right_drive.getPower()>0){right_drive.setPower(0); }
                else if (-gamepad1.right_stick_y<0){
                    left_drive.setPower((-gamepad1.left_stick_y-pow_dif));
                    right_drive.setPower((-gamepad1.left_stick_y+pow_dif));
                    if(left_drive.getPower()<-1){left_drive.setPower(-1); }
                    if(right_drive.getPower()<-1){right_drive.setPower(-1); }
                    if(left_drive.getPower()>0){left_drive.setPower(0); }
                    if(right_drive.getPower()>0){right_drive.setPower(0); }
                }
                else{
                    left_drive.setPower(-gamepad1.left_stick_y);
                    right_drive.setPower(-gamepad1.left_stick_y);
                }
                // Code for magnetic switch
                // If the up magnetic switch is closed (false) and the user is trying
                // to go up, don't allow to proceed further.
                ArmDrive();
                CarouselDrive();

                if (gamepad1.right_bumper) {
                    grab_servo.setPosition(0); // claw is closed
                } else if (gamepad1.left_bumper) {
                    grab_servo.setPosition(0.5); //claw is open
                }

                telemetry.addData("Left Pow", left_drive.getPower());
                telemetry.addData("Right Pow", right_drive.getPower());
                telemetry.addData("ARM Pow", arm_drive.getPower());
                telemetry.addData("Servo Pos", grab_servo.getPosition());
                telemetry.addData("Arm Pos", arm_drive.getCurrentPosition());
                //telemetry.addData("ms up", !(ms_up.getState()));
                //telemetry.addData("ms down", !(ms_dn.getState()));

                telemetry.update();
                // Use left stick to drive and right stick to turn
            }
        }
    }

    private void initRobot(){
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        arm_drive = hardwareMap.get(DcMotor.class, "arm_drive");
        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        grab_servo = hardwareMap.get(Servo.class, "grab_servo");
        carousel_drive = hardwareMap.get(DcMotor.class, "carousel_drive");
        ms_dn = hardwareMap.get(DigitalChannel.class, "ms_dn");
        ms_up = hardwareMap.get(DigitalChannel.class, "ms_up");

        left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        carousel_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        //arm_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        // we want to set the 0 to ms_dn
        //if (ms_dn.getState()) {
        //    arm_drive.setPower(-0.9);
        }
        //while(ms_dn.getState());

        //arm_drive.setPower(0);
        //sleep(1000);
        //arm_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //arm_drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    private void ArmDrive(){
        arm_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (!(ms_up.getState())&&(gamepad1.right_stick_y<0)) {
            arm_drive.setPower(0);
            return;
            // Same case as above but this time the down magnetic switch is close
            // and the user is trying to move further down. Don't allow that.
        } else if (!(ms_dn.getState())&&(gamepad1.right_stick_y>0)) {
            arm_drive.setPower(0);
            return;
        } else {
            arm_drive.setPower(-gamepad1.right_stick_y);
            return;
        }
    }

    private void CarouselDrive(){
        if(gamepad1.left_trigger > 0){
            carousel_drive.setPower(-1);
        }
        else{
            carousel_drive.setPower(0);
        }
        if(gamepad1.right_trigger > 0){
            carousel_drive.setPower(1);
        }
        else {
            carousel_drive.setPower(0);
        }
    }
}