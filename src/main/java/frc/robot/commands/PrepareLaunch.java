package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Drivetrain;

public class PrepareLaunch extends Command {
  private Shooter launcher;

  public PrepareLaunch(Shooter launcher) {
    this.launcher = launcher;
    addRequirements(launcher);
  }

  @Override
  public void initialize() {
    launcher.spinLaunchWheel(ShooterConstants.LAUNCH_WHEEL_LAUNCH_SPEED);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}