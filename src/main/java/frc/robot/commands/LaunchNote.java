package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.ShooterConstants;

public class LaunchNote extends Command {
    private Shooter shooter;

    public LaunchNote(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.spinLaunchWheel(ShooterConstants.LAUNCH_WHEEL_LAUNCH_SPEED);
        shooter.spinFeedWheel(ShooterConstants.FEED_WHEEL_LAUNCH_SPEED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }
}