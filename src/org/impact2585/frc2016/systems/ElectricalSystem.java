package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system provides information about the robot to the dashboard
 */
public class ElectricalSystem implements RobotSystem, Runnable{
	private PowerDistributionPanel panel;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		panel = new PowerDistributionPanel();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		SmartDashboard.putNumber("Total Current", panel.getTotalCurrent());
		SmartDashboard.putNumber("Total Power", panel.getTotalPower());
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		panel.free();
	}
}
