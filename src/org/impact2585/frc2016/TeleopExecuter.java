package org.impact2585.frc2016;

import org.impact2585.lib2585.RunnableExecuter;


/**
 * Executer for teleop (user operated) mode
 */
public class TeleopExecuter extends RunnableExecuter implements Initializable {
	
	private static final long serialVersionUID = 7851578871823450486L;

	/**
	 * Doesn't initialize anything
	 */
	public TeleopExecuter(){
		
	}
	
	/**
	 * Calls init
	 * @param environment the environment to initialize with
	 */
	public TeleopExecuter(Environment environment){
		init(environment);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		getRunnables().add(environment.getWheelSystem());
		getRunnables().add(environment.getIntakeSystem());
		getRunnables().add(environment.getElectricalSystem());
		getRunnables().add(environment.getAccelerometerSystem());
		getRunnables().add(environment.getLiftSystem());
	}

}

  
