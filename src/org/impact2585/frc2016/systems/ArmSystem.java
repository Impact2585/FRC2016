package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.RobotMap;
import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.lib2585.Toggler;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The system for the robot's arm
 */
public class ArmSystem implements RobotSystem, Runnable{
	
	private Environment env;
	private InputMethod input;
	private SpeedController topArm;
	private SpeedController bottomArm;
	public static final double BOTTOM_ARM_SPEED = 0.5;
	public static final double TOP_ARM_SPEED = 0.7;
	private boolean disableSpeedMultiplier;
	private Toggler toggler;
	private DigitalInput limitswitch;
	private Encoder topArmEncoder;
	private Encoder bottomArmEncoder;
	

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		env = environ;
		input = env.getInput();
		topArm = new Spark(RobotMap.TOP_ARM);
		bottomArm = new Spark(RobotMap.BOTTOM_ARM);
		limitswitch = new DigitalInput(RobotMap.ARM_LIMIT_SWITCH);
		topArmEncoder = new Encoder(RobotMap.TOP_ARM_ENCODER_PORT_A, RobotMap.TOP_ARM_ENCODER_PORT_B);
		bottomArmEncoder = new Encoder(RobotMap.BOTTOM_ARM_ENCODER_PORT_A, RobotMap.BOTTOM_ARM_ENCODER_PORT_B);
		disableSpeedMultiplier = false;
		toggler = new Toggler(disableSpeedMultiplier);
	}
	
	
	/**sets the top arm's motor to speed
	 * @param speed the speed to set the motor to
	 */
	public void setTopArmSpeed (double speed) {
		topArm.set(speed);
	}
	
	/**sets the bottom arm's motor to speed
	 * @param speed the speed to set the motor to
	 */
	public void setBottomArmSpeed(double speed) {
		bottomArm.set(speed);
	}
	
	/**
	 * @returns true if the limitswitch is closed, false if open
	 */
	public boolean isSwitchClosed() {
		return limitswitch.get();
	}
	
	/**sets the input method of the arm system
	 * @param newInput the input to set the system to
	 */
	protected void setInput(InputMethod newInput) {
		input = newInput;
	}
	
	/**
	 * @return the topArmEncoder
	 */
	public Encoder getTopArmEncoder() {
		return topArmEncoder;
	}


	/**
	 * @param topArmEncoder the topArmEncoder to set
	 */
	public void setTopArmEncoder(Encoder topArmEncoder) {
		this.topArmEncoder = topArmEncoder;
	}


	/**
	 * @return the bottomArmEncoder
	 */
	public Encoder getBottomArmEncoder() {
		return bottomArmEncoder;
	}


	/**
	 * @param bottomArmEncoder the bottomArmEncoder to set
	 */
	public void setBottomArmEncoder(Encoder bottomArmEncoder) {
		this.bottomArmEncoder = bottomArmEncoder;
	}

	/**
	 * @return the toggler
	 */
	protected synchronized Toggler getToggler() {
		return toggler;
	}


	/**
	 * @param toggler the toggler to set
	 */
	protected synchronized void setToggler(Toggler toggler) {
		this.toggler = toggler;
	}


	/**
	 * Puts the encoders' rate and distance to the SmartDashboard
	 */
	public void accessSmartDasboard() {
		SmartDashboard.putNumber("Top Arm Rate: ", topArmEncoder.getRate());
		SmartDashboard.putNumber("Top Arm Distance: ", topArmEncoder.getDistance());
		SmartDashboard.putNumber("Bottom Arm Rate: ", topArmEncoder.getRate());
		SmartDashboard.putNumber("Bottom Arm Distance: ", topArmEncoder.getDistance());
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		double toparmspeed = input.moveTopArm();
		double bottomarmspeed = input.moveBottomArm();
		disableSpeedMultiplier = toggler.toggle(input.toggleArmSpeed());
		
		if(!disableSpeedMultiplier) {
			toparmspeed *= TOP_ARM_SPEED;
			bottomarmspeed *= BOTTOM_ARM_SPEED;
		}
		
		if(isSwitchClosed() && bottomarmspeed > 0 && !input.ignoreArmLimitSwitch()) {
			bottomarmspeed = 0;
		}
		
		setTopArmSpeed(toparmspeed);
		setBottomArmSpeed(bottomarmspeed);
		accessSmartDasboard();
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(bottomArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) bottomArm;
			motor.free();
		}
		
		if(topArm instanceof SensorBase) {
			SensorBase motor = (SensorBase) topArm;
			motor.free();
		}
		limitswitch.free();
		topArmEncoder.free();
		bottomArmEncoder.free();
	}
	
}
