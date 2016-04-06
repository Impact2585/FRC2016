package org.impact2585.frc2016.systems;

import java.nio.ByteBuffer;

import org.impact2585.frc2016.Environment;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system gets the accelerometer and gyro values from the MPU6050
 */
public class AccelerometerSystem implements RobotSystem, Runnable{
	public static final int DEVICE_ADDRESS = 0x34;
	public static final short DMP_PACKET_SIZE = 42;
	public static final int RESET_BIT = 7;
	public static final int SLEEP_BIT = 6;
	public static final int SLAVE_0_ADDRESS =  0x25;
	public static final int POWER_MANAGEMENT_REGISTER = 0x6B;
	public static final int USER_CONTROL_REGISTER = 0x6A;
	public static final int USER_CONTROL_BIT = 5;
	public static final int USER_CONTROL_RESET_BIT = 1;
	public static final int USER_CONTROL_DMP_ENABLED_BIT = 7;
	public static final int USER_CONTROL_FIFO_ENABLED_BIT = 6;
	public static final int USER_CONTROL_DMP_RESET_BIT = 3;
	public static final int MASTER_RESET_BIT = 1;
	public static final int RATE_REGISTER = 0x19;
	public static final int DMP_CONFIG_REGISTER_1 = 0x70;
	public static final int DMP_CONFIG_REGISTER_2 = 0x71;
	public static final int XGYRO_OFFSET_REGISTER = 0x00;
	public static final int YGYRO_OFFSET_REGISTER = 0x01;
	public static final int ZGYRO_OFFSET_REGISTER = 0x02;
	public static final int OTP_BIT = 0;
	public static final int OFFSET_BIT = 6;
	public static final int MOTION_THRESHOLD_REGISTER = 0x1F;
	public static final int ZERO_MOTION_THRESHOLD_REGISER = 0x21;
	public static final int MOTION_DETECTION_DURATION_REGISTER = 0x20;
	
	private I2C i2cBus;
	private byte fifoBuffer[];
	private short rawValues[];
	private byte fifoCount[];
	private short currentFIFOcount;
	private Quaternion quaternion;
	private Vector accel;
	private Vector gravity;
	private double gyro[];
	private long prevTime;
	private double deltaTime;
	
	private double prevXVelocity;
	private double prevYVelocity;
	private double prevZVelocity;
	
	private double xVelocity;
	private double yVelocity;
	private double zVelocity;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		i2cBus = new I2C(I2C.Port.kOnboard, DEVICE_ADDRESS);
		SmartDashboard.putBoolean("Connection Successful: ", !i2cBus.addressOnly());
		
		//resets the dmp
		writeBit(POWER_MANAGEMENT_REGISTER, RESET_BIT, true);
		writeBit(POWER_MANAGEMENT_REGISTER, SLEEP_BIT, false);
		i2cBus.write(SLAVE_0_ADDRESS, 0x7F);
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_BIT, false);
		i2cBus.write(SLAVE_0_ADDRESS, 0x68);
		writeBit(USER_CONTROL_REGISTER, MASTER_RESET_BIT, true);
		
		//sets rate to 200 hz formula is 1000 hz / (1 + byte written)
		i2cBus.write(RATE_REGISTER, 4);
		//sets the frame sync rate
		writeBits(0x1A, 5, 3, 0x1); 
		
		//DMP config registers
		i2cBus.write(DMP_CONFIG_REGISTER_1, 3);
		i2cBus.write(DMP_CONFIG_REGISTER_2, 0);
		
		//clears the OTP
		writeBit(XGYRO_OFFSET_REGISTER, OTP_BIT, false);
		
		//offsets the gyro
		writeBits(XGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 220);
		writeBits(YGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 76);
		writeBits(ZGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, -85);
		
		//sets the motion detection threshold
		i2cBus.write(MOTION_THRESHOLD_REGISTER, 2);
		i2cBus.write(ZERO_MOTION_THRESHOLD_REGISER, 156);
		i2cBus.write(MOTION_DETECTION_DURATION_REGISTER, 80);
		
		//resets the FIFO buffer
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_RESET_BIT, true);
		//enables the FIFO buffer
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_FIFO_ENABLED_BIT, true);
		//enables the DMP
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_DMP_ENABLED_BIT, true);
		//resets the DMP
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_DMP_RESET_BIT, true);
		quaternion = new Quaternion();
		rawValues = new short[6];
		fifoBuffer = new byte[64];
		fifoCount = new byte[2];
		//sets the gyro and accelerometer ranges
		i2cBus.write(0x1B, 0);
		i2cBus.write(0x1C, 0);
		currentFIFOcount = 0;
		accel = new Vector();
		gravity = new Vector();
		gyro = new double[3];
		prevTime = System.currentTimeMillis();
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//wait for the FIFO count to reach the dmp packet size
		currentFIFOcount = getFIFOcount();
		if (currentFIFOcount >= DMP_PACKET_SIZE) {
			readRawValues();
			accessSmartDashboard();
			deltaTime = (System.currentTimeMillis() - prevTime)/1000.0;
			prevTime = System.currentTimeMillis();
			xVelocity = prevXVelocity + getXAccel() * deltaTime;
			yVelocity = prevYVelocity + getYAccel() * deltaTime;
			zVelocity = prevZVelocity + getZAccel() * deltaTime;
			prevXVelocity = xVelocity;
			prevYVelocity = yVelocity;
			prevZVelocity = zVelocity;
		}
	}
	
	/**
	 * Resets the velocities
	 */
	public void reset() {
		prevXVelocity = 0;
		prevYVelocity = 0;
		prevZVelocity = 0;
		zVelocity = 0;
		yVelocity = 0;
		xVelocity = 0;
	}
	
	/**Sets the previous time
	 * @param time the new time to set prevtime to
	 */
	public void setPrevTime(long time) {
		prevTime = time;
	}
	
	/**
	 * @return the change in time in seconds
	 */
	public double getChangedTime() {
		return deltaTime;
	}
	
	/**
	 *Gets the raw accelerometer and gyro values from the MPU6050 
	 */
	public void readRawValues() {
		i2cBus.read(0x74, DMP_PACKET_SIZE, fifoBuffer);
		quaternionValues();
		gravity();
		accelerationValues();
		gyroValues();
	}
	
	/**
	 * Puts the processed accelerometer and gyro values to the SmartDashboard
	 */
	public void accessSmartDashboard() {
		SmartDashboard.putNumber("X axis Acceleration: ", getXAccel());
		SmartDashboard.putNumber("Y axis Acceleration: ", getYAccel());
		SmartDashboard.putNumber("Z axis Acceleration: ", getZAccel());
		
		SmartDashboard.putNumber("Yaw: ", getYaw());
		SmartDashboard.putNumber("Pitch: ", getPitch());
		SmartDashboard.putNumber("Roll: ", getRoll());
	}
	
	/**
	 * @return the x-axis gyro value or the yaw
	 */
	public double getYaw() {
		return gyro[0]*180/Math.PI;
	}
	
	/**
	 * @return the y-axis gyro value or the pitch
	 */
	public double getPitch() {
		return gyro[1]*180/Math.PI;
	}
	
	/**
	 * @return the z-axis gyro value or the roll
	 */
	public double getRoll() {
		return gyro[2]*180/Math.PI;
	}
	
	/**
	 * @return the speed in the x axis
	 */
	public double getXSpeed() {
		return xVelocity;
	}
	
	/**
	 * @return the speed in the y axis
	 */
	public double getYSpeed() {
		return yVelocity;
	}
	
	/**
	 * @return the speed in the z-axis
	 */
	public double getZSpeed() {
		return zVelocity;
	}
	
	/**
	 * @returns the acceleration in the x-axis in m/s^2
	 */
	public double getXAccel() {
		return accel.x / 8192.0 * 9.8;
	}
	
	/**
	 * @returns the acceleration in the y-axis in m/s^2
	 */
	public double getYAccel() {
		return accel.y / 8192.0 * 9.8;
	}
	
	/**
	 * @returns the acceleration in the z-axis in m/s^2
	 */
	public double getZAccel() {
		return accel.z / 8192.0 * 9.8;
	}
	
	
	
	/**
	 * @returns the size of the FIFO buffer
	 */
	public short getFIFOcount() {
		i2cBus.read(0x72, 2, fifoCount);
		return (short)(((short)fifoCount[1] << 8) | fifoCount[0]);
	}
	
	/**writes a bit to the slave register through I2C
	 * @param register the register to write the bit to
	 * @param position the bit to write to (0-7)
	 * @param bit turn the bit on or off
	 */
	public void writeBit(int register, int position, boolean bit) {
		byte changedByte[] = new byte[8];
		i2cBus.read(register, 1, changedByte);
		if(bit) {
			changedByte[changedByte.length - 1 - position] |= 1;
		} else {
			changedByte[changedByte.length -1 - position] &= 0;
		}
		int bytetowrite = byteArrayToInt(changedByte);
		i2cBus.write(register, bytetowrite);
	}
	
	/**writes more than one bit through I2C
	 * @param register the register on the slave to write to
	 * @param position the starting bit on the register for the data to start writing to
	 * @param length the amount of bits the data writes to
	 * @param data the data to write to
	 */
	public void writeBits(int register, int position, int length, int data) {
		byte registerdata[] = new byte[8];
		i2cBus.read(register, 1, registerdata);
		int mask = (1 << length - 1) << (position - length + 1);
		
		//shifts the data to the correct position
		data <<= (position - length + 1);
		//zeros all the bits that do not need to be written
		data &= mask;
		int bytetowrite = byteArrayToInt(registerdata);
		//zeros the bits in the byte that are going to be written to
		bytetowrite &= ~mask;
		//writes the data to the byte
		bytetowrite |= data;
		i2cBus.write(register, bytetowrite);
	}
	
	/**
	 * @param initialbyte the byte array to transform into an int
	 * @return the int version of the byte array
	 */
	public int byteArrayToInt(byte initialbyte[]) {
		ByteBuffer buf = ByteBuffer.wrap(initialbyte);
		return buf.getInt();
	}
	
	/**
	 * Gets the values for the quaternion from the FIFO buffer
	 */
	public void quaternionValues() {
		for(int i = 0; i < 4; i++) {
			rawValues[i] = (short)((fifoBuffer[i * 4] << 8) | fifoBuffer[i+1]);
		}
		quaternion.w = (double)rawValues[0] / 16384;
		quaternion.x = (double)rawValues[0] / 16384;
		quaternion.y = (double)rawValues[0] / 16384;
		quaternion.z = (double)rawValues[0] / 16384;
	}
	
	/**
	 * Gets the acceleration values
	 */
	public void accelerationValues() {
		accel.x = fifoBuffer[28] << 8 | fifoBuffer[29];
		accel.y = fifoBuffer[32] << 8 | fifoBuffer[33];
		accel.z = fifoBuffer[36] << 8 | fifoBuffer[37];
		
		accel.x = accel.x - gravity.x * 8192;
		accel.y = accel.y - gravity.y * 8192;
		accel.z = accel.z - gravity.z * 8192;
	}
	
	/**
	 * gets the gravity vector
	 */
	public void gravity() {
		gravity.x = 2 * quaternion.x * quaternion.z - quaternion.w * quaternion.y;
		gravity.y = 2 * quaternion.w * quaternion.x + quaternion.y *quaternion.z;
		gravity.z = quaternion.w * quaternion.w - quaternion.x * quaternion.x - quaternion.y * quaternion.y + quaternion.z + quaternion.z;
	}
	
	/**
	 * gets the gyro values in yaw, pitch, and roll
	 */
	public void gyroValues() {
		gyro[0] = Math.atan2(2*quaternion.x*quaternion.y - 2*quaternion.w*quaternion.z, 2*quaternion.w*quaternion.w + 2*quaternion.x*quaternion.x - 1);
		gyro[1] = Math.atan(gravity.x / Math.sqrt(gravity.y * gravity.y + gravity.z * gravity.z));
		gyro[2] = Math.atan(gravity.y / Math.sqrt(gravity.x * gravity.x + gravity.z * gravity.z));
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		i2cBus.free();
	}
	
	/**
	 * A set of numbers that represent rotation, the x, y, and z components represent the axis and the w component represents the amount of rotation
	 */
	private class Quaternion {
		private double w, x, y ,z;
		
		/**
		 * Constructor that sets the magnitude of the magnitude of the quaternion to 1
		 */
		public Quaternion() {
			z = x = y = 0;
			w = 1;
		}
	}
	
	/**
	 * Not to be confused with the synchronized arraylist, this is a mathematical object that has both direction and magnitude
	 */
	private class Vector {
		private double x,y,z;
		
		/**
		 * Constructor that sets the quantities to 0
		 */
		public Vector() {
			x = y = z = 0;
		}
	}

}
