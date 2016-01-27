package com.team1389.y2016.robot;

import com.team1389.base.RobotCode;
import com.team1389.base.Team1389RobotBase;

/**
 * This class is the main robot class. All of its code comes from Robot and Team1389RobotBase,
 * so this file should never need to be added to.
 * 
 *  It is the main class that gets run when the program starts on the roborio.
 */
public class HardwareMain extends Team1389RobotBase<IOLayout>{

	@Override
	public RobotCode<IOLayout> getCode() {
		return new Robot(new IOHardware());
	}
}
