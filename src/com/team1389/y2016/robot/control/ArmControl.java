package com.team1389.y2016.robot.control;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.CommandsUtil;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.util.control.PositionControllerControlCommand;
import com.team1389.base.util.control.RampingSetpointProvider;
import com.team1389.base.wpiWrappers.PositionController;
import com.team1389.y2016.robot.control.ArmPositions.ArmPos;

public class ArmControl{
	//problem: position controller ramp command currently resets position on start, need to make a better one.
	PositionController elevation;
	PositionController turntable;
	Command setPid;
	

	public ArmControl(PositionController elevation, PositionController turntable, PIDConstants pid) {
		this.elevation = elevation;
		this.turntable = turntable;
		this.setPid = Command.create(() -> {
			elevation.setPID(pid);
			turntable.setPID(pid);
			return true;
		});
	}
	
	public Command getTeleopControlCommand(InputDevice joystick){
		RampingSetpointProvider turnSetpoint = new RampingSetpointProvider(ArmPositions.TURNTABLE_MIN,
				ArmPositions.TURNTABLE_MAX, ArmPositions.TURNTABLE_MAX_SPEED, turntable.getPosition());
		Command turnTableControl = new PositionControllerControlCommand(turnSetpoint, turntable);

		RampingSetpointProvider elevSetpoint = new RampingSetpointProvider(ArmPositions.ELEVATION_MIN,
				ArmPositions.ELEVATION_MAX, ArmPositions.ELEVATION_MAX_SPEED, elevation.getPosition());
		Command elevationControl = new PositionControllerControlCommand(elevSetpoint, elevation);
		
		Command input = new JoystickArmInput(joystick, elevSetpoint, turnSetpoint);
		
		Command all = CommandsUtil.combineSimultaneous(input, turnTableControl, elevationControl);
		return CommandsUtil.combineSequential(setPid, all);
	}
	
	class JoystickArmInput extends Command{
		InputDevice joystick;
		RampingSetpointProvider elevation, turntable;
		
		public JoystickArmInput(InputDevice joystick, RampingSetpointProvider elevation, RampingSetpointProvider turntable) {
			this.joystick = joystick;
			this.elevation = elevation;
			this.turntable = turntable;
		}

		@Override
		public boolean execute() {
//			ArmPos position = ArmPositions.getPosFromJoystick(joystick);
			double elev = joystick.getAxis(1).read() / 5;
			ArmPos position = new ArmPos(elev, 0);
			if (position != null){
				elevation.setGoalPoint(position.elevation);
				turntable.setGoalPoint(position.turntable);
			}
			return false;
		}
		
	}
	
}
