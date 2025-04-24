package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
        private SparkMax frontLeft;
        private SparkMax backLeft;
        private SparkMax frontRight;
        private SparkMax backRight;

        private ADXRS450_Gyro gyro;

        private DifferentialDrive drive;

        private DifferentialDriveOdometry odometry;
        private DifferentialDriveKinematics kinematics;

        private Field2d field;

        public Drivetrain() {
                frontLeft = new SparkMax(DrivetrainConstants.FRONT_LEFT_ID, MotorType.kBrushless);
                backLeft = new SparkMax(DrivetrainConstants.BACK_LEFT_ID, MotorType.kBrushless);
                frontRight = new SparkMax(DrivetrainConstants.FRONT_RIGHT_ID, MotorType.kBrushless);
                backRight = new SparkMax(DrivetrainConstants.BACK_RIGHT_ID, MotorType.kBrushless);

                SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();
                sparkMaxConfig.smartCurrentLimit(40);
                sparkMaxConfig.idleMode(IdleMode.kBrake);
                sparkMaxConfig.encoder.positionConversionFactor(DrivetrainConstants.ENCODER_POSITION_FACTOR);
                sparkMaxConfig.encoder.velocityConversionFactor(DrivetrainConstants.ENCODER_VELOCITY_FACTOR);
                frontLeft.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                sparkMaxConfig.follow(frontLeft);
                backLeft.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                sparkMaxConfig.disableFollowerMode();
                sparkMaxConfig.inverted(true);
                frontRight.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                sparkMaxConfig.follow(frontRight);
                backRight.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

                drive = new DifferentialDrive(frontLeft, frontRight);
                kinematics = new DifferentialDriveKinematics(DrivetrainConstants.TRACK_WIDTH);

                gyro = new ADXRS450_Gyro();

                odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), frontLeft.getEncoder().getPosition(),
                                frontRight.getEncoder().getPosition());

                field = new Field2d();

                SmartDashboard.putData("Field", field);

                // Load PathPlanner config
                RobotConfig config;
                try {
                        config = RobotConfig.fromGUISettings();
                } catch (Exception e) {
                        DriverStation.reportError("Failed to load PathPlanner config", e.getStackTrace());
                        return;
                }

                // Configure AutoBuilder
                AutoBuilder.configure(
                                this::getPose,
                                this::resetPose,
                                this::getRobotRelativeSpeeds,
                                this::driveRobotRelative,
                                new PPLTVController(0.02),
                                config,
                                () -> {
                                        var alliance = DriverStation.getAlliance();
                                        if (alliance.isPresent()) {
                                                return alliance.get() == DriverStation.Alliance.Red;
                                        }
                                        return false;
                                },
                                this);
        }

        public Pose2d getPose() {
                return odometry.getPoseMeters();
        }

        public void resetPose(Pose2d pose) {
                odometry.resetPosition(
                                gyro.getRotation2d(),
                                frontLeft.getEncoder().getPosition(),
                                frontRight.getEncoder().getPosition(),
                                pose);
        }

        public ChassisSpeeds getRobotRelativeSpeeds() {
                return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(
                                frontLeft.getEncoder().getVelocity(),
                                frontRight.getEncoder().getVelocity()));
        }

        public void driveRobotRelative(ChassisSpeeds chassisSpeeds) {
                DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
                frontLeft.set(wheelSpeeds.leftMetersPerSecond);
                frontRight.set(wheelSpeeds.rightMetersPerSecond);
        }

        public void drive(double forward, double rotation) {
                drive.arcadeDrive(forward, rotation);
        }

        @Override
        public void periodic() {
                odometry.update(gyro.getRotation2d(), frontLeft.getEncoder().getPosition(),
                                frontRight.getEncoder().getPosition());
                field.setRobotPose(odometry.getPoseMeters());
        }
}
