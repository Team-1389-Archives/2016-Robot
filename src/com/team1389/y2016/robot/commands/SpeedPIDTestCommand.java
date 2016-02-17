package com.team1389.y2016.robot.commands;

import org.strongback.command.Command;
import org.strongback.components.ui.InputDevice;

import com.team1389.base.util.Timer;
import com.team1389.base.util.control.ConfigurablePid.PIDConstants;
import com.team1389.base.webserver.chart.Chart;
import com.team1389.base.webserver.chart.DataPoint;
import com.team1389.base.webserver.chart.WebChartManager;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class SpeedPIDTestCommand extends Command{
	
	PIDConstants constants;
	CANTalon talon;
	InputDevice joystick;
	StringBuilder builder;
	Chart output;
	Timer timer;
	double targetSpeed;
	
	public SpeedPIDTestCommand(PIDConstants constants, CANTalon talon, InputDevice joystick, double targetSpeed) {
		this.constants = constants;
		this.talon = talon;
		this.joystick = joystick;
		this.builder = new StringBuilder();
		this.output = new Chart(.001, .1, "time", "output");
		WebChartManager.getInstance().addChart("pidTester", output);
		timer = new Timer();
		this.targetSpeed = targetSpeed;
	}
	
	@Override
	public void initialize() {
		StringBuilder builder = new StringBuilder();
		builder.append("p=");
		builder.append(constants.p);
		builder.append(" i=");
		builder.append(constants.i);
		builder.append(" d=");
		builder.append(constants.d);
		builder.append(" kv=");
		builder.append(constants.kv);
		builder.append(" ka=");
		builder.append(constants.ka);
		System.out.println(builder.toString());
		
		talon.configNominalOutputVoltage(0, 0);
		talon.configMaxOutputVoltage(12);
		
		talon.setProfile(0);//sets which pid gains to use
		talon.setP(constants.p);
		talon.setI(constants.i);
		talon.setD(constants.d);
		talon.setF(constants.kv);
		
		timer.zero();
	}

	@Override
	public boolean execute() {
		/* get gamepad axis */
    	double leftYstick = joystick.getAxis(1).read();
    	double motorOutput = talon.getOutputVoltage() / talon.getBusVoltage();
    	/* prepare line to print */
		builder.append("\tout:");
		builder.append(motorOutput);
        builder.append("\tspd:");
        builder.append(talon.getSpeed() );
        
        if(joystick.getButton(1).isTriggered()){
        	/* Speed mode */
        	double targetSpeed = leftYstick * this.targetSpeed; /* 1500 RPM in either direction */
        	talon.changeControlMode(TalonControlMode.Speed);
        	talon.set(targetSpeed); /* 1500 RPM in either direction */

        	/* append more signals to print when in speed mode. */
            builder.append("\terr:");
            builder.append(talon.getClosedLoopError());
            builder.append("\ttrg:");
            builder.append(targetSpeed);
            
            output.addPoint(new DataPoint(timer.get(), talon.getClosedLoopError()));
        } else {
        	/* Percent voltage mode */
        	talon.changeControlMode(TalonControlMode.PercentVbus);
        	talon.set(leftYstick);
        	
        	builder.append("\tpvb=");
        	builder.append(leftYstick);
        }

        System.out.println(builder.toString());

        builder.setLength(0);
		
		return false;
	}

}
