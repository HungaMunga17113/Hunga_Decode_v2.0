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
@Autonomous(name = "Align Blue Auton")
public class Align_Blue_Auton extends LinearOpMode {

    @Override
    public void runOpMode() {
        //Pose that the robot starts at
        Pose2d initialPose = new Pose2d(-55, -46, Math.toRadians(236));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Outtake outtake = new Outtake(hardwareMap);



        //-----------------------Paths-----------------------\\
        Action shoot1path = drive.actionBuilder(initialPose)
                .stopAndAdd(outtake.shoot())
                .strafeToLinearHeading(new Vector2d(-25.5,-25.5), Math.toRadians(226.25))
                .waitSeconds(3.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake1path = drive.actionBuilder(new Pose2d(-25.5, -25.5, Math.toRadians(226.25)))
                .strafeToLinearHeading(new Vector2d(-10,-16.25),Math.toRadians(273))
                .strafeToConstantHeading(new Vector2d(-9,-55),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                //.strafeToConstantHeading(new Vector2d(-11.5,-40))
                //.strafeToLinearHeading(new Vector2d(-3,-59.75),Math.toRadians(180))
                //.waitSeconds(0.3)
                .build();

        Action shoot2path = drive.actionBuilder(new Pose2d(-9, -55, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(-25,-25),Math.toRadians(229))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake2path = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(229)))
                .strafeToLinearHeading(new Vector2d(17,-15.5),Math.toRadians(273))
                .strafeToConstantHeading(new Vector2d(16,-62),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                .lineToYLinearHeading(-43, Math.toRadians(229))
                .build();

        Action shoot3path = drive.actionBuilder(new Pose2d(16, -43, Math.toRadians(229)))
                .strafeToLinearHeading(new Vector2d(-25,-25),Math.toRadians(229))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .build();

        Action intake3path = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(229)))
                .strafeToLinearHeading(new Vector2d(36.5,-15.5),Math.toRadians(273))
                .strafeToConstantHeading(new Vector2d(35.5,-62),new TranslationalVelConstraint(55))
                .stopAndAdd(intake.idle())
                .build();

        Action shoot4path = drive.actionBuilder(new Pose2d(35.5, -62, Math.toRadians(270)))
                //.strafeToConstantHeading(new Vector2d(31.5,50))
                .lineToYConstantHeading(-55)
                .strafeToLinearHeading(new Vector2d(-25,-25),Math.toRadians(232))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.in())
                .stopAndAdd(intake.in())
                .waitSeconds(0.8)
                .stopAndAdd(transfer.idle())
                .stopAndAdd(intake.idle())
                .stopAndAdd(outtake.idle())
                .build();

        Action extra = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(232)))
                .strafeToLinearHeading(new Vector2d(-20,-55),Math.toRadians(270))
                .build();
        Action extraTest = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(232)))
                .strafeToLinearHeading(new Vector2d(-25,-55),Math.toRadians(270))
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
                                //  outtake.shoot(),
                                //  outtake.down(),
                                //  outtake.idle()
                        ),

                        new SequentialAction(
                                //   intake.in(),
                                intake1path
                                //    intake.idle()
                        ),
                        new SequentialAction(
                                shoot2path
                                //    outtake.shoot(),
                                //    outtake.down(),
                                //    outtake.idle()
                        ),
                        new SequentialAction(
                                //       intake.in(),
                                intake2path
                                //      intake.idle()
                        ),
                        new SequentialAction(
                                shoot3path
                                //       outtake.shoot(),
                                //       outtake.down(),
                                //       outtake.idle()
                        ),
                        new SequentialAction(
                                //       intake.in(),
                                intake3path
                                //       intake.idle()
                        ),
                        new SequentialAction(
                                shoot4path
                                //       outtake.shoot(),
                                //       outtake.down(),
                                //       outtake.idle()
                        ),

                        new SequentialAction(
                                extraTest
                        )
                )
        );
    }
}