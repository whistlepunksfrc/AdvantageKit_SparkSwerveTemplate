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

public class ShooterSubsystem extends SubsystemBase {

  // Motor configuration for the shooter subsystem
  private static SparkMax shooterMotor =
      new SparkMax(14, MotorType.kBrushed); // sets cam ID 14 and type for the shooter motor
  private static SparkMaxConfig shooterMotorConfig = new SparkMaxConfig();

  public static void configureShooterMotor() {
    shooterMotorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(80);
    shooterMotor.configure(
        shooterMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public ShooterSubsystem() {
    configureShooterMotor();
  }

  public Command outtake() {
    return Commands.runOnce(() -> shooterMotor.set(.4), this); // .6 is the speed of the outtake
  }

  public Command backspin() {
    return Commands.runOnce(() -> shooterMotor.set(-.4), this); // This spins the motor backwards
  }

  public Command stopCommand() {
    return Commands.runOnce(() -> shooterMotor.set(0), this); // stops the shooter motor
  }

  public Command autoShoot() {
    return Commands.sequence(
        Commands.runOnce(() -> shooterMotor.set(0.4), this), // Start shooting at 40% speed
        Commands.waitSeconds(1.0), // Wait for 1.0 seconds
        Commands.runOnce(() -> shooterMotor.set(0), this));
  }
}
