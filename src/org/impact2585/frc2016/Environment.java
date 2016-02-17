package org.impact2585.frc2016;

import org.impact2585.frc2016.input.InputMethod;
import org.impact2585.frc2016.input.XboxInput;
import org.impact2585.frc2016.systems.ArmSystem;
import org.impact2585.frc2016.systems.IntakeSystem;
import org.impact2585.frc2016.systems.WheelSystem;
import org.impact2585.lib2585.RobotEnvironment;

/**
 * sets robot's systems 
 */
public class Environment extends RobotEnvironment{

	private static final long serialVersionUID = -8268997098529757749L;
	private InputMethod input;
	private WheelSystem wheels;
	private ArmSystem arm;
	private IntakeSystem intake;
	
	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/** Constructor that takes a robot argument
	 * @param robot controls whether the robot is in auton or teleop mode and sets the environment
	 */
	public Environment(Robot robot) {
		super(robot);
		wheels = new WheelSystem();
		input = new XboxInput();
		wheels.init(this);
		arm = new ArmSystem();
		arm.init(this);
		intake = new IntakeSystem();
		intake.init(this);
	}
	
	/**
	 * @returns inputMethod
	 */
	public InputMethod getInput(){
		return this.input;
	}
	
	/**
	 * @returns the wheel system
	 */
	public WheelSystem getWheelSystem() {
		return wheels;
	}
	
	/**Sets wheel system
	 * @param wheelsystem the drivetrain
	 */
	public void setWheelSystem(WheelSystem wheelsystem) {
		wheels = wheelsystem;
	}
	
	/**
	 * @returns the arm system
	 */
	public ArmSystem getArmSystem() {
		return arm;
	}
	
	/**Sets arm system
	 * @param armsystem the arm
	 */
	public void setArmSystem(ArmSystem armsystem) {
		arm = armsystem;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheels.destroy();
		arm.destroy();
		intake.destroy();
	}
	
}
