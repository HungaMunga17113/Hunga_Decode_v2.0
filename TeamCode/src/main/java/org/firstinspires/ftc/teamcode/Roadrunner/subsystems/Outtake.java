package org.firstinspires.ftc.teamcode.Roadrunner.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Outtake {
    public final DcMotorEx leftOuttake,rightOuttake;
    public static double ticksPerSecond = 1175;
    //420,30
    //42,14.915
    //0.002,32,600,1165
    public static PIDFCoefficients coeffs = new PIDFCoefficients(600, 0, 0.002, 32);


    public Outtake(HardwareMap hardwareMap) {
        leftOuttake = hardwareMap.get(DcMotorEx.class, "leftOuttake");
        rightOuttake = hardwareMap.get(DcMotorEx.class, "rightOuttake");
        leftOuttake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightOuttake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftOuttake.setDirection(DcMotorSimple.Direction.REVERSE);
        rightOuttake.setDirection(DcMotorSimple.Direction.FORWARD);
        leftOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightOuttake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    //-----------------------------Out--------------------------------------\\
    public class OuttakeOut implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
            rightOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
            leftOuttake.setVelocity(ticksPerSecond);
            rightOuttake.setVelocity(ticksPerSecond);
            return false;
        }
    }
    public Action shoot() {
        return new OuttakeOut();
    }
    // ----------------------------Idle-------------------------------------\\
    public class OuttakeIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
            rightOuttake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coeffs);
            leftOuttake.setVelocity(0);
            rightOuttake.setVelocity(0);
            return false;
        }
    }
    public Action idle() {
        return new OuttakeIdle();
    }

}


