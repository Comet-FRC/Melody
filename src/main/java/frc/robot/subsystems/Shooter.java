package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private SparkMax launchWheel;
    private SparkMax feedWheel;
    private SparkMaxConfig launchWheelConfig;
    private SparkMaxConfig feedWheelConfig;
    private DigitalInput beamBreak;

    public Shooter() {
        launchWheel = new SparkMax(ShooterConstants.LAUNCH_WHEEL_ID, MotorType.kBrushless);
        feedWheel = new SparkMax(ShooterConstants.FEED_WHEEL_ID, MotorType.kBrushless);
        beamBreak = new DigitalInput(ShooterConstants.BEAM_BREAK_CHANNEL);
        launchWheelConfig = new SparkMaxConfig();
        feedWheelConfig = new SparkMaxConfig();

        launchWheelConfig.smartCurrentLimit(ShooterConstants.LAUNCH_WHEEL_STALL_CURRENT_LIMIT, ShooterConstants.LAUNCH_WHEEL_FREE_CURRENT_LIMIT);
        launchWheelConfig.inverted(ShooterConstants.LAUNCH_WHEEL_INVERTED);
        feedWheelConfig.smartCurrentLimit(ShooterConstants.FEED_WHEEL_STALL_CURRENT_LIMIT, ShooterConstants.FEED_WHEEL_FREE_CURRENT_LIMIT);
        feedWheelConfig.inverted(ShooterConstants.FEED_WHEEL_INVERTED);

        launchWheel.configure(launchWheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        feedWheel.configure(feedWheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void spinLaunchWheel(double speed) {
        launchWheel.set(speed);
    }

    public void spinFeedWheel(double speed) {
        feedWheel.set(speed);
    }

      public Command getIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          spinFeedWheel(ShooterConstants.FEED_WHEEL_INTAKE_SPEED);
          spinLaunchWheel(ShooterConstants.LAUNCH_WHEEL_INTAKE_SPEED);
        },
        // When the command stops, stop the wheels
        () -> {});
  }

    public void stop() {
        launchWheel.set(0.0);
        feedWheel.set(0.0);
    }

    public boolean hasNote() {
        return beamBreak.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Has Note", hasNote());
    }
}