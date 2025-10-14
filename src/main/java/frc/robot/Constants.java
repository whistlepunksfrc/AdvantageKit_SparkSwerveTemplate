// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

public final class Constants {

  // Shooter Constants
  public static final class ShooterConstants {

    public static final int kShooterCANID = 14;
    public static final double kOuttakeSpeed = 0.6;
    public static final double kBackspinSpeed = 0.4;
    public static final int kAutoShootWaitTime = 3;
  }

  public static final class MotorLimits {
    public static final int kCim = 40;
    public static final int kNeo = 80;
    // public static final int kNeo550 = 20;
  }
  public static final class VisionConstants {
    // Camera 1 constants
    public static final String kCameraName1 = "shootcamera";
    public static final double kCamera1X = Units.inchesToMeters(20.75);  //set camera postion from center of robot.  x is forward, y is left, z is up
    public static final double kCamera1Y = Units.inchesToMeters(104.0);
    public static final double kCamera1Z = Units.inchesToMeters(24.0); //height of camera
       
    public static final double kCamera1Pitch = Units.degreesToRadians(-12.8); //degrees
    public static final double kCamera1Yaw = Units.degreesToRadians(180.0); //degrees
    public static final double kCamera1Roll = Units.degreesToRadians(0.0); //degrees
  }
public static final class OdometryStdDevs {
    public static final double kStateX = 0.05;
    public static final double kStateY = 0.05;
    public static final double kStateTheta = Units.degreesToRadians(5);

    public static final double kVisionX = 0.05;
    public static final double kVisionY = 0.05;
    public static final double kVisionTheta = Units.degreesToRadians(5);
  }
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }
}
