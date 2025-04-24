package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.util.Units;

public class Constants {
    public static final int CONTROLLER_PORT = 0;

    public static class GameConstants {
        public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2024Crescendo);
    }

    public static class DrivetrainConstants {
        public static final int FRONT_LEFT_ID = 6;
        public static final int BACK_LEFT_ID = 7;
        public static final int FRONT_RIGHT_ID = 8;
        public static final int BACK_RIGHT_ID = 9;
        public static final int FRONT_LEFT_STALL_CURRENT_LIMIT = 40;
        public static final int FRONT_LEFT_FREE_CURRENT_LIMIT = 60;
        public static final int BACK_LEFT_STALL_CURRENT_LIMIT = 40;
        public static final int BACK_LEFT_FREE_CURRENT_LIMIT = 60;
        public static final int FRONT_RIGHT_STALL_CURRENT_LIMIT = 40;
        public static final int FRONT_RIGHT_FREE_CURRENT_LIMIT = 60;
        public static final int BACK_RIGHT_STALL_CURRENT_LIMIT = 40;
        public static final int BACK_RIGHT_FREE_CURRENT_LIMIT = 60;
        public static final boolean FRONT_LEFT_INVERTED = false;
        public static final boolean FRONT_RIGHT_INVERTED = true;
        public static final double MASS = Units.lbsToKilograms(125);
        public static final double MOI = 3.0;
        public static final double GEARBOX_RATIO = 1.0 / 8.46;
        public static final double WHEEL_RADIUS = Units.inchesToMeters(3);
        public static final double WHEEL_BASE = Units.inchesToMeters(21.496063);
        public static final double WHEEL_COF = 1.0;
        public static final double TRACK_WIDTH = Units.inchesToMeters(20.875);
        public static final double ENCODER_POSITION_FACTOR = (2 * Math.PI * WHEEL_RADIUS) * GEARBOX_RATIO;
        public static final double ENCODER_VELOCITY_FACTOR = ENCODER_POSITION_FACTOR / 60.0;
        public static final double MAX_DRIVE_VELOCITY = 2.0;
    }

    public static class ShooterConstants {
        public static final int LAUNCH_WHEEL_ID = 3;
        public static final int FEED_WHEEL_ID = 2;
        public static final int BEAM_BREAK_CHANNEL = 0;
        public static final int LAUNCH_WHEEL_STALL_CURRENT_LIMIT = 80;
        public static final int LAUNCH_WHEEL_FREE_CURRENT_LIMIT = 80;
        public static final int FEED_WHEEL_STALL_CURRENT_LIMIT = 80;
        public static final int FEED_WHEEL_FREE_CURRENT_LIMIT = 80;
        public static final double LAUNCH_WHEEL_INTAKE_SPEED = -1.0;
        public static final double FEED_WHEEL_INTAKE_SPEED = -0.2;
        public static final double LAUNCH_WHEEL_LAUNCH_SPEED = 1.0;
        public static final double FEED_WHEEL_LAUNCH_SPEED = 1.0;
        public static final boolean LAUNCH_WHEEL_INVERTED = false;
        public static final boolean FEED_WHEEL_INVERTED = false;

    }
}