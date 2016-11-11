package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.lib2585.Drivetrain;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 * Drivetrain for the robot
 */
public class WheelSystem implements RobotSystem, Runnable{
	
	private RobotDrive drivebase;
	public static final double DEADZONE = 0.15;
	public static final double RAMP = 0.6;
	public static final double PRIMARY_ROTATION_EXPONENT = 1;
	public static final double SECONDARY_ROTATION_EXPONENT = 0.5;
	private InputMethod input;
	private PIDSubsystem forwardPIDSystem;
	private double timeDrivingForward;
	private double distanceDrivenForward;
	private AccelerometerSystem accel;
	private Drivetrain drivetrain;
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		drivebase = new RobotDrive(new Victor(RobotMap.FRONT_LEFT_DRIVE), new Victor(RobotMap.REAR_LEFT_DRIVE), new Victor(RobotMap.FRONT_RIGHT_DRIVE), new Victor(RobotMap.REAR_RIGHT_DRIVE));
		drivetrain = new Drivetrain(DEADZONE, RAMP, PRIMARY_ROTATION_EXPONENT, SECONDARY_ROTATION_EXPONENT, true, drivebase);
		input = environ.getInput();
		accel = environ.getAccelerometerSystem();
		forwardPIDSystem = new PIDSubsystem(0.45, 0.375, 0) {

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return distanceDrivenForward;
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#usePIDOutput(double)
			 */
			@Override
			protected void usePIDOutput(double output) {
				drive(output, 0);
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
			 */
			@Override
			protected void initDefaultCommand() {
			
			}};
	}
	
	/**Sets new input method
	 * @param controller the type of input method
	 */
	protected synchronized void setInput(InputMethod controller) {
		input = controller;
	}
	
	/**
	 * @return the accel
	 */
	public AccelerometerSystem getAccel() {
		return accel;
	}

	/**
	 * @param accel the accel to set
	 */
	public void setAccel(AccelerometerSystem accel) {
		this.accel = accel;
	}

	/**
	 * @returns the input method that this system is using
	 */
	public InputMethod getInput() {
		return input;
	}

	/**drives forward for distance
	 * @param distance the distance in meters to drive
	 * @return true if the drivetrain has driven the distance false if the drivetrain has not reached the distance
	 */
	public boolean driveDistanceForward(double distance) {
		if(timeDrivingForward == 0) {
			initializeForwardPID(distance);
			timeDrivingForward = System.currentTimeMillis();
			return false;
		}
		if(distanceDrivenForward < distance) {
			distanceDrivenForward += accel.getXSpeed() * (System.currentTimeMillis() - timeDrivingForward) * 1000;
			timeDrivingForward = System.currentTimeMillis();
			return false;
		} else {
			timeDrivingForward = 0;
			distanceDrivenForward = 0;
			forwardPIDSystem.getPIDController().reset();
			forwardPIDSystem.disable();
			return true; 
		}
		
	}

	/**
	 * @return the distanceDrivenForward
	 */
	public double getDistanceDrivenForward() {
		return distanceDrivenForward;
	}

	/**Sets the distance that the drivetrain has driven
	 * @param distanceDrivenForward the distanceDrivenForward to set
	 */
	public void setDistanceDrivenForward(double distanceDrivenForward) {
		this.distanceDrivenForward = distanceDrivenForward;
	}

	/**Starts the forward PID subsystem
	 * @param setpoint
	 */
	public void initializeForwardPID(double setpoint) {
		forwardPIDSystem.enable();
		forwardPIDSystem.setSetpoint(setpoint);
	}
	
	
	/** Drives forward and turns
	 * @param driveForward distance to drive forward
	 * @param rotate how much the robot will turn
	 */
	public void drive(double driveForward, double rotate) {
		drivebase.arcadeDrive(driveForward, rotate);
	}
	
	/**
	 * @return the drivetrain
	 */
	public Drivetrain getDrivetrain() {
		return drivetrain;
	}

	/**
	 * @param drivetrain the drivetrain to set
	 */
	public void setDrivetrain(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		drivetrain.arcadeControl(input.forwardMovement(), input.rotationValue(), input.invert(), input.toggleRotationExponent());
	}
	
	/**
	 * @returns the current ramp forward
	 */
	public double getCurrentRampForward() {
		return drivetrain.getCurrentRampForward();
	}
	
	/**sets the current ramp forward to rampForward
	 * @param rampForward the distance the robot is moving forward
	 */
	public void setCurrentRampForward(double rampForward) {
		drivetrain.setCurrentRampForward(rampForward);
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivebase.free();
		forwardPIDSystem.disable();
		forwardPIDSystem.getPIDController().free();
	}

}
