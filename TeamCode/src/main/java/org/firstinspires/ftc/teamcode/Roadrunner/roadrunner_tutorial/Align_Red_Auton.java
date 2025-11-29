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


        //-----------------------Paths-----------------------\\
        Action shoot1path = drive.actionBuilder(initialPose)
                .stopAndAdd(outtake.shoot())
                .strafeToConstantHeading(new Vector2d(-40,40))
                .stopAndAdd(intake.in())
                .stopAndAdd(transfer.in())
                .waitSeconds(0.67)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake1path = drive.actionBuilder(new Pose2d(-40, 40, Math.toRadians(127)))
                .strafeToLinearHeading(new Vector2d(-13.5,20),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(-14.5,55),new TranslationalVelConstraint(30))
                .strafeToConstantHeading(new Vector2d(-13,43))
                .strafeToLinearHeading(new Vector2d(-3.15,60.25),Math.toRadians(180))
                .stopAndAdd(intake.idle())
                .waitSeconds(0.3)
                .build();

        Action shoot2path = drive.actionBuilder(new Pose2d(-3.15, 60.25, Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(-40,40),Math.toRadians(125))
                .stopAndAdd(intake.in())
                .stopAndAdd(transfer.in())
                .waitSeconds(0.67)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake2path = drive.actionBuilder(new Pose2d(-40, 40, Math.toRadians(125)))
                .strafeToLinearHeading(new Vector2d(11.1,17),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(10.1,62),new TranslationalVelConstraint(30))
                .stopAndAdd(intake.idle())
                .lineToYSplineHeading(42.5, Math.toRadians(125))
                .build();

        Action shoot3path = drive.actionBuilder(new Pose2d(10.1, 42.5, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-40,40),Math.toRadians(125))
                .stopAndAdd(intake.in())
                .stopAndAdd(transfer.in())
                .waitSeconds(0.67)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake3path = drive.actionBuilder(new Pose2d(-40, 40, Math.toRadians(125)))
                .strafeToLinearHeading(new Vector2d(32.75,17.75),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(31.25,62),new TranslationalVelConstraint(30))
                .stopAndAdd(intake.idle())
                .build();

        Action shoot4path = drive.actionBuilder(new Pose2d(31.25, 62, Math.toRadians(90)))
                //.strafeToConstantHeading(new Vector2d(31.5,50))
                .lineToYSplineHeading(41.5, Math.toRadians(125))
                .strafeToLinearHeading(new Vector2d(-40,40),Math.toRadians(125))
                .stopAndAdd(intake.in())
                .stopAndAdd(transfer.in())
                .waitSeconds(0.67)
                .stopAndAdd(transfer.idle())
                .stopAndAdd(intake.idle())
                .stopAndAdd(outtake.idle())
                .build();

        Action extra = drive.actionBuilder(new Pose2d(-40, 40, Math.toRadians(125)))
                .strafeToLinearHeading(new Vector2d(-20,55),Math.toRadians(90))
                .build();
        Action extraTest = drive.actionBuilder(new Pose2d(-40, 40, Math.toRadians(125)))
                .strafeToLinearHeading(new Vector2d(-20,55),Math.toRadians(90))
                .build();




        // Initialize (What happens before when you press start)
        Actions.runBlocking(
                new SequentialAction(
                        //        intake.in()
                )
        );

        waitForStart();

        if (isStopRequested()) return;

        //Run (What happens when you press start)
        Actions.runBlocking(
                new SequentialAction(
                        new SequentialAction(
                                shoot1path
                        ),

                        new SequentialAction(
                                intake1path
                        ),
                        new SequentialAction(
                                shoot2path
                        ),
                        new SequentialAction(
                                intake2path
                        ),
                        new SequentialAction(
                                shoot3path
                        ),
                        new SequentialAction(
                                intake3path
                        ),
                        new SequentialAction(
                                shoot4path
                        ),

                        new SequentialAction(
                                extra
                        )
                )
        );
    }
}