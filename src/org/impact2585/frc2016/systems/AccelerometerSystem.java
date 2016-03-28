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
	
	private I2C I2Cbus;
	private byte FIFObuffer[];
	private short rawValues[];
	private byte FIFOcount[];
	private short currentFIFOcount;
	private Quaternion quaternion;
	private Vector accel;
	private Vector gravity;
	private double gyro[];


	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		I2Cbus = new I2C(I2C.Port.kOnboard, DEVICE_ADDRESS);
		SmartDashboard.putBoolean("Connection Successful: ", !I2Cbus.addressOnly());
		
		//resets the dmp
		writeBit(POWER_MANAGEMENT_REGISTER, RESET_BIT, true);
		writeBit(POWER_MANAGEMENT_REGISTER, SLEEP_BIT, false);
		I2Cbus.write(SLAVE_0_ADDRESS, 0x7F);
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_BIT, false);
		I2Cbus.write(SLAVE_0_ADDRESS, 0x68);
		writeBit(USER_CONTROL_REGISTER, MASTER_RESET_BIT, true);
		
		//sets rate to 200 hz formula is 1000 hz / (1 + byte written)
		I2Cbus.write(RATE_REGISTER, 4);
		//sets the frame sync rate
		writeBits(0x1A, 5, 3, 0x1); 
		
		//DMP config registers
		I2Cbus.write(DMP_CONFIG_REGISTER_1, 3);
		I2Cbus.write(DMP_CONFIG_REGISTER_2, 0);
		
		//clears the OTP
		writeBit(XGYRO_OFFSET_REGISTER, OTP_BIT, false);
		
		//offsets the gyro
		writeBits(XGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 220);
		writeBits(YGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 76);
		writeBits(ZGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, -85);
		
		//sets the motion detection threshold
		I2Cbus.write(MOTION_THRESHOLD_REGISTER, 2);
		I2Cbus.write(ZERO_MOTION_THRESHOLD_REGISER, 156);
		I2Cbus.write(MOTION_DETECTION_DURATION_REGISTER, 80);
		
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
		FIFObuffer = new byte[64];
		FIFOcount = new byte[2];
		//sets the gyro and accelerometer ranges
		I2Cbus.write(0x1B, 0);
		I2Cbus.write(0x1C, 0);
		currentFIFOcount = 0;
		accel = new Vector();
		gravity = new Vector();
		gyro = new double[3];
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//wait for the FIFO count to reach the dmp packet size
		currentFIFOcount = getFIFOcount();
		if (currentFIFOcount >= DMP_PACKET_SIZE) {
			I2Cbus.read(0x74, DMP_PACKET_SIZE, FIFObuffer);
			quaternionValues();
			gravity();
			accelerationValues();
			gyroValues();
		}
	}
	
	/**
	 * @returns the size of the FIFO buffer
	 */
	public short getFIFOcount() {
		I2Cbus.read(0x72, 2, FIFOcount);
		return (short)(((short)FIFOcount[1] << 8) | FIFOcount[0]);
	}
	
	/**writes a bit to the slave register through I2C
	 * @param register the register to write the bit to
	 * @param position the bit to write to (0-7)
	 * @param bit turn the bit on or off
	 */
	public void writeBit(int register, int position, boolean bit) {
		byte changedByte[] = new byte[8];
		I2Cbus.read(register, 1, changedByte);
		if(bit) {
			changedByte[changedByte.length - 1 - position] |= 1;
		} else {
			changedByte[changedByte.length -1 - position] &= 0;
		}
		int bytetowrite = byteArrayToInt(changedByte);
		I2Cbus.write(register, bytetowrite);
	}
	
	/**writes more than one bit through I2C
	 * @param register the register on the slave to write to
	 * @param position the starting bit on the register for the data to start writing to
	 * @param length the amount of bits the data writes to
	 * @param data the data to write to
	 */
	public void writeBits(int register, int position, int length, int data) {
		byte registerdata[] = new byte[8];
		I2Cbus.read(register, 1, registerdata);
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
		I2Cbus.write(register, bytetowrite);
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
			rawValues[i] = (short)((FIFObuffer[i * 4] << 8) | FIFObuffer[i+1]);
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
		accel.x = FIFObuffer[28] << 8 | FIFObuffer[29];
		accel.y = FIFObuffer[32] << 8 | FIFObuffer[33];
		accel.z = FIFObuffer[36] << 8 | FIFObuffer[37];
		
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
		I2Cbus.free();
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
