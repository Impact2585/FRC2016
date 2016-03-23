package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system gets the accelerometer and gyro values from the MPU6050
 */
public class AccelerometerSystem implements RobotSystem, Runnable{
	public static final int DEVICE_ADDRESS = 0x34;
	private I2C I2Cbus;
	private byte buffer[];
	private short rawValues[];

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		I2Cbus = new I2C(I2C.Port.kOnboard, DEVICE_ADDRESS);
		SmartDashboard.putBoolean("Connection Successful: ", !I2Cbus.addressOnly());
		
		buffer = new byte[14];
		rawValues = new short[6];
		
		//sets the gyro and accelerometer ranges
		I2Cbus.write(0x1B, 0);
		I2Cbus.write(0x1C, 0);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		I2Cbus.read(DEVICE_ADDRESS, 14, buffer);
		int j = 0;
		//raw values are ax, ay, az, gx, gy, gz
		for(int i = 0; i< rawValues.length; i++) {
			if(j == 3)
				j++;
			rawValues[i] = (short) ((((short)buffer[j * 2]) << 8) | buffer[j * 2 + 1]);
			j++;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		I2Cbus.free();
	}

}
