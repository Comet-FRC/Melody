package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {
    private Drivetrain drivetrain;
    private DoubleSupplier forward;
    private DoubleSupplier rotation;

    public Drive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier rotation) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.rotation = rotation;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.drive(forward.getAsDouble() * 0.5, rotation.getAsDouble() * 0.5);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(0, 0);
    }
}
