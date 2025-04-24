package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LED;

public class LEDOn extends Command {
  private final LED led;

  public LEDOn(LED led) {
    this.led = led;
    addRequirements(led);
  }

  @Override
  public void initialize() {
    led.turnOn();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}