package org.impact2585.frc2016;

import org.impact2585.lib2585.RobotEnvironment;

/**
 * sets robot's systems 
 */
public class Environment extends RobotEnvironment{

	private static final long serialVersionUID = -8268997098529757749L;
	
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

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}
	
	

}
