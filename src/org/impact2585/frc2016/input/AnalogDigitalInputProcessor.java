package org.impact2585.frc2016.input;

/**
 * Class for processing two different types of input
 */
public class AnalogDigitalInputProcessor {

	public static final double DEADZONE = 0.15;
	/**
	 * @param button1 the first digital input
	 * @param button2 the second digital input
	 * @param analogTrigger1 the first non-reversible analog input
	 * @param analogTrigger2 the second non-reversible analog input
	 * @return 1 if button1 is true, -1 if button2 is true, the value of analogTrigger1 if analogTrigger1 has a value greater than DEADZONE, the negative value of analogTrigger2 if it has a value greater than DEADZONE, or 0 if no input or the inputs conflict
	 */
	public static double processAnalogTriggers(boolean button1, boolean button2, double analogTrigger1, double analogTrigger2) {
		if(button1 && !button2 && analogTrigger1 <= DEADZONE && analogTrigger2 <= DEADZONE) {
			return 1;
		} else if (button2 && !button1 && analogTrigger1 <= DEADZONE && analogTrigger2 <= DEADZONE) {
			return -1;
		} else if (analogTrigger1 > DEADZONE && analogTrigger2 <= DEADZONE && !button1 && !button2) {
			return analogTrigger1;
		} else if (analogTrigger2 > DEADZONE && analogTrigger1 <= DEADZONE && !button1 && !button2) {
			return -analogTrigger2;
		} else {
			return 0;
		}
	}
	
	/**
	 * @param button1 the first digital input
	 * @param button2 the second digital input
	 * @param analogJoystick the reversible analog input
	 * @return 1 if button1 is true, -1 if button2 is true, the value of analogJoystick if it has a value greater than the deadzone, or 0 if no input or the inputs conflict
	 */
	public static double processAnalogJoyStick(boolean button1, boolean button2, double analogJoystick) {
		if(button1 && !button2 && Math.abs(analogJoystick) <= DEADZONE) {
			return 1;
		} else if(button2 && !button1 && Math.abs(analogJoystick) <= DEADZONE) {
			return -1;
		} else if(Math.abs(analogJoystick) > DEADZONE && !button1 && !button2) {
			return analogJoystick;
		} else {
			return 0;
		}
	}
	
	/**
	 * @param analogTrigger1 the first non-reversible analog input
	 * @param analogTrigger2 the second non-reversible analog input
	 * @param analogJoystick the reversible analog input
	 * @return -analogTrigger1 if it has a value greater than DEADZONE, analogTrigger2 if it has a value greater than DEADZONE, analogJoystick if it has a value greater than DEADZONE, or 0 if no input or the inputs conflict
	 */
	public static double processDoubleAnalog(double analogTrigger1, double analogTrigger2, double analogJoystick) {
		if(analogTrigger1 > DEADZONE && analogTrigger2 <= DEADZONE && Math.abs(analogJoystick) <= DEADZONE) {
			return -analogTrigger1;
		} else if(analogTrigger1 <= DEADZONE && analogTrigger2 > DEADZONE && Math.abs(analogJoystick) <= DEADZONE) {
			return analogTrigger2;
		} else if(analogTrigger1 <= DEADZONE && analogTrigger2 <= DEADZONE && Math.abs(analogJoystick) >= DEADZONE) {
			return analogJoystick;
		} else {
			return 0;
		}
	}

}
