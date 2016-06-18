package org.impact2585.frc2016.tests;


import org.impact2585.frc2016.input.AnalogDigitalInputProcessor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the AnalogDigitalInputProcessor
 */
public class AnalogDigitalInputProcessorTest {
	private boolean button1;
	private boolean button2;
	private double trigger1;
	private double trigger2;
	private double joystick;

	/**
	 * run the unit test
	 */
	@Test
	public void test() {
		
		//test basic digital input for digitalAndJoystickInput and digitalAndAnalogJoystickInput
		button1 = true;
		Assert.assertTrue(digitalAndJoystickInput() == 1 && digitalAndAnalogTriggerInput() == 1 && analogJoystickAndTriggerInput() == 0);
		
		//test to see if the the processor returns 0 if both digital inputs are true
		button2 = true;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == 0 && digitalAndJoystickInput() == 0 && analogJoystickAndTriggerInput() == 0);
		
		//test to see if the processor returns -1 if only the second button is pressed
		button1 = false;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == -1 && digitalAndJoystickInput() == -1 && analogJoystickAndTriggerInput() == 0);
		
		//test to see if the processor returns 0 if it gets digital and analog input(which it shouldn't)
		joystick = 1;
		Assert.assertTrue(digitalAndJoystickInput() == 0);
		
		//test the same thing except with the triggers
		joystick = 0;
		trigger1 = 1;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == 0);
		
		//test to see if the input works with joysticks
		button2 = false;
		joystick = 0.2;
		Assert.assertTrue(digitalAndJoystickInput() == joystick);
		
		//test to see if the processor for analog joystick and triggers returns 0 if they both give input
		trigger1 = 1;
		Assert.assertTrue(analogJoystickAndTriggerInput() == 0);
		
		//test to see if the processor for the digital input and triggers works with trigger input
		clear();
		trigger1 = 1;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == trigger1);
		
		trigger1 = 0;
		trigger2 = 1;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == -trigger2);
		
		//test deadzone
		clear();
		trigger1 = 0.14;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == 0 && analogJoystickAndTriggerInput() == 0);
		trigger1 = 0;
		trigger2 = 0.14;
		Assert.assertTrue(digitalAndAnalogTriggerInput() == 0 && analogJoystickAndTriggerInput() == 0);
		trigger2 = 0;
		joystick = -0.14;
		Assert.assertTrue(digitalAndJoystickInput() == 0 && analogJoystickAndTriggerInput() == 0);
	}
	
	/**
	 * @return the value of the processor processing two digital input and a reversible analog input(joystick)
	 */
	public double digitalAndJoystickInput() {
		return AnalogDigitalInputProcessor.processAnalogJoyStick(button1, button2, joystick);
	}
	
	/**
	 * @return the value of the processor processing two digital input and two non-reversible analog input(triggers)
	 */
	public double digitalAndAnalogTriggerInput() {
		return AnalogDigitalInputProcessor.processAnalogTriggers(button1, button2, trigger1, trigger2);
	}
	
	/**
	 * @return the value of the processor processing two non-reversible analog input(triggers) and a reversible analog input(joystick)
	 */
	public double analogJoystickAndTriggerInput() {
		return AnalogDigitalInputProcessor.processDoubleAnalog(trigger1, trigger2, joystick);
	}
	
	/**
	 * Set all digital input to false and analog input to 0
	 */
	public void clear() {
		joystick = 0;
		trigger1 = 0;
		trigger2 = 0;
		button1 = false;
		button2 = false;
	}
}
