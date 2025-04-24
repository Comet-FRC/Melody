package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class IntakeNote extends Command {
    private Shooter shooter;

    public IntakeNote(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.spinLaunchWheel(ShooterConstants.LAUNCH_WHEEL_INTAKE_SPEED);
        shooter.spinFeedWheel(ShooterConstants.FEED_WHEEL_INTAKE_SPEED);
    }

    @Override
    public void execute() {
        shooter.spinLaunchWheel(ShooterConstants.LAUNCH_WHEEL_INTAKE_SPEED);
        shooter.spinFeedWheel(ShooterConstants.FEED_WHEEL_INTAKE_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return shooter.hasNote();
    }
}