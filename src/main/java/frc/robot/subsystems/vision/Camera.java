package frc.robot.subsystems.vision;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFieldLayout.OriginPosition;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants.VisionConstants;
import java.io.IOException;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

public class Camera {

  public static final Camera SHOOT_CAMERA =
      new Camera(
          VisionConstants.kCameraName1,
          new Transform3d(
              new Translation3d(
                  VisionConstants.kCamera1X, VisionConstants.kCamera1Y, VisionConstants.kCamera1Z),
              new Rotation3d(
                  VisionConstants.kCamera1Pitch,
                  VisionConstants.kCamera1Roll,
                  VisionConstants.kCamera1Yaw)));

  private final Transform3d robotToCam;

  private Pose2d previousPose = new Pose2d();

  private final PhotonPoseEstimator photonPoseEstimator;
  private PhotonCamera camera;
  private static AprilTagFieldLayout aprilTagFieldLayout =
      AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);

  public Camera(String name, Transform3d robotToCam) {
    this.robotToCam = robotToCam;
    camera = new PhotonCamera(name);

    photonPoseEstimator =
        new PhotonPoseEstimator(
            aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, robotToCam);
  }

  public Optional<EstimatedRobotPose> getEstimatedGlobalPose() {
    if (previousPose != null) {
      photonPoseEstimator.setReferencePose(previousPose);
    }
    Optional<EstimatedRobotPose> estimatedPose =
        photonPoseEstimator.update(camera.getLatestResult());
    if (estimatedPose.isPresent()) {
      previousPose = estimatedPose.get().estimatedPose.toPose2d();
    }
    return estimatedPose;
  }

  public PhotonCamera getPhotonCamera() {
    return camera;
  }

  public Transform3d getRobotToCamera() {
    return robotToCam;
  }

  private static AprilTagFieldLayout layout;

  public static AprilTagFieldLayout getFieldLayout() {
    try {
      layout =
          AprilTagFieldLayout.loadFromResource(AprilTagFields.k2025ReefscapeWelded.m_resourceFile);
      layout.setOrigin(OriginPosition.kBlueAllianceWallRightSide);

    } catch (IOException e) {
      DriverStation.reportError("Failed to load AprilTagFieldLayout", e.getStackTrace());
      layout = null;
    }

    return layout;
  }
}
