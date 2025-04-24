package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LED;

public class LEDOff extends Command {
  private final LED led;

  public LEDOff(LED led) {
    this.led = led;
    addRequirements(led);
  }

  @Override
  public void initialize() {
    led.turnOff();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}