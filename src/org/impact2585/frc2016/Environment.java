package org.impact2585.frc2016;

import org.impact2585.lib2585.RobotEnvironment;
import org.impact2585.frc2016.input.InputMethod;

import org.impact2585.frc2016.systems.WheelSystem;

/**
 * sets robot's systems 
 */
public class Environment extends RobotEnvironment{

	private static final long serialVersionUID = -8268997098529757749L;
	private InputMethod input;
	private WheelSystem wheels;
	
	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/**
	 * @param robot controls whether the robot is in auton or teleop mode and sets the environment
	 */
	public Environment(Robot robot) {
		super(robot);
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
		return this.wheels;
	}
	
	/**Sets wheel system
	 * @param wheelsystem
	 */
	public void setWheelSystem(WheelSystem wheelsystem) {
		this.wheels = wheelsystem;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheels.destroy();
	}
	
}
