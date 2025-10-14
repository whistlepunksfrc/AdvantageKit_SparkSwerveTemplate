package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {

  // Motor configuration for the shooter subsystem
  private static SparkMax shooterMotor =
      new SparkMax(
          ShooterConstants.kShooterCANID,
          MotorType.kBrushed); // sets cam ID 14 and type for the shooter motor
  private static SparkMaxConfig shooterMotorConfig = new SparkMaxConfig();

  public static void configureShooterMotor() {
    shooterMotorConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(
            MotorLimits.kCim); // sets the motor to brake and limits the current to 40 amps
    shooterMotor.configure(
        shooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public ShooterSubsystem() {
    configureShooterMotor();
  }

  public Command outtake() {
    return Commands.runOnce(
        () -> shooterMotor.set(ShooterConstants.kOuttakeSpeed),
        this); // .6 is the speed of the outtake
  }

  public Command backspin() {
    return Commands.runOnce(
        () -> shooterMotor.set(ShooterConstants.kBackspinSpeed * -1),
        this); // This spins the motor backwards
  }

  public Command stopCommand() {
    return Commands.runOnce(() -> shooterMotor.set(0), this); // stops the shooter motor
  }

  public Command autoShoot() {
    return Commands.sequence(
        Commands.runOnce(
            () -> shooterMotor.set(ShooterConstants.kOuttakeSpeed),
            this), // Start shooting at 40% speed
        Commands.waitSeconds(ShooterConstants.kAutoShootWaitTime), // Wait for 3 seconds
        Commands.runOnce(() -> shooterMotor.set(0), this)); // Stop the shooter motor
  }
}
