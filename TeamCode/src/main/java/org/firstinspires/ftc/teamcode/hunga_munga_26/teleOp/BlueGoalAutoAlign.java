package org.firstinspires.ftc.teamcode.hunga_munga_26.teleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.Roadrunner.roadrunner_tutorial.base_subsystem_templates.MecanumDrive;

import java.util.concurrent.TimeUnit;

@Config
@TeleOp
public class BlueGoalAutoAlign extends OpMode {

    Deadline gamepadRateLimit = new Deadline(250, TimeUnit.MILLISECONDS);
    //Sloth
    DcMotor intake;
    DcMotor transfer;
    DcMotorEx leftOuttake, rightOuttake;
    DcMotor leftFront, leftBack, rightFront, rightBack;
    //Initialize Variables
    /*
    (Button) Initialize Period, before you press start on your program.
     */
    MecanumDrive drive;
    public static double ticksPerSecond = 1175;
    public Servo servo;
    public static double servoPos = 0.115;
    public static double transferPower = 0.95;
    public static PIDFCoefficients coeffs = new PIDFCoefficients(700, 0, 0.00367, 32);
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        transfer.setDirection(DcMotorSimple.Direction.FORWARD);

        leftOuttake = hardwareMap.get(DcMotorEx.class, "leftOuttake");
        rightOuttake = hardwareMap.get(DcMotorEx.class, "rightOuttake");

        leftOuttake.setDirection(DcMotorSimple.Direction.REVERSE);
        rightOuttake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        servo = hardwareMap.get(Servo.class, "Axon");
        drive = new MecanumDrive(hardwareMap, new Pose2d(-25, -55, Math.toRadians(270)));

    }

    @Override
    public void loop() {
        Drive();
        Pose2d currentPose = drive.localizer.getPose();
        Intake();
        Transfer();
        shootTest();
        double alignX = -72 - currentPose.position.x;
        double alignY = -72 - currentPose.position.y;
        double angle = Math.atan(alignX/alignY);
        double turnTowards =  273-Math.toDegrees(angle);
        Action align = drive.actionBuilder(currentPose)
                .turnTo(Math.toRadians(turnTowards))
                .build();
        if (gamepad1.a) {
            Actions.runBlocking(align);
        }



    }

    private void Drive() {
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));
        drive.updatePoseEstimate();


    }

    private void Intake() {
        double intakePower = 1;
        if (gamepad1.right_trigger > 0.15) {
            intake.setPower(intakePower);
        } else if (gamepad1.x) {
            intake.setPower(-intakePower);
        } else {
            intake.setPower(0);
        }
    }

    private void Transfer() {
        double transferPower = 1;
        if (gamepad1.right_bumper) {
            transfer.setPower(transferPower);
        } else if (gamepad1.y) {
            transfer.setPower(-transferPower);
        } else {
            transfer.setPower(0);
        }
    }

    public void shootTest() {
        servo.setPosition(servoPos);
        leftOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        rightOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
        if (gamepad1.x) {
            leftOuttake.setVelocity(-ticksPerSecond);
            rightOuttake.setVelocity(-ticksPerSecond);
        } else {
            leftOuttake.setVelocity(ticksPerSecond);
            rightOuttake.setVelocity(ticksPerSecond);
        }

        telemetry.addData("Target Velocity", ticksPerSecond);
        telemetry.addData("Ticks/s", ticksPerSecond);
        telemetry.addData("Left Velocity", leftOuttake.getVelocity());
        telemetry.addData("Right Velocity", rightOuttake.getVelocity());
        telemetry.addData("Error", ticksPerSecond-leftOuttake.getVelocity());
        telemetry.update();


    }

}