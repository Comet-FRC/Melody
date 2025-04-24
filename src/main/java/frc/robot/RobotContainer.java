package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.commands.LEDOn;
import frc.robot.commands.LEDOff;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  private Drivetrain drivetrain = new Drivetrain();
  private Shooter shooter = new Shooter();
  private LED led = new LED();
  private XboxController controller = new XboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    drivetrain.setDefaultCommand(
        new Drive(
            drivetrain,
            () -> -controller.getLeftY(),
            () -> -controller.getRightX()));

    new JoystickButton(controller, XboxController.Button.kLeftBumper.value)
        .whileTrue(new IntakeNote(shooter));

    new JoystickButton(controller, XboxController.Button.kRightBumper.value)
        .whileTrue(
            new PrepareLaunch(shooter)
                .withTimeout(1)
                .andThen(new LaunchNote(shooter))
                .handleInterrupt(() -> shooter.stop()));

    new JoystickButton(controller, XboxController.Button.kA.value)
        .onTrue(new LEDOn(led));

    new JoystickButton(controller, XboxController.Button.kB.value)
        .onTrue(new LEDOff(led));
  }

  public Command getAutonomousCommand() {
    return new PrintCommand("No Command");
  }
}