package org.firstinspires.ftc.teamcode.Roadrunner.roadrunner_tutorial;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Roadrunner.roadrunner_tutorial.base_subsystem_templates.MecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.subsystems.Intake;
import org.firstinspires.ftc.teamcode.Roadrunner.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Roadrunner.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Roadrunner.subsystems.Servo1;

@Config
@Autonomous(name = "Align Red Auton")
public class Align_Red_Auton extends LinearOpMode {

    @Override
    public void runOpMode() {
        //Pose that the robot starts at
        Pose2d initialPose = new Pose2d(-55, 46, Math.toRadians(127));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);
        Servo1 servo = new Servo1(hardwareMap);




        //-----------------------Paths-----------------------\\
        Action shoot1path = drive.actionBuilder(initialPose)
                .stopAndAdd(servo.toPos2())
                .stopAndAdd(outtake.shoot())
                .strafeToLinearHeading(new Vector2d(-21,21), Math.toRadians(131))
                .waitSeconds(2.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake1path = drive.actionBuilder(new Pose2d(-21, 21, Math.toRadians(131)))
                .strafeToLinearHeading(new Vector2d(-10,16.25),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-9,55),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                .build();

        Action shoot2path = drive.actionBuilder(new Pose2d(-9, 55, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-21,21),Math.toRadians(131))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake2path = drive.actionBuilder(new Pose2d(-21, 21, Math.toRadians(131)))
                .strafeToLinearHeading(new Vector2d(16.25,15.5),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(15.25,62),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                .lineToYConstantHeading(37)
                .build();

        Action shoot3path = drive.actionBuilder(new Pose2d(15.25, 37, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-21,21),Math.toRadians(131))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake3path = drive.actionBuilder(new Pose2d(-21, 21, Math.toRadians(131)))
                .strafeToLinearHeading(new Vector2d(36.5,15.5),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(35.5,62),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                .build();

        Action shoot4path = drive.actionBuilder(new Pose2d(35.5, 62, Math.toRadians(90)))
                //.strafeToConstantHeading(new Vector2d(31.5,50))
                .lineToYConstantHeading(55)
                .strafeToLinearHeading(new Vector2d(-21,21),Math.toRadians(131))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .stopAndAdd(intake.idle())
                .stopAndAdd(outtake.idle())
                .build();

        Action extra = drive.actionBuilder(new Pose2d(-21, 21, Math.toRadians(131)))
                .strafeToLinearHeading(new Vector2d(-20,55),Math.toRadians(90))
                .build();

        waitForStart();

        if (isStopRequested()) return;

        //Run (What happens when you press start)
        Actions.runBlocking(
                new SequentialAction(
                        shoot1path,
                        intake1path,
                        shoot2path,
                        intake2path,
                        shoot3path,
                        intake3path,
                        shoot4path,
                        extra

                )
        );
    }
}